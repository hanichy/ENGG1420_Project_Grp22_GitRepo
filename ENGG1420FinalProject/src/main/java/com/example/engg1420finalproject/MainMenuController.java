package com.example.engg1420finalproject;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MainMenuController{
    //Create VBox
    @FXML
    private VBox drawer = new VBox();

    //Open/Close Side Menu
    @FXML
    private void openMenu(ActionEvent e){
        //Check if Menu is already open
        double targetX = (drawer.getTranslateX() !=0)?0:-drawer.getPrefWidth();

        //Do the transition
        TranslateTransition transition = new TranslateTransition(Duration.millis(300), drawer);
        transition.setToX(targetX);
        transition.play();
    }

    //USER BUTTONS
    //Create User Menu
    //View User Details Menu
    //List All User Menus

    //EVENT BUTTONS
    //Create Event Menu
    //Update Event Information Menu
    //Cancel Event Menu
    //View Events Roster Menu
    //Search Events By Title Menu

    //BOOKING BUTTONS
    //Book an Event Menu
    //Cancel a Booking Menu

    //WAITLIST BUTTONS
    //View Event Waitlist Menu
    //Remove a Waitlist Booking Menu

}
