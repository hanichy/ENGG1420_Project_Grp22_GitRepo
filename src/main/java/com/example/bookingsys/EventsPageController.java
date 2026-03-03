package com.example.bookingsys;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class EventsPageController {
    @FXML
    public VBox container;

    private Stage stage;
    private Scene scene;

    @FXML
    private TextField eventID;
    @FXML
    private TextField eventTitle;
    @FXML
    private TextField eventDate;
    @FXML
    private TextField eventLocation;
    @FXML
    private TextField eventCapacity;
    @FXML
    private TextField eventTopic;
    @FXML
    private TextField eventSpeaker;
    @FXML
    private TextField eventAgeRestriction;

    private String eventType = "";
    EventManagement eventManager = EventManagement.getInstance();

    @FXML
    private void handleCreateEvent() {
        String title = eventTitle.getText();
        String date = eventDate.getText();
        String location = eventLocation.getText();
        int cap = Integer.parseInt(eventCapacity.getText());

        if (eventType.equals("Workshop")) {
            Workshop w = new Workshop(title, date, location, cap, eventTopic.getText());
            eventManager.createEvent(w);
        } else if (eventType.equals("Seminar")) {
            Seminar s = new Seminar(title, date, location, cap, eventSpeaker.getText());
            eventManager.createEvent(s);
        } else if (eventType.equals("Concert")) {
            int aR = Integer.parseInt(eventAgeRestriction.getText());
            Concert c = new Concert(title, date, location, cap, aR);
            eventManager.createEvent(c);
        }
    }

    @FXML
    public void displayList(ActionEvent ev) {
        container.getChildren().clear();
        for (Event e : Event.eventList) { // Accessing the shared list in Event
            Label info = new Label(e.getEventId() + ": " + e.getTitle() + " [" + (e.status ? "Active" : "Cancelled") + "]");
            container.getChildren().add(info);
        }
    }

    @FXML
    public void search(ActionEvent ev) {
        String id = eventID.getText();
        Event e = Event.findEventById(id);
        container.getChildren().clear();
        if (e != null) {
            container.getChildren().add(new Label("Found: " + e.getTitle() + " at " + e.getLocation()));
        } else {
            container.getChildren().add(new Label("Event Not Found"));
        }
    }

    @FXML
    public void updateEvent(ActionEvent ev) {
        Event e = Event.findEventById(eventID.getText());
        if (e == null) return;

        String title = eventTitle.getText().isEmpty() ? e.getTitle() : eventTitle.getText();
        String date = eventDate.getText().isEmpty() ? e.getDateTime() : eventDate.getText();
        String loc = eventLocation.getText().isEmpty() ? e.getLocation() : eventLocation.getText();
        int cap = eventCapacity.getText().isEmpty() ? e.getCapacity() : Integer.parseInt(eventCapacity.getText());

        if (e instanceof Workshop w) {
            String topic = eventTopic.getText().isEmpty() ? w.getTopic() : eventTopic.getText();
            w.updateEvent(title, date, loc, cap, topic);
        } else if (e instanceof Seminar s) {
            String speaker = eventSpeaker.getText().isEmpty() ? s.getSpeaker() : eventSpeaker.getText();
            s.updateEvent(title, date, loc, cap, speaker);
        } else if (e instanceof Concert c) {
            int age = eventAgeRestriction.getText().isEmpty() ?
                    Integer.parseInt(c.getAgeRestriction()) : Integer.parseInt(eventAgeRestriction.getText());
            c.updateEvent(title, date, loc, cap, age);
        }
    }

    @FXML
    public void backToMenuE(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // UI Visibility Toggles
    @FXML public void workShopButton() { eventTopic.setOpacity(1); eventType = "Workshop"; }
    @FXML public void seminarButton() { eventSpeaker.setOpacity(1); eventType = "Seminar"; }
    @FXML public void concertButton() { eventAgeRestriction.setOpacity(1); eventType = "Concert"; }
}