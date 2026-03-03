package com.example.bookingsys;
import java.util.ArrayList;
public class Waitlist {

    //
    private final ArrayList<Booking> waitlistBookings = new ArrayList<>();

    //checks if the waitlist is null
    //if it is not then it removes and returns indez 0
    public Booking RemRetBooking(){
        if (waitlistBookings.isEmpty()) {
            return null;
        }
        return waitlistBookings.remove(0);
    }

    //checks for null bookingid
    //loops through list to find the id that the user wants to delete
    //if the booking id exist, delete
    //if its not found return null
    public Booking removeId(String bookingId){
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
            if (b.userId.equals(userId) && !"Cancelled".equals(b.bookingStatus)) {
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
    public ArrayList<Booking> asListCopy(){
        return new ArrayList<>(waitlistBookings);
    }
    //clears waitlist
    public void clear(){
        waitlistBookings.clear();
    }


}


