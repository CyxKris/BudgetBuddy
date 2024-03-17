package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Database.UserDao;
import com.cyx.budgetbuddy.Models.User;
import com.cyx.budgetbuddy.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Controller class for the login screen.
 * Handles user interactions and navigation between login, sign-in, and guest login screens.
 */
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

    private final UserDao userDao = new UserDao();

    // Logger instance for logging errors
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    public LoginController() throws SQLException {
    }

    /**
     * Initializes the controller.
     * Binds event handlers to buttons for sign-in, guest login, and regular login actions.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Event handler for sign-in button
        signInButton.setOnAction(event -> onSignIn());

        // Event handler for guest login button
        guestLoginButton.setOnAction(event -> {
            try {
                onGuestLogin();
            } catch (Exception e) {
                logger.severe("Error during guest login: " + e.getMessage());
                throw new RuntimeException("Error during guest login", e);
            }
        });

        // Event handler for login button
        loginButton.setOnAction(event -> {
            try {
                onLogin();
            } catch (Exception e) {
                logger.severe("Error during login: " + e.getMessage());
                throw new RuntimeException("Error during login", e);
            }
        });
    }

    /**
     * Handles the login process.
     * Retrieves username and password from input fields,
     * authenticates the user against the database, and loads the main app scene if successful.
     */
    private void onLogin() throws Exception {
        // Retrieve username and password from input fields
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Check if username and password fields are not empty
        if (!username.isEmpty() && !username.isBlank() && !password.isBlank() && !password.isEmpty()) {
            // Authenticate the User
            if (userDao.authenticateUser(username, password)) {
                // Retrieve user object from database
                User user = userDao.getUserByUsername(username);
                // Show the main app scene with authenticated user
                ViewFactory.showAppScene(user);
            } else {
                logger.warning("User doesn't exist. Check your inputs or create a new User.");
            }
        } else {
            logger.warning("Username or Password Field is Empty!");
        }
    }

    /**
     * Handles the guest login process.
     * Creates a guest user instance and shows the main app scene with the guest user.
     */
    private void onGuestLogin() throws Exception {
        // Get the guest user instance and show the main app scene
        User guestUser = userDao.getUserByUsername("Guest");
        ViewFactory.showAppScene(guestUser);
    }

    /**
     * Handles the sign-in process.
     * Shows the sign-in scene for creating a new user account.
     */
    private void onSignIn() {
        // Show the sign-in scene
        ViewFactory.showSignInScene();
    }
}
