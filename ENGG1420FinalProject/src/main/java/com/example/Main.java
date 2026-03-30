package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;
//Runs the GUI
public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }

    //Sets up the Main Menu
    @Override
    public void start(Stage stage) throws Exception {
        //Get the Information from the Files
        EventManagement e = EventManagement.getInstance();
        e.startup();
        Waitlist b = Waitlist.getInstance();
        b.startup();
        UserManager.loadFromCSV();

        Parent root = FXMLLoader.load(getClass().getResource("MainMenuPHASE2.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("GROUP 22 SYSTEM");
        stage.setScene(scene);
        stage.show();
    }
}
