package com.example.bookingsys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

public class BookingManagement implements Serializable {
    private static final long serialVersionUID = 1L;
    private static com.example.bookingsys.BookingManagement instance;

    //Use ArrayList to Save the different bookingList
    private static ArrayList<Booking> bookingList;

    //Create booking Function Here
    //Constructor
    private BookingManagement(){
        this.bookingList = new ArrayList<>();
    }

    public static BookingManagement getInstance(){
        if(instance == null){
            instance = new BookingManagement();
        }
        return instance;
    }

    //startup the file function
    public void startup(){
        String stateFile = "booking_state.ser";
        File file = new File(stateFile);

        if(file.exists()){
            restoreFullSystemState(stateFile);
        }
        else{
            loadBookingsFromCSV("booking.csv");
        }
    }

    //File persistence
    public void saveBookingState(String fileName){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))){
            oos.writeObject(bookingList);
        }catch(IOException e){
            System.err.println("Error printing events to file" + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    //function to restore file
    public void restoreFullSystemState(String fileName){
        File file = new File(fileName);
        if(!file.exists()){
            return;
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))){
            //restore static list
            bookingList = (ArrayList<Booking>) ois.readObject();
            System.out.println("Events restored successfully from" + fileName);
        }catch(IOException | ClassNotFoundException e){
            System.err.println("Error printing events to file" + e.getMessage());
        }
    }

    //CSV loading
    public void loadBookingsFromCSV(String fileName){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;
            br.readLine();

            while((line = br.readLine()) != null){
                String[] data = line.split(",", -1);

                if(data.length < 6){
                    continue;
                }

                //mapping columns based on booking info
                String userId = data[0];
                String eventId = data[1];
                String bookingId = data[2];
                String date = data[3];
                String status = data[4];

                Booking b = new  Booking(eventId, userId, bookingId, date, status);
                bookingList.add(b);
            }
        }catch(IOException e){
            System.err.println("Error saving user to file");
        }
    }

    //Core booking logic
    public void createBooking(User user, Event event){
        //check for user type limit
        if(!canUserBookMore(user)){
            throw new IllegalArgumentException(user.getUserType() + "has reached their maximum booking limit");
        }
        //check for duplicates
        if(isAlreadyBooked(user.getUserId(), event.getEventId())){
            throw new IllegalArgumentException("Event had been registered");
        }
        //determine status based on event capacity
        String status = Booking.Status_CONFIRMED;
        if(countConfirmedForEvent(event.getEventId()) >= event.getCapacity()){
            status = Booking.Status_WAITLISTED;
        }
        //create and add
        String bId  = uniqueBookingId();
        Booking newBooking = new Booking(bId, user.getUserId(), event.getEventId(), event.getDateTime(), status);

        bookingList.add(newBooking);
        saveBookingState("booking_state.ser");
    }
    //helper functions

    //check if user reached max bookings
    public boolean canUserBookMore(User user){
        int count = 0;
        for(Booking booking : bookingList){
            if(booking.getUserId().equals(user.getUserId()) && booking.getBookingStatus().equals(Booking.Status_CONFIRMED)){
                count++;
            }
        }
        return count < user.getMaxBookings();
    }

    //check if the booking is booked by the user
    public boolean isAlreadyBooked(String userId, String eventId){
        for(Booking booking : bookingList){
            if(booking.getUserId().equals(userId) && booking.getEventId().equals(eventId) && !booking.getBookingStatus().equals(Booking.Status_CANCELLED)){
                return true;
            }
        }
        return false;
    }

    //count the number of event that have been confirmed
    public int countConfirmedForEvent(String eventId){
        int count =0;
        for(Booking booking : bookingList){
            if(booking.getEventId().equals(eventId) && booking.getBookingStatus().equals(Booking.Status_CONFIRMED)){
                count++;
            }
        }
        return count;
    }

    //generate a unique booking id
    private String uniqueBookingId(){
        Random random = new Random();
        String newId;
        do{
            newId = "B" + (100+random.nextInt(999));
        }while(findBookingById(newId) != null);
        return newId;
    }
    //find booking by id to make sure every id is unique
    public Booking findBookingById(String id){
        for(Booking booking : bookingList){
            if(booking.getBookingId().equalsIgnoreCase(id)){
                return booking;
            }
        }
        return null;
    }

    //create and promote user from waitlist
    public void cancelAndPromote(String bookingId){
        Booking toCancel = findBookingById(bookingId);

        if(toCancel != null && !toCancel.getBookingStatus().equals(Booking.Status_CANCELLED)){
            //mark the current booking as Cancelled
            toCancel.setBookingStatus(Booking.Status_CANCELLED);
            String eventId = toCancel.getEventId();

            //find the next user on the waitlist to promote to this event
            for(Booking booking : bookingList){
                if(booking.getEventId().equals(eventId) &&  booking.getBookingStatus().equals(Booking.Status_WAITLISTED)){
                    booking.setBookingStatus(Booking.Status_CONFIRMED);
                    System.out.println("Booking has been confirmed");

                    //promote one person only to fill onr empty spot
                    break;
                }
            }
            //save updated state to file
            saveBookingState("booking_state.ser");
        }
    }

    public static ArrayList<Booking> getBookingList() {
        return bookingList;
    }
}


