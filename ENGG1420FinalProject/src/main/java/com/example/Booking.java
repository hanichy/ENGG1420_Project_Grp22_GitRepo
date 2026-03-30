package com.example;

import java.io.Serializable;
import java.util.ArrayList;

public class Booking implements Serializable {
    public String bookingId;
    public String userId;
    public String eventId;
    public String createdAt; //date that the user has booked an event
    public String bookingStatus;

    public static final String Status_CONFIRMED = "Confirmed";
    public static final String Status_WAITLISTED = "WaitListed";
    public static final String Status_CANCELLED = "Cancelled";


    public Booking(String bookingId, String userId, String eventId, String createdAt, String bookingStatus)
    {
        this.bookingId = bookingId;
        this.userId = userId;
        this.eventId = eventId;
        this.createdAt = createdAt;
        this.bookingStatus = bookingStatus;
    }

    //getters and setters
    public String getBookingStatus() {
        return bookingStatus;
    }
    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    public String getBookingId() {
        return bookingId;
    }
    public String getEventId() {
        return eventId;
    }
    public String getUserId() {
        return userId;
    }
    public String getCreatedAt(){
        return createdAt;
    }

}
