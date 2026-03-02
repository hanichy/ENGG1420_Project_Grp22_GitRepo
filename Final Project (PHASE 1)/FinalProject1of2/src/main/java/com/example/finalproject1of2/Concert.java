package com.example.finalproject1of2;

public class Concert extends Event{
    public int AgeRestriction;

    //Constructor
    public Concert(String title, String dateTime, String location, int capacity, int ageRestriction) {
        super(title, dateTime, location, capacity);
        this.AgeRestriction = ageRestriction;
    }
}
