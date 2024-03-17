package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Database.UserDao;
import com.cyx.budgetbuddy.Models.User;
import com.cyx.budgetbuddy.Views.AppMenu;
import com.cyx.budgetbuddy.Views.AppView;
import com.cyx.budgetbuddy.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
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
        deleteUserButton.setOnMouseClicked(event -> showDeleteConfirmationDialog());
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
}