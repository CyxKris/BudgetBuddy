package com.cyx.budgetbuddy.Database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class DatabaseConnector {
    private static final String DATABASE_URL = "jdbc:sqlite:budgetbuddy.db";
    private static ConnectionSource connectionSource;

    public static ConnectionSource getConnectionSource() throws SQLException {
        if (connectionSource == null) {
            connectionSource = new JdbcConnectionSource(DATABASE_URL);
            
        }
        return connectionSource;
    }

    public static void closeConnection() throws Exception {
        if (connectionSource != null) {
            connectionSource.close();
        }
    }
}
