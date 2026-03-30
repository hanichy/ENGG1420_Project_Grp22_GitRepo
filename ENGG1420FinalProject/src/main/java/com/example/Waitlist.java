package com.example;

import java.io.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Waitlist implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Waitlist instance;

    //Use ArrayList to save all the bookings
    private ArrayList<Booking> bookingList;

    //Use Map to store waitlist bookings for each event
    private Map<String, ArrayList<Booking>> waitlistMap;

    //save the promoted booking after cancellation
    private Booking lastPromotedBooking;

    //Constructor
    private Waitlist(){
        this.bookingList = new ArrayList<>();
        this.waitlistMap = new HashMap<>();
        this.lastPromotedBooking = null;
    }

    public static Waitlist getInstance(){
        if(instance == null){
            instance = new Waitlist();
        }
        return instance;
    }
    //startup the file function
    public void startup(){
        String csvFile = "bookings.csv";
        File file = new File(csvFile);

        if(file.exists()){
            loadBookingsFromCSV(csvFile);
        }
        else{
            loadBookingsFromCSV("booking.csv");
        }
    }
    //save bookings to csv file
    public void saveBookingsToCSV(String fileName){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))){
            bw.write("bookingId,userId,eventId,createdAt,bookingStatus");
            bw.newLine();
            for(Booking b : bookingList){
                StringBuilder sb = new StringBuilder();
                sb.append(b.getBookingId()).append(",");
                sb.append(b.getUserId()).append(",");
                sb.append(b.getEventId()).append(",");
                sb.append(b.createdAt).append(",");
                sb.append(b.getBookingStatus());
                bw.write(sb.toString());
                bw.newLine();
            }
            System.out.println("Bookings saved successfully");
        }catch(IOException e){
            System.err.println("Error saving bookings to file: " + e.getMessage());
        }
    }
    //CSV loading
    public void loadBookingsFromCSV(String fileName){
        bookingList.clear();
        waitlistMap.clear();
        lastPromotedBooking = null;
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;
            br.readLine();
            while((line = br.readLine()) != null){
                String[] data = line.split(",", -1);
                if(data.length < 5){
                    continue;
                }
                String bookingId = data[0];
                String userId = data[1];
                String eventId = data[2];
                String createdAt = data[3];
                String status = data[4];
                Booking b = new Booking(bookingId, userId, eventId, createdAt, status);
                bookingList.add(b);
                if(status.equals(Booking.Status_WAITLISTED)){
                    getWaitlistForEventInternal(eventId).add(b);
                }
            }
        }catch(IOException e){
            System.err.println("Error loading bookings from file: " + e.getMessage());
        }
    }
    //create booking function here
    public Booking createBooking(User user, Event event){
        if(user == null){
            throw new IllegalArgumentException("User not found.");
        }
        if(event == null){
            throw new IllegalArgumentException("Event not found.");
        }
        if(!event.getStatus()){
            throw new IllegalArgumentException("Cannot book a cancelled event.");
        }
        if(!canUserBookMore(user)){
            throw new IllegalArgumentException(user.getUserType() + " has reached their maximum booking limit");
        }
        if(hasActiveBookingForUser(user.getUserId(), event.getEventId())){
            throw new IllegalArgumentException("Event had been registered");
        }

        String status = Booking.Status_CONFIRMED;
        if(countConfirmedForEvent(event.getEventId()) >= event.getCapacity()){
            status = Booking.Status_WAITLISTED;
        }
        String bId = uniqueBookingId();
        Booking newBooking = new Booking(bId, user.getUserId(), event.getEventId(), LocalDateTime.now().toString(), status);
        bookingList.add(newBooking);
        if(status.equals(Booking.Status_WAITLISTED)){
            getWaitlistForEventInternal(event.getEventId()).add(newBooking);
        }
        saveBookingsToCSV("bookings.csv");
        return newBooking;
    }
    //create booking by using ids
    public Booking createBookingByIds(String userId, String eventId){
        User user = UserManager.getInstance().findUserById(userId);
        Event event = EventManagement.getInstance().findEventById(eventId);
        return createBooking(user, event);
    }
    //cancel booking and promote next waitlisted booking
    public Booking cancelBooking(String bookingId){
        Booking toCancel = findBookingById(bookingId);
        if(toCancel == null){
            throw new IllegalArgumentException("Booking ID not found.");
        }
        if(toCancel.getBookingStatus().equals(Booking.Status_CANCELLED)){
            throw new IllegalArgumentException("Booking is already cancelled.");
        }
        String oldStatus = toCancel.getBookingStatus();
        lastPromotedBooking = null;
        toCancel.setBookingStatus(Booking.Status_CANCELLED);
        if(oldStatus.equals(Booking.Status_WAITLISTED)){
            removeWaitlistedBooking(toCancel);
        }
        else if(oldStatus.equals(Booking.Status_CONFIRMED)){
            promoteNextBooking(toCancel.getEventId());
        }

        saveBookingsToCSV("bookings.csv");
        return toCancel;
    }

    //promote one person only to fill one empty spot
    public Booking promoteNextBooking(String eventId){
        ArrayList<Booking> eventWaitlist = getWaitlistForEventInternal(eventId);

        while(!eventWaitlist.isEmpty()){
            Booking nextBooking = eventWaitlist.remove(0);

            if(nextBooking.getBookingStatus().equals(Booking.Status_WAITLISTED)){
                nextBooking.setBookingStatus(Booking.Status_CONFIRMED);
                lastPromotedBooking = nextBooking;
                saveBookingsToCSV("bookings.csv");
                return nextBooking;
            }
        }
        return null;
    }

    //find booking by id to make sure every id is unique
    public Booking findBookingById(String id){
        if(id == null){
            return null;
        }
        for(Booking booking : bookingList){
            if(booking.getBookingId().equalsIgnoreCase(id)){
                return booking;
            }
        }
        return null;
    }

    //check if user reached max bookings
    public boolean canUserBookMore(User user){
        int count = 0;
        for(Booking booking : bookingList){
            if(booking.getUserId().equals(user.getUserId()) && booking.getBookingStatus().equals(Booking.Status_CONFIRMED)){
                count++;
            }
        }
        return true;//count< user.getMaxBookings();
    }

    //check if the booking is booked by the user
    public boolean hasActiveBookingForUser(String userId, String eventId){
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

    //get promoted booking after cancellation
    public Booking getLastPromotedBooking(){
        return lastPromotedBooking;
    }

    public ArrayList<Booking> getBookingList() {
        return bookingList;
    }

    //return copy of waitlist for an event
    public ArrayList<Booking> getWaitlistForEvent(String eventId){
        return new ArrayList<>(getWaitlistForEventInternal(eventId));
    }

    //helper function to get waitlist for event
    private ArrayList<Booking> getWaitlistForEventInternal(String eventId){
        ArrayList<Booking> eventWaitlist = waitlistMap.get(eventId);
        if(eventWaitlist == null){
            eventWaitlist = new ArrayList<>();
            waitlistMap.put(eventId, eventWaitlist);
        }
        return eventWaitlist;
    }

    public Map<String, ArrayList<Booking>> getWaitlistMap() {
        return waitlistMap;
    }

    //remove waitlisted booking from waitlist if cancelled before promotion
    public void removeWaitlistedBooking(Booking booking){
        ArrayList<Booking> eventWaitlist = waitlistMap.get(booking.getEventId());
        if(eventWaitlist == null){
            return;
        }

        for(int i = 0; i < eventWaitlist.size(); i++){
            Booking b = eventWaitlist.get(i);
            if(b.getBookingId().equalsIgnoreCase(booking.getBookingId())){
                eventWaitlist.remove(i);
                break;
            }
        }
    }
    //generate a unique booking id
    private String uniqueBookingId(){
        Random random = new Random();
        String newId;
        do{
            newId = "B" + (1000 + random.nextInt(9000));
        }while(findBookingById(newId) != null);
        return newId;
    }
}
