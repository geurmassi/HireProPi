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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hadil ibenhajfraj
 */
public class LoginFXMLController implements Initializable {

    @FXML
    private AnchorPane LoginForm;
    @FXML
    private TextField login_email;
    @FXML
    private PasswordField login_password;
    @FXML
    private CheckBox login_showPassword;
    @FXML
    private Button login_btn;
    @FXML
    private Button login_createdAccount;
    @FXML
    private Hyperlink login_forgetPassword;
    private Connection connect;
    private PreparedStatement prepar;
    private ResultSet result;
    private Statement statement;
    private int loginAttempts = 0;
    @FXML
    private TextField login_selectPassword;
    /**
     * Initializes the controller class.
     */
    
    @FXML
public void login(ActionEvent event) {
        AlertMessage alert = new AlertMessage();
        if (login_email.getText().isEmpty() || login_password.getText().isEmpty()) {
            alert.errorMessage("Incorrect UserName/Password");
        } else {
            String email = login_email.getText();
            String password = login_password.getText();

            UserCRUD userService = new UserCRUD();
            boolean credentialsValid = userService.checkCredentials(email, password);
 
            if (credentialsValid) {
                alert.SuccessMessage("Successfully logged in!!");
            } else {
                loginAttempts++;
                if (loginAttempts >= 3) {
                    // Bloquer l'utilisateur
                   userService.blockUser(email);
                    alert.errorMessage("Incorrect UserName/Password. Your account has been blocked.");
                } else {
                    alert.errorMessage("Incorrect UserName/Password");
                }
            }
        }
        // Le reste de votre code...
        

        // Déterminer le rôle de l'utilisateur en fonction de loginEmail
          // Supposons que vous ayez une méthode pour déterminer si l'utilisateur est un recruteur
          UserCRUD userService = new UserCRUD();
          String loginEmail = login_email.getText();
boolean isRecruiter = userService.isRecruiter(loginEmail);
        try {
            if (event.getSource() == login_btn) {
                FXMLLoader loader;
                Parent profileRoot;
                if (isRecruiter) {
                    System.out.println("1- Success.....");
                    // Charger RecruteurFXML.fxml pour les recruteurs
                    loader = new FXMLLoader(getClass().getResource("RecruteurFXML.fxml"));
                    profileRoot = loader.load();
                    System.out.println("2- Success.....");
                    RecruteurFXMLController recruteurController = loader.getController();
                    recruteurController.setPersonalInformation(loginEmail);
                    System.out.println("3- Success.....");
                } else {
                    // Charger ProfilFXML.fxml pour les non-recruteurs
                    System.out.println("1- Fail......");
                    loader = new FXMLLoader(getClass().getResource("ProfilFXML.fxml"));
                    profileRoot = loader.load();
                    ProfileController profileController = loader.getController();
                    profileController.setPersonalInformation(loginEmail);
                    profileController.setPersonalScolaire(loginEmail);
                    profileController.setPersonalExperience(loginEmail);
                    System.out.println("3- Fail......");
                }

                Scene profileScene = new Scene(profileRoot);
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setScene(profileScene);
                primaryStage.show();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


  


    @FXML
   public void showPassword() {
    if (login_showPassword.isSelected()) {
        login_selectPassword.setText(login_password.getText());
       login_selectPassword.setVisible(true);
        login_password.setVisible(false);
    } else {
        login_password.setText(login_selectPassword.getText());
       login_selectPassword.setVisible(false);
        login_password.setVisible(true);
    }
}

             

                   

                    
   public void setLoginFormVisible(boolean visible) {
        LoginForm.setVisible(visible);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setLogin_email(TextField login_email) {
        this.login_email = login_email;
    }

    public void setLogin_password(PasswordField login_password) {
        this.login_password = login_password;
    }

    public void setLogin_forgetPassword(Hyperlink login_forgetPassword) {
        this.login_forgetPassword = login_forgetPassword;
    }

    public void setLogin_showPassword(CheckBox login_showPassword) {
        this.login_showPassword = login_showPassword;
    }

    public void setLogin_btn(Button login_btn) {
        this.login_btn = login_btn;
    }

    public void setLogin_createdAccount(Button login_createdAccount) {
        this.login_createdAccount = login_createdAccount;
    }

    @FXML
    private void registerBtn(ActionEvent event) {
         try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterFXML.fxml"));
           

            if (event.getSource() == login_createdAccount) {
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

    @FXML
    private void PasswordForget(ActionEvent event) {
          try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("EmailVerficiation.fxml"));
           

            if (event.getSource() == login_forgetPassword) {
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


  


    
    

    
    
}
