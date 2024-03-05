package com.cyx.budgetbuddy.Database;

import com.cyx.budgetbuddy.Models.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * The UserDao class handles database operations related to the User table.
 * It provides methods for creating new users, retrieving users by username,
 * and authenticating users.
 */
public class UserDao {
    private static final Logger logger = Logger.getLogger(UserDao.class.getName());
    private Dao<User, UUID> userDao; // DAO object for User table operations

    /**
     * Constructs a new UserDao instance and initializes the userDao DAO object.
     * @throws SQLException if there is an error initializing the DAO object
     */
    public UserDao() throws SQLException {
        try {
            userDao = DaoManager.createDao(DatabaseConnector.getConnectionSource(), User.class);
        } catch (SQLException e) {
            logger.severe("Error initializing UserDao: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Creates a new user if the user ID and username do not already exist in the database.
     * @param user The user object to be created
     * @return true if the user is created successfully, false otherwise
     * @throws SQLException if there is an error during database operations
     */
    public boolean createUserIfNotExists(User user) throws SQLException {
        try {
            User existingUser = userDao.queryForId(user.getUserId());
            if (existingUser != null) {
                return false; // User ID already exists
            }

            existingUser = userDao.queryForEq("username", user.getUsername()).stream().findFirst().orElse(null);
            if (existingUser != null) {
                return false; // Username already exists
            }

            userDao.create(user);
            return true; // User created successfully
        } catch (SQLException e) {
            logger.severe("Error creating user: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Retrieves a user from the database based on the provided username.
     * @param username The username of the user to retrieve
     * @return The User object if found, null otherwise
     * @throws SQLException if there is an error during database operations
     */
    public User getUserByUsername(String username) throws SQLException {
        try {
            QueryBuilder<User, UUID> queryBuilder = userDao.queryBuilder();
            queryBuilder.where().eq("username", username);
            PreparedQuery<User> preparedQuery = queryBuilder.prepare();
            return userDao.queryForFirst(preparedQuery);
        } catch (SQLException e) {
            logger.severe("Error getting user by username: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Authenticates a user by verifying the provided username and password.
     * @param username The username of the user to authenticate
     * @param password The password of the user to authenticate
     * @return true if the user is authenticated, false otherwise
     * @throws SQLException if there is an error during database operations
     */
    public boolean authenticateUser(String username, String password) throws SQLException {
        try {
            User user = getUserByUsername(username);
            return user != null && user.getPassword().equals(password);
        } catch (SQLException e) {
            logger.severe("Error authenticating user: " + e.getMessage());
            throw e;
        }
    }
}

