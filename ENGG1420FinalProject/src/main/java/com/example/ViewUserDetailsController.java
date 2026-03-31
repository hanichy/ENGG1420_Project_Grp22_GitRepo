package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.util.ArrayList;

public class ViewUserDetailsController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField userIdField;

    @FXML
    private TextArea outputArea;

    @FXML
    public void initialize() {
        displayAllUsers();
    }

    private void displayAllUsers() {
        ArrayList<User> allUsers = UserManager.getAllUsers();

        if (allUsers.isEmpty()) {
            outputArea.setText("No users currently registered in the system.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (User user : allUsers) {
            sb.append(user.toString()).append("\n");
        }
        outputArea.setText(sb.toString());
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String id = userIdField.getText().trim();

        if (id.isEmpty()) {
            displayAllUsers();
            return;
        }
        User user = UserManager.getUserById(id);

        if (user != null) {
            outputArea.setText("Found user:\n\n" + user.toString());
        } else {
            outputArea.setText("User with ID '" + id + "' not found.");
        }
    }

    @FXML
    private void switchToMainMenu(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("MainMenuPHASE2.fxml"));

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}