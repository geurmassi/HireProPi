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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * 
 */
public class ForgetPasswordFXMLController implements Initializable {

    @FXML
    private PasswordField forgetPassword;
    @FXML
    private Button forget_proceedBtn;
    @FXML
    private Button forgetBackBtn;
    private PasswordField confirmPassword;

    

    private Connection connect;
    private PreparedStatement prepar;
    private ResultSet result;
    private Statement statement;
    @FXML
    private AnchorPane ForgetPassword;
    @FXML
    private PasswordField ConfirmPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
    }

    private void backToLogin(ActionEvent event) {
       
    }

    @FXML
    private void changePassword(ActionEvent event) {
        AlertMessage alert = new AlertMessage();

        if (forgetPassword.getText().isEmpty() || confirmPassword.getText().isEmpty()) {
            alert.errorMessage("Please fill all fields");
        } else if (!forgetPassword.getText().equals(confirmPassword.getText())) {
            alert.errorMessage("Passwords do not match");
        } else if (forgetPassword.getText().length() < 8) {
            alert.errorMessage("Invalid Password, at least 8 characters needed");
        } else {
            String updateData = "UPDATE user SET password = ? WHERE email = ?";
            try {
                PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(updateData);
                pst.setString(1, forgetPassword.getText());
                pst.setString(2, confirmPassword.getText());
                pst.setString(3, getEmailFromAPI());

                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    alert.SuccessMessage("Password updated successfully");
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginFXML.fxml"));
                        Parent loginroot = loader.load();

                        Scene login = new Scene(loginroot);
                        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        primaryStage.setScene(login);
                        primaryStage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(RegisterFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    alert.errorMessage("Failed to update password");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ForgetPasswordFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void BackLog(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginFXML.fxml"));
            Parent loginroot = loader.load();

            Scene login = new Scene(loginroot);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(login);
            primaryStage.show();

        } catch (IOException ex) {
            Logger.getLogger(RegisterFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private String receivedPassword;

public void setReceivedPassword(String password) {
    this.receivedPassword = password;
}
  private String getEmailFromAPI() {
        String email = null;
        // Code pour appeler votre API et récupérer l'e-mail associé au nouveau mot de passe
        // Utilisez les fonctionnalités d'envoi d'e-mails de l'API spécifique que vous utilisez

        return email;
    }
}
