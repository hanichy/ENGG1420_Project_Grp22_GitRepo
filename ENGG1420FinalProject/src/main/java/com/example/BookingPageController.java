package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

//For What Each Button Will Do
public class BookingPageController {
    @FXML
    public VBox container;

    private Stage stage;
    private Scene scene;
    private Parent root;

    //The Text fields for booking section
    @FXML
    private TextField bookingID = new TextField();
    @FXML
    private TextField eventTitle = new TextField();
    @FXML
    private TextField eventDate = new TextField();

    Waitlist waitlist = Waitlist.getInstance();
    EventManagement event = EventManagement.getInstance();

    @FXML
    //gets called on intitialization of the fxml (ex when the page is pulled up)
    public void initialize() {
        if (container != null) {
            displayList();
        }
    }

    @FXML
    //use eventTitle as user id field and eventDate as event id field for now
    public void createBookingButton(ActionEvent ev){
        try{
            String userId = eventTitle.getText();
            String eventId = eventDate.getText();

            if(userId == null || userId.isBlank() || eventId == null || eventId.isBlank()){
                showError("Validation Error", "Enter both User ID and Event ID.");
                return;
            }

            Booking newBooking = waitlist.createBookingByIds(userId.trim(), eventId.trim());
            showInfo("Success", "Booking created successfully. Booking ID: " + newBooking.getBookingId() + " Status: " + newBooking.getBookingStatus());
            displayList();
        }catch(IllegalArgumentException e){
            showError("Booking Error", e.getMessage());
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

    //List Bookings
    @FXML
    public void displayList(){
        //Clear old VBox items
        container.getChildren().clear();

        //Loop through List
        for(Booking b : waitlist.getBookingList()){
            VBox card = new VBox(5);
            card.setStyle("-fx-padding: 10; -fx-border-color: #cccccc; -fx-background-color: #f9f9f9; -fx-border-radius: 5;");

            Event e = event.findEventById(b.getEventId());
            String eventName = b.getEventId();
            if(e != null){
                eventName = e.getTitle();
            }

            Label bookingLbl = new Label("Booking ID: " + b.getBookingId());
            bookingLbl.setStyle("-fx-padding:10; -fx-border-color: gray;");

            Label userLbl = new Label("User ID: " + b.getUserId());
            Label eventLbl = new Label("Event: " + eventName + " (" + b.getEventId() + ")");
            Label dateLbl = new Label("Created At: " + b.createdAt);
            Label statusLbl = new Label("Status: " + b.getBookingStatus());

            if (b.getBookingStatus().equals(Booking.Status_CONFIRMED)){
                statusLbl.setStyle("-fx-text-fill:green;");
            }
            else if (b.getBookingStatus().equals(Booking.Status_WAITLISTED)){
                statusLbl.setStyle("-fx-text-fill:orange;");
            }
            else{
                statusLbl.setStyle("-fx-text-fill:red;");
            }

            card.getChildren().addAll(bookingLbl, userLbl, eventLbl, dateLbl, statusLbl);
            container.getChildren().add(card);
        }
    }

    //Search Booking
    @FXML
    public void search(ActionEvent ev) {
        container.getChildren().clear();
        String searchId = bookingID.getText();
        Booking b = waitlist.findBookingById(searchId);
        if (b != null) {
            Label bookingLabel = new Label("Booking ID: " + b.getBookingId());
            Label userLabel = new Label("User ID: " + b.getUserId());
            Label eventLabel = new Label("Event ID: " + b.getEventId());
            Label dateLabel = new Label("Created At: " + b.createdAt);
            Label statusLabel = new Label("Status: " + b.getBookingStatus());
            container.getChildren().addAll(bookingLabel, userLabel, eventLabel, dateLabel, statusLabel);
        } else {
            container.getChildren().add(new Label("Booking not found."));
        }
    }

    //Cancel the booking
    @FXML
    public void cancelBookingButton(ActionEvent ev) {
        try{
            String cancelId = bookingID.getText();
            Booking cancelledBooking = waitlist.cancelBooking(cancelId);
            Booking promotedBooking = waitlist.getLastPromotedBooking();

            search(ev);

            if(promotedBooking != null && promotedBooking.getEventId().equals(cancelledBooking.getEventId())){
                showInfo("Success", "Booking has been cancelled. Promoted booking: " + promotedBooking.getBookingId());
            }
            else{
                showInfo("Success", "Booking has been cancelled.");
            }
        }catch(IllegalArgumentException e){
            showError("Error", e.getMessage());
        }
    }

    //Sends user back to the Main Menu
    public void backToMenuE(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenuPHASE2.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
