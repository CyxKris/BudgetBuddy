package com.cyx.budgetbuddy.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.UUID;

@DatabaseTable(tableName = "Transaction")
public class Transaction {
    @DatabaseField(id = true)
    private UUID transactionId;

    @DatabaseField(foreign = true, columnName = "userId", foreignAutoRefresh = true, canBeNull = false)
    private User user;

    @DatabaseField(foreign = true, columnName = "budgetId", foreignAutoRefresh = true, canBeNull = false)
    private Budget budget;

    @DatabaseField(foreign = true, columnName = "accountId", foreignAutoRefresh = true, canBeNull = false)
    private Account account;

    @DatabaseField(canBeNull = false)
    private double amount;

    @DatabaseField(canBeNull = false)
    private Date transactionDate;

    @DatabaseField(canBeNull = false)
    private String category;

    @DatabaseField(canBeNull = false)
    private String description;

    // Constructors, Getters, and Setters

    public Transaction() {
        // ORMLite requires a no-arg constructor
    }

    public Transaction(User user, Budget budget, Account account, double amount, Date transactionDate, String category, String description) {
        this.transactionId = UUID.randomUUID();
        this.user = user;
        this.budget = budget;
        this.account = account;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.category = category;
        this.description = description;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public User getUser() {
        return user;
    }

    public Budget getBudget() {
        return budget;
    }

    public Account getAccount() {
        return account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

