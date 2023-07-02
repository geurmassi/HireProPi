/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.messages.gui;

import edu.connection.entities.Message;
import edu.connection.entities.User;
import edu.connection.services.EmailSender;
import edu.connection.services.MessageCRUD;
import edu.connection.services.UserCRUD;
import java.awt.Color;
import java.io.IOException;
import edu.connection.services.MessageCRUD;
import edu.connection.services.UserCRUD;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.transform.Scale;

/**
 * FXML Controller class
 *
 * @author haith
 */
public class MessageController implements Initializable {

    @FXML
    private AnchorPane ap_main;
    @FXML
    private ImageView  button_send;
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
     private User objecForUserConnceted;
     private User objectSelectedUser;
     private String fileBase64String;
     private String fileName;
    

     
     
     
     
     
     
     
     
     
     private String convertFileToBase64(String filePath) throws IOException {
    File file = new File(filePath);
    FileInputStream fileInputStream = new FileInputStream(file);
    byte[] fileBytes = new byte[(int) file.length()];
    fileInputStream.read(fileBytes);
    fileInputStream.close();

    return Base64.getEncoder().encodeToString(fileBytes);
}
    private void downloadFileFromBase64(String base64String, String filePath) throws IOException {
    byte[] fileBytes = Base64.getDecoder().decode(base64String);
    FileOutputStream fileOutputStream = new FileOutputStream(filePath);
    fileOutputStream.write(fileBytes);
    fileOutputStream.close();
}
     
     @FXML
private void handleUploadButtonClick(ActionEvent event) throws IOException {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select File to Upload");
    Stage stage = (Stage) ap_main.getScene().getWindow();
    File file = fileChooser.showOpenDialog(stage);
    
    if (file != null) {
        // Convert the file to a byte array
        String fileBytes = convertFileToBase64(file.getAbsolutePath());
        int startIndex = file.getAbsolutePath().lastIndexOf("\\") + 1; // Add 1 to exclude the last backslash
        int endIndex = file.getAbsolutePath().lastIndexOf(".pdf");
        fileName = file.getAbsolutePath().substring(startIndex, endIndex);
                

       // downloadFileFromBase64(fileBytes, "C:\\Users\\haith\\Downloads\\HirePro-Kamel\\teeeeeeeest.pdf");
        fileBase64String=fileBytes;
    } else {
         fileBase64String="";
        // No file selected
        System.out.println("No file selected.");
    }
}
    /**
     * Initializes the controller class.
     */
 
    public void getMessagesFromDataBase(int userReceive) {
        MessageCRUD MC = new MessageCRUD();
        List<Message> messageList = MC.displayEntities();
        HBox hbox=new HBox();
        VBox.setMargin(hbox, new Insets(0, 0, 0, 0));
        for (Message message : messageList) {
            String msg = message.getMsg();
            int idUserSend = message.getIdUserSend();
            int idUserReceive = message.getIdUserReceive();
            
            Text text = new Text(msg);
            TextFlow textFlow = new TextFlow(text);
            hbox = new HBox();
            
            textFlow.setPadding(new Insets(5, 5, 5, 10));
            
            
            
            String textFlowCSS="-fx-background-color:rgb(15, 125, 242); -fx-background-radius: 10";
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
            if(idUserSend ==userConnected&&idUserReceive ==userReceive&&message.getFile()!=null){
            String pdfIconPath = "C:\\Users\\haith\\Downloads\\HirePro-Kamel\\HirePro\\src\\images\\pdfIcon.png";
            Image pdfIconImage = new Image(new File(pdfIconPath).toURI().toString());
            ImageView pdfIcon = new ImageView(pdfIconImage);
            pdfIcon.setFitHeight(20);
            pdfIcon.setFitWidth(30);

            
            pdfIcon.setOnMouseClicked(event -> {
                try {
                    //String userHomeDirectory = System.getProperty("user.home");
                    //String downloadFolderPath = userHomeDirectory + File.separator + "Downloads";
                    // System.out.println(userHomeDirectory);
                    downloadFileFromBase64(message.getFile(), "C:\\Users\\haith\\Downloads\\teeeeeeeest.pdf");
                }
                catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
});
            // Create a VBox to hold the pdfIcon and the fileNameLabel
            VBox vbox = new VBox();
            
             vbox.setMinHeight(10);
           
            // Add the pdfIcon to the VBox
            vbox.getChildren().add(pdfIcon);

            // Create a Label for the file name
            String fileName = "teeeeeeeest";
            Label fileNameLabel = new Label(fileName);

            // Add some spacing between the pdfIcon and the file name label
            vbox.setSpacing(5);

            // Add the fileNameLabel to the VBox
            vbox.getChildren().add(fileNameLabel);

            // Add the VBox to the HBox
            hbox.getChildren().add(vbox);
            }
            
            vbox_messages.getChildren().add(hbox);
            VBox.setMargin(hbox, new Insets(0, 0, 0, 0));
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
            else {
                 objecForUserConnceted = user;
                }
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
          objectSelectedUser=selectedUser[0];  
        });
    }    
    @FXML
    private void handleButtonClick() {
        // Call your function here
        System.out.println(objectSelectedUser);
        saveMessages(objectSelectedUser, objecForUserConnceted);
    }
private void sendMailWhenUserDisconnected(User userReceive,User objecForUserConnceted) {
    if (userReceive.getActif()!=1){
        EmailSender emailSender = new EmailSender();

        String recipient = userReceive.getEmail();
        String recipientName = userReceive.getNom();
        String receiver = objecForUserConnceted.getNom();
        String subject = "you have new message ||HirePro||";
        String template ="<html>\n" +
        "<head>\n" +
        "<style>\n" +
        "body { text-align: center; }\n" +
        "</style>\n" +
        "</head>\n" +
        "<body>\n" +
        "<h1>Dear {{recipientName}},</h1>\n" +  
        "<p>1 new message from {{receiver}} waiting for your answer</p>\n" +
        "<p>Best regards,</p>\n" +
        "<p>The HirePro Team</p>\n" +
        "</body>\n" +
        "</html>";
        String imagePath="C:\\Users\\haith\\Downloads\\HirePro-Kamel\\HirePro\\src\\images\\hireProLogo.jpg";
            try {
                template = template.replace("{{receiver}}", receiver);
                template = template.replace("{{recipientName}}", recipientName);
                emailSender.sendEmail(recipient, subject, template, imagePath);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
}
    
    private void saveMessages(User userReceive,User objecForUserConnceted) {
        System.out.println(fileName+"55555555");
        String msg=tf_message.getText();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Message M=new Message(msg,timestamp,userConnected,userReceive.getId(),fileBase64String,fileName);
        MessageCRUD MC=new MessageCRUD();
        MC.addEntity(M);
        tf_message.clear();
        // Clear the sp_main ScrollPane content
        VBox content = (VBox) sp_main.getContent();
        content.getChildren().clear();
        getMessagesFromDataBase(userReceive.getId());
        
        sendMailWhenUserDisconnected(userReceive,objecForUserConnceted);
    }
    
    
    
}
