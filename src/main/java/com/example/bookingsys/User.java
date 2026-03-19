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

//Subclasses for User class

class Student extends User {
    private int studentId;

    public Student(String userId, String name, String email, int studentId) {
        super(userId, name, email);
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public String getUserType() {
        return "Student";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Student ID: %d", studentId);
    }
}

class Staff extends User {
    private String department;

    public Staff(String userId, String name, String email, String department) {
        super(userId, name, email);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String getUserType() {
        return "Staff";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Department: %s", department);
    }
}

class Guest extends User {
    private String organization;

    public Guest(String userId, String name, String email, String organization) {
        super(userId, name, email);
        this.organization = organization;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public String getUserType() {
        return "Guest";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Organization: %s", organization);
    }
}
