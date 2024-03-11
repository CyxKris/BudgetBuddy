package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Database.AccountDao;
import com.cyx.budgetbuddy.Models.Account;
import com.cyx.budgetbuddy.Utils.NumericTextFieldUtil;
import com.cyx.budgetbuddy.Views.AppView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class AccountPopupController implements Initializable {

    @FXML
    private TextField accountBalance;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    AccountDao accountDao = new AccountDao();

    private static final Logger logger = Logger.getLogger(AccountPopupController.class.getName());

    public AccountPopupController() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Adding the utilities to ensure only numerical values are accepted in the accountBalance text field
        NumericTextFieldUtil.addNumericValidation(accountBalance);

        Account account = null;
        try {
            account = accountDao.getAccountByUser(AppView.getUser());
        } catch (SQLException e) {
            logger.severe("Error getting user's account: " + e);
        }

        assert account != null;
        accountBalance.setText(String.valueOf(account.getBalance()));

        // Closes the dialog when the cancelButton is clicked
        cancelButton.setOnAction(this::closeDialog);

        // saves the data from the user's input and closes the dialog
        saveButton. setOnAction(event -> {
            try {
                saveData(event);
            } catch (SQLException e) {
                logger.severe("Error saving data: " + e);
            }
        });
    }

    private void saveData(ActionEvent event) throws SQLException {

        accountDao.updateAccount(AppView.getUser(), Double.parseDouble(accountBalance.getText()));

        closeDialog(event);
    }

    private void closeDialog(ActionEvent event) {
        Stage dialog = (Stage) ((Node)event.getSource()).getScene().getWindow();
        dialog.close();
    }

    public void setAccountBalance(TextField accountBalance) {
        this.accountBalance = accountBalance;
    }
}
