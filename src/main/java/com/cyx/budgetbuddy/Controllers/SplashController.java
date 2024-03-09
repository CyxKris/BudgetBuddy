package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Database.DatabaseSetup;
import com.cyx.budgetbuddy.Views.MainStage;
import com.cyx.budgetbuddy.Views.ViewFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Controller class for the splash screen of the application.
 */
public class SplashController implements Initializable {

    // Logger instance for logging messages and errors
    private static final Logger logger = Logger.getLogger(ViewFactory.class.getName());

    // Reference to the root pane of the splash screen
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller after its root element has been completely processed.
     * This method is called automatically after the FXML file has been loaded.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object, or null.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Starting a new SplashScreen thread when the controller is initialized
        new SplashScreen().start();
    }

    /**
     * Inner class for handling the splash screen functionality.
     */
    class SplashScreen extends Thread {
        /**
         * The entry point for the thread.
         * This method contains the logic for displaying the splash screen and transitioning to the main application window.
         */
        @Override
        public void run() {
            try {
                // Adding a delay of 2000 milliseconds (2 seconds) before showing the main window
                Thread.sleep(2000);

                // Using Platform.runLater to update the UI on the JavaFX Application Thread
                Platform.runLater(() -> {
                    try {
                        // Loading the new login fxml file into a new stage and showing it.
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/Views/login.fxml")));
                        Stage stage = MainStage.getInstance();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);

                        // Set the behavior for when the stage is closed
                        stage.setOnCloseRequest(event -> {
                            Platform.exit(); // Ensure the JavaFX thread is properly terminated
                        });

                        // Create database tables if they do not exist
                        try {
                            DatabaseSetup.createTablesIfNotExist();
                        } catch (Exception e) {
                            logger.severe("An error occurred while creating database tables: " + e.getMessage());
                        }

                        // Show the main stage
                        stage.show();

                        // Hide the current window (splash screen)
                        Stage currentStage = (Stage) rootPane.getScene().getWindow();
                        currentStage.hide();
                    } catch (IOException e) {
                        logger.severe("An error occurred: " + e.getMessage());
                        logger.severe("Stack trace: " + e);
                    }
                });
            } catch (InterruptedException e) {
                // Handling any potential InterruptedException that may occur during the thread execution
                logger.severe("An error occurred: " + e.getMessage());
                logger.severe("Stack trace: " + e);
            }
        }
    }
}