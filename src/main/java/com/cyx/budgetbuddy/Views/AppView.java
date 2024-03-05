package com.cyx.budgetbuddy.Views;

import com.cyx.budgetbuddy.Database.DatabaseSetup;
import javafx.scene.Parent;
import javafx.geometry.Insets;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;

import java.util.logging.Logger;

/**
 * Represents the main view of the application.
 * This class manages the layout and switching of scenes within the application.
 */
public class AppView extends BorderPane {
    // Logger for handling logging messages
    private static final Logger logger = Logger.getLogger(AppView.class.getName());

    // SubScene to display the main content
    private SubScene subScene;

    /**
     * Constructs an instance of AppView with the specified username.
     *
     * @param username The username of the logged-in user
     * @throws Exception if an error occurs during initialization
     */
    public AppView(String username) throws Exception {
        super();
        // Set the background color and padding for the layout
        this.setStyle("-fx-background-color:#1B1B1B;");
        this.setPadding(new Insets(20));
        this.setPrefSize(1250, 680);

        // Create the application menu
        AppMenu menu = new AppMenu(username);
        this.setLeft(menu);

        // Load the initial view for the application
        Parent initialView = ViewFactory.loadDashboardView();
        subScene = new SubScene(initialView, 1000, 640);

        // Set event handlers for menu items
        menu.getTransactionsLink().setOnAction(event -> loadTransactionsView());
        menu.getSettingsLink().setOnAction(event -> loadSettingsView());
        menu.getDashboardLink().setOnAction(event -> loadDashboardView());
        menu.getLogoutButton().setOnAction(event -> logOut());

        // Set the initial view as the center content
        this.setCenter(subScene);

        // Create database tables if they do not exist
        try {
            DatabaseSetup.createTablesIfNotExist();
        } catch (Exception e) {
            logger.severe("An error occurred while creating database tables: " + e.getMessage());
        }
    }

    /**
     * Sets the specified view as the content of the subScene.
     *
     * @param view The view to be set as the content
     */
    private void setSubScene(Parent view) {
        if (subScene != null) {
            subScene.setRoot(view);
        } else {
            subScene = new SubScene(view, 1000, 640);
            this.setCenter(subScene);
        }
    }

    /**
     * Loads and sets the transactions view.
     */
    private void loadTransactionsView() {
        Parent view = ViewFactory.loadTransactionsView();
        setSubScene(view);
    }

    /**
     * Loads and sets the settings view.
     */
    private void loadSettingsView() {
        Parent view = ViewFactory.loadSettingsView();
        setSubScene(view);
    }

    /**
     * Loads and sets the dashboard view.
     */
    private void loadDashboardView() {
        Parent view = ViewFactory.loadDashboardView();
        setSubScene(view);
    }

    /**
     * Logs out the current user and returns to the login scene.
     */
    private void logOut() {
        ViewFactory.showLogInScene();
    }
}


