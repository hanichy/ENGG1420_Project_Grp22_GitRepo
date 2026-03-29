package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class CreateUserController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField userIdField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private ComboBox<String> userTypeBox;

    @FXML
    private TextField extraField;

    public void switchToCreateEvent(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root = loader.load();

        MainMenuController mainMenuController = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleCreateUser(ActionEvent event) {

        String userId = userIdField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String type = userTypeBox.getValue();

        //basic validation
        if (userId.isEmpty() || name.isEmpty() || email.isEmpty() || type == null) {
            System.out.println("Please fill all required fields.");
            return;
        }

        User user;

        switch (type) {

            case "Staff":
                String department = extraField.getText();

                if (department.isEmpty()) {
                    System.out.println("Please enter department.");
                    return;
                }

                user = new Staff(userId, name, email, department);
                break;

            case "Student":
                try {
                    int studentId = Integer.parseInt(extraField.getText());
                    user = new Student(userId, name, email, studentId);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Student ID (must be a number).");
                    return;
                }
                break;

            case "Guest":
                String org = extraField.getText();

                if (org.isEmpty()) {
                    user = new Guest(userId, name, email);
                } else {
                    user = new Guest(userId, name, email, org);
                }
                break;

            default:
                user = new RegularUser(userId, name, email);
                break;
        }

        //Save user
        UserManager.addUser(user);

        System.out.println("User created successfully: " + user);

        //clear fields
        userIdField.clear();
        nameField.clear();
        emailField.clear();
        extraField.clear();
        userTypeBox.setValue("Regular");
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

    @FXML
    public void initialize() {
        userTypeBox.getItems().addAll("Regular", "Staff", "Student", "Guest");
        userTypeBox.setValue("Regular"); // default
    }
}