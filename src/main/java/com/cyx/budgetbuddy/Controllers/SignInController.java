package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Models.User;
import com.cyx.budgetbuddy.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {
    @FXML
    Hyperlink logInLink;

    @FXML
    private Button guestLoginButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button signInButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // When the login link is clicked, show the login scene
        logInLink.setOnAction(event -> backToLogIn());

        // When the guestLoginButton is clicked, show the app scene with a guest instance
        guestLoginButton.setOnAction(event -> onGuestLogin());

        // When the signInButton (createUserButton) is clicked, a new user is created and the app scene is shown with the instance of that user
        signInButton.setOnAction(event -> onSignIn());
    }

    private void onSignIn() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!username.isBlank() && !username.isEmpty() && (password.equals(confirmPassword))) {
            User user = new User(username, password);
            ViewFactory.showAppScene(user);
        }
    }

    private void onGuestLogin() {
        User user = new User("Guest", "1234");
        ViewFactory.showAppScene(user);
    }

    private void backToLogIn() {
        ViewFactory.showLogInScene();
    }
}
