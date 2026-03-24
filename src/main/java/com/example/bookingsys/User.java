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

    // Constructor
    public User(String userId,String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public abstract String getUserType();

    public abstract int getMaxBookings();

    // Getters
    public String getUserId() {
        return userId;
    }
    public String getName() {

        return name;
    }
    public String getEmail() {

        return email;
    }
}

//Subclasses for User class
class Student extends User {
    public Student(String userId, String name, String email) {
        super(userId, name, email);
    }

    @Override
    public String getUserType() {
        return  "Student";
    }
    @Override
    //max 3 bookings for students
    public int getMaxBookings() {
        return 3;
    }


}

class Staff extends User {
    public Staff(String userId, String name, String email) {
        super(userId, name, email);
    }
    @Override
    public String getUserType() {
       return  "Staff";
    }
    @Override
    //max 5 bookings for staff
    public int getMaxBookings() {
        return 5;
    }

}

class Guest extends User {
    public Guest(String userId, String name, String email) {
        super(userId, name, email);
    }
    @Override
    public String getUserType() {
        return  "Guest";
    }
    @Override
    //max 1 booking for guest
    public int getMaxBookings() {
        return 1;
    }
}
