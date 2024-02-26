package com.cyx.budgetbuddy.Controllers;

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

public class SplashController implements Initializable {
    private static final Logger logger = Logger.getLogger(ViewFactory.class.getName());


    // Annotating the rootPane field with @FXML to enable FXML injection
    @FXML
    private AnchorPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Starting a new SplashScreen thread when the controller is initialized
        new SplashScreen().start();
    }

    // Inner class for the splash screen functionality
    class SplashScreen extends Thread {
        @Override
        public void run() {
            try {
                // Adding a delay of 2000 milliseconds (2 seconds) before showing the main window
                Thread.sleep(2000);

                // Using Platform.runLater to update the UI on the JavaFX Application Thread
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        // Loading the new login fxml file into a new stage and showing it.
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/Views/login.fxml")));
                        } catch (IOException e) {
                            logger.severe("An error occurred: " + e.getMessage());
                            logger.severe("Stack trace: " + e);
                        }
                        Stage stage = MainStage.getInstance();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);

                        // Set the behavior for when the stage is closed
                        stage.setOnCloseRequest(event -> {
                            Platform.exit(); // Ensure the JavaFX thread is properly terminated
                        });

                        // Show the main stage
                        stage.show();

                        // Hide the current window (splash screen)
                        Stage currentStage = (Stage) rootPane.getScene().getWindow();
                        currentStage.hide();
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