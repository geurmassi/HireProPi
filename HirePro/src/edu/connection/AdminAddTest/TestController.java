package edu.connection.AdminAddTest;


import edu.connection.entities.Test;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class TestController implements Initializable {

    @FXML
    private TextField NomFromulaireTextFiled;
    @FXML
    private TableView<Test> listeQuizTableview;
    @FXML
    private TableColumn<Test, String> titreTableColumn;
    private ObservableList<Test> tests;
    @FXML
    private Button SupprimerQuiz;
    @FXML
    private Button AjouterQuiz;
    @FXML
    private Button chercherQuiz;
    @FXML
    private Button AfficherQuiz;
    @FXML
    private Button ModifierQuiz;
    @FXML
    private Button AddQueston;
    @FXML
    private Button AfficheTest;
    @FXML
    private Button Bar_Chart;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tests = FXCollections.observableArrayList();
        listeQuizTableview.setItems(tests);
        titreTableColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
//        listeQuizTableview.setOnMouseClicked(this::handleListeQuizTableviewMouseClicked);
    }
//@FXML
//    private void handleListeQuizTableviewMouseClicked(MouseEvent event) {
//    if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
//        Test selectedTest = listeQuizTableview.getSelectionModel().getSelectedItem();
//        if (selectedTest == null) {
//            showAlert(Alert.AlertType.ERROR, "Error", null, "No test selected.");
//            return;
//        }
//
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/connection/AdminAddTest/afficheDetailsFXML.fxml"));
//            Parent root = loader.load();
//            Scene scene = new Scene(root);
//
//            Stage stage = new Stage();
//            stage.setTitle("Détails du quiz");
//            stage.setScene(scene);
//            stage.show();
//
//            afficheDetailsController controller = loader.getController();
//            controller.initData(selectedTest); // Pass the selected test to the AfficheDetailsController
//
//            // Close the current stage (TestController)
//            Stage currentStage = (Stage) listeQuizTableview.getScene().getWindow();
//            currentStage.close();
//        } catch (IOException e) {
//            showAlert(Alert.AlertType.ERROR, "Error", null, "Failed to load test details.");
//        }
//    }
//}


    @FXML
    private void modifierQuiz(ActionEvent event) {
        Test selectedTest = listeQuizTableview.getSelectionModel().getSelectedItem();

        if (selectedTest != null) {
            TextInputDialog dialog = new TextInputDialog(selectedTest.getTitre());
            dialog.setTitle("Modifier le quiz");
            dialog.setHeaderText(null);
            dialog.setContentText("Entrez le nouveau nom du quiz:");

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                String newQuizName = result.get();
                boolean success = updateQuizName(selectedTest, newQuizName);

                if (success) {
                    selectedTest.setTitre(newQuizName);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Le nom du quiz a été modifié avec succès.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Une erreur s'est produite lors de la modification du nom du quiz.");
                    alert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un quiz à modifier.");
            alert.showAndWait();
        }
    }

    private void afficheTest(MouseEvent event) {
    if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
        Test selectedTest = listeQuizTableview.getSelectionModel().getSelectedItem();
        if (selectedTest == null) {
            showAlert(Alert.AlertType.ERROR, "Error", null, "No test selected.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/connection/AdminAddTest/afficheDetailsFXML.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Détails du quiz");
            stage.setScene(scene);
            stage.show();

            afficheDetailsController controller = loader.getController();
            controller.initData(selectedTest); // Pass the selected test to the AfficheDetailsController

            // Close the current stage (TestController)
            Stage currentStage = (Stage) listeQuizTableview.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Failed to load test details.");
        }
    }
}


   
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
private void supprimerQuestion(ActionEvent event) {
    Test selectedTest = listeQuizTableview.getSelectionModel().getSelectedItem();

    if (selectedTest != null) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer le quiz");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer le quiz sélectionné ?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            TestService testService = new TestService();
            boolean success = testService.deleteTest(selectedTest);

            if (success) {
                tests.remove(selectedTest);
                listeQuizTableview.getSelectionModel().clearSelection();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", null, "Échec de la suppression du test.");
            }
        }
    } else {
        showAlert(Alert.AlertType.WARNING, null, null, "Veuillez sélectionner un quiz à supprimer.");
    }
}


  @FXML
