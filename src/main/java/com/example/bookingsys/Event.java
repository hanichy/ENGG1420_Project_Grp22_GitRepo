package com.example.bookingsys;

import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.io.Serializable;
import java.util.UUID;

//Gets Everything Set Up
public abstract class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    //Objects
    protected String eventId;
    protected String title;
    protected String dateTime; //DD/MM/YYYY Hour:Min (Will probably have to change cuz time is a pain)
    protected String location;
    protected int capacity;
    protected boolean status; //True = active False = Cancelled

    //protected static int totalEventCount = 0;

    //Constructor
    public Event(String eventId, String title, String dateTime, String location, int capacity) {
        if(capacity <= 0){
            throw new IllegalArgumentException("Error: Capacity must be greater than zero");
        }
        this.eventId = eventId;
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

    //Update Event Information
    public void updateEvent(String newTitle, String newTime, String newLocation, int newCapacity){
        title = newTitle;
        dateTime = newTime;
        location = newLocation;
        capacity = newCapacity;
        System.out.println("Event " + title + " has been updated");
    }

    //Cancel Event (Status = false) (Can be same for all types of events)
    public void cancelEvent(){
        this.status = false;
        System.out.println("Event " + title + " has been canceled");
    }

}

//Subclasses for Event class
class Concert extends Event{
    private String ageRestriction;

    public String getAgeRestriction(){
        return ageRestriction;
    }

    public void updateEvent(String newTitle, String newTime, String newLocation,int newCapacity, String newAgeRestriction){
        super.updateEvent(newTitle, newTime, newLocation, newCapacity);
        this.ageRestriction = newAgeRestriction;
    }

    //Constructor
    public Concert(String eventId, String title, String dateTime, String location, int capacity, String ageRestriction) {
        super(eventId, title, dateTime, location, capacity);
        this.ageRestriction = ageRestriction;
    }
}

class Workshop extends Event {
    private String topic;

    public Workshop(String eventId, String title, String dateTime, String location, int capacity, String topic) {
        super(eventId, title, dateTime, location, capacity);
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
    public Seminar(String eventId, String title, String dateTime, String location, int capacity, String speakerName) {
        super(eventId, title, dateTime, location, capacity);
        this.speakerName = speakerName;
    }
}

