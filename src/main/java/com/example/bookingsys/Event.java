package com.example.bookingsys;

import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.io.Serializable;

import static com.example.bookingsys.EventManagement.eventList;

//Gets Everything Set Up
public abstract class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    //Objects
    public String eventId;
    public String title;
    public String dateTime; //DD/MM/YYYY Hour:Min (Will probably have to change cuz time is a pain)
    public String location;
    protected int capacity;
    public boolean status; //True = active False = Cancelled

    //protected static int totalEventCount = 0;

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

