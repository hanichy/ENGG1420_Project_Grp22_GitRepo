package com.example;

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
import java.util.ArrayList;

public class WaitlistPageController {

    @FXML private TextField userIDField;
    @FXML private TextField bookingIDField; // Specific ID to remove
    @FXML private VBox waitlistBookingsContainer;
    @FXML private Label statusLabel;

    private Waitlist waitlist = Waitlist.getInstance();

    @FXML
    public void searchWaitlistByUser(ActionEvent event) {
        waitlistBookingsContainer.getChildren().clear();
        String userId = userIDField.getText().trim();

        if (userId.isEmpty()) {
            statusLabel.setText("Please enter a User ID.");
            return;
        }

        ArrayList<Booking> allBookings = waitlist.getBookingList();
        boolean found = false;

        for (Booking b : allBookings) {
            if (b.getUserId().equalsIgnoreCase(userId) && b.getBookingStatus().equals(Booking.Status_WAITLISTED)) {
                Label info = new Label(String.format("ID: %s | Event: %s", b.getBookingId(), b.getEventId()));
                info.setStyle("-fx-font-family: 'Baskerville Old Face'; -fx-font-size: 14;");
                waitlistBookingsContainer.getChildren().add(info);
                found = true;
            }
        }

        if (!found) {
            statusLabel.setText("No waitlisted bookings found for this user.");
        } else {
            statusLabel.setText("Results found.");
        }
    }

    @FXML
    public void handleRemoveWaitlist(ActionEvent event) {
        String idToRemove = bookingIDField.getText().trim();

        if (idToRemove.isEmpty()) {
            statusLabel.setText("Please enter a Booking ID to remove.");
            return;
        }

        // We use the boolean logic added to Waitlist logic in the previous step
        boolean success = waitlist.removeBookingById(idToRemove);

        if (success) {
            statusLabel.setText("Booking " + idToRemove + " has been removed.");
            bookingIDField.clear();
            waitlistBookingsContainer.getChildren().clear(); // Clear results to force refresh
        } else {
            statusLabel.setText("Error: Booking ID not found in waitlist.");
        }
    }

    @FXML
    public void backToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenuPHASE2.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}