package edu.connection.gui;

import edu.connection.entities.Experience;
import edu.connection.entities.Formation;
import edu.connection.entities.Skills;
import edu.connection.entities.Societe;
import edu.connection.entities.Universite;
import edu.connection.entities.User;
import edu.connection.utils.MyConnection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ProfileController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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

    public void setPersonalInformation(String loginEmail) {
        // Retrieve the user's information from the database based on the login email
        // Assuming you have a method to fetch the user's information based on their email
        User user = getUserByEmail(loginEmail);

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
        User user = getUserByUniversity(email);

        if (user != null) {
            Formation formation = user.getFormation();

            // Afficher les informations scolaires dans les champs appropriés
            diplomaTextField.setText(formation.getDiplome());
          startDatePicker.setValue(formation.getDateDebutFormation());
        endDatePicker.setValue(formation.getDateFin());

            // Attribuer les valeurs converties aux champs texte
            

            universityTextField.setText(formation.getUniversite().getLibelle());
        } else {
            // Aucun utilisateur trouvé avec l'e-mail spécifié
            System.out.println("Aucune information scolaire trouvée pour l'utilisateur avec l'e-mail : " + email);
        }
    }

   

 public void setPersonalExperience(String email) {
       User user = getUserByExperience(email);

        if (user != null) {
            Experience experience = user.getExperience();

            titleTextField.setText(experience.getTitreExp());
            tasksTextArea.setText(experience.getDetails());
            companyTextField.setText(experience.getSociete().getNom());
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
        // Perform logout operations, e.g., redirect to the login screen
    }

    private User getUserByEmail(String email) {
        try {
            String query = "SELECT * FROM user WHERE email = ?";
            PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setNom(resultSet.getString("nom"));
                user.setPrenom(resultSet.getString("prenom"));
                user.setAdresse(resultSet.getString("adresse"));
                user.setTel(resultSet.getString("tel"));
                user.setEmail(resultSet.getString("email"));
                return user;
            } else {
                System.out.println("No user found with the email: " + email);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user by email: " + e.getMessage());
        }

        return null; // No user found or an error occurred
    }

    private User getUserByUniversity(String email) {
        try {
            String query = "SELECT U.id, F.idF, T.idUniversite, T.libelle, F.diplome, F.dateDebutFormation, F.dateFin, U.email "
                    + "FROM user U "
                    + "INNER JOIN formation F ON F.idUser = U.id "
                    + "INNER JOIN universite T ON T.idUniversite = F.idUniversity "
                    + "WHERE U.email = ?";
            PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                Formation formation = new Formation();
                Universite universite = new Universite();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));

                formation.setIdF(resultSet.getInt("idF"));
                formation.setDiplome(resultSet.getString("diplome"));
                formation.setDateDebutFormation(resultSet.getDate("dateDebutFormation").toLocalDate());
                formation.setDateFin(resultSet.getDate("dateFin").toLocalDate());

                universite.setIdUniveriste(resultSet.getInt("idUniversite"));
                universite.setLibelle(resultSet.getString("libelle"));

                formation.setUniversite(universite);
                user.setFormation(formation);

                return user;
            } else {
                System.out.println("No user found with the email: " + email);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user by email: " + e.getMessage());
        }

        return null; // No user found or an error occurred
    }
 private User getUserByExperience(String email) {
        try {
            String query = "SELECT U.id, T.idEx, S.idS, T.titreExp, T.dateDebut, T.dateFin, T.details,c.nom, U.email "
                    + "FROM user U "
                    + "INNER JOIN skills S ON S.user = U.id "
                    + "INNER JOIN expriencepro T ON T.idSkills = S.idS "
                    + "INNER JOIN societe C ON C.idS = T.idSociete "
                    + "WHERE U.email = ?";
            PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                Skills skills = new Skills();
                Experience experience = new Experience();
                Societe societe = new Societe();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));

                skills.setIdS(resultSet.getInt("idS"));
                experience.setIdEx(resultSet.getInt("idEx"));
                societe.setIdS(resultSet.getInt("idS"));
                experience.setTitreExp(resultSet.getString("titreExp"));
                experience.setDateDebut(resultSet.getDate("dateDebut").toLocalDate());
                experience.setDateFin(resultSet.getDate("dateFin").toLocalDate());
                experience.setDetails(resultSet.getString("details"));
                societe.setNom(resultSet.getString("nom"));
               

  user.setSkills(skills);
            user.setExperience(experience);
            experience.setSociete(societe);
                return user;
            } else {
                System.out.println("No user found with the email: " + email);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user by email: " + e.getMessage());
        }

        return null; // No user found or an error occurred
    }
  
    @FXML
    private void savePersonalInformation(ActionEvent event) {
    }

}
