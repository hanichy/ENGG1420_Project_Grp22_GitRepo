package com.example;

import java.io.*;
import java.util.ArrayList;

public class UserManager {
    private static UserManager instance;
    private static ArrayList<User> users = new ArrayList<>();

    //Add user
    public static void addUser(User user) {
        users.add(user);
    }


    public static UserManager getInstance()
    {
        if (instance == null)
        {
            instance = new UserManager();
        }
        return instance;
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

    public static void saveToCSV() {

        try (FileWriter writer = new FileWriter("users.csv")) {

            writer.write("UserId,Name,Email,Type,Extra\n");

            for (User user : users) {

                String extra = "";

                if (user instanceof Staff) {
                    extra = ((Staff) user).getDepartment();
                } else if (user instanceof Student) {
                    extra = String.valueOf(((Student) user).getStudentId());
                } else if (user instanceof Guest) {
                    extra = ((Guest) user).getOrganization();
                }

                writer.write(String.format("%s,%s,%s,%s,%s\n",
                        user.getUserId(),
                        user.getName(),
                        user.getEmail(),
                        user.getUserType(),
                        extra));
            }

        } catch (IOException e) {
            System.err.println("Error saving users to CSV: " + e.getMessage());
        }
    }

    public static void loadFromCSV() {
        users.clear();
        File file = new File("users.csv");

        if(!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(
                new FileReader("users.csv"))) {

            String line;
            reader.readLine(); //Skip the Header Row

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");
                if(parts.length < 4) continue;
                String id = parts[0];
                String name = parts[1];
                String email = parts[2];
                String type = parts[3];
                String extra = parts.length > 4?parts[4]:"";

                User user;
                switch (type) {
                    case "Staff":
                        user = new Staff(id, name, email, extra);
                        break;
                    case "Student":
                        user = new Student(id, name, email, Integer.parseInt(extra));
                        break;
                    case "Guest":
                        user = new Guest(id, name, email, extra);
                        break;
                    default:
                        user = new RegularUser(id, name, email);
                }
                users.add(user);
            }

        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
    }
}