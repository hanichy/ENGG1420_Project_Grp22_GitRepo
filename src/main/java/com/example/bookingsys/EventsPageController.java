//This Class is to control the UI for the events section
//Hopefully with Dynamic UI it can be different scene for each section
//Ex showing the text fields for create Event Button vs showing just a list of events for the list events button
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

//For What Each Button Will Do
public class EventsPageController {
    @FXML
    public VBox container;

    private Stage stage;
    private Scene scene;
    private Parent root;
    //The Text fields to create/update an event
    @FXML
    private TextField eventID = new TextField();
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
    //gets called on intitialization of the fxml (ex when the page is pulled up)
    public void initialize() {
        if (container != null) {
            displayList();
        }
    }

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
                event.createEvent(w);
            } else if (eventType.equals("Seminar")) {
                Seminar s = new Seminar(title, date, location, cap, eventSpeaker.getText());
                event.createEvent(s);
            } else if (eventType.equals("Concert")) {
                int aR = parseInteger(eventAgeRestriction.getText(), "Age Restriction");
                Concert c = new Concert(title, date, location, cap, aR);
                event.createEvent(c);
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
    @FXML
    public void displayList(){
        //Clear old VBox items
        container.getChildren().clear();

        //Loop through List
        for(Event e :event.getEventList()){
            VBox card = new VBox(5);
            card.setStyle("-fx-padding: 10; -fx-border-color: #cccccc; -fx-background-color: #f9f9f9; -fx-border-radius: 5;");

            //Title Label
            Label titlelbl = new Label("Title: "+e.getTitle());
            titlelbl.setStyle("-fx-padding:10; -fx-border-color: gray;");

            Label idLbl = new Label("ID: "+ e.getEventId());
            Label dateLbl = new Label("Date: "+e.getDateTime());
            Label locationLbl = new Label("Loction: "+ e.getLocation());
            Label capLbl = new Label("Capacity: " +e.getCapacity());

            Label statusLbl = new Label("Status: "+ (e.getStatus()? "Active" : "Cancelled"));
            statusLbl.setStyle(e.getStatus()? "-fx-text-fill:green;":"-fx-text-fill:red;");

            container.getChildren().addAll(titlelbl, idLbl, dateLbl, locationLbl,capLbl);

            if (e instanceof Workshop){
                Workshop w =(Workshop) e;
                Label topicLbl = new Label("Topic: "+w.getTopic());
                container.getChildren().add(topicLbl);
            }
            else if (e instanceof Seminar){
                Seminar s = (Seminar)e;
                Label speakerLbl = new Label("Speaker: "+ s.getSpeaker());
                container.getChildren().add(speakerLbl);
            }
            else if (e instanceof Concert){
                Concert c = (Concert)e;
                Label ageLbl = new Label(c.getAgeRestriction()+"+");
                container.getChildren().add(ageLbl);
            }

            container.getChildren().add(statusLbl);
        }
    }

    //Cancel Event
    @FXML
    public void search(ActionEvent ev) {
        container.getChildren().clear();
        String searchId = eventID.getText();
        Event e = Event.findEventById(searchId);
        if (e != null) {
            Label titleLabel = new Label("Title: " + e.getTitle());
            Label statusLabel = new Label("Status: " + (e.status ? "Active" : "Cancelled"));
            container.getChildren().addAll(titleLabel, statusLabel);
        } else {
            container.getChildren().add(new Label("Event not found."));
        }
    }

    //Cancel the event
    @FXML
    public void cancelEventButton(ActionEvent ev) {
        String cancelId = eventID.getText();
        Event e = Event.findEventById(cancelId);
        if (e != null) {
            e.cancelEvent();
            search(ev);
            showInfo("Success", "Event has been cancelled.");
        } else {
            showError("Error", "Event ID not found.");
        }
    }

    //Update Event
    @FXML
    public void titleButton(ActionEvent ev){
        eventTitle.setOpacity(1);
    }
    @FXML
    public void dateButton(ActionEvent ev){
        eventDate.setOpacity(1);
    }
    @FXML
    public void locationButton(ActionEvent ev){
        eventLocation.setOpacity(1);
    }
    @FXML
    public void capacityButton(ActionEvent ev){
        eventCapacity.setOpacity(1);
    }
    @FXML
    public void showSubEventAttribute(ActionEvent ev){
        //Get the specific event
        String eventCancel = eventID.getText();
        Event e = Event.findEventById(eventCancel);

        if (e instanceof Workshop){
            eventTopic.setOpacity(1);
        }
        else if (e instanceof Seminar){
            eventSpeaker.setOpacity(1);
        }
        else if (e instanceof Concert){
            eventAgeRestriction.setOpacity(1);
        }
    }

    @FXML
    public void updateEvent(ActionEvent ev){
        String updateEventId = eventID.getText();
        Event e = Event.findEventById(updateEventId);

        if (e == null) {
            showError("Update Error", "Event ID not found.");
            return;
        }

        // Correctly assign values or keep originals
        String title = eventTitle.getText().isEmpty() ? e.getTitle() : eventTitle.getText();
        String date = eventDate.getText().isEmpty() ? e.getDateTime() : eventDate.getText();
        String location = eventLocation.getText().isEmpty() ? e.getLocation() : eventLocation.getText();

        int cap;
        try {
            cap = eventCapacity.getText().isEmpty() ? e.getCapacity() : Integer.parseInt(eventCapacity.getText());
        } catch (NumberFormatException ex) {
            showError("Input Error", "Capacity must be a number.");
            return;
        }

        // Update logic based on type
        if (e instanceof Workshop) {
            String topic = eventTopic.getText().isEmpty() ? ((Workshop) e).getTopic() : eventTopic.getText();
            ((Workshop) e).updateEvent(title, date, location, cap, topic);
        }
        // ... repeat pattern for Seminar and Concert

        showInfo("Success", "Event updated!");
        displayList(); // Refresh the UI
    }

    //Sends user back to the Main Menu
    public void backToMenuE(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
