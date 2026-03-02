package com.example.finalproject1of2;

public class Concert extends Event{
    public int ageRestriction;

    //Constructor
    public Concert(String title, String dateTime, String location, int capacity, int ageRestriction) {
        super(title, dateTime, location, capacity);
        this.ageRestriction = ageRestriction;
    }
}
