package com.cyx.budgetbuddy.Views;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Objects;

public class BudgetTrackerApp extends Application {

    private ObservableList<Expense> expenseData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        // Create sample expense data
        expenseData.addAll(
                new Expense("Money Debit", "Oct 24", "09:23 pm", 335.00),
                new Expense("Nike Store", "Oct 20", "04:50 pm", -149.99),
                new Expense("House Rent", "Oct 11", "10:00 am", -1125.00),
                new Expense("Utilities", "Oct 6", "12:43 pm", -125.00),
                new Expense("Spotify Plan", "Oct 2", "05:45 pm", -59.99),
                new Expense("Snacks", "Oct 2", "01:00 pm", -54.00),
                new Expense("Groceries", "Oct 2", "11:30 am", -368.54),
                new Expense("IKEA Purchase", "Oct 1", "02:45 pm", -114.99),
                new Expense("Salary", "Oct 1", "01:00 pm", 2500.00)
        );

        // Create the TableView and columns
        TableView<Expense> expenseTableView = new TableView<>();
        expenseTableView.setPlaceholder(new Label("No expenses to display"));
        expenseTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
//        expenseTableView.setTableMenuButtonVisible(false);
        expenseTableView.setStyle("-fx-table-cell-border-color: transparent;");

        TableColumn<Expense, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Expense, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Expense, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Expense, Double> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountColumn.setCellFactory(createAmountCellFactory());

        // Create a new TableColumn for the options button
        TableColumn<Expense, Void> optionsColumn = new TableColumn<>("");
        optionsColumn.setCellFactory(param -> new TableCell<Expense, Void>() {
            private final Button optionsButton = new Button("show up"); // Three-dot options button
            private final HBox container = new HBox(5, optionsButton);

            {
                // Create and style the edit and delete buttons
                Button editButton = new Button("", new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/edit-icon.png")))));
                editButton.getStyleClass().add("icon-button");

                Button deleteButton = new Button("", new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/delete-icon.png")))));
                deleteButton.getStyleClass().add("icon-button");

                container.getChildren().addAll(editButton, deleteButton);
                container.setVisible(false); // Initially hide the edit/delete buttons
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(container);
                    optionsButton.setOnMouseClicked(event -> {
                        container.setVisible(!container.isVisible()); // Toggle visibility of edit/delete buttons
                    });
                }
            }

        });

        expenseTableView.getColumns().addAll(nameColumn, dateColumn, timeColumn, amountColumn, optionsColumn);
        expenseTableView.setItems(expenseData);

        // Create the "Add New" button
        Button addNewButton = new Button("+ Add New");
        addNewButton.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white;");

        // Create the header section
        HBox headerSection = new HBox(10);
        headerSection.setAlignment(Pos.CENTER_LEFT);
        headerSection.getChildren().addAll(new Label("Expenses"), addNewButton);

        // Create the root layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(headerSection, expenseTableView);

        // Set the scene and show the stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Budget Tracker");
        primaryStage.show();
    }

    private Callback<TableColumn<Expense, Double>, TableCell<Expense, Double>> createAmountCellFactory() {
        return param -> new TableCell<>() {
            @Override
            protected void updateItem(Double amount, boolean empty) {
                super.updateItem(amount, empty);
                if (empty || amount == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.format("+ $%.2f", Math.abs(amount)));
                    setStyle("-fx-text-fill: " + (amount < 0 ? "red" : "green"));
                }
            }
        };
    }

    public static class Expense {
        private final String name;
        private final String date;
        private final String time;
        private final double amount;

        public Expense(String name, String date, String time, double amount) {
            this.name = name;
            this.date = date;
            this.time = time;
            this.amount = amount;
        }

        public String getName() {
            return name;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public double getAmount() {
            return amount;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}