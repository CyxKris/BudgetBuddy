package com.cyx.budgetbuddy.Views;

import javafx.scene.Parent;
import javafx.geometry.Insets;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;

public class AppView extends BorderPane {
    private SubScene subScene;
    private final AppMenu menu;

    public AppView() {
        super();
        this.setStyle("-fx-background-color:#1B1B1B;");
        this.setPadding(new Insets(20));
        this.setPrefSize(1250, 680);

        menu = new AppMenu();
        this.setLeft(menu);

        Parent initialView = ViewFactory.loadDashboardView();
        subScene = new SubScene(initialView, 1000, 640);

        menu.getTransactionsLink().setOnAction(event -> setSubScene(ViewFactory.loadTransactionsView()));
        menu.getSettingsLink().setOnAction(event -> setSubScene(ViewFactory.loadSettingsView()));
        menu.getDashboardLink().setOnAction(event -> setSubScene(ViewFactory.loadDashboardView()));
        menu.getLogoutButton().setOnAction(event -> ViewFactory.showLogInScene());

        this.setCenter(subScene);
//        stage.setScene(new Scene(this));
//        stage.show();
    }

    private void setSubScene(Parent view) {
        if (subScene != null) {
            subScene.setRoot(view);
        } else {
            subScene = new SubScene(view, 1000, 640);
            this.setCenter(subScene);
        }
    }
}

