/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package edu.connection.gui;

import edu.connection.services.UserCRUD;
import edu.connection.utils.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import edu.connection.utils.Captcha;

/**
* FXML Controller class
*
* @author hadil ibenhajfraj
*/
public class RegisterFXMLController implements Initializable {

@FXML
private ComboBox combox;
private Label label;
@FXML
private AnchorPane SignUpForm;
@FXML
private TextField signupNom;
@FXML
private PasswordField SignUpPassword;
@FXML
private Button SignUpBtn;
@FXML
private Button signupLog;
@FXML
private TextField signupPrenom;
@FXML
private DatePicker signupDateN;
@FXML
private TextField SignUpTel;
@FXML
private TextField SignUpAdress;
@FXML
private TextField SignUpEmail;

@FXML
private PasswordField SignUpConfirmPassword;
private Connection connect;
private PreparedStatement prepar;
private ResultSet result;
private Statement statement;
private String captcha;
@FXML
private TextField captcha_filed;
    @FXML
    private NoCopyPasteTextField captchaFiled;



/**
 * Initializes the controller class.
 */
@FXML

public void register(ActionEvent event) throws IOException {

   
    try {
       // FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginFXML.fxml"));
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/connection/Quiz/QT.fxml"));//loadMain("/edu/connection/Quiz/QT.fxml");

        AlertMessage alert = new AlertMessage();
        boolean isValid = true; // Ajout d'un indicateur pour suivre la validité du formulaire

        if (signupNom.getText().isEmpty() || signupPrenom.getText().isEmpty() || SignUpAdress.getText().isEmpty()) {
            alert.errorMessage("Please fill in all required fields");
            isValid = false;
        } else if (!signupNom.getText().matches("[a-zA-Z]+")) {
            alert.errorMessage("Invalid value for Nom. Only alphabetic characters are allowed.");
            isValid = false;
        } else if (!signupPrenom.getText().matches("[a-zA-Z]+")) {
            alert.errorMessage("Invalid value for Prenom. Only alphabetic characters are allowed.");
            isValid = false;
        } else if (signupDateN.getValue() == null) {
            alert.errorMessage("Please select a valid Date de Naissance.");
            isValid = false;
        } else if (SignUpEmail.getText().isEmpty() || !SignUpEmail.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            alert.errorMessage("Invalid email format.");
            isValid = false;
        } else if (SignUpTel.getText().isEmpty() || !SignUpTel.getText().matches("\\d+")) {
            alert.errorMessage("Invalid Tel. Only numeric digits are allowed.");
            isValid = false;
        } else if (SignUpAdress.getText().isEmpty()) {
            alert.errorMessage("Please fill in the Adress field.");
            isValid = false;
        } else if (SignUpPassword.getText().isEmpty() || SignUpConfirmPassword.getText().isEmpty()) {
            alert.errorMessage("Please fill in both Password and Confirm Password fields.");
            isValid = false;
        } else if (!SignUpPassword.getText().equals(SignUpConfirmPassword.getText())) {
            alert.errorMessage("Passwords do not match.");
            isValid = false;
        } else if (SignUpPassword.getText().length() < 8) {
            alert.errorMessage("Invalid Password. Password must be at least 8 characters long.");
            isValid = false;
        }

        String enteredCaptcha = captcha_filed.getText();

        if (!enteredCaptcha.equals(captcha)) {
            alert.errorMessage("Invalid captcha. Please try again.");
            isValid = false;
        }

        if (isValid) { // Vérifier la validité avant de passer à la page d'authentification
            UserCRUD userService = new UserCRUD();
            if (userService.checkUsernameExists(SignUpEmail.getText())) {
                alert.errorMessage(SignUpEmail.getText() + " is already taken");
            } else {
                userService.insertUser(
                        signupNom.getText(),
                        signupPrenom.getText(),
                        signupDateN.getValue(),
                        SignUpPassword.getText(),
                        SignUpEmail.getText(),
                        SignUpTel.getText(),
                        SignUpAdress.getText(),
                        (String) combox.getSelectionModel().getSelectedItem()
                );
                alert.SuccessMessage("Registered Successfully");

                SignUpForm.setVisible(false);
                Parent loginroot = loader.load();

                Scene login = new Scene(loginroot);
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setScene(login);
                primaryStage.show();
                loadProfileScene();
            }
        }
        captcha_filed.clear();
    } catch (IOException ex) {
        Logger.getLogger(RegisterFXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
}





public void switchForm(ActionEvent event) {
    try {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginFXML.fxml"));

        if (event.getSource() == SignUpBtn) {
            // Cacher le formulaire SignUpForm
            SignUpForm.setVisible(false);

            Parent loginroot = loader.load();

            Scene login = new Scene(loginroot);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(login);
            primaryStage.show();

        }
    } catch (IOException ex) {
        Logger.getLogger(RegisterFXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

@Override
public void initialize(URL url, ResourceBundle rb) {
    ObservableList<String> list = FXCollections.observableArrayList("Recruteur", "Candidat");
    combox.setItems(list);
    
generateNewCaptcha();

}

private void select(ActionEvent event) {
    String s = combox.getSelectionModel().getSelectedItem().toString();
    label.setText(s);
}

@FXML
private void loginBtn(ActionEvent event) {
    try {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginFXML.fxml"));

        if (event.getSource() == signupLog) {
            // Cacher le formulaire SignUpForm

            Parent loginroot = loader.load();

            Scene login = new Scene(loginroot);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(login);
            primaryStage.show();

        }
    } catch (IOException ex) {
        Logger.getLogger(RegisterFXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

private void generateNewCaptcha() {
    // Generate a new captcha
    captcha = Captcha.generateCaptcha(6);

    // Update the captcha label in the UI
    captchaFiled.setText(captcha);
}
private void loadProfileScene() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileFXML.fxml"));
Parent profileRoot = loader.load();
ProfileController profileController = loader.getController();


    // Pass the values to the ProfileController
    profileController.setPersonalInformation(
        signupNom.getText(),
        signupPrenom.getText(),
        SignUpAdress.getText(),
        SignUpTel.getText(),
        SignUpEmail.getText()
    );

    // Show the Profile scene
    Scene profileScene = new Scene(profileRoot);
    Stage primaryStage = (Stage) SignUpBtn.getScene().getWindow();
    primaryStage.setScene(profileScene);
    primaryStage.show();
}

}
