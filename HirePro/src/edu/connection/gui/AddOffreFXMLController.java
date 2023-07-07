/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.gui;

import edu.connection.entities.EmailSender;
import edu.connection.entities.Offre;
import edu.connection.entities.Poste;
import edu.connection.entities.ReceptionOfApplication;
import edu.connection.entities.TypeEmploi;
import static edu.connection.entities.TypeEmploi.Stage;
import edu.connection.entities.TypeLieuTravail;
import edu.connection.services.OffreEmploiCrud;
import edu.connection.utils.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AddOffreFXMLController implements Initializable {

    @FXML
    private TextField tfWorkplace;
    @FXML
    private TextField tfCompany;
    @FXML
    private TextArea tfDescription;
    @FXML
    private TextArea tfSkills;
    @FXML
    private ComboBox<TypeLieuTravail> CBTypeWorkplace;
    @FXML
    private ComboBox<TypeEmploi> CBTypeJob;
    @FXML
    private ComboBox<ReceptionOfApplication> CBReception;
    @FXML
    Button btnPost;
    @FXML
    private DatePicker DStartDate;
    @FXML
    private DatePicker DEndDate;
    @FXML
    private ComboBox<String> CBPoste;
    @FXML
    private Button btnManageoffers;

    private Stage primaryStage;
    // Define an instance variable to store the selected Offre
    private Offre selectedOffre;
    @FXML
    Button btnConfirmUpdate;
    @FXML
    private TextField tfIdOffre;

    // Setter method to set the selected Offre
    public void setOffre(Offre offre) {
        selectedOffre = offre;
        // Set the existing Offre values in the form fields
        CBPoste.setValue(offre.getJobHolder());
        tfWorkplace.setText(offre.getLieuT());
        tfDescription.setText(offre.getDescriptif());
        tfCompany.setText(offre.getCompany());
        CBTypeWorkplace.setValue(offre.getTypeLieuTravail());
        CBTypeJob.setValue(offre.getTypeEmploi());
        tfSkills.setText(offre.getSkills());
        CBReception.setValue(offre.getReceptionOfApplication());
        DStartDate.setValue(offre.getDateDebutOffre());
        DEndDate.setValue(offre.getDateFinOffre());
        tfIdOffre.setText(String.valueOf(offre.getIdOffre()));


    }

    public void updateOffre(Offre offre) {
        // Retrieve the updated values from the UI elements
        String updatedWorkplace = tfWorkplace.getText();
        String updatedCompany = tfCompany.getText();
        // Retrieve other updated values from other UI elements

        // Update the existing Offre object with the updated values
        offre.setLieuT(updatedWorkplace);
        offre.setCompany(updatedCompany);
        // Update other properties of the existing Offre object

        // You can perform any necessary validations or additional processing here
        // Save the updated Offre to the database or perform any necessary operations
        OffreEmploiCrud impl = new OffreEmploiCrud();
        impl.modifier(offre);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handlebtnManageoffres(ActionEvent event) {
        // Load the AfficherOffre.fxml file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherOffre.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Create a new stage for AfficherOffre and set the scene
            Stage afficherOffreStage = new Stage();
            afficherOffreStage.setScene(scene);
            afficherOffreStage.setTitle("Manage Job Offres");

            // Show the AfficherOffre stage
            afficherOffreStage.show();

            // Close the primary stage
            primaryStage.close();
        } catch (IOException ex) {
            System.out.println("Error loading FXML file");
            ex.printStackTrace();
        }
    }

    private void handlebtnPost() {
        // Load the AfficherOffre.fxml file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherOffre.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Create a new stage for AfficherOffre and set the scene
            Stage afficherOffreStage = new Stage();
            afficherOffreStage.setScene(scene);
            afficherOffreStage.setTitle("Afficher Offres");

            // Show the AfficherOffre stage
            afficherOffreStage.show();

            // Close the primary stage
            primaryStage.close();
        } catch (IOException ex) {
            System.out.println("Error loading FXML file");
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Get the enum values
        btnPost.setVisible(true);
        btnConfirmUpdate.setVisible(false);
        ObservableList<TypeEmploi> typeEmploiList = FXCollections.observableArrayList(TypeEmploi.values());
        CBTypeJob.setItems(typeEmploiList);
        ObservableList<TypeLieuTravail> typeLieuTravails = FXCollections.observableArrayList(TypeLieuTravail.values());
        CBTypeWorkplace.setItems(typeLieuTravails);
        ObservableList<ReceptionOfApplication> receptionOfApplications = FXCollections.observableArrayList(ReceptionOfApplication.values());
        CBReception.setItems(receptionOfApplications);

        ObservableList<String> postes = FXCollections.observableArrayList();

        try {
            Statement statement = MyConnection.getInstance().getCnx().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT poste FROM Poste");

            while (resultSet.next()) {
                String poste = resultSet.getString("poste");
                postes.add(poste);
            }

            CBPoste.setItems(postes);

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    
    @FXML
    private void AddOffreFXML(ActionEvent event) {
        String jobHolder = (String) CBPoste.getSelectionModel().getSelectedItem();
        String lieuT = tfWorkplace.getText();
        String descriptif = tfDescription.getText();
        String skills = tfSkills.getText();
        String company = tfCompany.getText();
        LocalDate dateDebutOffre = DStartDate.getValue();
        LocalDate dateFinOffre = DEndDate.getValue();
        TypeEmploi typeEmploi = (TypeEmploi) CBTypeJob.getSelectionModel().getSelectedItem();
        TypeLieuTravail typeLieuTravail = (TypeLieuTravail) CBTypeWorkplace.getSelectionModel().getSelectedItem();
        ReceptionOfApplication receptionOfApplication = (ReceptionOfApplication) CBReception.getSelectionModel().getSelectedItem();

        // Validate date fields
        if (dateDebutOffre == null || dateFinOffre == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Date");
            alert.setContentText("Please select a valid start and end date.");
            alert.showAndWait();
            return; // Exit the method if dates are null
        }

        // Check if end date is before the start date
        if (dateFinOffre.isBefore(dateDebutOffre)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Date");
            alert.setContentText("End date must be equal to or later than the start date.");
            alert.showAndWait();
            return; // Exit the method if end date is before start date
        }

        Offre O = new Offre(jobHolder, lieuT, descriptif, skills, company, dateDebutOffre, dateFinOffre, typeEmploi, typeLieuTravail, receptionOfApplication);
        OffreEmploiCrud Impl = new OffreEmploiCrud();
        Impl.addEntity(O);

        List<String> useremails = Impl.getAllUserEmails();
        EmailSender emailSender = new EmailSender();

        String subject = "New Job Offer";
        String message = "Dear Network,\n\nWe have a new job offer available. Please review the details below:\n\n"
               /* + "Job Holder: " + O.getJobHolder() + "\n"
                + "Description: " + O.getDescriptif() + "\n"
                + "Skills: " + O.getSkills() + "\n"
                + "Company: " + O.getCompany() + "\n"
                + "Start Date: " + O.getDateDebutOffre() + "\n"
                + "End Date: " + O.getDateFinOffre() + "\n"
                + "Type of Employment: " + O.getTypeEmploi() + "\n"
                + "Type of Workplace: " + O.getTypeLieuTravail() + "\n"
                + "Reception of Applications: " + O.getReceptionOfApplication() + "\n\n"*/
                + "Thank you.";

        for (String email : useremails) {
            emailSender.sendEmailToSAMU(email, subject, message, O);
        }
         System.out.println("All emails have been sent successfully");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Offre Added");
        alert.setContentText("The offre has been successfully added.");
        alert.showAndWait();

        // Clear input fields
        tfWorkplace.setText("");
        tfDescription.setText("");
        tfSkills.setText("");
        tfCompany.setText("");
        handlebtnPost();
    }


   @FXML
private void handleConfirmUpdateButton(ActionEvent event) throws IOException {
    String jobHolder = CBPoste.getValue();
    String lieuT = tfWorkplace.getText();
    String descriptif = tfDescription.getText();
    String skills = tfSkills.getText();
    String company = tfCompany.getText();
    LocalDate dateDebutOffre = DStartDate.getValue();
    LocalDate dateFinOffre = DEndDate.getValue();
    TypeEmploi typeEmploi = CBTypeJob.getValue();
    TypeLieuTravail typeLieuTravail = CBTypeWorkplace.getValue();
    ReceptionOfApplication receptionOfApplication = CBReception.getValue();

    String idOffreString = tfIdOffre.getText();
    if (idOffreString.isEmpty()) {
        // Handle the case when the text field is empty
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText("Please enter a valid ID.");
        alert.showAndWait();
        return; // Exit the method if the text field is empty
    }

    int idOffre;
    try {
        idOffre = Integer.parseInt(idOffreString);
    } catch (NumberFormatException e) {
        // Handle the case when the input cannot be parsed as an integer
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText("Please enter a valid ID.");
        alert.showAndWait();
        return; // Exit the method if the input cannot be parsed as an integer
    }

    // Validate date fields
    if (dateDebutOffre == null || dateFinOffre == null) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Date");
        alert.setContentText("Please select a valid start and end date.");
        alert.showAndWait();
        return; // Exit the method if dates are null
    }

    // Check if end date is before the start date
    if (dateFinOffre.isBefore(dateDebutOffre)) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Date");
        alert.setContentText("End date must be equal to or later than the start date.");
        alert.showAndWait();
        return; // Exit the method if end date is before start date
    }

    Offre O = new Offre(idOffre, jobHolder, lieuT, descriptif, skills, company, dateDebutOffre, dateFinOffre, typeEmploi, typeLieuTravail, receptionOfApplication);
    OffreEmploiCrud Impl = new OffreEmploiCrud();

    Impl.modifier(O);

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Success");
    alert.setHeaderText("Offre Update");
    alert.setContentText("The offre has been successfully Updated!");
    alert.showAndWait();

    // Clear input fields
    tfWorkplace.setText("");
    tfDescription.setText("");
    tfSkills.setText("");
    tfCompany.setText("");
    handlebtnPost();
}

}
