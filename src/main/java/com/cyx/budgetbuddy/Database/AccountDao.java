package com.cyx.budgetbuddy.Database;
import com.cyx.budgetbuddy.Models.Account;
import com.cyx.budgetbuddy.Models.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Logger;

public class AccountDao {

    private static final Logger logger = Logger.getLogger(AccountDao.class.getName());
    private Dao<Account, UUID> accountDao;

    public AccountDao() throws SQLException {
        try {
            accountDao = DaoManager.createDao(DatabaseConnector.getConnectionSource(), Account.class);
        } catch (SQLException e) {
            logger.severe("Error initializing AccountDao: " + e.getMessage());
            throw e;
        }
    }

    public void createAccount(User user, double initialBalance) throws SQLException {
        if (!hasAccount(user)) {
            Account account = new Account(user, initialBalance);
            accountDao.create(account);
        } else {
            logger.severe("Error creating Account!");
        }
    }

    public void updateAccount(User user, double newBalance) throws SQLException {
        if (hasAccount(user)) {
            // If account exists, update the balance
            Account existingAccount = getAccountByUser(user);
            existingAccount.setBalance(newBalance);
            accountDao.update(existingAccount);
        } else {
            logger.severe("Error updating account!");
        }
    }

    public boolean hasAccount(User user) throws SQLException {
        return getAccountByUser(user) != null;
    }

    public Account getAccountByUser(User user) throws SQLException {
        return accountDao.queryBuilder().where().eq("userId", user).queryForFirst();
    }

    public void deleteAccount(Account account) throws SQLException {
        accountDao.delete(account);
    }

}
