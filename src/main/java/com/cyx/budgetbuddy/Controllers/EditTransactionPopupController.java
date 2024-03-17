package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Database.AccountDao;
import com.cyx.budgetbuddy.Database.BudgetDao;
import com.cyx.budgetbuddy.Database.TransactionDao;
import com.cyx.budgetbuddy.Models.Account;
import com.cyx.budgetbuddy.Models.Budget;
import com.cyx.budgetbuddy.Models.Transaction;
import com.cyx.budgetbuddy.Models.User;
import com.cyx.budgetbuddy.Utils.DateUtils;
import com.cyx.budgetbuddy.Utils.NumericTextFieldUtil;
import com.cyx.budgetbuddy.Views.AppView;
import com.cyx.budgetbuddy.Views.DialogFactory;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class EditTransactionPopupController implements Initializable {

    @FXML
    private TextField transactionAmount;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    private DatePicker date;

    @FXML
    private TextField descriptionField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    private Double initialAmount;
    private String initialCategory;

    TransactionDao transactionDao = new TransactionDao();
    BudgetDao budgetDao = new BudgetDao();
    AccountDao accountDao = new AccountDao();

    // Logger for handling logging messages
    private static final Logger logger = Logger.getLogger(EditTransactionPopupController.class.getName());

    public EditTransactionPopupController() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Adding the utilities to ensure only numerical values are accepted in the transactionAmount text fieldAbo
        NumericTextFieldUtil.addNumericValidation(transactionAmount);

        categoryChoiceBox.getItems().addAll("Income", "Expense");

        // Restricting the description field to allow only 225 characters
        descriptionField.textProperty().addListener((ov, oldValue, newValue) -> {
            if (descriptionField.getText().length() > 225) {
                String s = descriptionField.getText().substring(0, 225);
                descriptionField.setText(s);
            }
        });

        populateFields();

        BooleanBinding fieldsEmpty = Bindings.createBooleanBinding(() ->   transactionAmount.getText().trim().isEmpty() ||
            descriptionField.getText().trim().isEmpty() ||
            date.getValue() == null ||
            categoryChoiceBox.getValue() == null,
            transactionAmount.textProperty(),
            descriptionField.textProperty(),
            date.valueProperty(),
            categoryChoiceBox.valueProperty()
        );

        saveButton.disableProperty().bind(fieldsEmpty);

        saveButton.setOnAction(event -> {
            try {
                saveData(event);
            } catch (SQLException e) {
                logger.severe("Error saving data: " + e);
            }
        });

        cancelButton.setOnAction(this::closeDialog);

    }

    private void populateFields() {
        Transaction transaction = DialogFactory.getSelectedTransaction();

        initialAmount = transaction.getAmount();
        initialCategory = transaction.getCategory();

        transactionAmount.setText(String.valueOf(transaction.getAmount()));
        categoryChoiceBox.setValue(transaction.getCategory());
        date.setValue(DateUtils.convertToLocalDate(transaction.getTransactionDate()));
        descriptionField.setText(transaction.getDescription());
    }


    private void saveData(ActionEvent event) throws SQLException {

        java.util.Date transactionDate = Date.valueOf(date.getValue());
        String category = categoryChoiceBox.getValue();
        String description = descriptionField.getText();
        double newTransactionAmount = Double.parseDouble(transactionAmount.getText());

        User user = AppView.getUser();
        Account userAccount = accountDao.getAccountByUser(user);
        Budget userBudget = budgetDao.getBudgetByUser(user);

        // Handle changes in account balance based on transaction category
        if (initialCategory.equals("Income")) {
            if (category.equals("Expense")) {
                // Deduct initial amount from account balance if category changed from income to expense
                userAccount.setBalance(userAccount.getBalance() - initialAmount);

                // Update the user's amount used in the budget table
                double amountDifference = newTransactionAmount - initialAmount;
                userBudget.setAmountUsed(userBudget.getAmountUsed() + amountDifference);
                budgetDao.updateBudgetAmountUsed(user, userBudget.getAmountUsed());
                budgetDao.updateBudget(user, userBudget.getStartDate(), userBudget.getEndDate(), userBudget.getBudgetAmount());
            } else if (category.equals("Income")) {
                // Add initial amount back to account balance if category remains as income
                userAccount.setBalance(userAccount.getBalance() + initialAmount);
            }
        } else if (initialCategory.equals("Expense")) {
            if (category.equals("Income")) {
                if (initialAmount > newTransactionAmount) {
                    // Subtract new amount from the account balance if income decreases
                    userAccount.setBalance(userAccount.getBalance() - newTransactionAmount);

                    // Correct the amount used in the budget table
                    // Update the user's amount used in the budget table
                    double amountDifference = initialAmount - newTransactionAmount;
                    userBudget.setAmountUsed(userBudget.getAmountUsed() - amountDifference);
                    budgetDao.updateBudgetAmountUsed(user, userBudget.getAmountUsed());

                } else if (initialAmount < newTransactionAmount) {
                    // Add initial amount back to account balance if category changed from expense to income
                    userAccount.setBalance(userAccount.getBalance() + initialAmount);

                    userBudget.setAmountUsed(userBudget.getAmountUsed() - initialAmount);
                    budgetDao.updateBudgetAmountUsed(user, userBudget.getAmountUsed());
                } else {
                    // If the two amounts are equal, do nothing
                    userAccount.setBalance(userAccount.getBalance());
                }

            } else if (category.equals("Expense")) {
                if (initialAmount > newTransactionAmount) {
                    // Adjust account balance if category remains as expense and the amount changes
                    double difference = initialAmount - newTransactionAmount;
                    userAccount.setBalance(userAccount.getBalance() + difference);

                    userBudget.setAmountUsed(userBudget.getAmountUsed() - difference);
                    budgetDao.updateBudgetAmountUsed(user, userBudget.getAmountUsed());
                }

            }
        }

        // Update the account balance in the database
        accountDao.updateAccount(user, userAccount.getBalance());
        // Update the budget amount used
        budgetDao.updateBudget(user, userBudget.getStartDate(), userBudget.getEndDate(), userBudget.getBudgetAmount());

        // Update the transaction
        transactionDao.updateTransaction(DialogFactory.getSelectedTransaction().getTransactionId(), newTransactionAmount, transactionDate, category, description);

        closeDialog(event);
    }

    private void closeDialog(ActionEvent event) {
        Stage dialog = (Stage) ((Node)event.getSource()).getScene().getWindow();
        dialog.close();
    }
}
