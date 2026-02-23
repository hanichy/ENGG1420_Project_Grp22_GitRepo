package com.example.finalproject1of2;

public class EventManagement {

    //Use ArrayList to Save the different Events

    //Objects
    protected int EventId;
    public String Title;
    public int DateTime; //DD/MM/YYYY Hour:Min
    public String Location;
    public int Capacity;
    public boolean Status; //True = active False = Cancelled

    //Create an event

    //Update Event Information

    //Cancel Event (Status = false) (Can be same for all types of events)

    //List Events

    //Search and Filter (PHASE 2)

    public EventManagement(int eventId, String title, int dateTime, String location, int capacity){
        EventId = eventId;
        Title = title;
        DateTime = dateTime;
        Location = location;
        Capacity = capacity;
        Status = true;
    }

}
