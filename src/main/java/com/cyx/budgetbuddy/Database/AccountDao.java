package com.cyx.budgetbuddy.Database;
import com.cyx.budgetbuddy.Models.Account;
import com.cyx.budgetbuddy.Models.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

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

    public void createOrUpdateAccount(User user, double initialBalance) throws SQLException {
        if (!hasAccount(user)) {
            Account account = new Account(user, initialBalance);
            accountDao.create(account);
        } else {
            // If account exists, update the balance
            Account existingAccount = getAccountByUser(user);
            existingAccount.setBalance(initialBalance);
            accountDao.update(existingAccount);
        }
    }

    public boolean hasAccount(User user) throws SQLException {
        return getAccountByUser(user) != null;
    }

    public Account getAccountByUser(User user) throws SQLException {
        return accountDao.queryBuilder().where().eq("userId", user).queryForFirst();
    }

    public Account getAccount(UUID accountId) throws SQLException {
        return accountDao.queryForId(accountId);
    }

}
