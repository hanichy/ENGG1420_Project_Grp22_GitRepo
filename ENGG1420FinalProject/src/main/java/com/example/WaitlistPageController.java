package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class WaitlistPageController {

    @FXML private TextField userIDField;
    @FXML private VBox waitlistBookingsContainer;
    @FXML private Label statusLabel;

    private Waitlist waitlist = Waitlist.getInstance();

    @FXML
    public void searchWaitlistByUser(ActionEvent event) {
        String userId = userIDField.getText().trim();
        waitlistBookingsContainer.getChildren().clear();

        if (userId.isEmpty()) {
            statusLabel.setText("Please enter a User ID.");
            return;
        }

        boolean found = false;
        // Search through the waitlistMap for this user
        for (String eventId : waitlist.getWaitlistMap().keySet()) {
            ArrayList<Booking> eventWaitlist = waitlist.getWaitlistForEvent(eventId);
            for (Booking b : eventWaitlist) {
                if (b.getUserId().equalsIgnoreCase(userId)) {
                    addWaitlistCard(b);
                    found = true;
                }
            }
        }

        if (!found) {
            statusLabel.setText("No waitlisted bookings found for this user.");
        } else {
            statusLabel.setText("");
        }
    }

    private void addWaitlistCard(Booking booking) {
        HBox card = new HBox(15);
        card.setStyle("-fx-border-color: #cccccc; -fx-padding: 5; -fx-background-color: #fefefe;");

        Label info = new Label("Event: " + booking.getEventId() + " | Booking ID: " + booking.getBookingId());
        Button removeBtn = new Button("Remove");
        removeBtn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");

        removeBtn.setOnAction(e -> {
            waitlist.removeWaitlistedBooking(booking);
            searchWaitlistByUser(null); // Refresh the list
        });

        card.getChildren().addAll(info, removeBtn);
        waitlistBookingsContainer.getChildren().add(card);
    }

    @FXML
    public void backToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenuPHASE2.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}