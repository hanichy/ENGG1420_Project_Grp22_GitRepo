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
    public static final String STATUS_CANCELLED = "Cancelled";

    protected int capacity;
    public boolean status; //True = active False = Cancelled


    public Booking(String bookingId, String userId, String eventId, String createdAt, String bookingStatus)
    {
        this.bookingId = uniqueBookingId();
        this.userId = userId;
        this.eventId = eventId;
        this.createdAt = createdAt;
        this.bookingStatus = bookingStatus;

        bookingList.add(this);
    }

    public void cancelBooking(){
        this.bookingStatus = "Cancelled";
    }
    public static Booking findBookingById(String id){
        for(Booking b : bookingList){
            if(b.bookingId.equals(id)){
                return b;
            }
        }
        return null;
    }

    //Generate random booking ID
    private String uniqueBookingId()
    {
        Random rand = new Random();
        String newId;
        do{
            int num = 300000 + rand.nextInt(900000);
            newId = String.valueOf(num);
        }while(findEventById(newId) == null);
        return newId;
    }

    // checks if the user already has a booking, if they're already in the waitlist and if they cancelled their booking
    private boolean hasActiveBookingForUser(String userId){
        for (Booking b : confirmedBookings) {
            if (b.userId.equals(userId) && !STATUS_CANCELLED.equals(b.bookingStatus)){
                return true;
            }
        }
        return false;
    }

    //booking method
    public Booking bookUser(String userId, String createdAt) {
        if (!this.status) throw new IllegalArgumentException("Event is cancelled. Cannot book.");
        //check if the user has proper data entered
        if (userId == null || userId.isBlank()) throw new IllegalArgumentException("userId required");
        if (createdAt == null || createdAt.isBlank()) throw new IllegalArgumentException("createdAt required");
        //makes sure the user doesn't already have a booking
        if (hasActiveBookingForUser(userId) || waitlist.containsUser(userId)) {
            throw new IllegalArgumentException("User already booked or waitlisted for this event.");
        }

        //checks if there are seats available
        if (confirmedBookings.size() < this.capacity) {
            Booking b = new Booking(bookingId, userId, eventId, createdAt, Status_CONFIRMED);
            confirmedBookings.add(b);
            return b;
        }
        //booking is created and status becomes confirmed
        else {
            Booking b = new Booking(bookingId, userId, eventId, createdAt, Status_WAITLISTED);
            waitlistBookings.add(b);
            return b;
        }
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

