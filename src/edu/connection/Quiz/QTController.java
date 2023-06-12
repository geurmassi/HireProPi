/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.Quiz;

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
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Cloud(ActionEvent event) {
    }

    @FXML
    private void mobile(ActionEvent event) {
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
        e.printStackTrace();
    }
}


    @FXML
    private void Business(ActionEvent event) {
    }
    
}
