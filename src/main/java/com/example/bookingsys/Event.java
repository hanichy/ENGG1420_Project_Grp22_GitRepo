package com.example.bookingsys;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

//Gets Everything Set Up
//Essentially a Blueprint
public abstract class Event {
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
        this.eventId = uniqueId();

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

    //Generate random event id
    private String uniqueId(){
        Random rand = new Random();
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


}
