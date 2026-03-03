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
public class BookingPageController {
    @FXML
    public VBox container;

    private Stage stage;
    private Scene scene;
    private Parent root;
    //The Text fields to create/update an event
    @FXML
    private TextField bookingID = new TextField();
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

    BookingManagement booking = BookingManagement.getInstance();
    EventManagement event = EventManagement.getInstance();

    @FXML
    //gets called on intitialization of the fxml (ex when the page is pulled up)
    public void initialize() {
        if (container != null) {
            displayList();
        }
    }

    @FXML
    private void CreateBooking(User u, Event e){

        //add the create stuff once user is complete.
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
        String searchId = bookingID.getText();
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
    public void cancelBookingButton(ActionEvent ev) {
        String cancelId = bookingID.getText();
        Booking b = Booking.findBookingById(cancelId);
        if (b != null) {
            b.cancelBooking();
            search(ev);
            showInfo("Success", "Booking has been cancelled.");
        } else {
            showError("Error", "Booking ID not found.");
        }
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
