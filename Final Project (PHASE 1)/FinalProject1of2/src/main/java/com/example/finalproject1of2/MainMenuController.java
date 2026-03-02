//This Class will be responsible for what all the buttons are doing in the main menu
package com.example.finalproject1of2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {


    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToCreateEvent(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateEventsPage.fxml"));
        root = loader.load();

        EventsPageController eventsPageController = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Switch to Cancel an Event
    public void switchToCancelEvent(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CancelEventPage.fxml"));
        root = loader.load();

        EventsPageController eventsPageController = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToListEvents(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListEventsPage.fxml"));
        root = loader.load();

        EventsPageController eventsPageController = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void backToMenuE(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
