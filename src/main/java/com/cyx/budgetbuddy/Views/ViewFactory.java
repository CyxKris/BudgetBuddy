package com.cyx.budgetbuddy.Views;

import com.cyx.budgetbuddy.Controllers.TransactionsViewController;
import com.cyx.budgetbuddy.Models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Utility class for managing views and scenes in the JavaFX application.
 */
public class ViewFactory {
    // The stage of the application
    // Obtain the main stage instance
    private static Stage stage = MainStage.getInstance();

    // Logger for handling logging messages
    private static final Logger logger = Logger.getLogger(ViewFactory.class.getName());

    /**
     * Sets the provided root node as the scene's root and shows the stage.
     *
     * @param root The root node to set in the scene
     */
    private static void setAndShowStage(Parent root) {

        // Create a new scene with the provided root
        Scene scene = new Scene(root);
        // Set the scene in the main stage
        stage.setScene(scene);
        // Show the stage
        stage.show();
    }

    /**
     * Loads the sign-in scene and displays it.
     */
    public static void showSignInScene() {
        Parent root = null;
        try {
            // Load the sign-in scene from FXML
            root = FXMLLoader.load(Objects.requireNonNull(ViewFactory.class.getResource("/Fxml/Views/sign-in.fxml")));
        } catch (IOException e) {
            // Log any errors that occur during loading
            logger.severe("An error occurred: " + e.getMessage());
            logger.severe("Stack trace: " + e);
        }
        // Set and show the stage with the loaded sign-in scene
        setAndShowStage(root);
    }

    /**
     * Loads the login scene and displays it.
     */
    public static void showLogInScene() {
        Parent root = null;
        try {
            // Load the login scene from FXML
            root = FXMLLoader.load(Objects.requireNonNull(ViewFactory.class.getResource("/Fxml/Views/login.fxml")));
        } catch (IOException e) {
            // Log any errors that occur during loading
            logger.severe("An error occurred: " + e.getMessage());
            logger.severe("Stack trace: " + e);
        }
        // Set and show the stage with the loaded login scene
        setAndShowStage(root);
    }

    /**
     * Shows the main application scene with the provided user.
     *
     * @param user The user to display in the application scene
     * @throws Exception if an error occurs while loading the view
     */
    public static void showAppScene(User user) throws Exception {
        // Create a new AppView with the user's username
        Parent root = new AppView(user.getUsername());
        // Set and show the stage with the AppView
        setAndShowStage(root);
    }

    /**
     * Loads the transactions view from FXML.
     *
     * @return The root node of the loaded transactions view
     */
    public static Parent loadTransactionsView() {
        Parent root = null;
        try {
            // Load the transactions view from FXML
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(ViewFactory.class.getResource("/Fxml/Views/transactions-view.fxml")));
            root = loader.load();

            // Get the controller instance
            TransactionsViewController controller = loader.getController();

            // Pass the reference of the transactionsTable to the controller
            controller.setTransactionsTable(controller.getTransactionsTable());

        } catch (IOException e) {
            // Log any errors that occur during loading
            logger.severe("An error occurred while loading transactions view: " + e.getMessage());
            logger.severe("Stack trace: " + e);
        }
        // Return the root node of the loaded transactions view
        return root;
    }

    /**
     * Loads the settings view from FXML.
     *
     * @return The root node of the loaded settings view
     */
    public static Parent loadSettingsView() {
        Parent root = null;
        try {
            // Load the settings view from FXML
            root = FXMLLoader.load(Objects.requireNonNull(ViewFactory.class.getResource("/Fxml/Views/settings-view.fxml")));
        } catch (IOException e) {
            // Log any errors that occur during loading
            logger.severe("An error occurred while loading settings view: " + e.getMessage());
            logger.severe("Stack trace: " + e);
        }
        // Return the root node of the loaded settings view
        return root;
    }

    /**
     * Loads the dashboard view from FXML.
     *
     * @return The root node of the loaded dashboard view
     */
    public static Parent loadDashboardView() {
        Parent root = null;
        try {
            // Load the dashboard view from FXML
            root = FXMLLoader.load(Objects.requireNonNull(ViewFactory.class.getResource("/Fxml/Views/dashboard-new.fxml")));
        } catch (IOException e) {
            // Log any errors that occur during loading
            logger.severe("An error occurred while loading dashboard view: " + e.getMessage());
            logger.severe("Stack trace: " + e);
        }
        // Return the root node of the loaded dashboard view
        return root;
    }

    public static Stage getStage() {
        return stage;
    }
}
