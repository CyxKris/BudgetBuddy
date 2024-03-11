package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Views.DialogFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionsViewController implements Initializable {

    @FXML
    private Button addTransactionButton;

    @FXML
    private TableView<?> transactionsTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addTransactionButton.setOnAction(event -> DialogFactory.showTransactionDialog());

        populateTransactionsTable();
    }

    private void populateTransactionsTable() {
        transactionsTable.setEditable(false);

        TableColumn categoryColumn = new TableColumn("Category");
        TableColumn descriptionColumn = new TableColumn("Description");
        TableColumn dateColumn = new TableColumn("Date");
        TableColumn amountColumn = new TableColumn("Amount");

        transactionsTable.getColumns().addAll(categoryColumn, descriptionColumn, dateColumn, amountColumn);
    }
}
