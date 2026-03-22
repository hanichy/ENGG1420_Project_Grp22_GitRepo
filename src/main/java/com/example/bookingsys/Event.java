package com.example.bookingsys;

import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.io.Serializable;

//Gets Everything Set Up
public abstract class Event implements Serializable {
    //Objects
    public String eventId;
    public String title;
    public String dateTime; //DD/MM/YYYY Hour:Min (Will probably have to change cuz time is a pain)
    public String location;
    protected int capacity;
    public boolean status; //True = active False = Cancelled

    protected static int totalCapacityCount = 0;
    protected static int totalEventCount = 0;
    protected static ArrayList<Event> eventList = new ArrayList<>();

    //Constructor
    public Event(String title, String dateTime, String location, int capacity) {
        if(capacity <= 0){
            throw new IllegalArgumentException("Error: Capacity must be greater than zero");
        }
        this.eventId = "E" + uniqueId();

        this.title = title;
        this.dateTime = dateTime;
        this.location = location;
        this.capacity = capacity;
        this.status = true;

        eventList.add(this);
        totalEventCount++;
    }

    //Getters
    public String getDateTime() {
        return dateTime;
    }
    public String getEventId() {
        return eventId;
    }
    public String getLocation() {
        return location;
    }
    public String getTitle() {
        return title;
    }
    public int getCapacity() {
        return capacity;
    }
    public boolean getStatus() {
        return status;
    }

    //load the event file and check if it's state
    public static void startup(){
        String stateFile = "system_state.ser";
        File file = new File(stateFile);

        if(file.exists()){
            restoreFullSystemState(stateFile);
        }
        else{
            loadEventsFromCSV("events.csv");
        }
    }
    //function to restore file
    public static void restoreFullSystemState(String fileName){
        File file = new File(fileName);
        if(file.exists()){
            return;
        }

        try(ObjectOutputStream ois = new ObjectOutputStream(new ObjectInputStream(fileName))){
            //restore static list
            eventList = (ArrayList<Event>) ois.readObject();
            totalEventCount = eventList.size();
            System.out.println("Events restored successfully");
        } catch (IOException e) {
            System.err.println("Error restoring events from file");
        }
    }
    //File persistence
    public static void loadEventsFromCSV(String fileName){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;
            br.readLine(); //skip event info

            while((line = br.readLine()) != null){
                String[] data = line.split(",", -1); // keep empty trailing columns

                if(data.length < 7){
                    continue;
                }

                //Mapping columns based on event info
                String eventId = data[0];
                String title = data[1];
                String dateTime = data[2];
                String location = data[3];
                int capacity = Integer.parseInt(data[4].trim());
                String statusValue = data[5];
                String type = data[6];

                Event newEvent = null;

                switch(type){
                    case "Workshop":
                        String topic =  data[7];
                        if(topic.isEmpty()) throw new IllegalArgumentException("Error: Topic is empty");
                        newEvent = new Workshop(title, dateTime, location, capacity, topic);
                        break;

                    case "Seminar":
                        String speaker = data[8];
                        if(speaker.isEmpty()) throw new IllegalArgumentException("Error: Speaker is empty");
                        newEvent = new Seminar(title, dateTime, location, capacity, speaker);
                        break;

                    case "Concert":
                        String ageReq =  data[9];
                        if(ageReq.isEmpty()) throw new IllegalArgumentException("Error: Concert requires age restriction");
                        newEvent = new Concert(title, dateTime, location, capacity, ageReq);
                        break;
                }
                if(newEvent != null && statusValue.equalsIgnoreCase("Cancelled")){
                    newEvent.cancelEvent();
                }
            }
        } catch(IOException e){
            System.err.println("Error saving events to file: " + e.getMessage());
        }
    }

    //save whole event state
    public static void saveEventState(String fileName){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))){
            oos.writeObject(eventList);
        } catch(IOException e){
            System.err.println("Error saving events to file: " + e.getMessage());
        }
    }

    //Generate random event id
    private String uniqueId(){
        Random rand = new Random();
        String newId;
        do {
            int num = rand.nextInt(999);
            newId = String.valueOf(num);
        } while(findEventById(newId) != null);
        return newId;

    }

    //find event by ID num to make sure each ID is unique
    public static Event findEventById(String id){
        for(Event e : eventList){
            if(e.eventId.equals(id)){
                return e;
            }
        }
        return null;
    }

    //Update Event Information
    public void updateEvent(String newTitle, String newTime, String newLocation, int newCapacity){
        title = newTitle;
        dateTime = newTime;
        location = newLocation;
        capacity = newCapacity;
        System.out.println("Event " + title + " has been updated");
    }

    //Cancel Event (Status = false) (Can be same for all types of events)
    public boolean cancelEvent(){
        this.status = false;
        System.out.println("Event " + title + " has been canceled");
        return false;
    }

    //List Events
    public static void listEvents(){
        System.out.println("Event List: " + totalEventCount + " Events Total." );
        for(Event e : eventList){
            String state = e.status ? "[ACTIVE]" : "[CANCELLED]";
            System.out.println(state + "ID:" + e.eventId + "| Title:" + e.title + "| Location:" + e.location);
        }
    }

    //Search and Filter (PHASE 2)
    //Search by title
    public static ArrayList<Event> searchByTitle(String title){
        ArrayList<Event> result = new ArrayList<>();
        for(Event e : eventList){
            if(e.title.toLowerCase().contains(title.toLowerCase())){
                result.add(e);
            }
        }
        return result;
    }

    //Filter by Type
    public static <T extends Event> ArrayList<T> filterByType(Class<T> type){
        ArrayList<T> filteredResult = new ArrayList<>();
        for(Event e : eventList){
            if(type.isInstance(e)){
                filteredResult.add(type.cast(e));
            }
        }
        return filteredResult;
    }

}

//Subclasses for Event class
class Concert extends Event{
    public String ageRestriction;

    public String getAgeRestriction(){
        String aR = String.valueOf(ageRestriction);
        return aR;
    }

    public void updateEvent(String newTitle, String newTime, String newLocation,int newCapacity, String newAgeRestriction){
        super.updateEvent(newTitle, newTime, newLocation, newCapacity);
        this.ageRestriction = newAgeRestriction;
    }

    //Constructor
    public Concert(String title, String dateTime, String location, int capacity, String ageRestriction) {
        super(title, dateTime, location, capacity);
        this.ageRestriction = ageRestriction;
    }
}

class Workshop extends Event {
    private String topic;

    public Workshop(String title, String dateTime, String location, int capacity, String topic) {
        super(title, dateTime, location, capacity);
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void updateEvent(String newTitle, String newTime, String newLocation, int newCapacity, String newTopic) {
        super.updateEvent(newTitle, newTime, newLocation, newCapacity);
        this.topic = newTopic;
    }
}

class Seminar extends Event {
    public String speakerName;

    public String getSpeaker(){
        return speakerName;
    }

    public void updateEvent(String newTitle, String newTime, String newLocation,int newCapacity, String newSpeaker){
        super.updateEvent(newTitle, newTime, newLocation, newCapacity);
        this.speakerName = newSpeaker;
    }

    //Constructor
    public Seminar(String title, String dateTime, String location, int capacity, String speakerName) {
        super(title, dateTime, location, capacity);
        this.speakerName = speakerName;
    }
}

