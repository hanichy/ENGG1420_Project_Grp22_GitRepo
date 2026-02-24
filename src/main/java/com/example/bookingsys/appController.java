package com.example.bookingsys;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class appController {
    @FXML
    private Label statusLabel;

    @FXML
    private void handleCreateUser() {
        statusLabel.setText("user not created");
    }

    @FXML
    private void handleCreateEvent(){
        statusLabel.setText("event not created");
    }
}
