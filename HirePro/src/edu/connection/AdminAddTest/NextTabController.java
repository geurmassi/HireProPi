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

/**
 *
 * @author Dell
 */
public class NextTabController {
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
    private Label idTest;
    @FXML
    private Button Save;
    @FXML
    private Button Corriger;

    @FXML
    private void initialize() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hirepro", "root", "")) {

            String query = "SELECT titre FROM Test";
            try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

                List<String> testTitles = new ArrayList<>();
                while (resultSet.next()) {
                    String titre = resultSet.getString("titre");
                    testTitles.add(titre);
                }

                cbxTitre.setItems(FXCollections.observableArrayList(testTitles));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            e.printStackTrace();
        }
    }

    /*@FXML
    private void goNext() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/connection/AdminAddTest/NextTab.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
    @FXML
    private void ajouterClicked(ActionEvent event) {
        String testTitle = cbxTitre.getValue();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hirepro", "root", "")) {
            String query = "INSERT INTO autre_table (question, reponse_1, reponse_2, reponse_3, reponse_4, titre) " +
                    "SELECT question, reponse_1, reponse_2, reponse_3, reponse_4, titre " +
                    "FROM test T " +
                    "INNER JOIN question Q ON Q.idTest = T.idTest " +
                    "WHERE T.titre = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, testTitle);
                statement.executeUpdate();
            }

            System.out.println("Data inserted into autre_table!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Clear the text fields
        txtquestion.clear();
        txtrep1.clear();
        txtrep2.clear();
        txtrep3.clear();
        txtrep4.clear();

        // Set the focus on the first text field
        txtquestion.requestFocus();
    }

    @FXML
    private void corrigerClicked(ActionEvent event) {
        txtquestion.setText("");
        txtrep1.setText("");
        txtrep2.setText("");
        txtrep3.setText("");
        txtrep4.setText("");

        txtquestion.requestFocus();
    }
}
