package com.example.bookingsys;

public class Workshop extends Event {
    private String topic;

    public Workshop(String title, String dateTime, String location, int capacity, String topic) {
        super(title, dateTime, location, capacity);
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void updateEvent(String newTitle, String newTime, String newLocation, int newCapacity, String newTopic) {
        super.updateEvent(newTitle, newTime, newLocation, newCapacity);
        this.topic = newTopic;
    }
}