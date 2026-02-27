package com.example.finalproject1of2;

import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Manages the Saving Events Asppect
//Specifically the List and File
public class EventManagement {
    private List<Event> eventList;

    //Use ArrayList to Save the different Events
    //This will store the events with ArrayLists and CSV FIles

    //Create Event Function Here
    public void createEvent(Event newEvent){
        //Check for capacity < 0
        //Check for Duplicate ID
        eventList.add(newEvent);
    }

    //Constructor
    public EventManagement(){
        this.eventList = new ArrayList<>();
    }


}
