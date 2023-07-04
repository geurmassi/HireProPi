package edu.connection.gui;

import edu.connection.services.UserCRUD;
import edu.connection.utils.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.sql.ResultSet;
import edu.connection.utils.EmailSender;
import java.util.UUID;

public class EmailVerficiationController implements Initializable {

    @FXML
    private AnchorPane email_verification;
    @FXML
    private TextField Email;
    @FXML
    private Button forget_proceedBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

  
   @FXML
private void SendEmail(ActionEvent event) {
    AlertMessage alert = new AlertMessage();

    if (Email.getText().isEmpty()) {
        alert.errorMessage("Please fill all blank fields");
    } else {
        UserCRUD userService = new UserCRUD();
        boolean emailExists = userService.isEmailExists(Email.getText());
        if (emailExists) {
            // L'email existe dans la base de données
            alert.SuccessMessage("Email sent successfully");

            // Envoyer l'email de récupération du mot de passe
            String recipient = Email.getText();
            String subject = "Password Recovery";
            String newPassword = generateRandomPassword(); // Génère un nouveau mot de passe aléatoire
            String message = "Voici votre nouveau mot de passe : " + newPassword;
            EmailSender.sendEmail(recipient, subject, message, newPassword);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ForgetPasswordFXML.fxml"));
                Parent loginroot = loader.load();
                ForgetPasswordFXMLController forgetPasswordController = loader.getController();
                forgetPasswordController.setUserEmail(Email.getText());

                Scene email_verification = new Scene(loginroot);
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setScene(email_verification);
                primaryStage.show();
            } catch (IOException ex) {
                Logger.getLogger(RegisterFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alert.errorMessage("Email does not exist");
        }
    }
}



    private String generateRandomPassword() {
        String password = UUID.randomUUID().toString().substring(0, 8); // Génère une chaîne aléatoire de 8 caractères
        return password;
    }
}
