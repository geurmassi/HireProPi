/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;

import edu.HirePro.api.EmailSender;
import edu.HirePro.entities.Condidature;
import edu.HirePro.services.CondidatureCRUD;
import edu.HirePro.services.candidatureMETIEER;
import java.io.File;
import java.text.SimpleDateFormat;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class AjouterCandidatureController implements Initializable {

    @FXML
    private Label lbcv;
    @FXML
    private Label lblm;
    @FXML
    private TextField TfUrl;
    String cv;
    String lm;
    @FXML
    private RadioButton rbFree;
    @FXML
    private Label lbiduser;
    @FXML
    private Label lbportfilio;
    @FXML
    private Label lbidoffre;
    @FXML
    private Label etoile;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        rbFree.setOnAction(event -> {
            if (rbFree.isSelected()) {
                lbportfilio.setVisible(true);
                TfUrl.setVisible(true);
                etoile.setVisible(true);
            } else {
                lbportfilio.setVisible(false);
                TfUrl.setVisible(false);
                etoile.setVisible(false);
            }
        });
    }

    public void setLbiduser(String id) {
        lbiduser.setText(id);
    }

    public void setLbIdOffre(String id) {
        lbidoffre.setText(id);
    }

    @FXML
    private void CvAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File selectFile = fileChooser.showOpenDialog(null);
        if (selectFile != null) {
            lbcv.setText(selectFile.getAbsolutePath());

            // Extract the filename from the selected file
            String filename = selectFile.getName();
            cv = filename; // Save only the file name instead of the absolute path
        } else {
            Platform.runLater(() -> {
                // Handle the case where no file is selected
                // For example, you can show an alert or display an error message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No File Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select a PDF file.");
                alert.showAndWait();
            });
        }
    }

    @FXML
    private void LMAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File selectFile = fileChooser.showOpenDialog(null);
        if (selectFile != null) {
            lblm.setText(selectFile.getAbsolutePath());
            String filename = selectFile.getName();
            lm = filename;
        } else {
            Platform.runLater(() -> {
                // Handle the case where no file is selected
                // For example, you can show an alert or display an error message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No File Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select a PDF file.");
                alert.showAndWait();
            });
        }
    }

    @FXML
    private void AjouterAction(ActionEvent event) {
        if (lbcv.getText().isEmpty() || lblm.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Vous n'avez pas ajouté votre CV ou votre lettre de motivation", ButtonType.OK);
            a.showAndWait();
        } else if (rbFree.isSelected()) {
            if (lbcv.getText().isEmpty() || lblm.getText().isEmpty() || TfUrl.getText().isEmpty()) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Vous n'avez pas ajouté votre CV ou votre lettre de motivation "
                        + "ou votre Portfilio Url ", ButtonType.OK);
                a.showAndWait();
            } else {
                CondidatureCRUD cc = new CondidatureCRUD();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                candidatureMETIEER mc = new candidatureMETIEER();
                String labelValue1 = lbidoffre.getText(); // Assuming lbiduser is a JavaFX Label
                String numericValue1 = labelValue1.replaceAll("[^0-9]", ""); // Extract numeric characters from the string
                int idOffre = Integer.parseInt(numericValue1);
                String labelValue = lbiduser.getText(); // Assuming lbiduser is a JavaFX Label
                String numericValue = labelValue.replaceAll("[^0-9]", ""); // Extract numeric characters from the string
                int idUser = Integer.parseInt(numericValue);
                Condidature c = new Condidature(mc.generateUniqueFileName(cv), mc.generateUniqueFileName(lm),
                        timestamp, idUser, idOffre, TfUrl.getText());
                cc.addEntityP(c);
                String sourceFilePathCV = lbcv.getText();
                File originaleFilecv = new File(sourceFilePathCV);
                File destinationDirCV = new File("C:/xampp/htdocs/cv");
                try {
                    // Create the destination directory if it doesn't exist
                    if (!destinationDirCV.exists()) {
                        destinationDirCV.mkdirs();
                    }

                    File destinationFilecv = new File(destinationDirCV, mc.generateUniqueFileName(cv));
                    FileUtils.copyFile(originaleFilecv, destinationFilecv);
                    System.out.println("File copied to CV directory successfully!");
                } catch (IOException ex) {
                    System.out.println("Failed to copy file to CV directory: " + ex.getMessage());
                }

                // Copy file to C:/xampp/htdocs/lettre_de_motivation
                String sourceFilePathLM = lblm.getText();
                File originaleFilelm = new File(sourceFilePathLM);
                File destinationDirLM = new File("C:/xampp/htdocs/lettre_de_motivation");
                try {
                    // Create the destination directory if it doesn't exist
                    if (!destinationDirLM.exists()) {
                        destinationDirLM.mkdirs();
                    }

                    File destinationFilelm = new File(destinationDirLM, mc.generateUniqueFileName(lm));
                    FileUtils.copyFile(originaleFilelm, destinationFilelm);
                    System.out.println("File copied to lettre_de_motivation directory successfully!");
                } catch (IOException ex) {
                    System.out.println("Failed to copy file to lettre_de_motivation directory: " + ex.getMessage());
                }
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Candidacature ajouter avec Succée", ButtonType.OK);
                a.showAndWait();
                lbcv.setText("");
                lblm.setText("");
                TfUrl.setText("");
                String nameCandidat = mc.rechercheNameUserbyId(idUser);
                String posteName = mc.recherchePosteNameByIdOffre(idOffre);
                String nameRecruteur = mc.retrieveUserNameByIdOffre(idOffre);
                String messageCandidat = "Cher(e)" + nameCandidat + ",\n"
                        + "\n"
                        + "Nous vous remercions d'avoir soumis votre candidature pour " + posteName + ". Nous tenons à vous informer que"
                        + " votre candidature a été envoyée avec succès.\n"
                        + "\n"
                        + "Notre équipe chargée des recrutements examinera attentivement votre profil et vos qualifications. "
                        + "Nous vous prions de bien vouloir patienter pendant que nous évaluons votre candidature. Nous nous "
                        + "engageons à vous fournir une réponse dans les plus brefs délais.\n"
                        + "\n"
                        + "Si votre candidature est retenue pour passer à l'étape suivante du processus de recrutement, nous "
                        + "prendrons contact avec vous pour convenir d'une date d'entretien ou pour vous demander des informations "
                        + "supplémentaires.\n"
                        + "\n"
                        + "Nous vous remercions encore une fois de l'intérêt que vous portez à notre entreprise. Votre candidature est"
                        + " importante pour nous, et nous nous efforçons de donner suite à chaque demande de manière équitable et"
                        + " professionnelle.\n"
                        + "\n"
                        + "Cordialement,\n"
                        + nameRecruteur;

                Date date = new Date();
                LocalTime currentTime = LocalTime.now();

                // Create a formatter for the desired time format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                SimpleDateFormat formatterdate = new SimpleDateFormat("dd/MM/yyyy");

                // Format the date using the formatter
                String formattedDate = formatterdate.format(date);

                // Format the current time using the formatter
                String formattedTime = currentTime.format(formatter);

                String messageRecruteur = "Cher " + nameRecruteur + ",\n"
                        + "\n"
                        + "Nous tenons à vous informer qu'une candidature pour le poste de " + posteName + " a été reçue avec succès "
                        + "le " + formattedDate + " à " + formattedTime + "."
                        + " Nous apprécions l'intérêt porté par le candidat à notre entreprise et à cette opportunité.\n"
                        + "\n"
                        + "Nous souhaitons vous informer que nous sommes conscients de l'importance d'une réponse rapide pour"
                        + " les candidats. Nous nous efforcerons donc d'examiner cette candidature dans les plus brefs délais."
                        + " Nous prendrons contact avec le candidat pour les étapes suivantes du processus de recrutement si son profil "
                        + "correspond à nos critères.\n"
                        + "\n"
                        + "Nous tenons à vous remercier pour votre rôle dans ce processus de recrutement et pour votre collaboration "
                        + "continue. Si vous avez des questions supplémentaires ou si vous souhaitez obtenir des informations"
                        + " complémentaires sur cette candidature, n'hésitez pas à nous contacter.\n"
                        + "\n"
                        + "Cordialement,\n"
                        + "Equipe Asministrative Hire Pro";

                EmailSender emailSender = new EmailSender();
                emailSender.sendEmails(mc.retrieveUserEmailByIdUser(idUser), "Votre candidature avec notre application : "
                        + "accusé de réception", messageCandidat, mc.retrieveUserEmailByIdOffre(idOffre), "Nouvelle candidature "
                        + "reçue pour votre offre d'emploi" + posteName, messageRecruteur);
            }

        } else {
            CondidatureCRUD cc = new CondidatureCRUD();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            candidatureMETIEER mc = new candidatureMETIEER();
            String labelValue1 = lbidoffre.getText(); // Assuming lbiduser is a JavaFX Label
            String numericValue1 = labelValue1.replaceAll("[^0-9]", ""); // Extract numeric characters from the string
            int idOffre = Integer.parseInt(numericValue1);
            String labelValue = lbiduser.getText(); // Assuming lbiduser is a JavaFX Label
            String numericValue = labelValue.replaceAll("[^0-9]", ""); // Extract numeric characters from the string
            int idUser = Integer.parseInt(numericValue);
            Condidature c = new Condidature(mc.generateUniqueFileName(cv), mc.generateUniqueFileName(lm),
                    timestamp, idUser, idOffre);
            cc.addEntity(c);
            String sourceFilePathCV = lbcv.getText();
            File originaleFilecv = new File(sourceFilePathCV);
            File destinationDirCV = new File("C:/xampp/htdocs/cv");
            try {
                // Create the destination directory if it doesn't exist
                if (!destinationDirCV.exists()) {
                    destinationDirCV.mkdirs();
                }

                File destinationFilecv = new File(destinationDirCV, mc.generateUniqueFileName(cv));
                FileUtils.copyFile(originaleFilecv, destinationFilecv);
                System.out.println("File copied to CV directory successfully!");
            } catch (IOException ex) {
                System.out.println("Failed to copy file to CV directory: " + ex.getMessage());
            }

            // Copy file to C:/xampp/htdocs/lettre_de_motivation
            String sourceFilePathLM = lblm.getText();
            File originaleFilelm = new File(sourceFilePathLM);
            File destinationDirLM = new File("C:/xampp/htdocs/lettre_de_motivation");
            try {
                // Create the destination directory if it doesn't exist
                if (!destinationDirLM.exists()) {
                    destinationDirLM.mkdirs();
                }

                File destinationFilelm = new File(destinationDirLM, mc.generateUniqueFileName(lm));
                FileUtils.copyFile(originaleFilelm, destinationFilelm);
                System.out.println("File copied to lettre_de_motivation directory successfully!");
            } catch (IOException ex) {
                System.out.println("Failed to copy file to lettre_de_motivation directory: " + ex.getMessage());
            }

            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Candidacature ajouter avec Succée", ButtonType.OK);
            a.showAndWait();
            lbcv.setText("");
            lblm.setText("");
            TfUrl.setText("");

            String nameCandidat = mc.rechercheNameUserbyId(idUser);
            String posteName = mc.recherchePosteNameByIdOffre(idOffre);
            String nameRecruteur = mc.retrieveUserNameByIdOffre(idOffre);
            String messageCandidat = "Cher(e) " + nameCandidat + ",\n"
                    + "\n"
                    + "Nous vous remercions d'avoir soumis votre candidature pour " + posteName + ". Nous tenons à vous informer que"
                    + " votre candidature a été envoyée avec succès.\n"
                    + "\n"
                    + "Notre équipe chargée des recrutements examinera attentivement votre profil et vos qualifications. "
                    + "Nous vous prions de bien vouloir patienter pendant que nous évaluons votre candidature. Nous nous "
                    + "engageons à vous fournir une réponse dans les plus brefs délais.\n"
                    + "\n"
                    + "Si votre candidature est retenue pour passer à l'étape suivante du processus de recrutement, nous "
                    + "prendrons contact avec vous pour convenir d'une date d'entretien ou pour vous demander des informations "
                    + "supplémentaires.\n"
                    + "\n"
                    + "Nous vous remercions encore une fois de l'intérêt que vous portez à notre entreprise. Votre candidature est"
                    + " importante pour nous, et nous nous efforçons de donner suite à chaque demande de manière équitable et"
                    + " professionnelle.\n"
                    + "\n"
                    + "Cordialement,\n"
                    + nameRecruteur;

            Date date = new Date();
            LocalTime currentTime = LocalTime.now();

            // Create a formatter for the desired time format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

            // Format the current time using the formatter
            String formattedTime = currentTime.format(formatter);
              SimpleDateFormat formatterdate = new SimpleDateFormat("dd/MM/yyyy");

                // Format the date using the formatter
                String formattedDate = formatterdate.format(date);

            String messageRecruteur = "Cher(e) " + nameRecruteur + ",\n"
                    + "\n"
                    + "Nous tenons à vous informer qu'une candidature pour le poste de " + posteName + " a été reçue avec succès "
                    + "le " + formattedDate + " à " + formattedTime + "."
                    + " Nous apprécions l'intérêt porté par le candidat à notre entreprise et à cette opportunité.\n"
                    + "\n"
                    + "Nous souhaitons vous informer que nous sommes conscients de l'importance d'une réponse rapide pour"
                    + " les candidats. Nous nous efforcerons donc d'examiner cette candidature dans les plus brefs délais."
                    + " Nous prendrons contact avec le candidat pour les étapes suivantes du processus de recrutement si son profil "
                    + "correspond à nos critères.\n"
                    + "\n"
                    + "Nous tenons à vous remercier pour votre rôle dans ce processus de recrutement et pour votre collaboration "
                    + "continue. Si vous avez des questions supplémentaires ou si vous souhaitez obtenir des informations"
                    + " complémentaires sur cette candidature, n'hésitez pas à nous contacter.\n"
                    + "\n"
                    + "Cordialement,\n"
                    + "Equipe Asministrative Hire Pro";

            EmailSender emailSender = new EmailSender();
            emailSender.sendEmails(mc.retrieveUserEmailByIdUser(idUser), "Votre candidature avec notre application : "
                    + "accusé de réception", messageCandidat, mc.retrieveUserEmailByIdOffre(idOffre), "Nouvelle candidature "
                    + "reçue pour votre offre d'emploi" + posteName, messageRecruteur);

        }

    }

}
