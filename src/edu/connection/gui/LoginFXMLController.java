package edu.connection.gui;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

    public void login() {
        AlertMessage alert = new AlertMessage();
        if (login_email.getText().isEmpty() || login_password.getText().isEmpty()) {
            alert.errorMessage("Incorrect UserName/Password");
        } else {
            String checkUsername = "SELECT email,password , actif FROM user WHERE email=? and password=?";
            try {
                prepar = MyConnection.getInstance().getCnx().prepareStatement(checkUsername);
                prepar.setString(1, login_email.getText());
                prepar.setString(2, login_password.getText());

                result = prepar.executeQuery();
                if (result.next()) {
                    boolean actif = result.getBoolean("actif");
                    if (actif) {
                        alert.SuccessMessage("Successfully logged in!!");
                    } else {
                        // Update the user's status to active
                        updateActifStatus(login_email.getText(), true);
                        alert.SuccessMessage("Successfully logged in!! Your account is now active.");
                    }
                } else {
                    loginAttempts++;
                    if (loginAttempts >= 3) {
                        // Bloquer l'utilisateur
                        blockUser(login_email.getText());
                        alert.errorMessage("Incorrect UserName/Password. Your account has been blocked.");
                    } else {
                        alert.errorMessage("Incorrect UserName/Password");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (result != null) {
                        result.close();
                    }
                    if (prepar != null) {
                        prepar.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void blockUser(String email) {
        try {
            String req = "UPDATE user SET blocked=? WHERE email=?";
            PreparedStatement updateStmt = MyConnection.getInstance().getCnx().prepareStatement(req);
            updateStmt.setBoolean(1, true);
            updateStmt.setString(2, email);
            updateStmt.executeUpdate();
            System.out.println("User blocked!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void updateActifStatus(String email, boolean actif) {
        try {
            String req = "UPDATE user SET actif=? WHERE email=?";
            PreparedStatement updateStmt = MyConnection.getInstance().getCnx().prepareStatement(req);
            updateStmt.setBoolean(1, actif);
            updateStmt.setString(2, email);
            updateStmt.executeUpdate();
            System.out.println("User updated!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
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

  


   /* @FXML
    
    private void loginBtn(ActionEvent event) {
       /* try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfilFXML.fxml"));
            Parent profileRoot = loader.load();
            ProfileController profileController = loader.getController();

            if (event.getSource() == login_btn) {
                // Get the logged-in user's email
                String loginEmail = login_email.getText();

                // Set the personal information in the ProfileController
                profileController.setPersonalInformation(loginEmail);
                profileController.setPersonalScolaire(loginEmail);
                profileController.setPersonalExperience(loginEmail);
                Scene profileScene = new Scene(profileRoot);
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setScene(profileScene);
                primaryStage.show();
            }
        } catch (IOException ex) {
           
        }
    } */
       /* try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RecruteurFXML.fxml"));
            Parent profileRoot = loader.load();
           RecruteurFXMLController RecruteurFXMLController = loader.getController();

            if (event.getSource() == login_btn) {
                // Get the logged-in user's email
                String loginEmail = login_email.getText();

                // Set the personal information in the ProfileController
               RecruteurFXMLController.setPersonalInformation(loginEmail);
                //profileController.setPersonalScolaire(loginEmail);
                //profileController.setPersonalExperience(loginEmail);
                Scene profileScene = new Scene(profileRoot);
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setScene(profileScene);
                primaryStage.show();
            }
        } catch (IOException ex) {
           
        }
    }
*/
    @FXML
private void loginBtn(ActionEvent event) {
    String loginEmail = login_email.getText();

    // Determine the user's role based on loginEmail
    boolean isRecruiter = isRecruiter(loginEmail); // Assuming you have a method to determine if the user is a recruiter

    try {
        FXMLLoader loader;
        Parent profileRoot;
        
        if (isRecruiter) {
            // Load RecruteurFXML.fxml for recruiters
            loader = new FXMLLoader(getClass().getResource("RecruteurFXML.fxml"));
            profileRoot = loader.load();
            RecruteurFXMLController recruteurController = loader.getController();
            recruteurController.setPersonalInformation(loginEmail);
        } else {
            // Load ProfilFXML.fxml for non-recruiters
            loader = new FXMLLoader(getClass().getResource("ProfilFXML.fxml"));
            profileRoot = loader.load();
            ProfileController profileController = loader.getController();
            profileController.setPersonalInformation(loginEmail);
            profileController.setPersonalScolaire(loginEmail);
            profileController.setPersonalExperience(loginEmail);
        }

        Scene profileScene = new Scene(profileRoot);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(profileScene);
        primaryStage.show();
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}

    private boolean isRecruiter(String email) {
    
    
    try {
       
         String checkUsername = "SELECT role FROM user WHERE email = ?";
         prepar = MyConnection.getInstance().getCnx().prepareStatement(checkUsername);
        prepar.setString(1, email);
        
        // Execute the query
        ResultSet resultSet = prepar.executeQuery();
        
        // Check if the user exists and has the role "recruteur"
        if (resultSet.next()) {
            String role = resultSet.getString("role");
            return role.equalsIgnoreCase("recruteur");
        } else {
            // User does not exist or has no role assigned
            return false;
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        // Handle the exception appropriately
        return false;
    } finally {
        // Close the database connection
        try {
            prepar.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle the exception appropriately
        }
    }
}




}
