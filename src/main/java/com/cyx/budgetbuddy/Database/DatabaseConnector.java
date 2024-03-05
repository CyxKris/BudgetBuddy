package com.cyx.budgetbuddy.Database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Class responsible for managing the connection to the SQLite database.
 */
public class DatabaseConnector {

    // URL for the SQLite database file
    private static final String DATABASE_URL = "jdbc:sqlite:budgetbuddy.db";

    // Connection source instance to manage the database connection
    private static ConnectionSource connectionSource;

    /**
     * Get the connection source to the SQLite database.
     *
     * @return ConnectionSource object for the SQLite database
     * @throws SQLException if a database access error occurs
     */
    public static ConnectionSource getConnectionSource() throws SQLException {
        // Create a new connection source if it doesn't exist
        if (connectionSource == null) {
            connectionSource = new JdbcConnectionSource(DATABASE_URL);
        }
        return connectionSource;
    }

    /**
     * Close the database connection.
     *
     * @throws Exception if an error occurs while closing the connection
     */
    public static void closeConnection() throws Exception {
        // Close the connection source if it is not null
        if (connectionSource != null) {
            connectionSource.close();
        }
    }
}
