package com.example.finalproject1of2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
//Sets Up the Event Page in the GUI
public class EventPageSetUp  extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventPageSetUp.class.getResource("CreateEventsPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("EVENTS");
        stage.setScene(scene);
        stage.show();
    }
}
