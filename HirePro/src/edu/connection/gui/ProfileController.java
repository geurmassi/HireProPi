package edu.connection.gui;

import edu.connection.entities.Experience;
import edu.connection.entities.Formation;
import edu.connection.entities.User;
import edu.connection.entities.UserConnect;
import edu.connection.services.UserCRUD;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ProfileController implements Initializable {

    private WebView mapWebView;
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
    private TextField diplomaTextField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField universityTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea tasksTextArea;
    @FXML
    private TextField companyTextField;
    @FXML
    private DatePicker starteDatePicker;
    @FXML
    private DatePicker endeDatePicker;
    @FXML
    private Button logout_btn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String role = UserConnect.roleUserConnect;
        String mail = UserConnect.email;
        if (role == "candidat") {

            setPersonalInformation(mail);
            setPersonalScolaire(mail);
            setPersonalExperience(mail);
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

    private void showMap(MouseEvent event) {
        String address = addressTextField.getText();
        String url = "https://www.google.com/maps/place/" + address;

        WebEngine webEngine = mapWebView.getEngine();
        webEngine.load(url);
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

    public void setPersonalScolaire(String email) {
        // Récupérer les informations scolaires de l'utilisateur à partir de l'e-mail
        UserCRUD userService = new UserCRUD();
        User user = userService.getUserByUniversity(email);

        if (user != null) {
            Formation formation = user.getFormation();

            // Afficher les informations scolaires dans les champs appropriés
            diplomaTextField.setText(formation.getDiplome());
            starteDatePicker.setValue(formation.getDateDebutFormation());
            endeDatePicker.setValue(formation.getDateFin());

            // Attribuer les valeurs converties aux champs texte
            universityTextField.setText(formation.getUniversite().getLibelle());
        } else {
            // Aucun utilisateur trouvé avec l'e-mail spécifié
            System.out.println("Aucune information scolaire trouvée pour l'utilisateur avec l'e-mail : " + email);
        }
    }

    public void setPersonalExperience(String email) {
        UserCRUD userService = new UserCRUD();
        User user = userService.getUserByExperience(email);

        if (user != null) {
            Experience experience = user.getExperience();

            titleTextField.setText(experience.getTitreExp());
            tasksTextArea.setText(experience.getDetails());
            companyTextField.setText(experience.getSociete().getNom());
            startDatePicker.setValue(experience.getDateDebut());
            endDatePicker.setValue(experience.getDateFin());
        } else {
            System.out.println("Aucune information d'expérience trouvée pour l'utilisateur avec l'e-mail : " + email);
        }
    }

    @FXML
    private void saveAcademicExperience(ActionEvent event) {
        String diploma = diplomaTextField.getText();
        String startDate = startDatePicker.getValue().toString();
        String endDate = endDatePicker.getValue().toString();
        String university = universityTextField.getText();
        String title = titleTextField.getText();

    }

    @FXML
    private void saveProfessionalExperience(ActionEvent event) {
        String tasks = tasksTextArea.getText();
        String company = companyTextField.getText();

        // Save professional experience to the database or perform other processing
        System.out.println("Professional experience saved:");
        System.out.println("Tasks: " + tasks);
        System.out.println("Company: " + company);
    }

    public void setPersonalInformation(String firstName, String lastName, String address, String phone, String email) {
        firstNameTextField.setText(firstName);
        lastNameTextField.setText(lastName);
        addressTextField.setText(address);
        phoneTextField.setText(phone);
        emailTextField.setText(email);
    }

    @FXML
    private void logout(ActionEvent event) {
        UserCRUD userService = new UserCRUD();
        try {
            String email = emailTextField.getText();

            // Check if logout button was clicked
            if (event.getSource() == logout_btn) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginFXML.fxml"));
                Parent profileRoot = loader.load();
                LoginFXMLController LoginFXMLController = loader.getController();

                Scene profileScene = new Scene(profileRoot);
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setScene(profileScene);
                primaryStage.show();
            }

            // Update user status to 0
            userService.updateUserInActiveStatus(email, 0);

            // Set handler for interface closure
            Stage primaryStage = (Stage) logout_btn.getScene().getWindow();

            // Set the action for the close request of the primary stage
            primaryStage.setOnCloseRequest(e -> {
                // Perform logout operations, e.g., update the user's status
                userService.updateUserInActiveStatus(email, 0);
                Platform.exit();
            });

        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

// Add the following code outside the logout() method
    @FXML
    private void savePersonalInformation(ActionEvent event) {
    }

}
