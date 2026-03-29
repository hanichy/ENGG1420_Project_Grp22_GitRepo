package com.example;

import java.io.Serializable;
import java.util.ArrayList;

public class BookingManagement implements Serializable {
    private static final long serialVersionUID = 1L;
    private static BookingManagement instance;
    //Use Waitlist to save the booking logic here
    private Waitlist waitlist;
    //Constructor
    private BookingManagement(){
        this.waitlist = Waitlist.getInstance();
    }
    public static BookingManagement getInstance(){
        if(instance == null){
            instance = new BookingManagement();
        }
        return instance;
    }

    //startup the file function
    public void startup(){
        waitlist.startup();
    }

    //Create booking Function Here
    public Booking createBooking(User user, Event event){
        return waitlist.createBooking(user, event);
    }

    public Booking createBookingByIds(String userId, String eventId){
        return waitlist.createBookingByIds(userId, eventId);
    }

    public Booking cancelBooking(String bookingId){
        return waitlist.cancelBooking(bookingId);
    }

    public Booking promoteNextBooking(String eventId){
        return waitlist.promoteNextBooking(eventId);
    }

    public Booking findBookingById(String id){
        return waitlist.findBookingById(id);
    }

    public boolean canUserBookMore(User user){
        return waitlist.canUserBookMore(user);
    }

    public boolean isAlreadyBooked(String userId, String eventId){
        return waitlist.hasActiveBookingForUser(userId, eventId);
    }

    public int countConfirmedForEvent(String eventId){
        return waitlist.countConfirmedForEvent(eventId);
    }

    public ArrayList<Booking> getBookingList() {
        return waitlist.getBookingList();
    }
}
