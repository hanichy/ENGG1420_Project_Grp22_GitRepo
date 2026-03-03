package com.example.bookingsys;

import java.util.ArrayList;

//Manages the Saving Events Asppect
//Specifically the List and File
public class EventManagement {
    private static EventManagement instance;
    private ArrayList<Event> eventList;

    //Use ArrayList to Save the different Events
    //This will store the events with ArrayLists and CSV FIles

    //Create Event Function Here
    public void createEvent(Event newEvent){
        //Check for capacity < 0
        //Check for Duplicate ID
        eventList.add(newEvent);
    }

    public static EventManagement getInstance(){
        if (instance == null){
            instance = new EventManagement();
        }
        return instance;
    }
    //Gives the ArrayList of the events
    public ArrayList<Event> getEventList(){
        return eventList;
    }

    //Constructor
    private EventManagement(){

        this.eventList = new ArrayList<>();
    }


}
