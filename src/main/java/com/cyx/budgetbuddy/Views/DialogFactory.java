package com.cyx.budgetbuddy.Views;

import com.cyx.budgetbuddy.Database.BudgetDao;
import com.cyx.budgetbuddy.Models.Transaction;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DialogFactory {

    // Logger for handling logging messages
    private static final Logger logger = Logger.getLogger(DialogFactory.class.getName());

    // Creating BudgetDao object
    static BudgetDao budgetDao;
    public static Transaction selectedTransaction;

    static {
        try {
            budgetDao = new BudgetDao();
        } catch (SQLException e) {
            logger.severe("Error in creating budgetDao object" + e);
        }
    }

    public DialogFactory() {
    }

    public static  void showDialogBudget() {
        try {
            FXMLLoader loader = new FXMLLoader(DialogFactory.class.getResource("/Fxml/Popups/budget-popup.fxml"));

            setAndShowDialog(loader);
        } catch (IOException e) {
            logger.severe("Error showing the budget dialog: " + e);
        }
    }
    

    public static void showAccountDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(DialogFactory.class.getResource("/Fxml/Popups/account-popup.fxml"));

            setAndShowDialog(loader);
        } catch (IOException e) {
            logger.severe("Error showing the account dialog: " + e);
        }
    }

    public static void showEditTransactionDialog(Transaction transaction) {
        try {
            FXMLLoader loader = new FXMLLoader(DialogFactory.class.getResource("/Fxml/Popups/new-transactions-popup.fxml"));

            selectedTransaction = transaction;

            setAndShowDialog(loader);
        } catch (IOException e) {
            logger.severe("Error showing the edit transactions dialog: " + e);
        }
    }

    public static void showAddTransactionDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(DialogFactory.class.getResource("/Fxml/Popups/transactions-popup.fxml"));

            setAndShowDialog(loader);
        } catch (IOException e) {
            logger.severe("Error showing the add transactions dialog: " + e);
        }
    }

    public static void showEditUserDetailsDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(DialogFactory.class.getResource("/Fxml/Popups/user-popup.fxml"));

            setAndShowDialog(loader);
        } catch (IOException e) {
            logger.severe("Error showing the edit user dialog: " + e);
        }
    }

    private static void setAndShowDialog(FXMLLoader loader) throws IOException {
        Parent root = loader.load();

        // Create a new stage for the dialog
        Stage dialogStage = new Stage();
        dialogStage.initOwner(ViewFactory.getStage());
        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(root));

        // Show the dialog
        dialogStage.showAndWait();
    }

    public static Transaction getSelectedTransaction() {
        return selectedTransaction;
    }

}
