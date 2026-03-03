package com.example.bookingsys;

public class Seminar extends Event {
    public String speakerName;

    public String getSpeaker(){
        return speakerName;
    }

    public void updateEvent(String newTitle, String newTime, String newLocation,int newCapacity, String newSpeaker){
        super.updateEvent(newTitle, newTime, newLocation, newCapacity);
        this.speakerName = newSpeaker;
    }

    //Constructor
    public Seminar(String title, String dateTime, String location, int capacity, String speakerName) {
        super(title, dateTime, location, capacity);
        this.speakerName = speakerName;
    }
}
