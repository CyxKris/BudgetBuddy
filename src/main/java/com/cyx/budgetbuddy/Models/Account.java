package com.cyx.budgetbuddy.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;


/**
 * Represents an account entity in the application.
 */
@DatabaseTable(tableName = "Account")
public class Account {

    @DatabaseField(id = true)
    private UUID accountId;

    @DatabaseField(foreign = true, columnName = "userId", foreignAutoRefresh = true, canBeNull = false)
    private User user;

    @DatabaseField(canBeNull = false)
    private double balance;

    // Constructors, Getters, and Setters

    /**
     * Default constructor required by ORMLite.
     */
    public Account() {
        // ORMLite requires a no-arg constructor
    }

    /**
     * Constructs a new Account object with the specified parameters.
     * @param user The user associated with the account.
     * @param balance The balance of the account.
     */
    public Account(User user, double balance) {
        this.accountId = UUID.randomUUID();
        this.user = user;
        this.balance = balance;
    }

    // Getters and Setters

    public UUID getAccountId() {
        return accountId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
