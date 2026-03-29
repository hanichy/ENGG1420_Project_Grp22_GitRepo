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
import java.util.ArrayList;

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

    //VBox for Selected Items
    @FXML
    private VBox selectedEventContainer;

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
    @FXML
    private TextField eventSearchTitle;
    @FXML
    private TextField createEventTopic;
    @FXML
    private TextField createEventSpeaker;
    @FXML
    private TextField createEventAgeRestriction;

    //Check Boxes for Filtering
    @FXML
    private CheckBox seminar;
    @FXML
    private CheckBox workshop;
    @FXML
    private CheckBox concerts;

    //Event Type Storage
    private String eventType ="";

    //Selected Event ID
    private String selectedEventID;

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
            //EventManagement.loadEventsFromCSV("events.csv");
            displayList();
        }
        hourInitialize();
        minuteInitialize();
        dateInitialization();

        if (eventTopic != null||eventSpeaker != null || eventAgeRestriction != null) {
            eventTopic.setVisible(false);
            eventTopic.setManaged(false);
            eventSpeaker.setVisible(false);
            eventSpeaker.setManaged(false);
            eventAgeRestriction.setVisible(false);
            eventAgeRestriction.setManaged(false);
        }

        // Empty Selected Events Container
        if (selectedEventContainer != null) {
            selectedEventContainer.getChildren().clear();
        }
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

            //Allows this to be clickable
            titlelbl.setOnMouseClicked(event -> {
                showSelectedEvent(e);
                if (eventTopic != null){
                    //Show Sub Attribute if necessary
                    showSubEventAttribute(e);
                }
            });
            eventsContainer.getChildren().addAll(statusLbl);
        }
    }

    //Show the Selected Event from list
    private void showSelectedEvent(Event e){
        //Clear Old Vbox Item
        selectedEventContainer.getChildren().clear();
        //Title Label
        Label titlelbl = new Label("Title: "+e.getTitle());
        titlelbl.setStyle("-fx-padding:10; -fx-border-color: gray;");

        Label details = new Label(

                "ID: " + e.getEventId() + "\n" +
                        "Location: " + e.getLocation() + "\n" +
                        "Date/Time: " + e.getDateTime() + "\n" +
                        "Capacity: " + e.getCapacity()
        );

        selectedEventContainer.getChildren().addAll(titlelbl, details);

        // Handle Type-Specific Info (Workshop/Seminar/Concert)
        if (e instanceof Workshop) {
            selectedEventContainer.getChildren().add(new Label("Topic: " + ((Workshop) e).getTopic()));
        } else if (e instanceof Seminar) {
            selectedEventContainer.getChildren().add(new Label("Speaker: " + ((Seminar) e).getSpeaker()));
        } else if (e instanceof Concert) {
            selectedEventContainer.getChildren().add(new Label("Age: " + ((Concert) e).getAgeRestriction()));
        }

        //Update Selected ID for functionality
        selectedEventID = e.getEventId();
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
            createEventSpeaker.setOpacity(1);
            eventType = "Seminar";
        }
    }
    //Workshop Button
    @FXML
    public void workShopButton(ActionEvent e){
        if (eventType.equals("")){
            createEventTopic.setOpacity(1);
            eventType = "Workshop";
        }
    }
    //Concert Button
    @FXML
    public void concertButton(ActionEvent e){
        if (eventType.equals("")){
            createEventAgeRestriction.setOpacity(1);
            eventType = "Concert";
        }
    }
    //Create Event Button
    @FXML
    private void createEvent(ActionEvent e){
      try{
          //Check if any main fields are empty
          if(eventTitle.getText().isBlank()||eventLocation.getText().isBlank()||eventDate.getValue().toString().isBlank()){
              showError("Validation Error", "Field(s) are Incomplete");
              return;
          }
          //Check if a time has been selected
          Integer selectedHour = hour.getValue();
          Integer selectedMintue = minute.getValue();
          if(selectedHour == null || selectedMintue == null){
              showError("Validation Error", "Field(s) are incomplete");
              return;
          }
          //Check if date is empty or in the past
          if (getEventDate() == null){
              showError("Selection Error", "Select a future date");
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
                  if (createEventTopic.getText().isEmpty()){
                      showError("Validation Error", "Field(s) are Incomplete");
                      return;
                  }
              }
              else if (eventType == "Seminar"){
                  if (createEventSpeaker.getText().isBlank()){
                      showError("Validation Error", "Field(s) are Incomplete");
                      return;
                  }
              }
              else if (eventType == "Concert"){
                  //Check if Field is blank
                  if (createEventAgeRestriction.getText().isBlank()){
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
              Workshop w = new Workshop(newId, eventTitle.getText(), getEventDate(), eventLocation.getText(), cap, createEventTopic.getText());
              event.createEvent(w);
          } else if (eventType.equals("Seminar")) {
              Seminar s = new Seminar(newId, eventTitle.getText(), getEventDate(), eventLocation.getText(), cap, createEventSpeaker.getText());
              event.createEvent(s);
          } else if (eventType.equals("Concert")) {
              int aR = parseInteger(createEventAgeRestriction.getText(), "Age Restriction");
              Concert c = new Concert(newId, eventTitle.getText(), getEventDate(), eventLocation.getText(), cap, String.valueOf(aR));
              event.createEvent(c);
          }

          showInfo("Success", "Event created successfully! \n Event ID: "+ newId);
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
        //Get Minute from ComboBox
        Integer selectedMinute = minute.getValue();

        //Check that user is booking in the future
        if(eventDate.getValue().isBefore(eventDate.getValue().now())){
            return null;
        }
        else if (eventDate.getValue().equals(LocalDate.now())){
            LocalTime selectedTime = LocalTime.of(selectedHour, selectedMinute);
            if (selectedTime.isBefore(LocalTime.now())) {
                return null;
            }
        }

        //Only create a string if the field is not empty
        if(selectedMinute != null || selectedHour != null){
            String eventMinute = selectedMinute.toString();
            String eventHour = selectedHour.toString();
            String dateTime = date+"T"+eventHour+":"+eventMinute;

            return dateTime;
        }


        return null;
    }
    //Shows Information of Successfully created event
    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //UPDATE EVENT INFORMATION
    //Update Event Button
    @FXML
    private void updateEvent(ActionEvent ev){
        //Use Event Selected
        Event e = event.findEventById(selectedEventID);


        if (e == null) {
            showError("Update Error", "Event ID not found.");
            return;
        }

        // Correctly assign values or keep originals
        String title = eventTitle.getText().isEmpty() ? e.getTitle() : eventTitle.getText();
        String date = getEventDate()==null ? e.getDateTime() : getEventDate();
        String location = eventLocation.getText().isEmpty() ? e.getLocation() : eventLocation.getText();

        int cap;
        try {
            cap = eventCapacity.getText().isEmpty() ? e.getCapacity() : Integer.parseInt(eventCapacity.getText());
        } catch (NumberFormatException ex) {
            showError("Input Error", "Capacity must be a number.");
            return;
        }

        // Update logic based on type
        if (e instanceof Workshop) {
            String topic = eventTopic.getText().isBlank() ? ((Workshop) e).getTopic() : eventTopic.getText();
            ((Workshop) e).updateEvent(title, date, location, cap, topic);
            event.updateWorkshop(selectedEventID,title, date, location, cap, topic);
        }
        else if (e instanceof Seminar){
            String speakerName = eventSpeaker.getText().isBlank() ? ((Seminar) e).getSpeaker() : eventSpeaker.getText();
            ((Seminar) e).updateEvent(title, date, location, cap, speakerName);
            event.updateSeminar(selectedEventID,title, date, location, cap, speakerName);
        }
        else if (e instanceof Concert){
            String aR = eventAgeRestriction.getText().isBlank() ? ((Concert) e).getAgeRestriction() : eventAgeRestriction.getText();
            ((Concert) e).updateEvent(title, date, location, cap, aR);
            event.updateConcert(selectedEventID, title, date, location, cap, aR);
        }

        showInfo("Success", "Event updated!");
        displayList(); // Refresh the UI
    }
    //Show the Sub Attribute for Editing
    private void showSubEventAttribute(Event e){
        //Initialize Hidden
        eventTopic.setVisible(false);
        eventTopic.setManaged(false);

        eventSpeaker.setVisible(false);
        eventSpeaker.setManaged(false);

        eventAgeRestriction.setVisible(false);
        eventAgeRestriction.setManaged(false);

        if (e instanceof Workshop){
            //Show Topic TextField
            eventTopic.setVisible(true);
            eventTopic.setManaged(true);
        }
        else if (e instanceof Seminar){
            //Show Speaker TextField
            eventSpeaker.setVisible(true);
            eventSpeaker.setManaged(true);

        }
        else if (e instanceof Concert){
            //Show Age Restriction TextField
            eventAgeRestriction.setVisible(true);
            eventAgeRestriction.setManaged(true);
        }
    }
    //Search By ID Button
    @FXML
    private void searchByID(ActionEvent ev) {
        eventsContainer.getChildren().clear();
        String searchId = eventID.getText();
        Event e = event.findEventById(searchId);
        if (e != null) {
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

            //Allows this to be clickable
            titlelbl.setOnMouseClicked(event -> {
                showSelectedEvent(e);
                if (eventTopic != null){
                    //Show Sub Attribute if necessary
                    showSubEventAttribute(e);
                }
            });
            eventsContainer.getChildren().addAll(statusLbl);
        } else {
            eventsContainer.getChildren().add(new Label("Event not found."));
        }
    }

    //CANCEL EVENT
    @FXML
    private void cancelEvent(ActionEvent ev){
        //Use Event Selected
        Event e = event.findEventById(selectedEventID);
        event.cancelEvent(selectedEventID);
        //Update Title to Cancelled
        String title = "Cancelled "+e.getTitle();
        event.updateEvent(selectedEventID, title, e.getDateTime(), e.getLocation(), e.getCapacity());
        //Refresh UI
        displayList();
    }

    //VIEW EVENT ROSTER

    //SEARCH EVENTS BY TITLE
    @FXML
    private void searchByTitle(ActionEvent ev) {
        eventsContainer.getChildren().clear();
        String searchTitle = eventSearchTitle.getText();
        ArrayList<Event> searchEvents = EventManagement.getInstance().searchByTitle(searchTitle);
        for(Event e: searchEvents) {
            if (e != null) {
                VBox card = new VBox(5);
                card.setStyle("-fx-padding: 10; -fx-border-color: #cccccc; -fx-background-color: #f9f9f9; -fx-border-radius: 5;");

                //Title Label
                Label titlelbl = new Label("Title: " + e.getTitle());
                titlelbl.setStyle("-fx-padding:10; -fx-border-color: gray;");

                Label idLbl = new Label("ID: " + e.getEventId());
                Label dateLbl = new Label("Date: " + e.getDateTime());
                Label locationLbl = new Label("Loction: " + e.getLocation());
                Label capLbl = new Label("Capacity: " + e.getCapacity());

                Label statusLbl = new Label("Status: " + (e.getStatus() ? "Active" : "Cancelled"));
                statusLbl.setStyle(e.getStatus() ? "-fx-text-fill:green;" : "-fx-text-fill:red;");

                eventsContainer.getChildren().addAll(titlelbl, idLbl, dateLbl, locationLbl, capLbl);

                if (e instanceof Workshop) {
                    Workshop w = (Workshop) e;
                    Label topicLbl = new Label("Topic: " + w.getTopic());
                    eventsContainer.getChildren().add(topicLbl);
                } else if (e instanceof Seminar) {
                    Seminar s = (Seminar) e;
                    Label speakerLbl = new Label("Speaker: " + s.getSpeaker());
                    eventsContainer.getChildren().add(speakerLbl);
                } else if (e instanceof Concert) {
                    Concert c = (Concert) e;
                    Label ageLbl = new Label(c.getAgeRestriction() + "+");
                    eventsContainer.getChildren().add(ageLbl);
                }

                //Allows this to be clickable
                titlelbl.setOnMouseClicked(event -> {
                    showSelectedEvent(e);
                    if (eventTopic != null) {
                        //Show Sub Attribute if necessary
                        showSubEventAttribute(e);
                    }
                });
                eventsContainer.getChildren().addAll(statusLbl);
            } else {
                eventsContainer.getChildren().add(new Label("Event not found."));
            }
        }
    }
    //Seminar Filter
    private boolean seminarCheck(){
        return seminar.isSelected();
    }
    //Workshop Filter
    private boolean workshopCheck(){
        return workshop.isSelected();
    }
    //Concert Filter
    private boolean concertCheck(){
        return concerts.isSelected();
    }
    //Displays the filtered events
    @FXML
    private void filteredDisplay(ActionEvent ev){
        //Clear old VBox items
        eventsContainer.getChildren().clear();

        //Seminar is Checked
        if (seminarCheck()){
            for(Event e: event.getEventList()){
                if (e instanceof Seminar){
                    VBox card = new VBox(5);
                    card.setStyle("-fx-padding: 10; -fx-border-color: #cccccc; -fx-background-color: #f9f9f9; -fx-border-radius: 5;");

                    //Title Label
                    Label titlelbl = new Label("Title: " + e.getTitle());
                    titlelbl.setStyle("-fx-padding:10; -fx-border-color: gray;");

                    Label idLbl = new Label("ID: " + e.getEventId());
                    Label dateLbl = new Label("Date: " + e.getDateTime());
                    Label locationLbl = new Label("Loction: " + e.getLocation());
                    Label capLbl = new Label("Capacity: " + e.getCapacity());

                    Label statusLbl = new Label("Status: " + (e.getStatus() ? "Active" : "Cancelled"));
                    statusLbl.setStyle(e.getStatus() ? "-fx-text-fill:green;" : "-fx-text-fill:red;");

                    eventsContainer.getChildren().addAll(titlelbl, idLbl, dateLbl, locationLbl, capLbl);

                    Seminar s = (Seminar) e;
                    Label speakerLbl = new Label("Speaker: " + s.getSpeaker());
                    eventsContainer.getChildren().add(speakerLbl);
                }
            }
        }
        //WorkShop is Checked
        if (workshopCheck()){
            for(Event e: event.getEventList()){
                if (e instanceof Workshop){
                    VBox card = new VBox(5);
                    card.setStyle("-fx-padding: 10; -fx-border-color: #cccccc; -fx-background-color: #f9f9f9; -fx-border-radius: 5;");

                    //Title Label
                    Label titlelbl = new Label("Title: " + e.getTitle());
                    titlelbl.setStyle("-fx-padding:10; -fx-border-color: gray;");

                    Label idLbl = new Label("ID: " + e.getEventId());
                    Label dateLbl = new Label("Date: " + e.getDateTime());
                    Label locationLbl = new Label("Loction: " + e.getLocation());
                    Label capLbl = new Label("Capacity: " + e.getCapacity());

                    Label statusLbl = new Label("Status: " + (e.getStatus() ? "Active" : "Cancelled"));
                    statusLbl.setStyle(e.getStatus() ? "-fx-text-fill:green;" : "-fx-text-fill:red;");

                    eventsContainer.getChildren().addAll(titlelbl, idLbl, dateLbl, locationLbl, capLbl);

                    Workshop w = (Workshop) e;
                    Label topicLbl = new Label("Topic: " + w.getTopic());
                    eventsContainer.getChildren().add(topicLbl);
                }
            }
        }
        //Concert is Checked
        if (concertCheck()){
            for(Event e: event.getEventList()){
                if (e instanceof Concert){
                    VBox card = new VBox(5);
                    card.setStyle("-fx-padding: 10; -fx-border-color: #cccccc; -fx-background-color: #f9f9f9; -fx-border-radius: 5;");

                    //Title Label
                    Label titlelbl = new Label("Title: " + e.getTitle());
                    titlelbl.setStyle("-fx-padding:10; -fx-border-color: gray;");

                    Label idLbl = new Label("ID: " + e.getEventId());
                    Label dateLbl = new Label("Date: " + e.getDateTime());
                    Label locationLbl = new Label("Loction: " + e.getLocation());
                    Label capLbl = new Label("Capacity: " + e.getCapacity());

                    Label statusLbl = new Label("Status: " + (e.getStatus() ? "Active" : "Cancelled"));
                    statusLbl.setStyle(e.getStatus() ? "-fx-text-fill:green;" : "-fx-text-fill:red;");

                    eventsContainer.getChildren().addAll(titlelbl, idLbl, dateLbl, locationLbl, capLbl);

                    Concert c = (Concert) e;
                    Label ageLbl = new Label(c.getAgeRestriction() + "+");
                    eventsContainer.getChildren().add(ageLbl);
                }
            }
        }
    }
}
