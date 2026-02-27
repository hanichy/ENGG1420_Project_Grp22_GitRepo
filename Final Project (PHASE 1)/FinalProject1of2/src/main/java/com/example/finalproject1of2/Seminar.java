package com.example.finalproject1of2;

public class Seminar extends Event {
    public String SpeakerName;

    //Constructor
    public Seminar(String eventId, String title, String dateTime, String location, int capacity, String speakerName) {
        super(eventId, title, dateTime, location, capacity);
        SpeakerName = speakerName;
    }
}
