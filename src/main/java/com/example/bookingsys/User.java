package com.example.bookingsys;

public abstract class User {
    // Basic user info
    protected String userId;
    protected String name;
    protected String email;
    // Constructor
    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Abstract method: must be implemented by subclasses
    public abstract String getUserType();

    // Optional: a display-friendly summary
    @Override
    public String toString() {
        return String.format("User ID: %s | Name: %s | Email: %s | Type: %s",
                userId, name, email, getUserType());
    }
}