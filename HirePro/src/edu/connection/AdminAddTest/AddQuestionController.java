package edu.connection.AdminAddTest;


import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class AddQuestionController {
    @FXML
    private Button btnBack;
    @FXML
    private Label question;
    @FXML
    private Label reponse_1;
    @FXML
    private Label reponse_2;
    @FXML
    private Label reponse_3;
    @FXML
    private Label reponse_4;
    @FXML
    private TextField txtquestion;
    @FXML
    private TextField txtrep1;
    @FXML
    private TextField txtrep2;
    @FXML
    private TextField txtrep3;
    @FXML
    private ComboBox<String> cbxTitre;
    @FXML
    private TextField txtrep4;
    @FXML
    private Button Next;
    @FXML
    private Button Save;
    
    
    
    @FXML
    private Button Corriger;
    
    @FXML
    private TextField txtrepvrai;
    @FXML
    private Label TitreTest;
   

    private Connection connection;
    @FXML
    private Label reponse_41;
    
    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    public void initialize() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hirepro", "root", "");
            List<String> testTitles = getTestTitles();
            cbxTitre.setItems(FXCollections.observableArrayList(testTitles));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", null,
                    "An error occurred while connecting to the database: " + e.getMessage());
        }
    }

    
    @FXML
private void corrigerClicked(ActionEvent event) {
    clearFields();

    // Show a success message
    showAlert(Alert.AlertType.INFORMATION, "Success", null, "All submitted information has been cleared.");
}

    
    private List<String> getTestTitles() {
        List<String> titles = new ArrayList<>();
        try {
            // Fetch the test titles from the database
            String query = "SELECT titre FROM test";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String title = resultSet.getString("titre");
                    titles.add(title);
                }

            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", null,
                    "An error occurred while fetching test titles: " + e.getMessage());
        }

        return titles;
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/connection/AdminAddTest/Test.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
        }
    }

    @FXML
    private void goNext() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/connection/AdminAddTest/NextTab.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
        }
    }

    // Method to handle the "ajouter" button click event
  @FXML
private void ajouterClicked(ActionEvent event) {
    String question = txtquestion.getText().trim();
    String reponse1 = txtrep1.getText().trim();
    String reponse2 = txtrep2.getText().trim();
    String reponse3 = txtrep3.getText().trim();
    String reponse4 = txtrep4.getText().trim();
    String titre = cbxTitre.getValue();

    // Validate input
    if (!isString(question) || !isString(reponse1) || !isString(reponse2)
            || !isString(reponse3) || !isString(reponse4) || titre == null) {
        showAlert(Alert.AlertType.ERROR, "Error", null, "Please enter valid string values for all fields.");
        return;
    }

    try {
        int repVrai = Integer.parseInt(txtrepvrai.getText());

        // Validate input
        if (question.isEmpty() || reponse1.isEmpty() || reponse2.isEmpty()
                || reponse3.isEmpty() || reponse4.isEmpty() || titre == null) {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Please fill in all fields.");
            return;
        }

        // Insert data into the question table
        String insertQuery = "INSERT INTO question (question, reponse_1, reponse_2, reponse_3, reponse_4, idTest, rep_vrai) " +
                "VALUES (?, ?, ?, ?, ?, (SELECT idTest FROM test WHERE titre = ?), ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, question);
            preparedStatement.setString(2, reponse1);
            preparedStatement.setString(3, reponse2);
            preparedStatement.setString(4, reponse3);
            preparedStatement.setString(5, reponse4);
            preparedStatement.setString(6, titre);
            preparedStatement.setInt(7, repVrai);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", null, "Question added successfully.");
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", null, "Failed to add question.");
            }
        }
    } catch (NumberFormatException e) {
        showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input",
                "Please enter a valid integer for the correct answer.");
    } catch (SQLException e) {
        showAlert(Alert.AlertType.ERROR, "Error", null,
                "An error occurred while adding the question: " + e.getMessage());
    }
}

private boolean isString(String value) {
    return value.matches("[a-zA-Z]+");
}

    // Helper method to show an alert dialog
    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    // Helper method to clear the input fields
    private void clearFields() {
        txtquestion.clear();
        txtrep1.clear();
        txtrep2.clear();
        txtrep3.clear();
        txtrep4.clear();
        cbxTitre.getSelectionModel().clearSelection();
        txtrepvrai.clear();
    }



   }