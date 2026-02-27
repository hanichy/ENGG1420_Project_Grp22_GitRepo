package com.example.finalproject1of2;

public class Workshop extends Event{
    public String Topic;

    //Constructor
    public Workshop(String eventId, String title, String dateTime, String location, int capacity, String topic) {
        super(eventId, title, dateTime, location, capacity);
        Topic = topic;
    }
}
