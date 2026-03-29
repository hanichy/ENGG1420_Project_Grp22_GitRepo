package com.example.bookingsys;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

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

    public static void saveToCSV() {

        try (FileWriter writer = new FileWriter("src/main/resources/users.csv")) {

            writer.write("userId,name,email,type,extra\n");

            for (User user : users) {

                String extra = "N/A";

                if (user instanceof Staff) {
                    extra = ((Staff) user).getDepartment();
                } else if (user instanceof Student) {
                    extra = String.valueOf(((Student) user).getStudentId());
                } else if (user instanceof Guest) {
                    extra = ((Guest) user).getOrganization();
                }

                writer.write(user.getUserId() + "," +
                        user.getName() + "," +
                        user.getEmail() + "," +
                        user.getUserType() + "," +
                        extra + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFromCSV() {

        users.clear();

        try (BufferedReader reader = new BufferedReader(
                new FileReader("src/main/resources/users.csv"))) {

            String line;
            reader.readLine(); //skip header

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");
                String id = parts[0];
                String name = parts[1];
                String email = parts[2];
                String type = parts[3];
                String extra = parts[4];

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
            e.printStackTrace();
        }
    }
}