package com.example.finalproject1of2;

public class Workshop extends Event{
    public String Topic;

    //Constructor
    public Workshop(String title, String dateTime, String location, int capacity, String topic) {
        super(title, dateTime, location, capacity);
        this.Topic = topic;
    }
}
