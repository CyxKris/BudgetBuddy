package com.cyx.budgetbuddy;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

/**
 * Main class responsible for initializing and starting the JavaFX application.
 */
public class App extends Application {

    private static HostServices hostServices;

    /**
     * The entry point of the JavaFX application.
     * @param stage The primary stage to display the application.
     * @throws Exception If an exception occurs during application startup.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Get the HostServices instance from the Parameters
        hostServices = getHostServices();

        // Set a system property to configure the rendering of text on the screen
        // Ensures that all text appears properly on the screen
        System.setProperty("prism.lcdtext", "false");

        // Load the Splash Screen FXML file
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/splash.fxml")));
        // Create a new scene with the loaded FXML content
        Scene scene = new Scene(root);

        // Set the title and icon of the stage
        Image icon = new Image(String.valueOf(getClass().getResource("/Images/bb-icon.png")));
        stage.getIcons().add(icon); // Add the icon to the stage

        // Remove the default title bar to customize the stage appearance
        stage.initStyle(StageStyle.UNDECORATED);

        // Set the scene to be displayed on the stage
        stage.setScene(scene);
        // Display the stage with the splash screen
        stage.show();
    }

    public static HostServices getAppHostServices() {
        return hostServices;
    }
}