package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Database.AccountDao;
import com.cyx.budgetbuddy.Database.BudgetDao;
import com.cyx.budgetbuddy.Models.Account;
import com.cyx.budgetbuddy.Models.Budget;
import com.cyx.budgetbuddy.Utils.CurrencyFormatUtils;
import com.cyx.budgetbuddy.Views.AppView;
import com.cyx.budgetbuddy.Views.DialogFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class DashboardController implements Initializable {

    @FXML
    private Label accountBalance;

    @FXML
    private Label budgetAmount;

    @FXML
    private Button editBalance;

    @FXML
    private Button editBudget;


    // Logger instance for logging errors
    private static final Logger logger = Logger.getLogger(DashboardController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            // HANDLING THE BUDGET AMOUNT AND EDITING ACTION
            BudgetDao budgetDao = new BudgetDao();

            Budget userBudget = budgetDao.getBudgetByUser(AppView.getUser());

            budgetAmount.setText(CurrencyFormatUtils.formatCurrency(userBudget.getBudgetAmount()));
            editBudget.setOnAction(event -> DialogFactory.showDialogBudget(userBudget));


            // HANDLING THE ACCOUNT BALANCE AMOUNT AND EDITING ACTION
            AccountDao accountDao = new AccountDao();

            Account userAccount = accountDao.getAccountByUser(AppView.getUser());

            accountBalance.setText(CurrencyFormatUtils.formatCurrency(userAccount.getBalance()));
            editBalance.setOnAction(event -> DialogFactory.showAccountDialog(userAccount));
        } catch (SQLException e) {
            logger.severe("Error loading budgetDao");
        }

    }

    public void refreshBudgetAmount() {
        try {
            BudgetDao budgetDao = new BudgetDao();
            Budget userBudget = budgetDao.getBudgetByUser(AppView.getUser());
            budgetAmount.setText("₦" + userBudget.getBudgetAmount());
        } catch (SQLException e) {
            logger.severe("Error loading budget data");
        }
    }

}
