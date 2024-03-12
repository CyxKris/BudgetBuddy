package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Database.TransactionDao;
import com.cyx.budgetbuddy.Models.Transaction;
import com.cyx.budgetbuddy.Views.AppView;
import com.cyx.budgetbuddy.Views.DialogFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class TransactionsViewController implements Initializable {

    @FXML
    private Button addTransactionButton;

    @FXML
    private Button refreshButton;

    @FXML
    private TableView<Transaction> transactionsTable;

    TransactionDao transactionDao = new TransactionDao();

    // Logger for handling logging messages
    private static final Logger logger = Logger.getLogger(TransactionsViewController.class.getName());

    public TransactionsViewController() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addTransactionButton.setOnAction(event -> DialogFactory.showTransactionDialog());

        refreshButton.setOnAction(event -> {
            try {
                refreshTransactionTable();
            } catch (SQLException e) {
                logger.severe("Error while refreshing table items: " + e);
            }
        });

        try {
            populateTransactionsTable();
        } catch (SQLException e) {
            logger.severe("Error while populating transactions table: " + e);
        }
    }

    private void refreshTransactionTable() throws SQLException {
        // Clear the existing items in the transactions table
        transactionsTable.getItems().clear();

        // Add the updated list of transactions to the table
        transactionsTable.getItems().addAll(transactionDao.getAllTransactions(AppView.getUser()));
    }

    private void populateTransactionsTable() throws SQLException {
        transactionsTable.setEditable(false);

        TableColumn<Transaction, String> categoryColumn = new TableColumn<>("Category");
        TableColumn<Transaction, String> descriptionColumn = new TableColumn<>("Description");
        TableColumn<Transaction, Date> transactionDate = new TableColumn<>("Date");
        TableColumn<Transaction, Double> amount = new TableColumn<>("Amount");

        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        transactionDate.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));


        List<TableColumn<Transaction, ?>> columns = new ArrayList<>();
        columns.add(categoryColumn);
        columns.add(descriptionColumn);
        columns.add(transactionDate);
        columns.add(amount);

        transactionsTable.getColumns().addAll(columns);

        // populating the rows
        transactionsTable.getItems().addAll(transactionDao.getAllTransactions(AppView.getUser()));
    }

    public TableView<Transaction> getTransactionsTable() {
        return transactionsTable;
    }

    public void setTransactionsTable(TableView<Transaction> transactionsTable) {
        this.transactionsTable = transactionsTable;
    }
}
