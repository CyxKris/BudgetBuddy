package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.App;
import com.cyx.budgetbuddy.Database.TransactionDao;
import com.cyx.budgetbuddy.Database.UserDao;
import com.cyx.budgetbuddy.Models.Transaction;
import com.cyx.budgetbuddy.Models.User;
import com.cyx.budgetbuddy.Views.AppMenu;
import com.cyx.budgetbuddy.Views.AppView;
import com.cyx.budgetbuddy.Views.DialogFactory;
import com.cyx.budgetbuddy.Views.ViewFactory;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SettingsViewController implements Initializable {

    @FXML
    private Label changeProfileButton;

    @FXML
    private Label deleteUserButton;

    @FXML
    private Label editUserButton;

    @FXML
    private Label exportDataButton;

    @FXML
    private Label sendReportButton;

    // Logger for handling logging messages
    private static final Logger logger = Logger.getLogger(SettingsViewController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeProfileButton.setOnMouseClicked(event -> {
            try {
                openFileChooser();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        exportDataButton.setOnMouseClicked(event -> {
            try {
                exportUserData();
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        editUserButton.setOnMouseClicked(event -> {
            try {
                editUser();
            } catch (SQLException e) {
                logger.severe("Error in editing user details..." + e);
            }
        });

        deleteUserButton.setOnMouseClicked(event -> showDeleteConfirmationDialog());

        sendReportButton.setOnMouseClicked(event -> mailToDeveloper());
    }

    private void editUser() throws SQLException {
        DialogFactory.showEditUserDetailsDialog();
    }

    private void exportUserData() throws SQLException, IOException {
        TransactionDao transactionDao = new TransactionDao();

        // Get all transactions from the database
        List<Transaction> transactions = transactionDao.getAllTransactions(AppView.getUser()); // Assuming TransactionDao.getAllTransactions() returns a list of all transactions

        // Show file chooser dialog to choose file location and name
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Transaction Data");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            // Write transactions to the CSV file
            try (FileWriter writer = new FileWriter(file)) {
                // Write CSV header
                writer.append("Transaction ID,User Name,Account Balance,Amount,Transaction Date,Category,Description\n");

                // Write each transaction's data
                for (Transaction transaction : transactions) {
                    writer.append(String.join(",",
                            transaction.getTransactionId().toString(),
                            transaction.getUser().getUsername(),
                            String.valueOf(transaction.getAmount()),
                            String.valueOf(transaction.getAmount()),
                            transaction.getTransactionDate().toString(),
                            transaction.getCategory(),
                            transaction.getDescription()));
                    writer.append("\n");
                }

                // Success message
                logger.info("Transaction data exported successfully to: " + file.getAbsolutePath());
            }
        }
    }

    private void openFileChooser() throws SQLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Image");

        // Set the initial directory (optional)
        // fileChooser.setInitialDirectory(new File("path/to/initial/directory"));

        // Set extension filters
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG Images (*.png)", "*.png");
        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG Images (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter jpegFilter = new FileChooser.ExtensionFilter("JPG Images (*.jpeg)", "*.jpeg");
        fileChooser.getExtensionFilters().addAll(pngFilter, jpgFilter, jpegFilter);

        // Show the file chooser dialog
        File selectedFile = fileChooser.showOpenDialog(changeProfileButton.getScene().getWindow());

        if (selectedFile != null) {
            // Load the selected image
            Image image = new Image(selectedFile.toURI().toString());
            UserDao userDao = new UserDao();
            userDao.updateUserProfileImage(AppView.getUser(), selectedFile.toURI().toString());
            AppMenu.setProfileImage(image);
        }
    }

    private void showDeleteConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(ViewFactory.getStage());

        alert.setTitle("Delete User");
        alert.setHeaderText("Are you sure you want to delete your user account?");
        alert.setContentText("This action cannot be undone.");


        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Perform the deletion operation here

                try {
                    User user = AppView.getUser();
                    UserDao userDao = new UserDao();
                    ViewFactory.showLogInScene();

                    userDao.deleteUser(user);
                } catch (SQLException e) {
                    logger.severe("Unable to delete user! " + e);
                }
            }
        });
    }

    private void mailToDeveloper() {
        String developerEmail = "cyxkris6@gmail.com";
        String subject = "Report on BudgetBuddy";

        // Encode the subject string
        String encodedSubject = URLEncoder.encode(subject, StandardCharsets.UTF_8);

        // Construct the mailto URI
        String mailtoUri = "mailto:" + developerEmail + "?subject=" + encodedSubject;

        // Get the host services from the application parameters
        HostServices hostServices = App.getAppHostServices();

        // Open the default email client with the new email draft
        hostServices.showDocument(mailtoUri);

    }
}
