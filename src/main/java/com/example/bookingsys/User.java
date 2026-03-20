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
    protected static int userCount = 0;

    // Constructor
    public User(String name, String email) {
        this.userId = uniqueUserId();

        this.name = name;
        this.email = email;

        userList.add(this);
        userCount++;

    }

    // Getters
    public String getName() {

        return name;
    }
    public String getEmail() {

        return email;
    }

    //generate random unique Id for user
    private String uniqueUserId() {
        Random rand = new Random();
        String newId;
        do{
            int num = 100000 + rand.nextInt(900000);
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

    //List Users
    public static void listUser(){
        System.out.println("User List:" + userCount + "Total User:");
        for (User user : userList) {
            System.out.println("ID" + user.userId + "| Name" + user.name + "| Email" + user.email + "| Type:" + user.getUserType());
        }
    }
}

//Subclasses for User class
class Student extends User {
    public String userType;

    public String getUserType() {
        userType =  "Student";
        return  userType;
    }
    public Student(String name, String email, String userType) {
        super(name, email);
        this.userType = userType;
    }

}

class Staff extends User {
    public String userType;

    public String getUserType() {
       userType =  "Staff";
       return  userType;
    }

    public Staff(String name, String email, String userType) {
        super(name, email);
        this.userType = userType;
    }
}

class Guest extends User {
    public String userType;

    public String getUserType() {
        userType =  "Guest";
        return  userType;
    }

    public Guest(String name, String email, String userType) {
        super(name, email);
        this.userType = userType;
    }

}
