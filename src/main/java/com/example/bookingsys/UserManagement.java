package com.example.bookingsys;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedWriter;

public class UserManagement implements Serializable{
    private static final long serialVersionUID = 1L;
    private static UserManagement instance;
    private ArrayList<User> userList;

    private UserManagement()
    {

        userList = new ArrayList<>();
    }

    public static UserManagement getInstance()
    {
        if (instance == null)
        {
            instance = new UserManagement();
        }
        return instance;
    }

    //startup the file function
    public void startup(){
        String stateFile = "user_state.ser";
        File file = new File(stateFile);

        if(file.exists()){
            restoreFullSystemState(stateFile);
        }
        else{
            loadUsersFromCSV("user.csv");
        }
    }

    //File persistence
    public void saveUserState(String fileName){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))){
            for (User u : userList){
                StringBuilder sb = new StringBuilder();

                sb.append(u.getUserId()).append(",");
                sb.append(u.getName()).append(",");
                sb.append(u.getEmail()).append(",");

                // Subtype
                if (u instanceof Staff)
                {
                    sb.append("Staff");
                }
                else if (u instanceof Student) {
                    sb.append("Student");
                }
                else if (u instanceof Guest) {
                    sb.append("Guest");
                }

                bw.write(sb.toString());
                bw.newLine();
            }
            System.out.println("Event saved successfully");
        }catch(IOException e){
            System.err.println("Error printing events to file" + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    //function to restore file
    public void restoreFullSystemState(String fileName){
        File file = new File(fileName);
        if(!file.exists()){
            return;
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))){
            //restore static list
            userList = (ArrayList<User>) ois.readObject();
            System.out.println("Events restored successfully from" + fileName);
        }catch(IOException | ClassNotFoundException e){
            System.err.println("Error printing events to file" + e.getMessage());
        }
    }

    //CSV loading
    public void loadUsersFromCSV(String fileName){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;
            br.readLine();

            while((line = br.readLine()) != null){
                String[] data = line.split(",", -1);

                if(data.length < 4){
                    continue;
                }

                //mapping columns based on user info
                String userId = data[0];
                String name = data[1];
                String email = data[2];
                String type = data[3];

                User newUser = null;

                switch(type){
                    case "Guest":
                        newUser = new Guest(userId, name, email);
                        getInstance().getUserList().add(newUser);
                        break;

                    case "Staff":
                        newUser = new Staff(userId, name, email);
                        getInstance().getUserList().add(newUser);
                        break;

                        case "Student":
                        newUser = new Student(userId, name, email);
                        getInstance().getUserList().add(newUser);
                        break;
                }
                if(newUser != null){
                    userList.add(newUser);
                }
            }
        }catch(IOException e){
            System.err.println("Error saving user to file");
        }
    }

    //keep the user list more organize
    private User createUserByType(String type, String id, String name, String email){
        return switch (type) {
            case "Guest" -> new Guest(id, name, email);
            case "Staff" -> new Staff(id, name, email);
            case "Student" -> new Student(id, name, email);
            default -> null;
        };
    }

    //generate random unique Id for user
    private String uniqueUserId() {
        Random rand = new Random();
        String newId;
        boolean isDuplicated;
        do{
            isDuplicated = false;
            newId = "U" + (100 + rand.nextInt(999));
            for(User u: userList){
                if(u.getUserId().equals(newId)){
                    isDuplicated = true;
                    break;
                }
            }
        }while (isDuplicated);
        return newId;
    }

    //find user by id num to make sure each id is unique
    public User findUserById(String id) {
        for (User u : userList) {
            if (u.getUserId().equalsIgnoreCase(id)) {
                return u;
            }
        }
        return null;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    //list all the user
    public void listUser(){
        System.out.println("Listing users" + userList.size());
        if(userList.isEmpty()){
            System.out.println("No users found");
            return;
        }
        for(User e: userList){
            System.out.println("Username:" + e.getName() + "| Email"+ e.getEmail() + "| UserType:" + e.getUserType());
        }
    }
}
