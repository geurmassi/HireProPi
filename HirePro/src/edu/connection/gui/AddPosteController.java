/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.gui;

import edu.connection.entities.Poste;
import edu.connection.services.PosteCrud;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AddPosteController implements Initializable {

    @FXML
    private TextField tfPoste;
    @FXML
    private Button btnSubmit;

    /**
     * Initializes the controller class.
     */
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        // TODO
    }

    private void handlebtnPost() {
        // Load the AfficherOffre.fxml file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherPoste.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Create a new stage for AfficherOffre and set the scene
            Stage afficherPosteStage = new Stage();
            afficherPosteStage.setScene(scene);
            afficherPosteStage.setTitle("Afficher Postes");

            // Show the AfficherOffre stage
            afficherPosteStage.show();

            // Close the primary stage
            primaryStage.close();
        } catch (IOException ex) {
            System.out.println("Error loading FXML file");
            ex.printStackTrace();
        }
    }

    @FXML
    private void AddPoste(ActionEvent event) {
 
        String poste = (String) tfPoste.getText();

        if (poste == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Poste");
            alert.setContentText("Please write somthing.");
            alert.showAndWait();
            return;
        }

        Poste p = new Poste(poste);
        PosteCrud Impl = new PosteCrud();
        Impl.addEntity(p);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Poste Added");
        alert.setContentText("The Poste has been successfully added.");
        alert.showAndWait();

        // Clear input fields
        tfPoste.setText("");

        handlebtnPost();
    }

}
