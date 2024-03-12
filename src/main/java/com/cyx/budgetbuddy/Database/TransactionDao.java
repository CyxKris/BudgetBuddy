package com.cyx.budgetbuddy.Database;
import com.cyx.budgetbuddy.Controllers.TransactionsViewController;
import com.cyx.budgetbuddy.Models.Account;
import com.cyx.budgetbuddy.Models.Budget;
import com.cyx.budgetbuddy.Models.Transaction;
import com.cyx.budgetbuddy.Models.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class TransactionDao {
    private final Dao<Transaction, UUID> transactionDao;

    BudgetDao budgetDao = new BudgetDao();
    AccountDao accountDao = new AccountDao();

    private static final Logger logger = Logger.getLogger(TransactionDao.class.getName());

    public TransactionDao() throws SQLException {
        try {
            transactionDao = DaoManager.createDao(DatabaseConnector.getConnectionSource(), Transaction.class);
        } catch (SQLException e) {
            logger.severe("Error initializing TransactionDao: " + e.getMessage());
            throw e;
        }

    }

    public void createTransaction(User user, Double amount, Date transactionDate, String category, String description) throws SQLException {
        Budget budget = budgetDao.getBudgetByUser(user);
        Account account = accountDao.getAccountByUser(user);

        Transaction transaction = new Transaction(user, budget, account, amount, transactionDate, category, description);
        transactionDao.create(transaction);
    }

    public void updateTransaction(User user, Double amount, Date transactionDate, String category, String description) throws SQLException {

        Transaction transaction = getTransactionByUser(user);
        transaction.setAmount(amount);
        transaction.setTransactionDate(transactionDate);
        transaction.setCategory(category);
        transaction.setDescription(description);

        transactionDao.update(transaction);
    }

    public void deleteTransaction(UUID transactionId) throws SQLException {
        Transaction transaction = transactionDao.queryForId(transactionId);
        if (transaction != null) {
            transactionDao.delete(transaction);
        }
    }

    public Transaction getTransactionByUser(User user) throws SQLException {
        return transactionDao.queryBuilder().where().eq("userId", user).queryForFirst();
    }

    public List<Transaction> getAllTransactions() throws SQLException {
        return transactionDao.queryForAll();
    }

    public List<Transaction> getRecentTransactions(int limit) throws SQLException {
        return transactionDao.queryBuilder()
                .orderBy("transactionDate", false) // Sort by transactionDate in descending order
                .limit((long) limit) // Limit the number of results
                .query(); // Execute the query
    }

    private void updateTransactionsTable() throws SQLException {
        try {
            // Fetch the updated list of transactions from the database
            List<Transaction> transactions = getAllTransactions();

            TransactionsViewController transactionsViewController = new TransactionsViewController();

            TableView<Transaction> transactionsTable = transactionsViewController.getTransactionsTable();

            // Clear the existing items in the transactions table
            transactionsTable.getItems().clear();

            // Add the updated list of transactions to the table
            transactionsTable.getItems().addAll(transactions);
        } catch (SQLException e) {
            // Handle any exceptions
            logger.severe("Error updating transaction table: " + e.getMessage());
            throw e;
        }
    }

}
