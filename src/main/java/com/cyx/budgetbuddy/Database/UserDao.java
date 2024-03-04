package com.cyx.budgetbuddy.Database;

import com.cyx.budgetbuddy.Models.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import java.sql.SQLException;

public class UserDao {
    private Dao<User, String> userDao;

    public UserDao() throws SQLException {
        userDao = DaoManager.createDao(DatabaseConnector.getConnectionSource(), User.class);
    }

    // Other CRUD operations for User table

    public User getUserByUsername(String username) throws SQLException {
        QueryBuilder<User, String> queryBuilder = userDao.queryBuilder();
        Where<User, String> where = queryBuilder.where();
        where.eq("username", username);
        return queryBuilder.queryForFirst();
    }

    public boolean authenticateUser(String username, String password) throws SQLException {
        User user = getUserByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
}

