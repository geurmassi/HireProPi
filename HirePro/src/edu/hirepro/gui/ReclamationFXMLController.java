/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hirepro.gui;

import com.itextpdf.text.DocumentException;
import edu.hirepro.pi.EmailSender;
import edu.hirepro.pi.PDFCreator;
import edu.hirepro.services.ReclamationCRUD;
import edu.hirepro.services.reclamationMetier;
import edu.hirepro.entities.Etat;
import edu.hirepro.entities.Reclamation;
import edu.hirepro.entities.User;
import edu.hirepro.services.UserCRUD;
import java.awt.Desktop;
import java.nio.file.Path;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author samsung
 */
public class ReclamationFXMLController implements Initializable {

    @FXML
    private TableColumn<Reclamation, String> contenue;
    @FXML
    private TableColumn<Reclamation, String> objet;
    @FXML
    private TableColumn<Reclamation, Timestamp> time;
    @FXML
    private TableView<Reclamation> tableaurec;

    private ObservableList<Reclamation> reclamationInstance;
    @FXML
    private Label lbcount;
    @FXML
    private Button buttDesactiver;
    @FXML
    private Button buttonrep;
    @FXML
    private Label lbCon;
    @FXML
    private Label lbobj;

    Reclamation selectedReclamation;
    int selectedIdR;
    int selectedIdUser;
    int selectedIdUserReclame;
    reclamationMetier rm = new reclamationMetier();
    @FXML
    private Label lbcount1;
    @FXML
    private TableColumn<Reclamation, Integer> idR;
    @FXML
    private TableColumn<Reclamation, Integer> idUser;
    @FXML
    private TableColumn<Reclamation, Integer> idUserReclame;
    @FXML
    private TableColumn<Reclamation, Etat> etat;
    @FXML
    private TableColumn<Reclamation, String> nomavecmail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reclamationInstance = FXCollections.observableList(rm.displayEntitiesNonvue());
        contenue.setCellValueFactory(new PropertyValueFactory<>("contenue"));
        objet.setCellValueFactory(new PropertyValueFactory<>("objet"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        idR.setCellValueFactory(new PropertyValueFactory<>("idR"));
        idUser.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        idUserReclame.setCellValueFactory(new PropertyValueFactory<>("idUserReclame"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        nomavecmail.setCellValueFactory(cellData -> {
            Reclamation reclamation = cellData.getValue();
            int idUserReclame = reclamation.getIdUserReclame();
            String fullNameWithEmail = rm.getFullNameById(idUserReclame);
            return new SimpleStringProperty(fullNameWithEmail);
        });
        tableaurec.setItems(reclamationInstance);
        lbobj.setVisible(false);
        lbCon.setVisible(false);
        buttDesactiver.setVisible(false);
        buttonrep.setVisible(false);
        tableaurec.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Retrieve the selected reclamation entity
                selectedReclamation = tableaurec.getSelectionModel().getSelectedItem();
                // Retrieve the data from the selected reclamation entity
                selectedIdR = selectedReclamation.getIdR();
                String selectedContenue = selectedReclamation.getContenue();
                String selectedObjet = selectedReclamation.getObjet();
                selectedIdUser = selectedReclamation.getIdUser();
                selectedIdUserReclame = selectedReclamation.getIdUserReclame();
                System.out.println(selectedReclamation);
                lbobj.setVisible(true);
                lbCon.setVisible(true);
                lbCon.setText("Continue   : " + selectedContenue);
                lbobj.setText("Objet      : " + selectedObjet);
                buttDesactiver.setVisible(true);
                buttonrep.setVisible(true);

            }
        });
        int count = rm.conterReclamation();
        lbcount.setText("Total Reclamation : " + count);
        int count1 = rm.conterReclamationNonVue();
        lbcount1.setText("Total Reclamation non vue : " + count1);

    }

    private void loadReclamations() {
        List<Reclamation> reclamations = rm.displayEntitiesNonvue();
        reclamationInstance.clear();
        reclamationInstance.addAll(reclamations);
    }

