package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Database.TransactionDao;
import com.cyx.budgetbuddy.Models.Transaction;
import com.cyx.budgetbuddy.Views.AppView;
import com.cyx.budgetbuddy.Views.DialogFactory;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

        addTransactionButton.setOnAction(event -> DialogFactory.showAddTransactionDialog());

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


        // Create the context menu
        ContextMenu rowMenu = new ContextMenu();

        rowMenu.setStyle("""
                -fx-background-color: #1B1B1B;
                    -fx-font-size: 16px;
                    -fx-background-radius: 10;
                    -fx-padding: 4;""");

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setStyle("""
                -fx-font-family: "urbanist-semibold";
                    -fx-font-weight: 700;
                    -fx-cursor: hand;
                    -fx-padding: 2; -fx-text-fill: white;""");

        MenuItem editItem = new MenuItem("Edit");
        editItem.setStyle("""
                -fx-font-family: "urbanist-semibold";
                -fx-font-weight: 700;
                -fx-cursor: hand;
                -fx-padding: 2;
                -fx-text-fill: white;""");

        rowMenu.getItems().addAll(deleteItem, editItem);

        // Add event handlers for the context menu items
        deleteItem.setOnAction(event -> {
            Transaction selectedTransaction = transactionsTable.getSelectionModel().getSelectedItem();
            if (selectedTransaction != null) {
                try {
                    transactionDao.deleteTransactionAndUpdateChart(selectedTransaction.getTransactionId());
                } catch (SQLException e) {
                    logger.severe("Error while deleting transaction: " + e);
                }
                transactionsTable.getItems().remove(selectedTransaction);
            }
        });

        editItem.setOnAction(event -> {
            Transaction selectedTransaction = transactionsTable.getSelectionModel().getSelectedItem();
            if (selectedTransaction != null) {
                // Open a dialog or window to allow the user to edit the transaction
                // After editing, update the transaction in the database and refresh the table view
                DialogFactory.showEditTransactionDialog(selectedTransaction);
            }
        });


        // Set the row factory to apply the context menu
        transactionsTable.setRowFactory(tv -> {
            TableRow<Transaction> row = new TableRow<>();

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(rowMenu)
            );
            return row;
        });

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
