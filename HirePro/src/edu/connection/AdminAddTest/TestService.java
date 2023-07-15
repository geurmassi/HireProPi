/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.AdminAddTest;

import edu.connection.entities.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author Dell
 */
public class TestService {
    private Connection connection;

    public TestService() {
        // Establish the database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hirepro", "root", "");
        } catch (SQLException e) {
        }
    }

    public boolean addQuiz(Test test) {
        try {
            // Check if the quiz already exists
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM test WHERE titre = ?");
            statement.setString(1, test.getTitre());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Quiz already exists, return false
                statement.close();
                resultSet.close();
                return false;
            }

            // Add the quiz to the database
            statement = connection.prepareStatement("INSERT INTO test (titre) VALUES (?)");
            statement.setString(1, test.getTitre());
            statement.executeUpdate();

            // Close the resources
            statement.close();
            resultSet.close();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public int countTestsByTitre(String titre) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM test WHERE titre=?")) {
            preparedStatement.setString(1, titre);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors du comptage des tests dans la base de données !");
            alert.showAndWait();
            return 0;
        }
    }

    public ObservableList<Test> getAllTests() {
        ObservableList<Test> tests = FXCollections.observableArrayList();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM test")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                String titre = resultSet.getString("titre");
                Test test = new Test(titre);
                tests.add(test);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de la récupération des tests à partir de la base de données !");
            alert.showAndWait();
        }

        return tests;
    }

    private boolean updateQuizName(Test selectedTest, String newQuizName) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE test SET titre=? WHERE titre=?")) {
            preparedStatement.setString(1, newQuizName);
            preparedStatement.setString(2, selectedTest.getTitre());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                refreshUI(); // Refresh the UI to reflect the updated data
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteTest(Test selectedTest) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM test WHERE titre=?")) {
            preparedStatement.setString(1, selectedTest.getTitre());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    private void refreshUI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
