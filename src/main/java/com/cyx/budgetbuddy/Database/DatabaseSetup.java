package com.cyx.budgetbuddy.Database;

import com.cyx.budgetbuddy.Models.Account;
import com.cyx.budgetbuddy.Models.Budget;
import com.cyx.budgetbuddy.Models.Transaction;
import com.cyx.budgetbuddy.Models.User;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.logging.Logger;


/**
 * Utility class for database setup.
 * Creates necessary tables if they do not exist in the database.
 */
public class DatabaseSetup {

    // Logger instance for logging errors
    private static final Logger logger = Logger.getLogger(DatabaseSetup.class.getName());

    // Object used for synchronization
    private static final Object lock = new Object();

    /**
     * Creates tables if they do not already exist in the database.
     * Uses synchronization to ensure thread safety.
     * @throws SQLException if an SQL exception occurs during table creation.
     */
    public static synchronized void createTablesIfNotExist() {
        // Synchronize access to ensure thread safety
        synchronized (lock) {
            try {
                // Get the connection source from the database connector
                ConnectionSource connectionSource = DatabaseConnector.getConnectionSource();

                // Create tables if they do not exist
                TableUtils.createTableIfNotExists(connectionSource, User.class);
                TableUtils.createTableIfNotExists(connectionSource, Account.class);
                TableUtils.createTableIfNotExists(connectionSource, Budget.class);
                TableUtils.createTableIfNotExists(connectionSource, Transaction.class);

                // Create the guest user
                UserDao userDao = new UserDao();
                User guestUser = new User("Guest", "1234");
                userDao.createUserIfNotExists(guestUser);
            } catch (SQLException e) {
                logger.severe("Error in setting up database: " + e);
            }
        }
    }
}


