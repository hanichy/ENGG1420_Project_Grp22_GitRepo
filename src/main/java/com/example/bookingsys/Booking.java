package com.example.bookingsys;

import java.util.ArrayList;
import java.util.Random;

public class Booking {
    public String bookingId;
    public String userId;
    public String eventId;
    public String createdAt; //date that the user has booked an event
    public String bookingStatus;
    protected static ArrayList<Booking> bookingList = new ArrayList<>();

    public Booking(String bookingId, String userId, String eventId, String createdAt, String bookingStatus)
    {
        this.bookingId = bookingId;
        this.userId = userId;
        this.eventId = eventId;
        this.createdAt = createdAt;
        this.bookingStatus = bookingStatus;
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
    private String uniqueId(){
        Random rand = new Random();
        String newId;
        do {
            int num = 100000 + rand.nextInt(900000);
            newId = String.valueOf(num);
        } while(findBookingById(newId) != null);
        return newId;
    }
}
