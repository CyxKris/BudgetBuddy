package com.cyx.budgetbuddy.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.UUID;

@DatabaseTable(tableName = "Budget")
public class Budget {
    @DatabaseField(id = true)
    private UUID budgetId;

    @DatabaseField(foreign = true, columnName = "userId", foreignAutoRefresh = true, canBeNull = false)
    private User user;

    @DatabaseField(canBeNull = false)
    private Date startDate;

    @DatabaseField(canBeNull = false)
    private Date endDate;

    @DatabaseField(canBeNull = false)
    private double budgetAmount;

    @DatabaseField(canBeNull = false)
    private double remainingAmount;

    // Constructors, Getters, and Setters

    public Budget() {
        // ORMLite requires a no-arg constructor
    }

    public Budget(User user, Date startDate, Date endDate, double budgetAmount, double remainingAmount) {
        this.budgetId = UUID.randomUUID();
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budgetAmount = budgetAmount;
        this.remainingAmount = remainingAmount;
    }

    public UUID getBudgetId() {
        return budgetId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }
}
