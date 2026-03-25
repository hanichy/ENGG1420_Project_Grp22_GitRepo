package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;

public class EventsPageController {
    //ComboBox Hour
    @FXML
    ComboBox<Integer> hour;
    //ComboBox Minute
    @FXML
    ComboBox<Integer> minute;

    //VBox to Show All Events
    @FXML
    private VBox eventsContainer;
    //TextFields
    @FXML
    private TextField eventTitle;
    @FXML
    private TextField eventLocation;
    @FXML
    private DatePicker eventDate;
    @FXML
    private TextField eventCapacity;
    @FXML
    private TextField eventSpeaker;
    @FXML
    private TextField eventTopic;
    @FXML
    private TextField eventAgeRestriction;
    @FXML
    private TextField eventID;

    //com.example.Event Type Storage
    private String eventType ="";

    //Needed to Switch Scenes
    private Stage stage;
    private Scene scene;
    private Parent root;

    //Get the List of Events Created before
    EventManagement event = EventManagement.getInstance();

    //Displays List of Events Upon Page Initialization
    @FXML
    private void initialize() {
        if (eventsContainer != null) {
            displayList();
        }
        hourInitialize();
        minuteInitialize();
        dateInitialization();
    }
    //List Events
    @FXML
    public void displayList(){
        //Clear old VBox items
        eventsContainer.getChildren().clear();

        //Loop through List
        for(Event e : event.getEventList()){
            VBox card = new VBox(5);
            card.setStyle("-fx-padding: 10; -fx-border-color: #cccccc; -fx-background-color: #f9f9f9; -fx-border-radius: 5;");

            //Title Label
            Label titlelbl = new Label("Title: "+e.getTitle());
            titlelbl.setStyle("-fx-padding:10; -fx-border-color: gray;");

            Label idLbl = new Label("ID: "+ e.getEventId());
            Label dateLbl = new Label("Date: "+e.getDateTime());
            Label locationLbl = new Label("Loction: "+ e.getLocation());
            Label capLbl = new Label("Capacity: " +e.getCapacity());

            Label statusLbl = new Label("Status: "+ (e.getStatus()? "Active" : "Cancelled"));
            statusLbl.setStyle(e.getStatus()? "-fx-text-fill:green;":"-fx-text-fill:red;");

            eventsContainer.getChildren().addAll(titlelbl, idLbl, dateLbl, locationLbl,capLbl);

            if (e instanceof Workshop){
                Workshop w =(Workshop) e;
                Label topicLbl = new Label("Topic: "+w.getTopic());
                eventsContainer.getChildren().add(topicLbl);
            }
            else if (e instanceof Seminar){
                Seminar s = (Seminar)e;
                Label speakerLbl = new Label("Speaker: "+ s.getSpeaker());
                eventsContainer.getChildren().add(speakerLbl);
            }
            else if (e instanceof Concert){
                Concert c = (Concert)e;
                Label ageLbl = new Label(c.getAgeRestriction()+"+");
                eventsContainer.getChildren().add(ageLbl);
            }

            eventsContainer.getChildren().add(statusLbl);
        }
    }

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
    //Hour ComboBox Initialization
    @FXML
    private void hourInitialize(){
        //Populate the options
        for (int i = 0; i< 24;i++){
            //Make sure there is a Combo Box
            if(hour!=null) {
                hour.getItems().add(i);
            }
        }
    }
    //Minute ComboBox Initialization
    @FXML
    private void minuteInitialize(){
        //Populate the options
        for (int i = 0; i<46; i=i+15){
            //Make sure there is a Combo Box
            if (minute!=null) {
                minute.getItems().add(i);
            }
        }
    }
    //DatePicker Initialization
    @FXML
    private void dateInitialization(){
        //Make sure there is a DatePicker
        if (eventDate!=null) {
            eventDate.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);

                    // Disable all dates before today
                    if (date.isBefore(LocalDate.now())) {
                        //eventDate.setDisable(true);
                        setStyle("-fx-background-color: #FF0000;"); // Optional: grey out the past dates
                    }
                }
            });

            //Prevent User from Typing in a Past Date
            eventDate.setEditable(false);
        }
    }
    //Show Error
    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    //Check if Integer
    private int parseInteger(String input, String fieldName) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            showError("Invalid Input", fieldName + " must be a valid number.");
            throw e;
        }
    }


    //CREATE EVENT
    //Seminar Button
    @FXML
    public void seminarButton(ActionEvent e){
        if (eventType.equals("")){
            eventSpeaker.setOpacity(1);
            eventType = "Seminar";
        }
    }
    //Workshop Button
    @FXML
    public void workShopButton(ActionEvent e){
        if (eventType.equals("")){
            eventTopic.setOpacity(1);
            eventType = "Workshop";
        }
    }
    //Concert Button
    @FXML
    public void concertButton(ActionEvent e){
        if (eventType.equals("")){
            eventAgeRestriction.setOpacity(1);
            eventType = "Concert";
        }
    }
    //Create Event Button
    @FXML
    private void createEvent(ActionEvent e){
      try{
          //Check if any main fields are empty
          if(eventTitle.getText().isBlank()||eventLocation.getText().isBlank()){
              showError("Validation Error", "Field(s) are Incomplete");
              return;
          }
          //Check if date is empty or in the past
          if (getEventDate().isBlank()){
              showError("Selection Error", "Cannot book an event in the past.");
              return;
          }
          //Check that user has chosen an event type
          if (eventType.isBlank()){
              showError("Selection Error", "Select an event type (Workshop/Seminar/Concert");
              return;
          }
          //Check if the event has its Topic/Speaker/Age Restriction
          else{
              if (eventType == "Workshop"){
                  if (eventTopic.getText().isEmpty()){
                      showError("Validation Error", "Field(s) are Incomplete");
                      return;
                  }
              }
              else if (eventType == "Seminar"){
                  if (eventSpeaker.getText().isBlank()){
                      showError("Validation Error", "Field(s) are Incomplete");
                      return;
                  }
              }
              else if (eventType == "Concert"){
                  //Check if Field is blank
                  if (eventAgeRestriction.getText().isBlank()){
                      showError("Validation Error", "Field(s) are Incomplete");
                      return;
                  }
              }
          }
          //Check that Capacity is a Valid Input
          int cap = parseInteger(eventCapacity.getText(), "Capacity");
          //Create Event ID
          String newId = event.uniqueEventID();
          Event newEvent = null;

          //Create Event Based on Event Type
          if (eventType.equals("Workshop")) {
              Workshop w = new Workshop(newId, eventTitle.getText(), getEventDate(), eventLocation.getText(), cap, eventTopic.getText());
              event.createEvent(w);
          } else if (eventType.equals("Seminar")) {
              Seminar s = new Seminar(newId, eventTitle.getText(), getEventDate(), eventLocation.getText(), cap, eventSpeaker.getText());
              event.createEvent(s);
          } else if (eventType.equals("Concert")) {
              int aR = parseInteger(eventAgeRestriction.getText(), "Age Restriction");
              Concert c = new Concert(newId, eventTitle.getText(), getEventDate(), eventLocation.getText(), cap, String.valueOf(aR));
              event.createEvent(c);
          }

          showInfo("Success", "Event created successfully!");
      } catch (NumberFormatException ev) {
    } catch (IllegalArgumentException ev) {
        showError("Input Error", ev.getMessage());
    } catch (Exception ev) {
        showError("System Error", "An unexpected error occurred: " + ev.getMessage());
    }
    }
    //Get Date Time in YYYY/MM/DDTHH:MM
    private String getEventDate(){
        //Get Date from Datepicker
        String date = eventDate.getValue().toString();

        //Get Hour from ComboBox
        Integer selectedHour = hour.getValue();
        String eventHour = selectedHour.toString();
        //Get Minute from ComboBox
        Integer selectedMinute = minute.getValue();
        String eventMinute = selectedMinute.toString();

        //Check that user is booking in the future
        if(eventDate.getValue().isBefore(eventDate.getValue().now())){
            return null;
        }
        else if (eventDate.getValue().equals(LocalDate.now())){
            LocalTime selectedTime = LocalTime.of(selectedHour, selectedMinute);
            if (selectedTime.isBefore(LocalTime.now())) {
                showError("Time Error", "That time has already passed today.");
                return null;
            }
        }

        String dateTime = date+"T"+eventHour+":"+eventMinute;

        return dateTime;
    }
    //Shows Information of Successfully created event
    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //UPDATE EVENT INFORMATION

    //CANCEL EVENT

    //VIEW EVENT ROSTER

    //SEARCH EVENTS BY TITLE
}
