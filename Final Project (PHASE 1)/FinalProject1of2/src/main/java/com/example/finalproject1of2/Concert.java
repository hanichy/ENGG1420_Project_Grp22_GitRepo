package com.example.finalproject1of2;

public class Concert extends Event{
    public int AgeRestriction;

    //Constructor
    public Concert(String eventId, String title, String dateTime, String location, int capacity, int ageRestriction) {
        super(eventId, title, dateTime, location, capacity);
        AgeRestriction = ageRestriction;
    }
}
