package com.example.bookingsys;

public class Concert extends Event{
    public String ageRestriction;

    public String getAgeRestriction(){
        String aR = String.valueOf(ageRestriction);
        return aR;
    }

    public void updateEvent(String newTitle, String newTime, String newLocation,int newCapacity, String newAgeRestriction){
        super.updateEvent(newTitle, newTime, newLocation, newCapacity);
        this.ageRestriction = newAgeRestriction;
    }

    //Constructor
    public Concert(String title, String dateTime, String location, int capacity, String ageRestriction) {
        super(title, dateTime, location, capacity);
        this.ageRestriction = ageRestriction;
    }
}
