package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EventsPageController {
    //TextFields
    @FXML
    private TextField eventTitle;
    @FXML
    private TextField eventLocation;
    @FXML
    private DatePicker eventDate;
    @FXML
    private TextField eventCapacity;
    @FXML
    private TextField eventSpeaker;
    @FXML
    private TextField eventTopic;
    @FXML
    private TextField eventAgeRestriction;
    @FXML
    private TextField eventID;

    //Event Type Storage
    private String eventType ="";

    //Needed to Switch Scenes
    private Stage stage;
    private Scene scene;
    private Parent root;

    //Back to Main Menu
    @FXML
    private void backToMenu(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuPHASE2.fxml"));
        root = loader.load();

        MainMenuController mainMenuController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        System.out.println("Switching...");
    }

    //CREATE EVENT
    //Seminar Button
    @FXML
    public void seminarButton(ActionEvent e){
        if (eventType.equals("")){
            eventSpeaker.setOpacity(1);
            eventType = "Seminar";
        }
    }
    //Workshop Button
    @FXML
    public void workShopButton(ActionEvent e){
        if (eventType.equals("")){
            eventTopic.setOpacity(1);
            eventType = "Workshop";
        }
    }
    //Concert Button
    @FXML
    public void concertButton(ActionEvent e){
        if (eventType.equals("")){
            eventAgeRestriction.setOpacity(1);
            eventType = "Concert";
        }
    }
    //Create Event Button

    //UPDATE EVENT INFORMATION

    //CANCEL EVENT

    //VIEW EVENT ROSTER

    //SEARCH EVENTS BY TITLE
}
