/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package edu.connection.gui;

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
private TextField captchaFiled;
@FXML
private TextField captcha_filed;

/**
 * Initializes the controller class.
 */
@FXML
public void register(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginFXML.fxml"));

        AlertMessage alert = new AlertMessage();
        if (signupNom.getText().isEmpty() || signupPrenom.getText().isEmpty() || SignUpAdress.getText().isEmpty()) {
            alert.errorMessage("Please fill in all required fields");
        } else if (!signupNom.getText().matches("[a-zA-Z]+")) {
            alert.errorMessage("Invalid value for Nom. Only alphabetic characters are allowed.");
        } else if (!signupPrenom.getText().matches("[a-zA-Z]+")) {
            alert.errorMessage("Invalid value for Prenom. Only alphabetic characters are allowed.");
        } else if (signupDateN.getValue() == null) {
            alert.errorMessage("Please select a valid Date de Naissance.");
        } else if (SignUpEmail.getText().isEmpty() || !SignUpEmail.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            alert.errorMessage("Invalid email format.");
        } else if (SignUpTel.getText().isEmpty() || !SignUpTel.getText().matches("\\d+")) {
            alert.errorMessage("Invalid Tel. Only numeric digits are allowed.");
        } else if (SignUpAdress.getText().isEmpty()) {
            alert.errorMessage("Please fill in the Adress field.");
        } else if (SignUpPassword.getText().isEmpty() || SignUpConfirmPassword.getText().isEmpty()) {
            alert.errorMessage("Please fill in both Password and Confirm Password fields.");
        } else if (!SignUpPassword.getText().equals(SignUpConfirmPassword.getText())) {
            alert.errorMessage("Passwords do not match.");
        } else if (SignUpPassword.getText().length() < 8) {
            alert.errorMessage("Invalid Password. Password must be at least 8 characters long.");
        }

        String enteredCaptcha = captcha_filed.getText();

        if (!enteredCaptcha.equals(captcha)) {
            alert.errorMessage("Invalid captcha. Please try again.");
        }else {
            String checkUsername = "SELECT * FROM user WHERE email='" + SignUpEmail.getText() + "'";
            try {
                Statement st = MyConnection.getInstance().getCnx().createStatement();
                ResultSet rs = st.executeQuery(checkUsername);

                if (rs.next()) {
                    alert.errorMessage(SignUpEmail.getText() + "is already taken");
                } else {
                    String requete = "INSERT INTO user(nom,prenom,dateNaissance,password,email,tel,adresse,role) VALUES " + "(?,?,?,?,?,?,?,?)";
                    PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
                    pst.setString(1, signupNom.getText());
                    pst.setString(2, signupPrenom.getText());
                    pst.setDate(3, Date.valueOf(signupDateN.getValue()));
                    pst.setString(4, SignUpPassword.getText());
                    pst.setString(5, SignUpEmail.getText());
                    pst.setString(6, SignUpTel.getText());
                    pst.setString(7, SignUpAdress.getText());
                    pst.setString(8, (String) combox.getSelectionModel().getSelectedItem());
                    pst.executeUpdate();
                    alert.SuccessMessage("Registred Successfully");

                    SignUpForm.setVisible(false);
                    Parent loginroot = loader.load();

                    Scene login = new Scene(loginroot);
                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    primaryStage.setScene(login);
                    primaryStage.show();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
                        captcha_filed.clear();

        }
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

}
