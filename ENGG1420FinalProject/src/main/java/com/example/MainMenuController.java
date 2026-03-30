package com.example;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateUser.fxml"));
        root = loader.load();

        CreateUserController userPageController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //View User Details Menu
    @FXML
    private void switchToUserDetails(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("viewUserDetails.fxml"));
        root = loader.load();

        ViewUserDetailsController userPageController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //List All User Menus
    @FXML
    private void switchToUserList(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("listUsers.fxml"));
        root = loader.load();

        ListAllUserController userPageController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //EVENT BUTTONS
    //Create com.example.Event Menu
    @FXML
    private void switchToCreateEvent(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateEventMenuPHASE2.fxml"));
        root = loader.load();

        EventsPageController eventsPageController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //Update com.example.Event Information Menu
    @FXML
    private void switchToUpdateEvent(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateEventsMenuPHASE2.fxml"));
        root = loader.load();

        EventsPageController eventsPageController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //Cancel com.example.Event Menu
    @FXML
    private void switchToCancelEvent(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CancelEventMenuPHASE2.fxml"));
        root = loader.load();

        EventsPageController eventsPageController = loader.getController();
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

        EventsPageController eventsPageController = loader.getController();
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

        EventsPageController eventsPageController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //BOOKING BUTTONS
    //Book an com.example.Event Menu
    @FXML
    private void switchToCreateBooking(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BookingEventPage.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CancelBookingPage.fxml"));
        root = loader.load();
        BookingPageController bookingPageController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void switchToViewWaitlistBookingRemove(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewWaitlistBookingRemove.fxml"));
        root = loader.load();
        WaitlistPageController waitlistPageController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void switchToViewWaitlist(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewWaitlist.fxml"));
        root = loader.load();
        WaitlistPageController waitlistPageController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //WAITLIST BUTTONS
    //View com.example.Event Waitlist Menu
    //Remove a Waitlist Booking Menu

}
