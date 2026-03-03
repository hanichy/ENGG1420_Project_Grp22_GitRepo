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
import javafx.scene.control.Alert;
import java.io.IOException;

public class EventsPageController {

    @FXML
    //gets called on intitialization of the fxml (ex when the page is pulled up)
    public void initialize() {
        if (container != null) {
            displayList();
        }
    }

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
        try {
            // input validation
            if (eventTitle.getText().isEmpty() || eventDate.getText().isEmpty()) {
                showError("Validation Error", "Title and Date cannot be empty.");
                return;
            }

            String title = eventTitle.getText();
            String date = eventDate.getText();
            String location = eventLocation.getText();

            // Numeric validation
            int cap = parseInteger(eventCapacity.getText(), "Capacity");

            if (eventType.isEmpty()) {
                showError("Selection Error", "Please select an event type (Workshop/Seminar/Concert).");
                return;
            }

            // Logic-specific validation
            if (eventType.equals("Workshop")) {
                Workshop w = new Workshop(title, date, location, cap, eventTopic.getText());
                eventManager.createEvent(w);
            } else if (eventType.equals("Seminar")) {
                Seminar s = new Seminar(title, date, location, cap, eventSpeaker.getText());
                eventManager.createEvent(s);
            } else if (eventType.equals("Concert")) {
                int aR = parseInteger(eventAgeRestriction.getText(), "Age Restriction");
                Concert c = new Concert(title, date, location, cap, aR);
                eventManager.createEvent(c);
            }

            showInfo("Success", "Event created successfully!");

        } catch (NumberFormatException e) {
        } catch (IllegalArgumentException e) {
            showError("Input Error", e.getMessage());
        } catch (Exception e) {
            showError("System Error", "An unexpected error occurred: " + e.getMessage());
        }
    }

    private int parseInteger(String input, String fieldName) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            showError("Invalid Input", fieldName + " must be a valid number.");
            throw e;
        }
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void displayList() {
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