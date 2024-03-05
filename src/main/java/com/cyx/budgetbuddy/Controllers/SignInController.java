package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Database.UserDao;
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
import java.util.logging.Logger;

/**
 * Controller class for the sign-in screen.
 * Handles user interactions and navigation between sign-in and login screens.
 */
public class SignInController implements Initializable {

    @FXML
    private Hyperlink logInLink;

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

    // Logger instance for logging errors
    private static final Logger logger = Logger.getLogger(SignInController.class.getName());

    /**
     * Initializes the controller.
     * Binds event handlers to buttons for login link, guest login, and sign-in actions.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set action for the login link
        logInLink.setOnAction(event -> backToLogIn());

        // Set action for the guest login button
        guestLoginButton.setOnAction(event -> {
            try {
                onGuestLogin();
            } catch (Exception e) {
                logger.severe("Error during guest login: " + e.getMessage());
                throw new RuntimeException("Error during guest login", e);
            }
        });

        // Set action for the sign-in button
        signInButton.setOnAction(event -> onSignIn());
    }

    /**
     * Handles the sign-in process.
     * Validates user input and creates a new user if the input is valid.
     * Shows appropriate messages if the user creation fails.
     */
    private void onSignIn() {
        // Retrieve user input
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Check for valid input and password match
        if (!username.isBlank() && !username.isEmpty() && (password.equals(confirmPassword))) {
            // Create a new user instance
            User user = new User(username, password);

            try {
                // Create UserDao instance to interact with the database
                UserDao userDao = new UserDao();
                // Check if the user already exists, if not, create the user
                boolean userCreated = userDao.createUserIfNotExists(user);

                // Show appropriate message based on user creation status
                if (userCreated) {
                    ViewFactory.showAppScene(user);
                } else {
                    logger.warning("Username already exists. Please choose another username.");
                }
            } catch (Exception e) {
                // Log error if user creation fails
                logger.severe("Error occurred while creating the user: " + e.getMessage());
                throw new RuntimeException("Error occurred while creating the user", e);
            }
        } else {
            logger.warning("Username or Password Field is Empty or passwords don't match!");
        }
    }

    /**
     * Handles the guest login process.
     * Creates a guest user instance and shows the app scene.
     */
    private void onGuestLogin() throws Exception {
        // Create a guest user instance
        User user = new User("Guest", "1234");
        // Show the app scene with the guest user
        ViewFactory.showAppScene(user);
    }

    /**
     * Navigates back to the login screen.
     */
    private void backToLogIn() {
        // Show the login scene
        ViewFactory.showLogInScene();
    }
}
