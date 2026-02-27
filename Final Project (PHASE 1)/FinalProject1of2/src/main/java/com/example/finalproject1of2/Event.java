package com.example.finalproject1of2;

//Gets Everything Set Up
//Essentially a Blueprint
public abstract class Event {
    //Objects
    public String EventId;
    public String Title;
    public String DateTime; //DD/MM/YYYY Hour:Min (Will probably have to change cuz time is a pain)
    public String Location;
    public int Capacity;
    public boolean Status; //True = active False = Cancelled

    //Update Event Information

    //Cancel Event (Status = false) (Can be same for all types of events)

    //List Events

    //Search and Filter (PHASE 2)

    //Constructor
    public Event(String eventID, String title, String dateTime, String location, int capacity) {
        EventId = eventID;
        Title = title;
        DateTime = dateTime;
        Location = location;
        Capacity = capacity;
        Status = true;
    }
}
