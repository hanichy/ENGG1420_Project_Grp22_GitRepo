package com.example.bookingsys;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

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
        String stateFile = "system_state.ser";
        File file = new File(stateFile);

        if(file.exists()){
            restoreFullSystemState(stateFile);
        }
        else{
            loadEventsFromCSV("user.csv");
        }
    }

    //File persistence
    public void saveUserState(String fileName){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName)){
            oos.writeObject(userList);
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
        try(ObjectIuputStream ois = new ObjectIuputStream(new FileInputStream(fileName))){
            //restore static list
            userList = (ArrayList<User>) ois.readObject();
            System.out.println("Events restored successfully from" + fileName);
        }catch(IOException | ClassNotFoundException e){
            System.err.println("Error printing events to file" + e.getMessage());
        }
    }

    //CSV loading
    public static void loadEventsFromCSV(String fileName){
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
                        break;

                    case "Staff":
                        newUser = new Staff(userId, name, email);
                        break;

                        case "Student":
                        newUser = new Student(userId, name, email);
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
