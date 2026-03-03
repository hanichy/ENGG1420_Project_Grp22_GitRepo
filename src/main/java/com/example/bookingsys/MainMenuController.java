//This Class will be responsible for what all the buttons are doing in the main menu
package com.example.bookingsys;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {


    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToCreateEvent(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateEventsPage.fxml"));
        root = loader.load();

        EventsPageController eventsPageController = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Switch to Cancel an Event
    public void switchToCancelEvent(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CancelEventPage.fxml"));
        root = loader.load();

        EventsPageController eventsPageController = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Switch to Update Event
    public void switchToUpdateEvent(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateEventsPage.fxml"));
        root = loader.load();

        EventsPageController eventsPageController = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Switch to List Events
    public void switchToListEvents(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListEventsPage.fxml"));
        root = loader.load();
        EventsPageController eventsPageController = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToBooking(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BookingEventPage.fxml"));
        root = loader.load();

        BookingPageController bookingPageController = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToCancelBooking(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CancelBookingPage.fxml"));
        root = loader.load();

        BookingPageController bookingPageController = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