private void AjouterQuiz(ActionEvent event) {
    String titre = NomFromulaireTextFiled.getText();

    if (!titre.isEmpty()) {
        Test test = new Test(titre);
        TestService testService = new TestService(); // Assuming you have a TestService class

        boolean success = testService.addQuiz(test);

        if (success) {
            tests.add(test); // Add the test to the ObservableList
            clearInputFields(); // Clear input fields after adding the test
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Le quiz a été ajouté avec succès à la base de données.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Ce quiz existe déjà !");
            alert.showAndWait();
        }
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Le titre du quiz ne peut pas être vide !");
        alert.showAndWait();
    }
}


    @FXML
    private void chercherQuestion(ActionEvent event) {
        String titre = NomFromulaireTextFiled.getText();

        TestService testService = new TestService(); // Assuming you have a TestService class

        int count = testService.countTestsByTitre(titre);

        if (count > 0) {
            // Test(s) found in the database
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Le test a été trouvé " + count + " fois dans la base de données.");
            alert.showAndWait();
        } else {
            // Test not found in the database
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Le test n'a pas été trouvé dans la base de données.");
            alert.showAndWait();
        }
    }

    private void clearInputFields() {
        NomFromulaireTextFiled.clear();
    }
    
  private void refreshUI() {
    // Fetch the updated data from the database
    TestService testService = new TestService();
    ObservableList<Test> updatedTests = testService.getAllTests();

    // Update the UI elements with the latest data
    tests.clear();
    tests.addAll(updatedTests);
}



    @FXML
    private void AfficherQuiz(ActionEvent event) {
        TestService testService = new TestService(); // Assuming you have a TestService class

        ObservableList<Test> allTests = testService.getAllTests();

        if (!allTests.isEmpty()) {
            // Tests found in the database
            tests.setAll(allTests);
        } else {
            // No tests found in the database
            tests.clear();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Aucun test trouvé dans la base de données.");
            alert.showAndWait();
        }
    }
    
    private boolean updateQuizName(Test selectedTest, String newQuizName) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hirepro", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE test SET titre=? WHERE titre=?")) {

            preparedStatement.setString(1, newQuizName);
            preparedStatement.setString(2, selectedTest.getTitre());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            return false;
        }
    }

private void handleModifierTitre(TableColumn.CellEditEvent<Test, String> event) {
    // Get the selected test
    Test selectedTest = event.getRowValue();

    // Get the new quiz name from the edited cell
    String newQuizName = event.getNewValue();

    if (!newQuizName.isEmpty()) {
        // Call a method to update the quiz name in the database
        boolean success = updateQuizName(selectedTest, newQuizName);

        if (success) {
            selectedTest.setTitre(newQuizName);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Le nom du quiz a été modifié avec succès.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de la modification du nom du quiz.");
            alert.showAndWait();
        }
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Le titre du quiz ne peut pas être vide !");
        alert.showAndWait();
    }
}



   @FXML
private void handleTitreTableColumnEdit(TableColumn.CellEditEvent event) {
    // Method implementation
}

   @FXML
private void handleAddQuestion(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/connection/AdminAddTest/AddQuestion.fxml"));
    Parent addQuestionRoot = loader.load();

    Scene addQuestionScene = new Scene(addQuestionRoot);
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    primaryStage.setScene(addQuestionScene);
    primaryStage.show();
}

    @FXML
private void handleAfficheQuestion(ActionEvent event) {
    Test selectedTest = listeQuizTableview.getSelectionModel().getSelectedItem();
    if (selectedTest == null) {
        showAlert(Alert.AlertType.ERROR, "Error", null, "No test selected.");
        return;
    }

    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/connection/AdminAddTest/afficheDetailsFXML.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Questions du quiz");
        stage.setScene(scene);
        stage.show();

        afficheDetailsController controller = loader.getController();
        controller.initData(selectedTest); // Pass the selected test to the AfficheQuestionsController

        // Close the current stage (TestController)
        Stage currentStage = (Stage) listeQuizTableview.getScene().getWindow();
        currentStage.close();
    } catch (IOException e) {
        showAlert(Alert.AlertType.ERROR, "Error", null, "Failed to load quiz questions.");
    }
}

    


@FXML
private void handleBarChart(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QuestionCountFXML.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Question Count Bar Chart");
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}


}