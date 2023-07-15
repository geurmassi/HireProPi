/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.gui;

import edu.HirePro.entities.User;
import edu.HirePro.services.UserMETIER;
import edu.HirePro.services.candidatureMETIEER;
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
 * @author LENOVO
 */
public class UserFXMLController implements Initializable {

    @FXML
    private Label idname;
    @FXML
    private TextField lbidu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        int id = Integer.parseInt(lidu.getText());
//        candidatureMETIEER cm = new candidatureMETIEER();
//        String name = cm.rechercherUserbyId(id);
//        idname.setText(name);
//        nameuser = idname.getText();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("Condidature.fxml"));
//        Parent root = null;
//        try {
//            root = loader.load();
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());;
//        }
//        idname.getScene().setRoot(root);
//        CondidatureController cc = loader.getController();
//        cc.setLbNom(idname.getText());
        // TODO
    }

    @FXML
    private void rechercherAction(ActionEvent event) throws IOException {
        int id = Integer.parseInt(lbidu.getText());
        System.out.println(id);
        System.out.println(lbidu.getText());
        UserMETIER um = new UserMETIER();
        User u = new User();
        u = um.rechercheUserByID(id);
        if (u.getId() != 0 && u.getNom() != null && u.getPrenom() != null && u.getAdresse() != null
                && u.getEmail() != null && u.getPassword() != null) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "User Exsicte", ButtonType.OK);
            a.showAndWait();
            System.out.println(u);
            // System.out.println(id);
            candidatureMETIEER cm = new candidatureMETIEER();
            String name = cm.rechercheNameUserbyId(id);
            idname.setText(name);
            String userRole = cm.retrieveUserRoleById(id);
            // System.out.println(userRole);
            if (userRole != null) {
                if (userRole.equals("candidat")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("afficheOffreEtPoste.fxml"));
                    Parent root = loader.load();
                    idname.getScene().setRoot(root);
                    AfficheOffreEtPosteController apc = loader.getController();
                    apc.setLbname(idname.getText());
                    apc.setLbIdUser(lbidu.getText());
//                    AjouterCandidatureController acc = loader.getController();
//                    acc.setLbiduser(lidu.getText());
                } else if (userRole.equals("recruteur")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("RecruteurCandidature.fxml"));
                    Parent root = loader.load();
                    idname.getScene().setRoot(root);
                    RecruteurCandidatureController controller = loader.getController();
                    String ids = lbidu.getText();
                    controller.setIdUser(ids);
                    controller.initialize(null, null);
                    // System.out.println(ids);
                } else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Condidature.fxml"));
                    Parent root = loader.load();
                    idname.getScene().setRoot(root);
                }}

            } else {
                Alert al = new Alert(Alert.AlertType.ERROR, "User n'Exsicte pas", ButtonType.OK);
                al.showAndWait();
                System.out.println(u);
                System.out.println(id);
            }

        }

//    public String getNameuser() throws IOException {
//        int id = Integer.parseInt(lidu.getText());
//        candidatureMETIEER cm = new candidatureMETIEER();
//        String name = cm.rechercherUserbyId(id);
//        idname.setText(name);
//        nameuser = idname.getText();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("Condidature.fxml"));
//        Parent root = loader.load();
//        idname.getScene().setRoot(root);
//        CondidatureController cc = loader.getController();
//        cc.setLbNom(idname.getText());
//        return nameuser;
//        
//    }
    }

