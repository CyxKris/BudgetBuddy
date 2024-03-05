package com.cyx.budgetbuddy.Models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

/**
 * Represents a user entity in the application.
 */
@DatabaseTable(tableName = "User")
public class User {

    // Unique identifier for the user
    @DatabaseField(id = true, dataType = DataType.UUID)
    private UUID userId;

    // Username of the user
    @DatabaseField(canBeNull = false)
    private String username;

    // Password of the user
    @DatabaseField(canBeNull = false)
    private String password;

    /**
     * Default constructor required by ORMLite.
     */
    public User() {
        // ORMLite requires a no-arg constructor
    }

    /**
     * Constructs a new User instance with a generated UUID.
     *
     * @param username the username of the user
     * @param password the password of the user
     */
    public User(String username, String password) {
        this.userId = UUID.randomUUID();
        setUsername(username);
        setPassword(password);
    }

    /**
     * Constructs a new User instance with the specified UUID.
     *
     * @param userId   the UUID of the user
     * @param username the username of the user
     * @param password the password of the user
     */
    public User(UUID userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    /**
     * Get the UUID of the user.
     *
     * @return the UUID of the user
     */
    public UUID getUserId() {
        return userId;
    }

    /**
     * Get the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of the user.
     *
     * @param username the new username to set
     * @throws IllegalArgumentException if the username is invalid
     */
    public void setUsername(String username) {
        // Apply business rules for username modification
        if (isValidUsername(username)) {
            this.username = username;
        } else {
            throw new IllegalArgumentException("Invalid username");
        }
    }

    /**
     * Get the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the user.
     *
     * @param password the new password to set
     * @throws IllegalArgumentException if the password is invalid
     */
    public void setPassword(String password) {
        // Apply business rules for password modification
        if (isValidPassword(password)) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("Invalid password");
        }
    }

    /**
     * Validates the username against business rules.
     *
     * @param username the username to validate
     * @return true if the username is valid, otherwise false
     */
    private boolean isValidUsername(String username) {
        // Implement business rules for username validation
        return username.length() >= 2;
    }

    /**
     * Validates the password against business rules.
     *
     * @param password the password to validate
     * @return true if the password is valid, otherwise false
     */
    private boolean isValidPassword(String password) {
        // Implement business rules for password validation
        return password.length() >= 3;
    }
}
