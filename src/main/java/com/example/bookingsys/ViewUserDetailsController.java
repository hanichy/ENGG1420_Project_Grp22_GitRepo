package com.example.bookingsys;

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

public class ViewUserDetailsController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField userIdField;

    @FXML
    private TextArea outputArea;

    @FXML
    private void handleSearch(ActionEvent event) {

        String id = userIdField.getText();
        User user = UserManager.getUserById(id);

        if (user != null) {
            outputArea.setText(user.toString());
        } else {
            outputArea.setText("User not found.");
        }
    }

    @FXML
    private void switchToMainMenu(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("MainMenu.fxml"));

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}