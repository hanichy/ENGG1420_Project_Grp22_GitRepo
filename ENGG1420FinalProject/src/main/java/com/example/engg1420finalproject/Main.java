package com.example.engg1420finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenuPHASE2.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("GROUP 22 SYSTEM");
        stage.setScene(scene);
        stage.show();
    }
}
