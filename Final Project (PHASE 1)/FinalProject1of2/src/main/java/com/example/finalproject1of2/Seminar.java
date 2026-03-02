package com.example.finalproject1of2;

public class Seminar extends Event {
    public String SpeakerName;

    //Constructor
    public Seminar(String title, String dateTime, String location, int capacity, String speakerName) {
        super(title, dateTime, location, capacity);
        this.SpeakerName = speakerName;
    }
}
