package com.cyx.budgetbuddy.Models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * Represents a financial transaction entity.
 */
@DatabaseTable(tableName = "Transaction")
public class Transaction {

    @DatabaseField(id = true)
    private UUID transactionId;

    @DatabaseField(dataType = DataType.TIME_STAMP)
    private Timestamp creationTime;

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

    /**
     * Default constructor required by ORMLite.
     */
    public Transaction() {
        // ORMLite requires a no-arg constructor
    }

    /**
     * Constructs a new Transaction object with specified parameters.
     * @param user The user associated with the transaction.
     * @param budget The budget associated with the transaction.
     * @param account The account associated with the transaction.
     * @param amount The amount of the transaction.
     * @param transactionDate The date of the transaction.
     * @param category The category of the transaction.
     * @param description The description of the transaction.
     */
    public Transaction(User user, Budget budget, Account account, double amount, Date transactionDate, String category, String description) {
        this.transactionId = UUID.randomUUID();
        this.creationTime = new Timestamp(System.currentTimeMillis()); // Set current timestamp
        this.user = user;
        this.budget = budget;
        this.account = account;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.category = category;
        this.description = description;
    }

    // Getters and Setters

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

