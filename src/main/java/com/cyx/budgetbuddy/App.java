package com.cyx.budgetbuddy;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Set a system property to configure the rendering of text on the screen
        // Makes sure that all text appears properly on the screen
        System.setProperty("prism.lcdtext", "false");

        // Loading the Splash Screen FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/splash.fxml"));
        // Setting the scene to the splash screen
        Scene scene = new Scene(root);
        // Setting the title and icon of the stage
        Image icon = new Image(String.valueOf(getClass().getResource("/Images/bb-icon.png")));

        stage.getIcons().add(icon); // Add the icon to the stage
        // Removing the default title bar
        stage.initStyle(StageStyle.UNDECORATED);
        // setting the stage shown to the splash scene
        stage.setScene(scene);
        stage.show();
    }
}
 
