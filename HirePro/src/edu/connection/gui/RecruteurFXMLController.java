package edu.connection.gui;

import edu.connection.entities.User;
import edu.connection.services.UserCRUD;
import edu.connection.utils.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RecruteurFXMLController implements Initializable {

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TableView<Formation> formationTable;
    private TableColumn<Formation, String> diplomaColumn;
    private TableColumn<Formation, String> startDateColumn;
    private TableColumn<Formation, String> endDateColumn;
    private TableColumn<Formation, String> universityColumn;
    @FXML
    private TableView<Skill> skillsTable;
    private TableColumn<Skill, String> skillColumn;
    @FXML
    private TableView<Experience> experienceTable;
    private TableColumn<Experience, String> companyNameColumn;
    private TableColumn<Experience, String> companyAddressColumn;
    private TableColumn<Experience, String> descriptionColumn;
    private TableColumn<Experience, String> phoneColumn;
    @FXML
    private Button logout_btn;

    public void initialize() {
        // Initialize the columns for the Formation table
        diplomaColumn.setCellValueFactory(new PropertyValueFactory<>("diplome"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateDebutFormation"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        universityColumn.setCellValueFactory(cellData -> cellData.getValue().getUniversite().libelleProperty());

        // Initialize the columns for the Skills table
        skillColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));

        // Initialize the columns for the Experience table
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        companyAddressColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
    }

    @FXML
    private void savePersonalInformation() {
        // Handle the save action for personal information
        // Implement your logic here
    }

    @FXML
    private void saveAcademicExperience() {
        // Handle the save action for academic experience
        // Implement your logic here
    }

    @FXML
    private void saveSkills() {
        // Handle the save action for skills
        // Implement your logic here
    }

    @FXML
    private void saveProfessionalExperience() {
        // Handle the save action for professional experience
        // Implement your logic here
    }

   
    @FXML
    private void logout(ActionEvent event) {
            UserCRUD userService = new UserCRUD();
        try {
            String email = emailTextField.getText();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginFXML.fxml"));
            Parent profileRoot = loader.load();
            LoginFXMLController LoginFXMLController = loader.getController();
            // Update the active status of the user to 0 (inactive) in the database
            if (event.getSource() == logout_btn) {
                  Scene profileScene = new Scene(profileRoot);
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setScene(profileScene);
                primaryStage.show();
               userService.updateUserInActiveStatus(email, 0);
            }
            // Perform logout operations, e.g., redirect to the login screen
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


     @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    // Define the classes for data models
    public static class Formation {

        private StringProperty diplome;
        private StringProperty dateDebutFormation;
        private StringProperty dateFin;
        private Universite universite;

        public Formation(String diplome, String dateDebutFormation, String dateFin, Universite universite) {
            this.diplome = new SimpleStringProperty(diplome);
            this.dateDebutFormation = new SimpleStringProperty(dateDebutFormation);
            this.dateFin = new SimpleStringProperty(dateFin);
            this.universite = universite;
        }

        public StringProperty diplomeProperty() {
            return diplome;
        }

        public StringProperty dateDebutFormationProperty() {
            return dateDebutFormation;
        }

        public StringProperty dateFinProperty() {
            return dateFin;
        }

        public Universite getUniversite() {
            return universite;
        }
    }

    public static class Skill {

        private StringProperty libelle;

        public Skill(String libelle) {
            this.libelle = new SimpleStringProperty(libelle);
        }

        public StringProperty libelleProperty() {
            return libelle;
        }
    }

    public static class Experience {

        private StringProperty nom;
        private StringProperty adresse;
        private StringProperty description;
        private StringProperty telephone;

        public Experience(String nom, String adresse, String description, String telephone) {
            this.nom = new SimpleStringProperty(nom);
            this.adresse = new SimpleStringProperty(adresse);
            this.description = new SimpleStringProperty(description);
            this.telephone = new SimpleStringProperty(telephone);
        }

        public StringProperty nomProperty() {
            return nom;
        }

        public StringProperty adresseProperty() {
            return adresse;
        }

        public StringProperty descriptionProperty() {
            return description;
        }

        public StringProperty telephoneProperty() {
            return telephone;
        }
    }

    public static class Universite {

        private StringProperty libelle;

        public Universite(String libelle) {
            this.libelle = new SimpleStringProperty(libelle);
        }

        public StringProperty libelleProperty() {
            return libelle;
        }
    }

    public void setPersonalInformation(String loginEmail) {
        // Retrieve the user's information from the database based on the login email
        // Assuming you have a method to fetch the user's information based on their email
            UserCRUD userService = new UserCRUD();
        User user = userService.getUserByEmail(loginEmail);

        if (user != null) {
            String firstName = user.getNom();
            String lastName = user.getPrenom();
            String address = user.getAdresse();
            String phone = user.getTel();
            String email = user.getEmail();

            // Set the retrieved information in the text fields
            firstNameTextField.setText(firstName);
            lastNameTextField.setText(lastName);
            addressTextField.setText(address);
            phoneTextField.setText(phone);
            emailTextField.setText(email);
        }
    }

    public void setNom(String nom) {
        this.firstNameTextField.setText(nom);
    }

    public void setAdresse(String adresse) {
        this.addressTextField.setText(adresse);
    }

    public void setEmail(String email) {
        this.emailTextField.setText(email);
    }

    public void setPrenom(String prenom) {
        this.lastNameTextField.setText(prenom);
    }

    public void setTel(String tel) {
        this.phoneTextField.setText(tel);
    }

    
}
