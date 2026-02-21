package com.example.finalproject1of2;//solved an error with com.example.finalproject1of2 keep this line

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello, JavaFX!");
        Scene scene = new Scene(label, 400, 200);
        stage.setTitle("My JavaFX App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //main code here* (using functions from class and such)
        launch();
    }
}