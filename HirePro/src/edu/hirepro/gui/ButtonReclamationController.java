/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hirepro.gui;

import edu.hirepro.entities.Role;
import edu.hirepro.services.reclamationMetier;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author samsung
 */
public class ButtonReclamationController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Button buttonRec;
    @FXML
    private Button buttonVoir;
    reclamationMetier rm = new reclamationMetier();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            int userId = Integer.parseInt(label.getText());
            if (rm.getUserRoleById(userId) == Role.admin) {
               buttonVoir.setVisible(true);
            } 
        } catch (NumberFormatException e) {
            // Handle the case where the text in the label is not a valid integer
            System.out.println("Invalid user ID: " + label.getText());
            System.out.println(e.getMessage());
        }
        // TODO
    }

    public void setLbIdUser(String user) {
        label.setText(user);
    }

    @FXML
    private void reclamationChoise(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjoutReclamation.fxml"));
            Parent root = loader.load();
            AjoutReclamationController arc = loader.getController();
            arc.setLbIdUser(label.getText());
            label.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void voirReclamationAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReclamationFXML.fxml"));
            Parent root = loader.load();
            label.getScene().setRoot(root);
            ReclamationFXMLController rfc = new ReclamationFXMLController();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
