/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.gui;

import edu.connection.entities.UserConnect;
import edu.connection.services.UserCRUD;
import edu.connection.tests.SMSNotifier;
import edu.connection.utils.EmailNotificationService;
import edu.connection.utils.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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
    private String email;

    // Existing code
    private void handleLogin() {
        email = login_email.getText();
        // Handle login logic here
    }

    public String getEmail() {
        return email;
    }

    /**
     * Initializes the controller class.
     */
    /**
     * Initializes the controller class.
     *
     * @param event
     */
    @FXML
    public void login(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (login_email.getText().isEmpty() || login_password.getText().isEmpty()) {
            alert.setContentText("Incorrect UserName/Password");
            alert.showAndWait();
        } else {
            String email = login_email.getText();
            String password = login_password.getText();

            UserCRUD userService = new UserCRUD();
            boolean credentialsValid = userService.checkCredentials(email, password);

            if (credentialsValid) {
                boolean isRecruiter = userService.isRecruiter(email);
                boolean isBlocked = userService.isBlocked(email);
                if (isBlocked) {
                    // Afficher un message d'erreur et empêcher l'accès à l'interface
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Your account has been blocked. Please contact the administrator for assistance.");
                    alert.showAndWait();
                } else {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Successfully logged in!!");
                    alert.showAndWait();

                    // Le reste de votre code...
                    try {
                        FXMLLoader loader;
                        Parent profileRoot;
                        loader = new FXMLLoader(getClass().getResource("Main.fxml"));
                        profileRoot = loader.load();
                        MainController mainController = loader.getController();
                        int id = userService.getIdByEmail(email);
                        String role =userService.getRoleByEmail(email);
                        
                        System.out.println("*/*/*/*/*/*/*");
                        System.out.println(id);
                        System.out.println(role);
                        System.out.println(email);
                        UserConnect.roleUserConnect = email;
                         System.out.println(UserConnect.roleUserConnect);
                        System.out.println("*/*/*/*/*/*/");
                        mainController.setIdUser(String.valueOf(id));// Convert the int to a String
                         mainController.setRoleUser(role); 
                         UserConnect.idUserConnect = id;
                         UserConnect.roleUserConnect = role;
                         
                         

                        /* if (isRecruiter) {
                            // Charger RecruteurFXML.fxml pour les recruteurs

                            RecruteurFXMLController recruteurController = loader.getController();
                            recruteurController.setPersonalInformation(email);
                        } else {
                            // Charger ProfilFXML.fxml pour les non-recruteurs
                            loader = new FXMLLoader(getClass().getResource("ProfilFXML.fxml"));
                            profileRoot = loader.load();
                            ProfileController profileController = loader.getController();
                            profileController.setPersonalInformation(email);
                            profileController.setPersonalScolaire(email);
                            profileController.setPersonalExperience(email);
                        }*/
                        Scene profileScene = new Scene(profileRoot);
                        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        primaryStage.setScene(profileScene);
                        primaryStage.show();
                    } catch (IOException ex) {

                    }
                }
            } else {
                loginAttempts++;
                if (loginAttempts >= 3) {
                    // Bloquer l'utilisateur
                    userService.blockUser(email);
                    userService.updateUserInActiveStatus(email, 0);
                    String phoneNumber = userService.getPhoneNumberByEmail(email); // Récupérez le numéro de téléphone de l'utilisateur depuis la base de données

                    if (phoneNumber != null) {
                        String[] phoneNumbers = {phoneNumber};
                        SMSNotifier.sendBlockedNotification();
                    } else {
                        System.out.println("Failed to get phone number for user.");
                    }

                    boolean emailExists = userService.isEmailExists(login_email.getText()); // Vérifiez si l'adresse e-mail de l'utilisateur existe

                    if (emailExists) {
                        String recipientEmail = userService.getEmailByUsername(login_email.getText()); // Récupérez l'adresse e-mail de l'utilisateur depuis la base de données
                        String subject = "Account Blocked";
                        String content = "Your account has been blocked. Please contact the administrator for assistance.";

                        EmailNotificationService emailNotifier = new EmailNotificationService();
                        emailNotifier.sendEmailNotification(recipientEmail, subject, content);
                    } else {
                        System.out.println("Failed to get email for user.");
                    }

                    // Afficher une boîte de dialogue pour contacter l'administrateur
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Your account has been blocked. Please contact the administrator for assistance.");
                    alert.showAndWait();
                } else {
                    alert.setContentText("Incorrect UserName/Password");
                    alert.showAndWait();
                }
            }

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
