package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class ListAllUserController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextArea outputArea;

    @FXML
    public void initialize() {

        StringBuilder sb = new StringBuilder();

        for (User user : UserManager.getAllUsers()) {
            sb.append(user.toString()).append("\n");
        }

        outputArea.setText(sb.toString());
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