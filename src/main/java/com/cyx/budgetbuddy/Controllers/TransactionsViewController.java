package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Models.Transaction;
import com.cyx.budgetbuddy.Views.DialogFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class TransactionsViewController implements Initializable {

    @FXML
    private Button addTransactionButton;

    @FXML
    private static TableView<Transaction> transactionsTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addTransactionButton.setOnAction(event -> DialogFactory.showTransactionDialog());

        populateTransactionsTable();
    }

    private void populateTransactionsTable() {
        transactionsTable.setEditable(false);

        TableColumn<Transaction, String> categoryColumn = new TableColumn<>("Category");
        TableColumn<Transaction, String> descriptionColumn = new TableColumn<>("Description");
        TableColumn<Transaction, Date> dateColumn = new TableColumn<>("Date");
        TableColumn<Transaction, Double> amountColumn = new TableColumn<>("Amount");

        // Set the cell value factories for each column to extract data from Transaction objects
//        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
//        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
//        dateColumn.setCellValueFactory(cellData -> cellData.getValue().transactionDateProperty());
//        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
//
//        transactionsTable.getColumns().addAll(categoryColumn, descriptionColumn, dateColumn, amountColumn);
    }

    public static TableView<?> getTransactionsTable() {
        return transactionsTable;
    }
}
