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
private TextField eventTitle = new TextField();
    @FXML
private TextField eventDate = new TextField();
    @FXML
private TextField eventLocation = new TextField();
    @FXML
private TextField eventCapacity = new TextField();
private String eventType = "";
    @FXML
    private TextField eventTopic = new TextField();
    @FXML
    private TextField eventSpeaker = new TextField();
    @FXML
    private TextField eventAgeRestriction = new TextField();

    //Change to the thingy that it was before get...()
EventManagement event = EventManagement.getInstance();

    @FXML
    private void handleCreateEvent(){
        String title = eventTitle.getText();
        String date = eventDate.getText();
        String location = eventLocation.getText();
        String capacity = eventCapacity.getText();

        //Add something to catch cap<0 and cap != number
        int cap = Integer.parseInt(capacity);

        //Create Event based on user inputs
        if (eventType.equals("Workshop")){
            String topic = eventTopic.getText();
            Workshop workshop = new Workshop(title, date, location, cap, topic);
            event.createEvent(workshop);
        }
        else if (eventType.equals("Seminar"))
        {

        }
        else if (eventType.equals("Concert")){

        }
        else{
            //Input for type is wrong
            //Loop and fix it
        }

        //Add event successfully created statement
        //Clear old input (and makes specific type button invisible again)
        //If it does create event then put a mistake message
    }

    //Workshop button (Makes textField visible)
    @FXML
    public void workShopButton(ActionEvent e){
        if (eventType.equals("")){
            eventTopic.setOpacity(1);
            eventType = "Workshop";
        }
    }

    //Seminar button (Makes textField visible)
    @FXML
    public void seminarButton(ActionEvent e){
        if (eventType.equals("")){
            eventSpeaker.setOpacity(1);
            eventType = "Seminar";
        }
    }

    //Concert button (Makes textField visible)
    @FXML
    public void concertButton(ActionEvent e){
        if (eventType.equals("")){
            eventAgeRestriction.setOpacity(1);
            eventType = "Concert";
        }
    }

    //List Events

    //Sends user back to the Main Menu
    public void backToMenuE(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
