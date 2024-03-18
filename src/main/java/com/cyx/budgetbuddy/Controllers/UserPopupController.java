package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Database.UserDao;
import com.cyx.budgetbuddy.Views.AppMenu;
import com.cyx.budgetbuddy.Views.AppView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class UserPopupController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private static final Logger logger = Logger.getLogger(UserPopupController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        usernameField.setText(AppView.getUser().getUsername());

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

        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (password.equals(confirmPassword)) {
            UserDao userDao = new UserDao();
            userDao.updateUserDetails(AppView.getUser(), username, password);
            AppView.setUser(username);
            AppMenu.setUsername(username);
        }

        closeDialog(event);
    }

    private void closeDialog(ActionEvent event) {
        Stage dialog = (Stage) ((Node)event.getSource()).getScene().getWindow();
        dialog.close();
    }
}
