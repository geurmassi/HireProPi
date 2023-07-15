/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hirepro.gui;

import edu.hirepro.services.UserMETIER;
import edu.hirepro.entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author samsung
 */
public class UserRechercheController implements Initializable {

    @FXML
    private TextField idrech;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void rechercheAction(ActionEvent event) throws IOException {
        int id = Integer.parseInt(idrech.getText());
        UserMETIER um = new UserMETIER();
        User u = new User();
        u = um.rechercheUserByID(id);
        if (u.getId() != 0 && u.getNom() != null && u.getPrenom() != null && u.getAdresse() != null
                && u.getEmail() != null && u.getPassword() != null) {
            Alert a = new Alert(Alert.AlertType.INFORMATION, "User Exsicte", ButtonType.OK);
            a.showAndWait();
            System.out.println(u);
            System.out.println(id);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ButtonReclamation.fxml"));
            Parent root = loader.load();
            idrech.getScene().setRoot(root);
//            ReclamationFXMLController apc = loader.getController();
//            apc.setLbIdUser(idrech.getText());
            ButtonReclamationController brc = loader.getController();
            brc.setLbIdUser(idrech.getText());
            brc.initialize(null, null);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "User n'Exsicte pas", ButtonType.OK);
            a.showAndWait();
            System.out.println(u);
            System.out.println(id);
        }
    }

}
