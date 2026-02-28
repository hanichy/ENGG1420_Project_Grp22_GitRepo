//This Class is to control the UI for the events section
//Hopefully with Dynamic UI it can be different scene for each section
//Ex showing the text fields for create Event Button vs showing just a list of events for the list events button
package com.example.finalproject1of2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

//For What Each Button Will Do
public class EventsPageController {
    private Stage stage;
    private Scene scene;
    private Parent root;
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
    public void backToMenuE(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
