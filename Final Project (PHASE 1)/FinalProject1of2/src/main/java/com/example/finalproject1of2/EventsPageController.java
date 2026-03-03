//This Class is to control the UI for the events section
//Hopefully with Dynamic UI it can be different scene for each section
//Ex showing the text fields for create Event Button vs showing just a list of events for the list events button
package com.example.finalproject1of2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

//For What Each Button Will Do
public class EventsPageController {
    @FXML
    public VBox container;

    private Stage stage;
    private Scene scene;
    private Parent root;
    //The Text fields to create/update an event
    @FXML
    private TextField eventID = new TextField();
    @FXML
private TextField eventTitle = new TextField();
    @FXML
private TextField eventDate = new TextField();
    @FXML
private TextField eventLocation = new TextField();
    @FXML
private TextField eventCapacity = new TextField();
private String eventType = "";
    @FXML
    private TextField eventTopic = new TextField();
    @FXML
    private TextField eventSpeaker = new TextField();
    @FXML
    private TextField eventAgeRestriction = new TextField();

    //Change to the thingy that it was before get...()
EventManagement event = EventManagement.getInstance();

    @FXML
    private void handleCreateEvent(){
        String title = eventTitle.getText();
        String date = eventDate.getText();
        String location = eventLocation.getText();
        String capacity = eventCapacity.getText();

        //Add something to catch cap<0 and cap != number
        int cap = Integer.parseInt(capacity);

        //Create Event based on user inputs
        if (eventType.equals("Workshop")){
            String topic = eventTopic.getText();
            Workshop w = new Workshop(title, date, location, cap, topic);
            event.createEvent(w);
        }
        else if (eventType.equals("Seminar"))
        {
            String speaker = eventSpeaker.getText();
            Seminar s = new Seminar(title, date, location, cap, speaker);
            event.createEvent(s);
        }
        else if (eventType.equals("Concert")){
            String ageRestriction = eventAgeRestriction.getText();
            int aR = Integer.parseInt(ageRestriction);
            Concert c = new Concert(title, date, location, cap, aR);
            event.createEvent(c);
        }
        else{
            //Input for type is wrong
            //Loop and fix it
        }

        //Add event successfully created statement
        //Clear old input (and makes specific type button invisible again)
        //If it does create event then put a mistake message
    }

    //Workshop button (Makes textField visible)
    @FXML
    public void workShopButton(ActionEvent e){
        if (eventType.equals("")){
            eventTopic.setOpacity(1);
            eventType = "Workshop";
        }
    }

    //Seminar button (Makes textField visible)
    @FXML
    public void seminarButton(ActionEvent e){
        if (eventType.equals("")){
            eventSpeaker.setOpacity(1);
            eventType = "Seminar";
        }
    }

    //Concert button (Makes textField visible)
    @FXML
    public void concertButton(ActionEvent e){
        if (eventType.equals("")){
            eventAgeRestriction.setOpacity(1);
            eventType = "Concert";
        }
    }

    //List Events
    @FXML
    public void displayList(ActionEvent ev){
        //Clear old VBox items
        container.getChildren().clear();

        //Loop through List
        for(Event e :event.getEventList()){
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

            container.getChildren().addAll(titlelbl, idLbl, dateLbl, locationLbl,capLbl);

            if (e instanceof Workshop){
                Workshop w =(Workshop) e;
                Label topicLbl = new Label("Topic: "+w.getTopic());
                container.getChildren().add(topicLbl);
            }
            else if (e instanceof Seminar){
                Seminar s = (Seminar)e;
                Label speakerLbl = new Label("Speaker: "+ s.getSpeaker());
                container.getChildren().add(speakerLbl);
            }
            else if (e instanceof Concert){
                Concert c = (Concert)e;
                Label ageLbl = new Label(c.getAgeRestriction()+"+");
                container.getChildren().add(ageLbl);
            }

            container.getChildren().add(statusLbl);
        }
    }

