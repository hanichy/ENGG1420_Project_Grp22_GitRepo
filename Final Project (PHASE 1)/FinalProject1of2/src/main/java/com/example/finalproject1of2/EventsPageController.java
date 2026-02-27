//This Class is to control the UI for the events section
//Hopefully with Dynamic UI it can be different scene for each section
//Ex showing the text fields for create Event Button vs showing just a list of events for the list events button
package com.example.finalproject1of2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

//For What Each Button Will Do
public class EventsPageController {
    //The Text fields to create/update an event
    @FXML
private TextField eventTitle = new TextField("Title");
private TextField eventDate = new TextField("Date (YYYY/MM/DD)");
private TextField eventLocation = new TextField("Location");
private TextField eventCapacity = new TextField("Capacity");
private TextField eventType = new TextField("Workshop/Seminar/Concert");
EventManagement event = new EventManagement();
    @FXML
    private void handleCreateEvent(){
        String type = eventType.getText();
        String title = eventTitle.getText();
        String date = eventDate.getText();
        String location = eventLocation.getText();
        String capacity = eventCapacity.getText();

        //Create Event based on user inputs
        if (type.equals("Workshop") || type.equals("workshop")){
            String topic = "Fart";
            int cap = Integer.parseInt(capacity);
            Workshop workshop = new Workshop("ID", title, date, location, cap, topic);
            event.createEvent(workshop);
        }
        else if (type.equals("Seminar")|| type.equals("seminar"))
        {

        }
        else if (type.equals("Concert")|| type.equals("concert")){

        }
        else{
            //Input for type is wrong
            //Loop and fix it
        }

    }

}
