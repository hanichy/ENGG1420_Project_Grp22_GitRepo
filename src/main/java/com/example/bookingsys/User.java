package com.example.bookingsys;

import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.io.Serializable;

public abstract class User {
    // Basic user info
    public String userId;

    public String name;
    public String email;

    public static ArrayList<User> userList = new ArrayList<User>();

    // Constructor
    public User(String name, String email) {
        this.userId = uniqueUserId();
        this.name = name;
        this.email = email;

        userList.add(this);

    }

    // Getters and Setters

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

    //generate random unique Id for user
    private String uniqueUserId() {
        Random rand = new Random();
        String newId;
        do{
            int num = 300000 + rand.nextInt(900000);
            newId = String.valueOf(num);
        }while (findUserById(newId) == null);
        return newId;
    }

    //find user by id num to make sure each id is unique
    public static User findUserById(String id) {
        for (User user : userList) {
            if (user.userId.equals(id)) {
                return user;
            }
        }
        return null;
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