    //Cancel Event
    public void search(ActionEvent ev){
        String eventCancel = eventID.getText();

        Event e = Event.findEventById(eventCancel);

        if (e != null) {
            container.getChildren().clear();
            //Display the event
            container.setStyle("-fx-padding: 10; -fx-border-color: #cccccc; -fx-background-color: #f9f9f9; -fx-border-radius: 5;");

            //Title Label
            Label titlelbl = new Label("Title: " + e.getTitle());
            titlelbl.setStyle("-fx-padding:10; -fx-border-color: gray;");

            Label idLbl = new Label("ID: " + e.getEventId());
            Label dateLbl = new Label("Date: " + e.getDateTime());
            Label locationLbl = new Label("Loction: " + e.getLocation());
            Label capLbl = new Label("Capacity: " + e.getCapacity());



            Label statusLbl = new Label("Status: " + (e.getStatus() ? "Active" : "Cancelled"));
            statusLbl.setStyle(e.getStatus() ? "-fx-text-fill:green;" : "-fx-text-fill:red;");

            container.getChildren().addAll(titlelbl, idLbl, dateLbl, locationLbl,capLbl);

            if (e instanceof Workshop){
                Workshop w =(Workshop) e;
                Label topicLbl = new Label("Topic: "+w.getTopic());
                container.getChildren().add(topicLbl);
            }
            else if (e instanceof Seminar){
                Seminar s = (Seminar)e;
                Label speakerLbl = new Label("Speaker: "+ s.getSpeaker());
                container.getChildren().add(speakerLbl);
            }
            else if (e instanceof Concert){
                Concert c = (Concert)e;
                Label ageLbl = new Label(c.getAgeRestriction()+"+");
                container.getChildren().add(ageLbl);
            }

            container.getChildren().add(statusLbl);

            container.getChildren().addAll(titlelbl, idLbl, dateLbl, locationLbl, capLbl, statusLbl);
        }

    }

    //Cancel the event
    public void cancelEventButton(ActionEvent ev){
        String eventCancel = eventID.getText();
        Event e = Event.findEventById(eventCancel);

        e.cancelEvent();
    }

    //Update Event
    @FXML
    public void titleButton(ActionEvent ev){
        eventTitle.setOpacity(1);
    }
    @FXML
    public void dateButton(ActionEvent ev){
        eventDate.setOpacity(1);
    }
    @FXML
    public void locationButton(ActionEvent ev){
        eventLocation.setOpacity(1);
    }
    @FXML
    public void capacityButton(ActionEvent ev){
        eventCapacity.setOpacity(1);
    }
    @FXML
    public void showSubEventAttribute(ActionEvent ev){
        //Get the specific event
        String eventCancel = eventID.getText();
        Event e = Event.findEventById(eventCancel);

        if (e instanceof Workshop){
            eventTopic.setOpacity(1);
        }
        else if (e instanceof Seminar){
            eventSpeaker.setOpacity(1);
        }
        else if (e instanceof Concert){
            eventAgeRestriction.setOpacity(1);
        }
    }

    @FXML
    public void updateEvent(ActionEvent ev){
        //Get the specific event
        String updateEvent = eventID.getText();
        Event e = Event.findEventById(updateEvent);

        //Get updated fields otherwise keep them at their orginal
        String title = eventTitle.getText();
        if(title.isEmpty()){title = e.getTitle();}
        String date = eventDate.getText();
        if(date.isEmpty()){title = e.getDateTime();}
        String location = eventLocation.getText();
        if(location.isEmpty()){title = e.getLocation();}
        String capacity = eventCapacity.getText();
        if(capacity.isEmpty()){int cap = e.getCapacity();}
        String topic = eventTopic.getText();
        String speaker = eventSpeaker.getText();
        String ageRestriction = eventAgeRestriction.getText();
        int cap = Integer.parseInt(capacity);

        //Check which type of event it is
        if (e instanceof Workshop){
            Workshop w = (Workshop) e;
            if(topic.isEmpty()){topic = w.getTopic();}
            w.updateEvent(title, date, location, cap, topic);
        }
        else if (e instanceof Seminar){
            Seminar s = (Seminar) e;
            if(speaker.isEmpty()){speaker = s.getSpeaker();}
            s.updateEvent(title, date, location, cap, speaker);
        }
        else if (e instanceof Concert){
            Concert c = (Concert)e;
            if(ageRestriction.isEmpty()){ageRestriction = c.getAgeRestriction();}
            int aR = Integer.parseInt(ageRestriction);
            c.updateEvent(title, date, location, cap, aR);
        }
    }

    //Sends user back to the Main Menu
    public void backToMenuE(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
