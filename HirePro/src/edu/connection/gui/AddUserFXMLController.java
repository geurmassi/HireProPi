package edu.connection.gui;
import edu.connection.entities.User;
import edu.connection.entities.Role;
import edu.connection.services.UserCRUD;
import edu.connection.gui.AlertMessage;
import java.io.IOException;

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
import javafx.scene.effect.Glow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class AddUserFXMLController implements Initializable {
   private User selectedUser;
    @FXML
    private AnchorPane SignUpForm;
    @FXML
    private TextField signupNom;
    @FXML
    private PasswordField SignUpPassword;
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
    @FXML
    private Button AddBtn;
    @FXML
    private Button upadateBtn;
    @FXML
    private ComboBox<String> combox;
    private TextField userIdField;
    private Role role;

   @Override
public void initialize(URL url, ResourceBundle rb) {
    //userTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectUser(newValue));
    ObservableList<String> list = FXCollections.observableArrayList("Recruteur", "Candidat");
    combox.setItems(list);
    combox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> select(newValue));
}
public void setUser(User user) {
        //selectedOffre = offre;
        // Set the existing Offre values in the form fields
        signupNom.setText(user.getNom());
        signupPrenom.setText(user.getPrenom());
        SignUpTel.setText(user.getTel());
        SignUpEmail.setText(user.getEmail());
        combox.setValue(user.getRole().toString());
        
        signupDateN.setValue(user.getDateNaissance());
        userIdField.setText(String.valueOf(user.getId()));


    }
private void selectUser(User user) {
    if (user != null) {
        userIdField.setText(String.valueOf(user.getId()));
        signupNom.setText(user.getNom());
        signupPrenom.setText(user.getPrenom());
        signupDateN.setValue(user.getDateNaissance());
        SignUpTel.setText(user.getTel());
        SignUpAdress.setText(user.getAdresse());
        SignUpEmail.setText(user.getEmail());
        // Set other fields as needed
    } else {
        clearFields();
    }
}

private void clearFields() {
    userIdField.clear();
    signupNom.clear();
    signupPrenom.clear();
    signupDateN.setValue(null);
    SignUpTel.clear();
    SignUpAdress.clear();
    SignUpEmail.clear();
    // Clear other fields as needed
}
    @FXML
    private void add(ActionEvent event) throws IOException {
        AlertMessage alert = new AlertMessage();
        
        boolean isValid = true;

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

        if (isValid) {
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
                        combox.getValue()
                );
                alert.SuccessMessage("Ajout Successfully");

                SignUpForm.setVisible(false);
            }
        }
          FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherUser.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
    }

    @FXML
    private void updateBtn(ActionEvent event) throws IOException {
       
     // Check if all fields are filled
        if (signupNom.getText().isEmpty() || signupPrenom.getText().isEmpty()
                || SignUpTel.getText().isEmpty() || SignUpAdress.getText().isEmpty()
                || SignUpEmail.getText().isEmpty() || signupDateN.getValue() == null
                || combox.getValue() == null) {
            // Display an error message if any field is empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        // Update the selected User with the new values
        selectedUser.setNom(signupNom.getText());
        selectedUser.setPrenom(signupPrenom.getText());
        selectedUser.setTel(SignUpTel.getText());
        selectedUser.setAdresse(SignUpAdress.getText());
        selectedUser.setEmail(SignUpEmail.getText());
        selectedUser.setDateNaissance(signupDateN.getValue());
        String selectedRole = combox.getValue();
Role role;

if (selectedRole.equals("Recruteur")) {
    role = Role.recruteur;
} else if (selectedRole.equals("Candidat")) {
    role = Role.candidat;
} else {
    // Handle the case when the selected role is unknown or not supported
    return;
}
          selectedUser.setRole(role);


        // Save the updated User in the database
        UserCRUD userDAO = new UserCRUD();
        userDAO.modifier(selectedUser);
  FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherUser.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
        // Close the window
        //Stage stage = (Stage) btnConfirmUpdate.getScene().getWindow();
        //stage.close();
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;

        // Fill the form fields with the selected User's information
        signupNom.setText(selectedUser.getNom());
        signupPrenom.setText(selectedUser.getPrenom());
        SignUpTel.setText(selectedUser.getTel());
        SignUpAdress.setText(selectedUser.getAdresse());
        SignUpEmail.setText(selectedUser.getEmail());
        signupDateN.setValue(selectedUser.getDateNaissance());
     Role selectedRole = selectedUser.getRole();
String role;

if (selectedRole == Role.recruteur) {
    role = "Recruteur";
} else if (selectedRole == Role.candidat) {
    role = "Candidat";
} else {
    // Handle the case when the selected role is unknown or not supported
    return;
}

combox.setValue(role);


      
    }
    

    
 public void setRole(Role role) {
        this.role = role;
    }
    private void select(String role) {
        System.out.println("Selected Role: " + role);
    }

    void setLabelIdUserValue(String labelIdUserValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