    @FXML
    private void CreationPdf(ActionEvent event) {
        try {
            PDFCreator p = new PDFCreator();
            Path pdfFilePath = p.createPDF(tableaurec, "Liste de reclamation.pdf");
            java.io.File file = new java.io.File(pdfFilePath.toString());
            Desktop.getDesktop().open(file);
        } catch (DocumentException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void DesactiverAction(ActionEvent event) {
        System.out.println(selectedIdUserReclame);
        User u = rm.getUserByIdUserReclame(selectedIdUserReclame);
        System.out.println(u);
        u.setActif(false);
        UserCRUD uc = new UserCRUD();
        uc.update(u);

        // Update reclamations to invalid
        reclamationMetier rm = new reclamationMetier();
        rm.updateReclamationsByUserIdReclame(selectedIdUserReclame);

        // Send email notification to the person who has been blocked
        String blockedPersonEmail = rm.getEmailByUserId(selectedIdUserReclame);
        String blockedPersonName = rm.getFullNameById(selectedIdUserReclame);
        String message = "Cher " + blockedPersonName + ",\n\nNous vous informons que votre compte a été bloqué en raison de réclamations qui nous ont été soumises. "
                + "Nous vous encourageons à contacter notre équipe de support pour plus d'informations et pour résoudre les problèmes éventuels.\n\n"
                + "Cordialement,\nL'équipe de gestion des réclamations";
        String senderEmail = "athmouni.amina@esprit.tn";
        String senderPassword = "221SFT0050";
        EmailSender.sendEmail(blockedPersonEmail, senderEmail, senderPassword, "Votre compte a été bloqué", message);

        // Send email notifications to users who submitted the reclamations
        sendEmailNotifications(selectedIdUserReclame);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Utilisateur désactivé");
        alert.setHeaderText(null);
        alert.setContentText("L'utilisateur a été désactivé avec succès.");
        alert.showAndWait();
        loadReclamations();
        int count1 = rm.conterReclamationNonVue();
        lbcount1.setText("Total Reclamation non vue : " + count1);
        buttDesactiver.setVisible(true);
        buttonrep.setVisible(true);
        lbobj.setVisible(true);
        lbCon.setVisible(true);
    }

    @FXML
    private void repondreAction(ActionEvent event) {
        // Get the selected reclamaion and its associated user
        Reclamation selectedReclamation = tableaurec.getSelectionModel().getSelectedItem();
        int idUserReclame = selectedReclamation.getIdUserReclame();
        User user = rm.getUserByIdUserReclame(idUserReclame);

        // Show the reply dialog to get the email reply
        Dialog<String> replyDialog = new Dialog<>();
        replyDialog.setTitle("Reply Email");
        replyDialog.setHeaderText("Compose Reply Email");

        // Create a GridPane for the content
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Create a TextField for input
        TextArea replyTextArea = new TextArea();
        replyTextArea.setPrefWidth(300);
        replyTextArea.setPrefHeight(100);

        // Add the TextField to the GridPane
        gridPane.add(replyTextArea, 0, 0);

        // Set the GridPane as the content of the dialog
        replyDialog.getDialogPane().setContent(gridPane);

        // Create the buttons
        ButtonType sendButton = new ButtonType("Send", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Set the buttons in the dialog
        replyDialog.getDialogPane().getButtonTypes().addAll(sendButton, cancelButton);

        // Set the result converter to return the entered text
        replyDialog.setResultConverter(dialogButton -> {
            if (dialogButton == sendButton) {
                return replyTextArea.getText();
            }
            return null;
        });

        // Show the dialog and wait for user input
        Optional<String> replyResult = replyDialog.showAndWait();
        if (replyResult.isPresent()) {
            String replyEmail = replyResult.get();

            // Show a confirmation dialog before sending the email
            Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Send Email Reply");
            confirmationAlert.setContentText("Are you sure you want to send an email reply to " + user.getNom() + "?");

            Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();
            if (confirmationResult.isPresent() && confirmationResult.get() == ButtonType.OK) {
                // Send the email reply
                String userEmail = user.getEmail();
                String senderEmail = "athmouni.amina@esprit.tn";
                String senderPassword = "221SFT0050";
                String subject = "Reply to Your Reclamation";
                String message = replyEmail;

                EmailSender.sendEmail(userEmail, senderEmail, senderPassword, subject, message);

                // Update the reclamations etat to "vue"
                selectedReclamation.setEtat(Etat.vue);
                ReclamationCRUD rc = new ReclamationCRUD();
                rc.update(selectedReclamation);

                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("Email Sent");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Your email reply has been sent successfully.");
                successAlert.showAndWait();

                loadReclamations();
                int count1 = rm.conterReclamationNonVue();
                lbcount1.setText("Total Reclamation non vue : " + count1);
                buttDesactiver.setVisible(false);
                buttonrep.setVisible(false);
                lbobj.setVisible(false);
                lbCon.setVisible(false);
            }
        }
    }

    @FXML
    private void goRecVue(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReclamationVue.fxml"));
            Parent root = loader.load();
            lbCon.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void sendEmailNotifications(int idUserReclame) {
        List<Reclamation> reclamations = rm.getReclamationsByUserIdReclame(idUserReclame);

        for (Reclamation reclamation : reclamations) {
            int idUser = reclamation.getIdUser();
            String userEmail = rm.getEmailByUserId(idUser);
            String blockedPersonName = rm.getFullNameById(idUser);
            String message = "Cher utilisateur,\n\nVotre réclamation a été examinée, et nous tenons à vous informer que "
                    + "la personne que vous avez réclamée, " + blockedPersonName + ", est maintenant inactive. Nous apprécions "
                    + "votre retour d'information et prendrons les mesures appropriées pour résoudre le problème.\n\nNous vous "
                    + "remercions de votre compréhension et de votre coopération.\n\nCordialement,\nL'équipe de gestion des réclamations";

            String senderEmail = "athmouni.amina@esprit.tn";
            String senderPassword = "221SFT0050";

            try {
                if (userEmail != null) {
                    EmailSender.sendEmail(userEmail, senderEmail, senderPassword, "Mise à jour de l'état de la réclamation", message);
                }
            } catch (Exception e) {
                // Handle the exception (e.g., log the error) without interrupting the loop
                System.out.println("Failed to send email: " + e.getMessage());
            }
        }
    }

    @FXML
    private void goRecIn(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReclamationInactive.fxml"));
            Parent root = loader.load();
            lbCon.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
