package com.example.bookingsys;

import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookingManagement {
    private static com.example.bookingsys.BookingManagement instance;
    private static ArrayList<Booking> bookingList;

    //Use ArrayList to Save the different bookingList
    //This will store the events with ArrayLists and CSV Files

    //Create Event Function Here
    public void bookEvent(Booking newBooking){
        bookingList.add(newBooking);
    }

    public static com.example.bookingsys.BookingManagement getInstance(){
        if (instance == null){
            instance = new com.example.bookingsys.BookingManagement();
        }
        return instance;
    }
    //Gives the ArrayList of the booking
    public ArrayList<Booking> getBookingList(){
        return bookingList;
    }

    //Constructor
    private BookingManagement(){
        this.bookingList = new ArrayList<>();
    }

    public static ArrayList<Booking> findByUserId(String userId) {
        ArrayList<Booking> userBookingList;
        userBookingList = new ArrayList<>();
        for (Booking b : bookingList) {
            if (b.userId.equals(userId)) {
                userBookingList.add(b);
            }
        }
        if (!userBookingList.isEmpty()){
            return userBookingList;
        }
        else{
            return null;
        }
    }

    public static ArrayList<Booking> findByEventId(String eventId) {
        ArrayList<Booking> eventBookingList;
        eventBookingList = new ArrayList<>();
        for (Booking b : bookingList) {
            if (b.eventId.equals(eventId)) {
                eventBookingList.add(b);
            }
        }
        if (!eventBookingList.isEmpty()){
            return eventBookingList;
        }
        else{
            return null;
        }
    }


}


