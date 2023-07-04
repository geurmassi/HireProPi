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
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ForgetPasswordFXMLController implements Initializable {

    @FXML
    private PasswordField newPasswordTextField;

    private String userEmail;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setUserEmail(String email) {
        userEmail = email;
    }

    @FXML
    private void changePassword(ActionEvent event) {
        String newPassword = newPasswordTextField.getText();
        updatePasswordInDatabase(newPassword);
        showAlert("Success", "Password changed successfully");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginFXML.fxml"));
            Parent loginRoot = loader.load();

            Scene loginScene = new Scene(loginRoot);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(loginScene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(ForgetPasswordFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   private void updatePasswordInDatabase(String newPassword) {
    // Mettez en œuvre la logique pour mettre à jour le mot de passe dans la base de données
    // en utilisant l'adresse e-mail de l'utilisateur (userEmail) et le mot de passe saisi (newPassword)
    // Assurez-vous de prendre les mesures nécessaires pour sécuriser le stockage du mot de passe,
    // comme le hachage et le salage.

    // Exemple de code pour mettre à jour le mot de passe dans la base de données
    UserCRUD userService = new UserCRUD();
    userService.updateUserPassword(userEmail, newPassword);
}

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
