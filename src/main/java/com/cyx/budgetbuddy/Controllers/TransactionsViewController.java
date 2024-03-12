package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Models.Transaction;
import com.cyx.budgetbuddy.Views.DialogFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class TransactionsViewController implements Initializable {

    @FXML
    private Button addTransactionButton;

    @FXML
    private TableView<Transaction> transactionsTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addTransactionButton.setOnAction(event -> DialogFactory.showTransactionDialog());

        populateTransactionsTable();
    }

    private void populateTransactionsTable() {
        transactionsTable.setEditable(false);

        TableColumn<Transaction, String> categoryColumn = new TableColumn<>("Category");
        TableColumn<Transaction, String> descriptionColumn = new TableColumn<>("Description");
        TableColumn<Transaction, Date> transactionDate = new TableColumn<>("Date");
        TableColumn<Transaction, Double> amount = new TableColumn<>("Amount");

        transactionsTable.getColumns().addAll(categoryColumn, descriptionColumn, transactionDate, amount);

    }

    public TableView<Transaction> getTransactionsTable() {
        return transactionsTable;
    }
}
