package com.example.bookingsys;

public class Booking {
    public String bookingId;
    public String userId;
    public String createdAt; //date that the user has booked an event
    public String bookingStatus;

    public Booking(String bookingId, String userId, String createdAt, String bookingStatus)
    {
        this.bookingId = bookingId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.bookingStatus = bookingStatus;
    }
}
