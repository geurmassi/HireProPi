/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.gui;

import edu.connection.entities.Reclamation;
import edu.connection.api.EmailSender;
import edu.connection.entities.UserConnect;
import edu.connection.services.ReclamationCRUD;
import edu.connection.services.reclamationMetier;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author samsung
 */
public class AjoutReclamationController implements Initializable {

    @FXML
    private TextField TObjet;
    @FXML
    private TextArea TContinue;
    @FXML
    private Label lbIdUser;
    @FXML
    private TextField TfUserReclam;
    @FXML
    private ListView<String> resultList;

    private reclamationMetier rm = new reclamationMetier();
    private BooleanProperty resultListVisible = new SimpleBooleanProperty(false);

    String selectedEmail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resultList.visibleProperty().bind(resultListVisible);
        // TODO
    }

    public void setLbIdUser(String user) {
        if (lbIdUser != null) {
            lbIdUser.setText(user);
        } else {
            System.out.println("Label is null.");
        }
    }

    @FXML
    private void AjoutAction(ActionEvent event) {

  
        int iduser=UserConnect.idUserConnect;
      //int iduser=13;
  
        if (TObjet.getText().isEmpty() || TContinue.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Continu ou objet invalide(s)", ButtonType.OK);
            a.showAndWait();
        } else if (rm.getReclamationCountByUserIdAndDate(iduser) > 10) {
            Alert a = new Alert(Alert.AlertType.ERROR, "vous ne pouvez pas ajouter une reclamation", ButtonType.OK);
            a.showAndWait();
        } else if (rm.containsBadWord(TContinue.getText())) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Continu contient des gros mots", ButtonType.OK);
            a.showAndWait();
            TContinue.setText("");
            TObjet.setText("");
            TfUserReclam.setText("");
        } else if (selectedEmail.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Veuillez choisir un utilisateur", ButtonType.OK);
            a.showAndWait();
        } else {
            ReclamationCRUD rc = new ReclamationCRUD();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            System.out.println(selectedEmail);
            int idUserReclame = rm.getUserIdByEmail(selectedEmail);
            System.out.println(idUserReclame);
            //System.out.println(iduser);
            Reclamation r = new Reclamation(TContinue.getText(), TObjet.getText(), timestamp, iduser, idUserReclame);
            rc.addEntity(r);
            String recipientEmail = "athmouniamina2019@gmail.com";
            String senderEmail = "athmouni.amina@esprit.tn";
            String senderPassword = "221SFT0050";
            String objet = "Cher administrateur, Vous avez reçu une nouvelle réclamation de la part de " + rm.getFullNameById(r.getIdUser());
            String message = "Cher administrateur,\n"
                    + "\n"
                    + "Nous vous informons par la présente que nous avons reçu une réclamation de la part "
                    + "de " + rm.getFullNameById(r.getIdUser()) + " . Veuillez trouver ci-dessous les détails de la réclamation :\n"
                    + "\n"
                    + "Objet de la réclamation : " + r.getObjet() + "\n"
                    + "Contenu de la réclamation : " + r.getContenue() + "\n"
                    + "\n"
                    + "Nous vous prions de prendre les mesures nécessaires pour examiner cette réclamation et "
                    + "y répondre dans les meilleurs délais. Votre attention et votre promptitude dans le traitement"
                    + " des réclamations sont essentielles pour assurer la satisfaction de nos utilisateurs.\n"
                    + "\n"
                    + "Si vous avez des questions ou des besoins supplémentaires d'informations, n'hésitez pas à "
                    + "nous contacter.\n"
                    + "\n"
                    + "Cordialement,\n"
                    + "L'équipe d'assistance technique";
            EmailSender.sendEmail(recipientEmail, senderEmail, senderPassword, objet, message);
            Alert a = new Alert(Alert.AlertType.INFORMATION, "reclamation added !", ButtonType.OK);
            a.showAndWait();
            TObjet.setText("");
            TContinue.setText("");
            TfUserReclam.setText("");
//                System.out.println(rm.getReclamationCountByUserIdAndDate(iduser));
//                System.out.println(iduser);
        }
    }

    @FXML
    private void handleSearch(KeyEvent event) {
        String searchQuery = TfUserReclam.getText();

        if (searchQuery.isEmpty()) {
            resultListVisible.set(false);
        } else {
            ObservableList<String> searchResults = rm.performSearch(searchQuery);
            resultList.setItems(searchResults);
            resultListVisible.set(true);
        }
    }

    @FXML
    private void handleResultSelection(MouseEvent event) {
        String selectedItem = resultList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            int delimiterIndex = selectedItem.lastIndexOf("-");
            if (delimiterIndex != -1) {
                String fullName = selectedItem.substring(0, delimiterIndex).trim();
                String email = selectedItem.substring(delimiterIndex + 1).trim();
                TfUserReclam.setText(fullName);
                selectedEmail = email; // Save the email in a separate variable if needed
                System.out.println("Selected Email: " + selectedEmail);
            }
            resultListVisible.set(false);
        }
        event.consume();
    }

}
