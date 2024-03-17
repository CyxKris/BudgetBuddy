package com.cyx.budgetbuddy.Database;
import com.cyx.budgetbuddy.Models.Account;
import com.cyx.budgetbuddy.Models.Budget;
import com.cyx.budgetbuddy.Models.Transaction;
import com.cyx.budgetbuddy.Models.User;
import com.cyx.budgetbuddy.Views.AppView;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;

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

//        Transaction transaction = getTransactionById();
//        transaction.setAmount(amount);
//        transaction.setTransactionDate(transactionDate);
//        transaction.setCategory(category);
//        transaction.setDescription(description);
//
//        transactionDao.update(transaction);
    }

    public void deleteTransaction(UUID transactionId) throws SQLException {
        // Get the transaction
        Transaction transaction = transactionDao.queryForId(transactionId);

        // If the transaction isn't null, update the user's budget and account balance and delete the transaction.
        if (transaction != null) {

            // Update the budget
            Budget userBudget = budgetDao.getBudgetByUser(AppView.getUser());
            Account userAccount = accountDao.getAccountByUser(AppView.getUser());

            if (transaction.getCategory().equals("Income")) {
                // CORRECTING THE ACCOUNT BALANCE
                userAccount.setBalance(userAccount.getBalance() - transaction.getAmount());
                // Updating the user's account
                accountDao.updateAccount(AppView.getUser(), userAccount.getBalance());
            } else if (transaction.getCategory().equals("Expense")) {
                // CORRECTING THE ACCOUNT BALANCE
                userAccount.setBalance(userAccount.getBalance() + transaction.getAmount());
                // CORRECTING THE AMOUNT USED IN THE BUDGET TABLE
                userBudget.setAmountUsed(userBudget.getAmountUsed() - transaction.getAmount());

                // Updating the user's account and budget in the database.
                accountDao.updateAccount(AppView.getUser(), userAccount.getBalance());
                budgetDao.updateBudgetAmountUsed(AppView.getUser(), userBudget.getAmountUsed());
            } else {
                logger.severe("Unable to update user account balance and budget");
            }

            transactionDao.delete(transaction);
        }
    }

    public Transaction getTransactionByUser(User user) throws SQLException {
        return transactionDao.queryBuilder().where().eq("userId", user).queryForFirst();
    }

    public Transaction getTransactionById(UUID transactionId) throws SQLException {
        return transactionDao.queryForId(transactionId);
    }

//    public Transaction getTransaction;

    public List<Transaction> getAllTransactions(User user) throws SQLException {
        QueryBuilder<Transaction, UUID> queryBuilder = transactionDao.queryBuilder();
        queryBuilder.where().eq("userId", user);

        return queryBuilder.query();
    }


    public List<Transaction> getRecentTransactions(User user, int limit) throws SQLException {
        QueryBuilder<Transaction, UUID> queryBuilder = transactionDao.queryBuilder();
        queryBuilder.where().eq("userId", user); // Filter transactions by user ID
        queryBuilder.orderBy("creationTime", false); // Sort by creationTime in descending order
        queryBuilder.limit((long) limit); // Limit the number of results

        return queryBuilder.query(); // Execute the query
    }

    public void deleteTransactions(List<Transaction> transactionList) throws SQLException {
        transactionDao.delete(transactionList);
    }
}
