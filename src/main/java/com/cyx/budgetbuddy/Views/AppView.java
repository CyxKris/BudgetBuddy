package com.cyx.budgetbuddy.Views;

import javafx.scene.Parent;
import javafx.geometry.Insets;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;

public class AppView extends BorderPane {
    private SubScene subScene;

    public AppView() {
        super();
        this.setStyle("-fx-background-color:#1B1B1B;");
        this.setPadding(new Insets(20));
        this.setPrefSize(1250, 680);

        AppMenu menu = new AppMenu();
        this.setLeft(menu);

        Parent initialView = ViewFactory.loadDashboardView();
        subScene = new SubScene(initialView, 1000, 640);

        menu.getTransactionsLink().setOnAction(event -> loadTransactionsView());
        menu.getSettingsLink().setOnAction(event -> loadSettingsView());
        menu.getDashboardLink().setOnAction(event -> loadDashboardView());
        menu.getLogoutButton().setOnAction(event -> logOut());

        this.setCenter(subScene);
    }


    private void setSubScene(Parent view) {
        if (subScene != null) {
            subScene.setRoot(view);
        } else {
            subScene = new SubScene(view, 1000, 640);
            this.setCenter(subScene);
        }
    }

    private void loadTransactionsView() {
        Parent view = ViewFactory.loadTransactionsView();
        setSubScene(view);
    }

    private void loadSettingsView() {
        Parent view = ViewFactory.loadSettingsView();
        setSubScene(view);
    }

    private void loadDashboardView() {
        Parent view = ViewFactory.loadDashboardView();
        setSubScene(view);
    }

    private void logOut() {
        ViewFactory.showLogInScene();
    }
}

