package com.example.bookingsys;

import java.util.ArrayList;
import java.util.Random;

import static com.example.bookingsys.Event.*;

public class Booking {
    public String bookingId;
    public String userId;
    public String eventId;
    public String createdAt; //date that the user has booked an event
    public String bookingStatus;

    protected static ArrayList<Booking> bookingList = new ArrayList<>();

    //for waitlist
    protected final ArrayList<Booking> confirmedBookings = new ArrayList<>();
    protected final ArrayList<Booking> waitlistBookings = new ArrayList<>();
    protected final Waitlist waitlist = new Waitlist();

    public static final String Status_CONFIRMED = "Confirmed";
    public static final String Status_WAITLISTED = "WaitListed";
    public static final String Status_CANCELLED = "Cancelled";

    protected int capacity;
    public boolean status; //True = active False = Cancelled


    public Booking(String bookingId, String userId, String eventId, String createdAt, String bookingStatus)
    {
        this.bookingId = bookingId;
        this.userId = userId;
        this.eventId = eventId;
        this.createdAt = createdAt;
        this.bookingStatus = bookingStatus;

        bookingList.add(this);
    }

    //getters and setters

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    public String getBookingStatus() {
        return bookingStatus;
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

    //put user out of waitlist when there's a spot
    private Booking promoteWaitlist (){
        Booking promoted = waitlist.RemRetBooking();
        if (promoted == null) {
            return null;
        }
        else{
            return promoted;
        }
    }
}

