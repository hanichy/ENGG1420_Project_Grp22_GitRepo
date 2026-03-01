package com.example.finalproject1of2;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//Gets Everything Set Up
//Essentially a Blueprint
public abstract class Event {
    //Objects
    public String EventId;
    public String Title;
    public String DateTime; //DD/MM/YYYY Hour:Min (Will probably have to change cuz time is a pain)
    public String Location;
    protected int Capacity;
    public boolean Status; //True = active False = Cancelled

    protected static int totalCapacityCount = 0;
    protected static int totalEventCount = 0;
    protected static ArrayList<Event> eventList = new ArrayList<>();

    //Constructor
    public Event(String eventID, String title, String dateTime, String location, int capacity) {
        this.EventId = eventID;
        this.Title = title;
        this.DateTime = dateTime;
        this.Location = location;
        this.Capacity = capacity;
        this.Status = true;

        eventList.add(this);
        totalEventCount++;
    }

    public String getDateTime() {
        return DateTime;
    }
    public String getEventId() {
        return EventId;
    }
    public String getLocation() {
        return Location;
    }
    public String getTitle() {
        return Title;
    }
    public int getCapacity() {
        return Capacity;
    }
    public boolean getStatus() {
        return Status;
    }
    //Update Event Information
    public void updateEvent(String newTitle, String newTime, String newLocation){
        Title = newTitle;
        DateTime = newTime;
        Location = newLocation;
        System.out.println("Event " + Title + " has been updated");
    }


    //Cancel Event (Status = false) (Can be same for all types of events)

    //List Events
    public static void ListEvents(){
        System.out.println("Event List: " + totalEventCount + " Events Total." );
        for(Event e : eventList){
            String state = e.status ? "[ACTIVE]" : "[CANCELLED]";
            System.out.println(state + "ID:" + e.EventId + "| Title:" + e.Title + "| Location:" + e.Location);
        }
    }

    //Search and Filter (PHASE 2)


}
