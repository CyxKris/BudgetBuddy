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

/**
 * Utility class for creating and showing dialogs in the application.
 */
public class DialogFactory {

    // Logger for handling logging messages
    private static final Logger logger = Logger.getLogger(DialogFactory.class.getName());

    // Creating BudgetDao object
    static BudgetDao budgetDao;
    public static Transaction selectedTransaction;

    // Static block to initialize the BudgetDao object
    static {
        try {
            budgetDao = new BudgetDao();
        } catch (SQLException e) {
            logger.severe("Error in creating budgetDao object" + e);
        }
    }

    /**
     * Private constructor to prevent instantiation of the utility class.
     */
    private DialogFactory() {
    }

    /**
     * Method to show the budget dialog.
     * Loads the FXML file for the budget dialog and displays it.
     */
    public static  void showDialogBudget() {
        try {
            FXMLLoader loader = new FXMLLoader(DialogFactory.class.getResource("/Fxml/Popups/budget-popup.fxml"));
            setAndShowDialog(loader);
        } catch (IOException e) {
            logger.severe("Error showing the budget dialog: " + e);
        }
    }

    /**
     * Method to show the account dialog.
     * Loads the FXML file for the account dialog and displays it.
     */
    public static void showAccountDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(DialogFactory.class.getResource("/Fxml/Popups/account-popup.fxml"));
            setAndShowDialog(loader);
        } catch (IOException e) {
            logger.severe("Error showing the account dialog: " + e);
        }
    }

    /**
     * Method to show the edit transaction dialog.
     * Loads the FXML file for the edit transaction dialog, sets the selected transaction,
     * and displays the dialog.
     * @param transaction The transaction to be edited.
     */
    public static void showEditTransactionDialog(Transaction transaction) {
        try {
            FXMLLoader loader = new FXMLLoader(DialogFactory.class.getResource("/Fxml/Popups/new-transactions-popup.fxml"));
            selectedTransaction = transaction;
            setAndShowDialog(loader);
        } catch (IOException e) {
            logger.severe("Error showing the edit transactions dialog: " + e);
        }
    }

    /**
     * Method to show the add transaction dialog.
     * Loads the FXML file for the add transaction dialog and displays it.
     */
    public static void showAddTransactionDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(DialogFactory.class.getResource("/Fxml/Popups/transactions-popup.fxml"));
            setAndShowDialog(loader);
        } catch (IOException e) {
            logger.severe("Error showing the add transactions dialog: " + e);
        }
    }

    /**
     * Method to show the edit user details dialog.
     * Loads the FXML file for the edit user details dialog and displays it.
     */
    public static void showEditUserDetailsDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(DialogFactory.class.getResource("/Fxml/Popups/user-popup.fxml"));
            setAndShowDialog(loader);
        } catch (IOException e) {
            logger.severe("Error showing the edit user dialog: " + e);
        }
    }

    /**
     * Private method to set up and show the dialog.
     * @param loader The FXMLLoader to load the dialog content.
     * @throws IOException If an error occurs while loading the dialog content.
     */
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

    /**
     * Getter for the selected transaction.
     * @return The selected transaction.
     */
    public static Transaction getSelectedTransaction() {
        return selectedTransaction;
    }

}
