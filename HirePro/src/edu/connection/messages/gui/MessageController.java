/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.messages.gui;

import edu.connection.entities.Message;
import edu.connection.entities.User;
import edu.connection.services.MessageCRUD;
import edu.connection.services.UserCRUD;
import java.awt.Color;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author haith
 */
public class MessageController implements Initializable {

    @FXML
    private AnchorPane ap_main;
    @FXML
    private Button button_send;
    @FXML
    private TextField tf_message;
    @FXML
    private ScrollPane sp_main;
    @FXML
    private VBox vbox_messages;
    @FXML
    private ChoiceBox<String> usersChoiceBox;
    @FXML
    private Circle activeCercle;
     private String[] users={};
     private List<User> listOfUsers= new ArrayList();
     private int userConnected =1;
    

    /**
     * Initializes the controller class.
     */
     
    public void getMessagesFromDataBase(int userReceive) {
        MessageCRUD MC = new MessageCRUD();
        List<Message> messageList = MC.displayEntities();
        
        for (Message message : messageList) {
           
            String msg = message.getMsg();
            int idUserSend = message.getIdUserSend();
            int idUserReceive = message.getIdUserReceive();
            
            Text text = new Text(msg);
            TextFlow textFlow = new TextFlow(text);
            HBox hbox = new HBox();
            
            textFlow.setPadding(new Insets(5, 5, 5, 10));
            String textFlowCSS="-fx-background-color:rgb(15, 125, 242); -fx-background-radius: 10;";
            if (idUserSend ==userConnected&&idUserReceive ==userReceive) {
                 hbox.setAlignment(Pos.CENTER_LEFT);
                 textFlowCSS="-fx-background-color:rgb(15, 125, 242); -fx-background-radius: 10;";
                 textFlow.setStyle(textFlowCSS);
                 hbox.getChildren().add(textFlow);
            } else if (idUserSend ==userReceive&&idUserReceive ==userConnected) {
              hbox.setAlignment(Pos.CENTER_RIGHT);
               textFlowCSS="-fx-background-color:rgb(180,180,180); -fx-background-radius: 10;";
               textFlow.setStyle(textFlowCSS);
               hbox.getChildren().add(textFlow);
            }
            
            vbox_messages.getChildren().add(hbox);
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       UserCRUD U = new UserCRUD();
       listOfUsers= U.displayEntities(); 
        // Create a HashMap to store the mapping between users names and user objects
        HashMap<String, User> userMap = new HashMap<>();
        // Populate the HashMap
        for (User user : listOfUsers) {
            if (user.getId()!=userConnected){ String userName = user.getNom();
            String[] newUser = Arrays.copyOf(users, users.length + 1);
            newUser[users.length] = userName;
            users = newUser;
            userMap.put(userName, user);}
           
        }
       // Update the choiceBox items
       usersChoiceBox.getItems().addAll(users);

        // Declare selectedUser as a final variable
        final User[] selectedUser = {null};

        usersChoiceBox.setOnAction((event) -> {
            // Get the selected users
            String selectedUserfromChoice = usersChoiceBox.getValue();
            // Retrieve the corresponding user object
            selectedUser[0] = userMap.get(selectedUserfromChoice);
            // Clear the sp_main ScrollPane content
            VBox content = (VBox) sp_main.getContent();
            content.getChildren().clear();
            System.out.println(selectedUser[0]);
            // Call your function or perform any desired actions with the selected user
            getMessagesFromDataBase(selectedUser[0].getId());
            if (selectedUser[0].getActif()==1){
                activeCercle.setStroke(Paint.valueOf("#7FFF65"));
                activeCercle.setFill(Paint.valueOf("#7FFF65"));
            }else{
                activeCercle.setStroke(Paint.valueOf("#e1dcdc"));
                activeCercle.setFill(Paint.valueOf("#e6e8eb"));
            }
            
            
        });
        

        button_send.setOnAction((event) -> {
            saveMessages( selectedUser[0].getId());
        });
        
    }    

    
    private void saveMessages(int userReceive) {
        String msg=tf_message.getText();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Message M=new Message(msg,timestamp,userConnected,userReceive);
        MessageCRUD MC=new MessageCRUD();
        MC.addEntity(M);
        tf_message.clear();
        // Clear the sp_main ScrollPane content
        VBox content = (VBox) sp_main.getContent();
        content.getChildren().clear();
        getMessagesFromDataBase(userReceive);
        
        
    }
    
    
    
}
