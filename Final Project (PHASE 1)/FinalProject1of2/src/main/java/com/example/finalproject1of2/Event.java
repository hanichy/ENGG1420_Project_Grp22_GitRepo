package com.example.finalproject1of2;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

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
        if(capacity <= 0){
            throw new IllegalArguementException("Error: Capacity must be greater than zero");
        }
        this.EventId = UniqueId();

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
    //Generate random event id
    private String uniqueId(){
        Randoom rand = new Random();
        String newId;

        do{
            int num = 100000 + rand.nextInt(900000);
            newId = String.valueOf(num);
        }while(findEventById(newId) != null);

        return newId;
    }
    //find event by Id num to make sure each Id is unique
    public static Event findEventById(String id){
        for(Event e : eventList){
            if(e.EventId.equals(id)){
                return e;
            }
        }
        return null;
    }
    //Update Event Information
    public void updateEvent(String newTitle, String newTime, String newLocation){
        Title = newTitle;
        DateTime = newTime;
        Location = newLocation;
        System.out.println("Event " + Title + " has been updated");
    }

    //Cancel Event (Status = false) (Can be same for all types of events)
    public void cancelEvent(){
        this.Status = false;
        System.out.println("Event " + Title + " has been canceled");
    }

    //List Events
    public static void listEvents(){
        System.out.println("Event List: " + totalEventCount + " Events Total." );
        for(Event e : eventList){
            String state = e.Status ? "[ACTIVE]" : "[CANCELLED]";
            System.out.println(state + "ID:" + e.EventId + "| Title:" + e.Title + "| Location:" + e.Location);
        }
    }

    //Search and Filter (PHASE 2)


}
