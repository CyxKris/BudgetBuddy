package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Database.BudgetDao;
import com.cyx.budgetbuddy.Models.Budget;
import com.cyx.budgetbuddy.Utils.DateUtils;
import com.cyx.budgetbuddy.Utils.NumericTextFieldUtil;
import com.cyx.budgetbuddy.Views.AppView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class BudgetPopupController implements Initializable {

    @FXML
    private TextField budgetAmount;

    @FXML
    private Button cancelButton;

    @FXML
    private DatePicker endDate;

    @FXML
    private Button saveButton;

    @FXML
    private DatePicker startDate;

    BudgetDao budgetDao = new BudgetDao();

    private static final Logger logger = Logger.getLogger(BudgetPopupController.class.getName());

    public BudgetPopupController() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Budget budget = null;
        try {
            budget = budgetDao.getBudgetByUser(AppView.getUser());
        } catch (SQLException e) {
            logger.severe("Error getting user's budget: " + e);
        }

        // Adding the utilities to ensure only numerical values are accepted in the budgetAmount text field
        NumericTextFieldUtil.addNumericValidation(budgetAmount);

        assert budget != null;
        budgetAmount.setText(String.valueOf(budget.getBudgetAmount()));

        startDate.setValue(DateUtils.convertToLocalDate(budget.getStartDate()));
        endDate.setValue(DateUtils.convertToLocalDate(budget.getEndDate()));


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

        java.util.Date startDateValue = Date.valueOf(startDate.getValue());
        java.util.Date endDateValue = Date.valueOf(endDate.getValue());


        budgetDao.updateBudget(AppView.getUser(), startDateValue, endDateValue, Double.parseDouble(budgetAmount.getText()));

        closeDialog(event);
    }

    private void closeDialog(ActionEvent event) {
        Stage dialog = (Stage) ((Node)event.getSource()).getScene().getWindow();
        dialog.close();
    }
}
