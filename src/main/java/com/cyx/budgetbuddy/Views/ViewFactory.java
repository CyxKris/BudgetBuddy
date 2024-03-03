package com.cyx.budgetbuddy.Views;

import com.cyx.budgetbuddy.Models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public class ViewFactory {
    private static final Logger logger = Logger.getLogger(ViewFactory.class.getName());


    private static void setAndShowStage(Parent root) {
        // Method to set a new scene to the mainStage
        Stage stage = MainStage.getInstance();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void showSignInScene () {
        // Loading the new sign-in fxml file into a new scene and showing it.
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(ViewFactory.class.getResource("/Fxml/Views/sign-in.fxml")));
        } catch (IOException e) {
            logger.severe("An error occurred: " + e.getMessage());
            logger.severe("Stack trace: " + e);
        }
        setAndShowStage(root);
    }

    public static void showLogInScene () {
        // Loading the new login fxml file into a new scene and showing it.
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(ViewFactory.class.getResource("/Fxml/Views/login.fxml")));
        } catch (IOException e) {
            logger.severe("An error occurred: " + e.getMessage());
            logger.severe("Stack trace: " + e);
        }
        setAndShowStage(root);
    }

    public static void showAppScene(User user) {
        // Loading the new app-
        System.out.println(user.getUsername());
        Parent root = new AppView();
        setAndShowStage(root);
    }


    public static Parent loadTransactionsView() {
        Parent root = null;

        try {
            root = FXMLLoader.load(Objects.requireNonNull(ViewFactory.class.getResource("/Fxml/Views/transactions-view.fxml")));
        } catch (IOException e) {
            logger.severe("An error occurred: " + e.getMessage());
            logger.severe("Stack trace: " + e);
        }

        return root;
    }

    public static Parent loadSettingsView() {
        Parent root = null;

        try {
            root = FXMLLoader.load(Objects.requireNonNull(ViewFactory.class.getResource("/Fxml/Views/settings-view.fxml")));
        } catch (IOException e) {
            logger.severe("An error occurred: " + e.getMessage());
            logger.severe("Stack trace: " + e);
        }

        return root;
    }

    public static Parent loadDashboardView() {
        Parent root = null;

        try {
            root = FXMLLoader.load(Objects.requireNonNull(ViewFactory.class.getResource("/Fxml/Views/dashboard-view.fxml")));
        } catch (IOException e) {
            logger.severe("An error occurred: " + e.getMessage());
            logger.severe("Stack trace: " + e);
        }

        return root;
    }
}
