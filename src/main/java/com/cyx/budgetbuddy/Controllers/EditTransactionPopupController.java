package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Database.AccountDao;
import com.cyx.budgetbuddy.Database.BudgetDao;
import com.cyx.budgetbuddy.Database.TransactionDao;
import com.cyx.budgetbuddy.Models.Budget;
import com.cyx.budgetbuddy.Models.Transaction;
import com.cyx.budgetbuddy.Models.User;
import com.cyx.budgetbuddy.Utils.NumericTextFieldUtil;
import com.cyx.budgetbuddy.Views.AppView;
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

    private Transaction transaction;

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


    private void saveData(ActionEvent event) throws SQLException {

        java.util.Date transactionDate = Date.valueOf(date.getValue());
        String category = categoryChoiceBox.getValue();

        User user = AppView.getUser();

        // Add or deduct transaction amount from account balance
        if (category.equals("Income")) {
            accountDao.updateAccount(user, accountDao.getAccountByUser(user).getBalance() + Double.parseDouble(transactionAmount.getText()));
        } else if (category.equals("Expense")) {
            accountDao.updateAccount(user, accountDao.getAccountByUser(user).getBalance() - Double.parseDouble(transactionAmount.getText()));
        }

        // Update amount used in budget
        Budget budget = budgetDao.getBudgetByUser(user);
        if (budget != null && category.equals("Expense")) {
            budget.setAmountUsed(budget.getAmountUsed() + Double.parseDouble(transactionAmount.getText()));
            budgetDao.updateBudgetAmountUsed(user, budget.getAmountUsed());
            budgetDao.updateBudget(user, budget.getStartDate(), budget.getEndDate(), budget.getBudgetAmount());
        }

        // creating the transaction
        transactionDao.updateTransaction(AppView.getUser(), Double.parseDouble(transactionAmount.getText()), transactionDate, category, descriptionField.getText());

        closeDialog(event);
    }

    private void closeDialog(ActionEvent event) {
        Stage dialog = (Stage) ((Node)event.getSource()).getScene().getWindow();
        dialog.close();
    }
}
