package com.cyx.budgetbuddy.Database;

import com.cyx.budgetbuddy.Models.Account;
import com.cyx.budgetbuddy.Models.Budget;
import com.cyx.budgetbuddy.Models.Transaction;
import com.cyx.budgetbuddy.Models.User;
import com.j256.ormlite.table.TableUtils;


public class DatabaseSetup {

    private static final Object lock = new Object();

    public static synchronized void createTablesIfNotExist() throws Exception {
        synchronized (lock) {
            try {
                TableUtils.createTableIfNotExists(DatabaseConnector.getConnectionSource(), User.class);
                TableUtils.createTableIfNotExists(DatabaseConnector.getConnectionSource(), Account.class);
                TableUtils.createTableIfNotExists(DatabaseConnector.getConnectionSource(), Budget.class);
                TableUtils.createTableIfNotExists(DatabaseConnector.getConnectionSource(), Transaction.class);
            } finally {
                DatabaseConnector.closeConnection();
            }
        }
    }
}

