package com.example.bookingsys;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

//Gets Everything Set Up
public abstract class Event {
    //Objects
    public String eventId;
    public String title;
    public String dateTime; //DD/MM/YYYY Hour:Min (Will probably have to change cuz time is a pain)
    public String location;
    protected int capacity;
    public boolean status; //True = active False = Cancelled

    protected static int totalCapacityCount = 0;
    protected static int totalEventCount = 0;
    protected static ArrayList<Event> eventList = new ArrayList<>();

    //for waitlist
    protected final ArrayList<Booking> confirmedBookings = new ArrayList<>();
    protected final ArrayList<Booking> waitlistBookings = new ArrayList<>();
    protected final Waitlist waitlist = new Waitlist();

    public static final String Status_CONFIRMED = "Confirmed";
    public static final String Status_WAITLISTED = "WaitListed";
    public static final String STATUS_CANCELLED = "Cancelled";

    private static int bookingCounter = 1;


    //Constructor
    public Event(String title, String dateTime, String location, int capacity) {
        if(capacity <= 0){
            throw new IllegalArgumentException("Error: Capacity must be greater than zero");
        }
        this.eventId = uniqueId();

        this.title = title;
        this.dateTime = dateTime;
        this.location = location;
        this.capacity = capacity;
        this.status = true;

        eventList.add(this);
        totalEventCount++;
    }

    //Getters
    public String getDateTime() {
        return dateTime;
    }
    public String getEventId() {
        return eventId;
    }
    public String getLocation() {
        return location;
    }
    public String getTitle() {
        return title;
    }
    public int getCapacity() {
        return capacity;
    }
    public boolean getStatus() {
        return status;
    }

    //Generate random event id
    private String uniqueId(){
        Random rand = new Random();
        String newId;
        do {
            int num = 100000 + rand.nextInt(900000);
            newId = String.valueOf(num);
        } while(findEventById(newId) != null);
        return newId;

    }
    //find event by Id num to make sure each Id is unique
    public static Event findEventById(String id){
        for(Event e : eventList){
            if(e.eventId.equals(id)){
                return e;
            }
        }
        return null;
    }

    //Update Event Information
    public void updateEvent(String newTitle, String newTime, String newLocation, int newCapacity){
        title = newTitle;
        dateTime = newTime;
        location = newLocation;
        capacity = newCapacity;
        System.out.println("Event " + title + " has been updated");
    }

    //Cancel Event (Status = false) (Can be same for all types of events)
    public boolean cancelEvent(){
        this.status = false;
        System.out.println("Event " + title + " has been canceled");
        return false;
    }

    //List Events
    public static void listEvents(){
        System.out.println("Event List: " + totalEventCount + " Events Total." );
        for(Event e : eventList){
            String state = e.status ? "[ACTIVE]" : "[CANCELLED]";
            System.out.println(state + "ID:" + e.eventId + "| Title:" + e.title + "| Location:" + e.location);
        }
    }
    
    //Search and Filter (PHASE 2)
    //Search by title
    public static ArrayList<Event> searchByTitle(String title){
        ArrayList<Event> result = new ArrayList<>();
        for(Event e : eventList){
            if(e.title.toLowerCase().contains(title.toLowerCase())){
                result.add(e);
            }
        }
        return result;
    }

    //Filter by Type
    public static <T extends Event> ArrayList<T> filterByType(Class<T> type){
        ArrayList<T> filteredResult = new ArrayList<>();
        for(Event e : eventList){
            if(type.isInstance(e)){
                filteredResult.add(type.cast(e));
            }
        }
        return filteredResult;
    }

    //Generate booking ID
    private String generateBookingId()
    {

        return "Booking ID: B" + (bookingCounter++);
    }

    // checks if the user already has a booking, if they're already
    // in the waitlist and if they cancelled their booking
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
        //creates booking ID
        String bookingId = generateBookingId();

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
