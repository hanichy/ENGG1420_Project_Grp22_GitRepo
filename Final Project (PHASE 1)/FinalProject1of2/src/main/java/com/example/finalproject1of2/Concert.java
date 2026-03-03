package com.example.finalproject1of2;

public class Concert extends Event{
    public int ageRestriction;

    public String getAgeRestriction(){
        String aR = String.valueOf(ageRestriction);
        return aR;
    }

    public void updateEvent(String newTitle, String newTime, String newLocation,int newCapacity, int newAgeRestriction){
        super.updateEvent(newTitle, newTime, newLocation, newCapacity);
        this.ageRestriction = newAgeRestriction;
    }

    //Constructor
    public Concert(String title, String dateTime, String location, int capacity, int ageRestriction) {
        super(title, dateTime, location, capacity);
        this.ageRestriction = ageRestriction;
    }
}
