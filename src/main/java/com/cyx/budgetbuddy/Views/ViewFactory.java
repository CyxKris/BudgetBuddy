package com.cyx.budgetbuddy.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {

    private static void setAndShowStage(Parent root) {
        // Method to set a new scene to the mainStage
        Stage stage = MainStage.getInstance();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void showSignInScene () {
        // Loading the new sign-in fxml file into a new scene and showing it.
        System.out.println("Showing Sign In Scene");
        Parent root = null;
        try {
            root = FXMLLoader.load(ViewFactory.class.getResource("/Fxml/Views/sign-in.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setAndShowStage(root);
    }

    public static void showLogInScene () {
        // Loading the new login fxml file into a new scene and showing it.
        Parent root = null;
        try {
            root = FXMLLoader.load(ViewFactory.class.getResource("/Fxml/Views/login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setAndShowStage(root);
    }


}
