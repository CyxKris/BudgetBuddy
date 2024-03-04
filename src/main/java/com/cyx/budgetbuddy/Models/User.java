package com.cyx.budgetbuddy.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "User")
public class User {
    @DatabaseField(id = true)
    private UUID userId;

    @DatabaseField(canBeNull = false)
    private String username;

    @DatabaseField(canBeNull = false)
    private String password;

    // Constructors, Getters, and Setters
    public User() {
        // ORMLite requires a no-arg constructor
    }

    public User(String username, String password) {
        this.userId = UUID.randomUUID();
        setUsername(username);
        setPassword(password);
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        // Apply business rules for username modification
        // For example, check for username length, uniqueness, etc.
        if (isValidUsername(username)) {
            this.username = username;
        } else {
            throw new IllegalArgumentException("Invalid username");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        // Apply business rules for password modification
        // For example, check for password strength, encryption, etc.
        if (isValidPassword(password)) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("Invalid password");
        }
    }

    private boolean isValidUsername(String username) {
        // Implement business rules for username validation
        // For example, check for username length, uniqueness, etc.
        return username.length() >= 2;
    }

    private boolean isValidPassword(String password) {
        // Implement business rules for password validation
        // For example, check for password strength, encryption, etc.
        return password.length() >= 3;
    }
}
