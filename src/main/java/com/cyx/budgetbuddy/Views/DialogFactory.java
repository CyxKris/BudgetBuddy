package com.cyx.budgetbuddy.Views;

import com.cyx.budgetbuddy.Database.BudgetDao;
import com.cyx.budgetbuddy.Models.Account;
import com.cyx.budgetbuddy.Models.Budget;
import com.cyx.budgetbuddy.Utils.DateUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Logger;

public class DialogFactory {

    // Logger for handling logging messages
    private static final Logger logger = Logger.getLogger(DialogFactory.class.getName());

    // Creating BudgetDao object
    static BudgetDao budgetDao;

    static {
        try {
            budgetDao = new BudgetDao();
        } catch (SQLException e) {
            logger.severe("Error in creating budgetDao object" + e);
        }
    }

    public DialogFactory() {
    }

    public static  void showDialogBudget(Budget budget) {
        try {
            FXMLLoader loader = new FXMLLoader(DialogFactory.class.getResource("/Fxml/Popups/budget-popup.fxml"));

            Parent root = loader.load();

            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));

            // Set controller if necessary
            // DialogController controller = loader.getController();

            // Show the dialog
            dialogStage.showAndWait();
        } catch (IOException e) {
            logger.severe("Error showing the budget dialog: " + e);
        }
    }

    public static void showBudgetDialog(Budget budget) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Edit Budget");
        dialog.initStyle(StageStyle.UNDECORATED);

        TextField budgetField = new TextField(String.valueOf(budget.getBudgetAmount()));


        // Create DatePickers and set their initial values
        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setValue(DateUtils.convertToLocalDate(budget.getStartDate()));

        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setValue(DateUtils.convertToLocalDate(budget.getEndDate()));

        Button cancelButton = new Button("Cancel");
        Button saveButton = new Button("Save");

        saveButton.setOnAction(event -> {
            try {
                budgetDao.updateBudget(AppView.getUser(), DialogFactory.getDate(startDatePicker), DialogFactory.getDate(endDatePicker), Integer.parseInt(budgetField.getText()));

            } catch (SQLException e) {
                logger.severe("Error in updating budget" + e);
            }
        });

        cancelButton.setOnAction(event -> dialog.close());

        GridPane formPane = new GridPane();
        formPane.addRow(0, new Label("Budget Amount"), budgetField);
        formPane.addRow(1, new Label("Start Date"), startDatePicker);
        formPane.addRow(2, new Label("End Date"), endDatePicker);
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setPadding(new Insets(10));

        HBox buttonBox = new HBox(10, cancelButton, saveButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        VBox root = new VBox(10, new Label("Edit Expense"), formPane, buttonBox);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #1B1B1B; -fx-text-fill: white; -fx-fill: white; -fx-background-radius: 20;");
        root.setPrefHeight(280);

        Scene scene = new Scene(root);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    public static void showAccountDialog(Account account) {
        try {
            FXMLLoader loader = new FXMLLoader(DialogFactory.class.getResource("/Fxml/Popups/account-popup.fxml"));

            Parent root = loader.load();

            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.initOwner(ViewFactory.getStage());
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));

            // Set controller if necessary
//             AccountPopupController controller = loader.getController();

            // Show the dialog
            dialogStage.showAndWait();
        } catch (IOException e) {
            logger.severe("Error showing the account dialog: " + e);
        }
    }

    private static Date getDate(DatePicker datePicker) {
        // Convert LocalDate to Date

        return Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
