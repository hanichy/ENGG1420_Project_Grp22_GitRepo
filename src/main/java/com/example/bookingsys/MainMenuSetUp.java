//This Class Sets up the UI for the FXML of the Main Menu
package com.example.bookingsys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//I know its ugly its just there to kinda be a placeholder of what is needed

public class MainMenuSetUp extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("GROUP 22 SYSTEM");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //main code here* (using functions from class and such)
        launch();
    }
}

