package com.example;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MainMenuController{
    //Create VBox
    @FXML
    private VBox drawer;

    //Needed to Switch Scenes
    private Stage stage;
    private Scene scene;
    private Parent root;

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
    @FXML
    private void switchToCreateUser(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateUserMenuPHASE2.fxml"));
        root = loader.load();

        UserPageController userPageController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //View User Details Menu
    @FXML
    private void switchToUserDetails(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserDetailsMenuPHASE2.fxml"));
        root = loader.load();

        UserPageController userPageController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //List All User Menus
    @FXML
    private void switchToUserList(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListUserMenuPHASE2.fxml"));
        root = loader.load();

        UserPageController userPageController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //EVENT BUTTONS
    //Create Event Menu
    @FXML
    private void switchToCreateEvent(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateEventMenuPHASE2.fxml"));
        root = loader.load();

        EventsPageConroller eventsPageController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //Update Event Information Menu
    @FXML
    private void switchToUpdateEvent(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateEventsMenuPHASE2.fxml"));
        root = loader.load();

        EventsPageConroller eventsPageController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //Cancel Event Menu
    @FXML
    private void switchToCancelEvent(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CancelEventMenuPHASE2.fxml"));
        root = loader.load();

        EventsPageConroller eventsPageController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //View Events Roster Menu
    @FXML
    private void switchToEventRoster(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EventRosterMenuPHASE2.fxml"));
        root = loader.load();

        EventsPageConroller eventsPageController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //Search Events By Title Menu
    @FXML
    private void switchToEventSearch(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchEventMenuPHASE2.fxml"));
        root = loader.load();

        EventsPageConroller eventsPageController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //BOOKING BUTTONS
    //Book an Event Menu
    @FXML
    private void switchToCreateBooking(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateBookingMenuPHASE2.fxml"));
        root = loader.load();

        BookingPageController bookingPageController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //Cancel a Booking Menu
    @FXML
    private void switchToCancelBooking(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CancelBookingMenuPHASE2.fxml"));
        root = loader.load();

        BookingPageController bookingPageController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //WAITLIST BUTTONS
    //View Event Waitlist Menu
    //Remove a Waitlist Booking Menu

}
