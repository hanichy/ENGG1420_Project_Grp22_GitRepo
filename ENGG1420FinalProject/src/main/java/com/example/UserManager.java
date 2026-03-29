package com.example;

import java.util.ArrayList;

public class UserManager {

    private static ArrayList<User> users = new ArrayList<>();

    //Add user
    public static void addUser(User user) {
        users.add(user);
    }

    //Get user by ID
    public static User getUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    public static ArrayList<User> getAllUsers() {
        return users;
    }
}