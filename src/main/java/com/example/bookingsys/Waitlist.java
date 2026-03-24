package com.example.bookingsys;

import java.io.Serializable;
import java.util.ArrayList;

public class Waitlist implements Serializable {
    private static final long serialVersionUID = 1L;

    //list of bookings that in the waitlist status
    private final ArrayList<Booking> waitlistBookings = new ArrayList<>();

    //checks if the waitlist is null
    //if it is not then it removes and returns index 0
    public Booking RemRetBooking(){
        if (waitlistBookings.isEmpty()) {
            return null;
        }
        return waitlistBookings.remove(0);
    }

    //add a new booking to the end of the waitlist
    public void adBooking(Booking booking){
        if(booking != null){
            waitlistBookings.add(booking);
        }
    }

    //checks for null bookingId
    //loops through list to find the id that the user wants to delete
    //if the booking id exist, delete
    //if it's not found return null
    public Booking removeById(String bookingId){
        if (bookingId == null || bookingId.isBlank()){
            throw new IllegalArgumentException("bookingId required");
        }

        for (int i =0; i < waitlistBookings.size(); i++){
            Booking b = waitlistBookings.get(i);
            if (b.bookingId.equals(bookingId)){
                return waitlistBookings.remove(i);
            }
        }
        return null;
    }

    //checks through waitlist for any booking that has the same user id
    //if there is the same userid and it sint cancelled then it returns true, owtherwise false
    public boolean containsUser(String userId) {
        for (Booking b : waitlistBookings){
            //check for active waitlisted status only
            if (b.userId.equals(userId) && Booking.Status_WAITLISTED.equals(b.getBookingStatus())) {
                return true;
            }
        }
        return false;
    }

    //size check
    public int size() {

        return waitlistBookings.size();
    }

    //returns a copy of the waitlist
    public ArrayList<Booking> asListCopy()
    {
        return new ArrayList<>(waitlistBookings);
    }
    //clears waitlist
    public void clear()
    {
        waitlistBookings.clear();
    }


}


