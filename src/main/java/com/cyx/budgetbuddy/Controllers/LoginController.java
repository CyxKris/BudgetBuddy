package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Models.User;
import com.cyx.budgetbuddy.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button guestLoginButton;

    @FXML
    private Button signInButton;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // When the signInButton is clicked, the sign in scene shows (to create new user)
        signInButton.setOnAction(event -> onSignIn());

        // When the guestLoginButton is clicked, the app view shows (with the guest instance)
        guestLoginButton.setOnAction(event -> onGuestLogin());

        // When the loginButton is clicked, the app view shows (with the user from the input fields)
        loginButton.setOnAction(event -> onLogin());
    }

    private void onLogin() {
        // Accessing the viewFactory class to show the app scene with the user
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = new User(username, password);
        ViewFactory.showAppScene(user);
    }

    private void onGuestLogin() {
        // Accessing the viewFactory class to show the app scene with a guest user instance
        User user = new User("Guest", "1234");
        ViewFactory.showAppScene(user);
    }

    private void onSignIn() {
        // Accessing the viewFactory class to show the sign in scene
        ViewFactory.showSignInScene();
    }
}
