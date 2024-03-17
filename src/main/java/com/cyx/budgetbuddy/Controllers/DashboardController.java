package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Database.AccountDao;
import com.cyx.budgetbuddy.Database.BudgetDao;
import com.cyx.budgetbuddy.Database.TransactionDao;
import com.cyx.budgetbuddy.Models.Account;
import com.cyx.budgetbuddy.Models.Budget;
import com.cyx.budgetbuddy.Models.Transaction;
import com.cyx.budgetbuddy.Utils.CurrencyFormatUtils;
import com.cyx.budgetbuddy.Views.AppView;
import com.cyx.budgetbuddy.Views.DialogFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

public class DashboardController implements Initializable {

    @FXML
    private Label accountBalance;

    @FXML
    private Label budgetAmount;

    @FXML
    private Label amountUsed;

    @FXML
    private Button editBalance;

    @FXML
    private Button editBudget;

    @FXML
    private TableView<Transaction> recentTransactions;

    @FXML
    private PieChart spendingSummary;

    TransactionDao transactionDao = new TransactionDao();


    // Logger instance for logging errors
    private static final Logger logger = Logger.getLogger(DashboardController.class.getName());

    public DashboardController() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // setting the amounts for the account balance and budget amount
        showAccountBalance();
        showBudgetAmount();

        try {
            // POPULATING RECENT TRANSACTIONS TABLE
            populateRecentTransactionsTable();
            initializePieChart();

        } catch (SQLException e) {
            logger.severe("Error while populating recent transactions table: " + e);
        }

    }

    private void handleBalanceEdit() {
        DialogFactory.showAccountDialog();
        showAccountBalance();
    }

    private void handleBudgetEdit() {
        DialogFactory.showDialogBudget();
        showBudgetAmount();
    }

    public void showAccountBalance() {
        try {
            // HANDLING THE ACCOUNT BALANCE AMOUNT AND EDITING ACTION
            AccountDao accountDao = new AccountDao();

            Account userAccount = accountDao.getAccountByUser(AppView.getUser());
            accountBalance.setText(CurrencyFormatUtils.formatCurrency(userAccount.getBalance()));
            editBalance.setOnAction(event -> handleBalanceEdit());
        } catch (SQLException e) {
            logger.severe("Error loading budget data");
        }
    }

    public void showBudgetAmount() {
        try {
            // HANDLING THE BUDGET AMOUNT AND EDITING ACTION
            BudgetDao budgetDao = new BudgetDao();
            Budget userBudget = budgetDao.getBudgetByUser(AppView.getUser());

            budgetAmount.setText(CurrencyFormatUtils.formatCurrency(userBudget.getBudgetAmount()));
            amountUsed.setText(CurrencyFormatUtils.formatCurrency(userBudget.getAmountUsed()));
            editBudget.setOnAction(event -> handleBudgetEdit());
        } catch (SQLException e) {
            logger.severe("Error loading budget data");
        }
    }

    private void populateRecentTransactionsTable() throws SQLException {
        recentTransactions.setEditable(false);

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

        recentTransactions.getColumns().addAll(columns);

        // populating the rows
        recentTransactions.getItems().addAll(transactionDao.getRecentTransactions(AppView.getUser(), 7));
    }

    private void initializePieChart() throws SQLException {

        spendingSummary.getData().clear();

        // Get the total income and expenses from the TransactionDao
        double totalIncome = transactionDao.getTotalIncome(AppView.getUser());
        double totalExpenses = transactionDao.getTotalExpenses(AppView.getUser());

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Income", totalIncome),
                new PieChart.Data("Expense", totalExpenses)
        );

//        spendingSummary.setData(pieChartData);

        spendingSummary = new PieChart(pieChartData);


//
//        PieChart.Data incomeData = pieChartData.getFirst();
//        PieChart.Data expenseData = pieChartData.getLast();
//        spendingSummary.getData().addAll(incomeData, expenseData);

        // Bind data to transactionDao properties for dynamic updating
//        incomeData.pieValueProperty().bind(transactionDao.totalIncomeProperty());
//        expenseData.pieValueProperty().bind(transactionDao.totalExpensesProperty());

        spendingSummary.setTitle("Spending summary for " + AppView.getUser().getUsername());
        spendingSummary.setLabelsVisible(true);
        //Setting the length of the label line
        spendingSummary.setLabelLineLength(50);
        //Setting the start angle of the pie chart
        spendingSummary.setStartAngle(180);
    }

}
