/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.Quiz;

import edu.connection.Cloud.QuizCController;
import edu.connection.Mobile.QuizCCController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class QTController implements Initializable {

    @FXML
    private Button Cloud;
    @FXML
    private Button mobile;
    @FXML
    private Button web;
    @FXML
    private Button Business;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
private void Cloud(ActionEvent event) {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/connection/Cloud/QuizC.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Cloud Quiz");

        // Access the controller of the Cloud Quiz
        QuizCController controller = fxmlLoader.getController();
        // Set any necessary data or parameters to the controller if needed

        stage.show();
    } catch (IOException e) {
    }
}


   @FXML
private void mobile(ActionEvent event) {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/connection/Mobile/QuizCC.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Mobile Quiz");

        // Access the controller of the Mobile Quiz
        QuizCCController controller = fxmlLoader.getController();
        // Set any necessary data or parameters to the controller if needed

        stage.show();
    } catch (IOException e) {
    }
}


    @FXML
private void web(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/connection/Quiz/StandardTemplate.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Standard Template");

        StandardTemplateController controller = loader.getController();
        // Set any necessary data or parameters to the controller if needed

        stage.show();
    } catch (IOException e) {
    }
}

    @FXML  
    private void Business(ActionEvent event) {
    try {
        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource("/edu/connection/Quiz/BusinessTemplate.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Standard Template");

        BusinessTemplateController controller = loader.getController();
        // Set any necessary data or parameters to the controller if needed

        stage.show();
    } catch (IOException e) {
    }
}
    
}
