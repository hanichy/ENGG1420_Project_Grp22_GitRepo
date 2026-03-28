package com.example.bookingsys;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedWriter;

//Manages the Saving Events Aspect
//Specifically the List and File
public class EventManagement implements Serializable {
    private static final long serialVersionUID = 1L;
    private static EventManagement instance;
    private ArrayList<Event> eventList;

    //Use ArrayList to Save the different Events
    //This will store the events with ArrayLists and CSV Files

    //Constructor
    private EventManagement()
    {
        this.eventList = new ArrayList<>();
    }

    public static EventManagement getInstance()
    {
        if (instance == null){
            instance = new EventManagement();
        }
        return instance;
    }

    //startup functions
    public void startup(){
        String stateFile = "system_state.ser";
        File file = new File(stateFile);

        if(file.exists()){
            restoreFullSystemState(stateFile);
        }
        else{
            loadEventsFromCSV("events.csv");
        }
    }

    //file persistence
    //save whole event state
    public void saveEventState(String fileName){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))){
            for (Event event : eventList){
                StringBuilder sb = new StringBuilder();

                sb.append(event.getEventId()).append(",");
                sb.append(event.getTitle()).append(",");
                sb.append(event.getDateTime()).append(",");
                sb.append(event.getLocation()).append(",");
                sb.append(event.getCapacity()).append(",");
                sb.append(event.status ? "Active" : "Cancelled").append(",");

                // Subtype specific logic (Columns 7-9)
                if (event instanceof Workshop)
                {
                    sb.append("Workshop,").append(((Workshop) event).getTopic()).append(",,");
                }
                else if (event instanceof Seminar) {
                    sb.append("Seminar,,").append(((Seminar) event).getSpeaker()).append(",");
                }
                else if (event instanceof Concert) {
                    sb.append("Concert,,,") .append(((Concert) event).getAgeRestriction());
                }

                bw.write(sb.toString());
                bw.newLine();
            }
            System.out.println("Event saved successfully");
        } catch(IOException e){
            System.err.println("Error saving events to file: " + e.getMessage());
        }
    }

    //saves the lists of events in file
    public void saveFullSystemState(String fileName){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))){
            oos.writeObject(eventList);
            System.out.println("Event saved successfully");
        }catch(IOException e){
            System.err.println("Error saving events to file: " + e.getMessage());
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
            eventList = (ArrayList<Event>) ois.readObject();
            System.out.println("Events restored successfully from " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error restoring events from file");
        }
    }

    //CSV loading
    public void loadEventsFromCSV(String fileName){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;
            br.readLine(); //skip event info

            while((line = br.readLine()) != null){
                String[] data = line.split(",", -1); // keep empty trailing columns

                if(data.length < 7){
                    continue;
                }

                //Mapping columns based on event info
                String eventId = data[0];
                String title = data[1];
                String dateTime = data[2];
                String location = data[3];
                int capacity = Integer.parseInt(data[4].trim());
                String statusValue = data[5];
                String type = data[6];

                Event newEvent = null;

                switch(type){
                    case "Workshop":
                        String topic =  data[7];
                        if(topic.isEmpty()) throw new IllegalArgumentException("Error: Topic is empty");
                        newEvent = new Workshop(eventId, title, dateTime, location, capacity, topic);
                        getInstance().getEventList().add(newEvent);
                        break;

                    case "Seminar":
                        String speaker = data[8];
                        if(speaker.isEmpty()) throw new IllegalArgumentException("Error: Speaker is empty");
                        newEvent = new Seminar(eventId, title, dateTime, location, capacity, speaker);
                        getInstance().getEventList().add(newEvent);
                        break;

                    case "Concert":
                        String ageReq =  data[9];
                        if(ageReq.isEmpty()) throw new IllegalArgumentException("Error: Concert requires age restriction");
                        newEvent = new Concert(eventId, title, dateTime, location, capacity, ageReq);
                        getInstance().getEventList().add(newEvent);
                        break;
                }
                if(newEvent != null && statusValue.equalsIgnoreCase("Cancelled")){
                    newEvent.cancelEvent();
                }
            }
        } catch(IOException e){
            System.err.println("Error saving events to file: " + e.getMessage());
        }
    }

    //create events
    public void createEvent(Event newEvent){
        this.eventList.add(newEvent);
        System.out.println("Event created successfully");
    }

    //Search and Filter (PHASE 2)
    //Search by title
    public ArrayList<Event> searchByTitle(String title){
        ArrayList<Event> result = new ArrayList<>();
        for(Event e : eventList){
            if(e.title.toLowerCase().contains(title.toLowerCase())){
                result.add(e);
            }
        }
        return result;
    }

    //Filter by Type
    public <T extends Event> ArrayList<T> filterByType(Class<T> type){
        ArrayList<T> filteredResult = new ArrayList<>();
        for(Event e : eventList){
            if(type.isInstance(e)){
                filteredResult.add(type.cast(e));
            }
        }
        return filteredResult;
    }

    //Gives the ArrayList of the events
    public ArrayList<Event> getEventList(){
        return eventList;
    }

    //generates a unique event id for each event
    public String uniqueEventID(){
        Random rand = new Random();
        String newId;
        boolean isDuplicate;

        do{
            isDuplicate = false;
            newId = "E" +(100 + rand.nextInt(999));

            for(Event e : eventList){
                if(e.getEventId().equals(newId)){
                    isDuplicate = true;
                }
            }
        }while(isDuplicate);
        return newId;
    }

    //make sure every event id is unique
    public Event findEventById(String eventId){
        if(eventId == null){
            return null;
        }
        for(Event e : eventList){
            if(e.getEventId().equalsIgnoreCase(eventId)){
                return e;
            }
        }
        return null;
    }

    //cancelled event
    public void cancelEvent(String eventId){
        Event e = findEventById(eventId);
        if(e != null){
            e.cancelEvent();
            e.setStatus(false);
            saveFullSystemState("system_state.ser");
        }
    }

    //List Events
    public void listEvents(){
        System.out.println("Event List: " + eventList.size());
        if(eventList.isEmpty()){
            System.out.println("No events found");
            return;
        }

        for(Event e : eventList){
            String state = e.status ? "[ACTIVE]" : "[CANCELLED]";
            System.out.println(state + "ID:" + e.getEventId() + "| Title:" + e.getTitle() + "| Location:" + e.getLocation());
        }
    }

}
