package com.cyx.budgetbuddy.Views;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

public class BudgetTrackerApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        //Preparing ObservbleList object
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Income", 650000.0),
                new PieChart.Data("Expense", 45000.0));

    }


    public static void main(String[] args) {
        launch(args);
    }
}