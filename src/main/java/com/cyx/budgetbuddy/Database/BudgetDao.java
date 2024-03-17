package com.cyx.budgetbuddy.Database;

import com.cyx.budgetbuddy.Models.Budget;
import com.cyx.budgetbuddy.Models.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

public class BudgetDao {

    private static final Logger logger = Logger.getLogger(BudgetDao.class.getName());
    private final Dao<Budget, UUID> budgetDao;

    public BudgetDao() throws SQLException {
        try {
            budgetDao = DaoManager.createDao(DatabaseConnector.getConnectionSource(), Budget.class);
        } catch (SQLException e) {
            logger.severe("Error initializing BudgetDao: " + e.getMessage());
            throw e;
        }
    }

    public void createBudget(User user, Date startDate, Date endDate, double budgetAmount) throws SQLException {
        Budget budget = new Budget(user, startDate, endDate, budgetAmount);
        budget.setAmountUsed(0.0); // Set amount used to 0 initially
        budgetDao.create(budget);
    }

    public void updateBudget(User user, Date startDate, Date endDate, double budgetAmount) throws SQLException {
        Budget budget = getBudgetByUser(user);

        budget.setStartDate(startDate);
        budget.setEndDate(endDate);
        budget.setBudgetAmount(budgetAmount);
        // Assuming the remaining amount logic is handled elsewhere or not needed here
        budgetDao.update(budget);
    }


    public boolean hasBudget(User user) throws SQLException {
        return getBudgetByUser(user) != null;
    }

    public Budget getBudgetByUser(User user) throws SQLException {
        return budgetDao.queryBuilder().where().eq("userId", user).queryForFirst();
    }

    public void updateBudgetAmountUsed (User user, Double amountUsed) throws SQLException {
        Budget budget = getBudgetByUser(user);
        budget.setAmountUsed(amountUsed);

        budgetDao.update(budget);
    }

    public void deleteBudget(Budget budget) throws SQLException {
        budgetDao.delete(budget);
    }

}
