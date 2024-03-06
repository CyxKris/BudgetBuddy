package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Database.BudgetDao;
import com.cyx.budgetbuddy.Models.Budget;
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
    private Label budgetAmount;

    @FXML
    private Button editBudget;


    // Logger instance for logging errors
    private static final Logger logger = Logger.getLogger(DashboardController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            BudgetDao budgetDao = new BudgetDao();

            Budget userBudget = budgetDao.getBudgetByUser(AppView.getUser());

            budgetAmount.setText("₦" + userBudget.getBudgetAmount());
            editBudget.setOnAction(event -> DialogFactory.showBudgetDialog(userBudget));
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
