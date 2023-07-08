package edu.connection.AdminAddTest;

import edu.connection.entities.Test;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class afficheDetailsController implements Initializable {

    @FXML
    private TableView<?> listeQuestionTableview;
    @FXML
    private TableColumn<?, ?> titreTableColumn;
    @FXML
    private Button btnClose;
    private Label questionLabel;

    private int testId; // Store the test ID
    private int IdTest;

    /**
     *
     * @param IdTest
     */
    public void setIdTest(int IdTest) {
        this.IdTest = IdTest;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Retrieve the question from the database based on the test ID
        String question = null;
        try {
            question = retrieveQuestionFromDatabase(testId);
        } catch (SQLException ex) {
            Logger.getLogger(afficheDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Display the question in the label
        questionLabel.setText(question);
    }

    private String retrieveQuestionFromDatabase(int testId) throws SQLException {
        // Perform the necessary SQL query to retrieve the question based on the test ID
        String question = "";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hirepro", "root", "")) {
            String query = "SELECT question FROM question WHERE idTest = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, testId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        question = resultSet.getString("question");
                    }
                }
            }
        }

        return question;
    }

    @FXML
    private void handleTitreTableColumnEdit(TableColumn.CellEditEvent event) {
        // Method implementation
    }

    @FXML
    private void handleCloseQuestion(ActionEvent event) {
        // Close the window
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    void initData(Test selectedTest) {
        // Set the test ID for the current instance
        setIdTest(selectedTest.getId());
    }

    
}
