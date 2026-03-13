package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EventsPageConroller {
    //Labels
    //TextFields

    //Needed to Switch Scenes
    private Stage stage;
    private Scene scene;
    private Parent root;

    //Back to Main Menu
    @FXML
    private void backToMenu(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuPHASE2.fxml"));
        root = loader.load();

        MainMenuController mainMenuController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //CREATE EVENT
    //Seminar Button
    //Workshop Button
    //Concert Button
    //Create Event Button

    //UPDATE EVENT INFORMATION

    //CANCEL EVENT

    //VIEW EVENT ROSTER

    //SEARCH EVENTS BY TITLE
}
