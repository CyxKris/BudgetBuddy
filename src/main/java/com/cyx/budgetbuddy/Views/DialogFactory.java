package com.cyx.budgetbuddy.Views;

import com.cyx.budgetbuddy.Database.BudgetDao;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
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

    public static void showTransactionDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(DialogFactory.class.getResource("/Fxml/Popups/transactions-popup.fxml"));

            setAndShowDialog(loader);
        } catch (IOException e) {
            logger.severe("Error showing the transactions dialog: " + e);
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

        // Set controller if necessary
//             AccountPopupController controller = loader.getController();

        // Show the dialog
        dialogStage.showAndWait();
    }

    private static Date getDate(DatePicker datePicker) {
        // Convert LocalDate to Date

        return Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
