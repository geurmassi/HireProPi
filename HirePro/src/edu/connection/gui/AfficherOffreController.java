package edu.connection.gui;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import edu.connection.entities.Offre;
import edu.connection.entities.Poste;
import edu.connection.entities.ReceptionOfApplication;
import edu.connection.entities.TypeEmploi;
import edu.connection.entities.TypeLieuTravail;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import edu.connection.services.OffreEmploiCrud;
import edu.connection.services.PosteCrud;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AfficherOffreController implements Initializable {

    @FXML
    private TableView<Offre> tableViewOffre;
    @FXML
    private TableColumn<Offre, String> colJobHolder;
    @FXML
    private TableColumn<Offre, String> colLieuT;
    @FXML
    private TableColumn<Offre, String> colDescriptif;
    @FXML
    private TableColumn<Offre, String> colSkills;
    @FXML
    private TableColumn<Offre, String> colCompany;
    @FXML
    private TableColumn<Offre, LocalDate> colDateDebutOffre;
    @FXML
    private TableColumn<Offre, LocalDate> colDateFinOffre;
    @FXML
    private TableColumn<Offre, TypeEmploi> colTypeEmploi;
    @FXML
    private TableColumn<Offre, TypeLieuTravail> colTypeLieuTravail;
    @FXML
    private TableColumn<Offre, ReceptionOfApplication> colReceptionOfApplication;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnNew;
    @FXML
    private TextField searchTextField;
    private ObservableList<Offre> offresList = FXCollections.observableArrayList();
    private ObservableList<Offre> offreList;

    private FilteredList<Offre> filteredOffreList;

    private Stage primaryStage;
    @FXML
    private TableColumn<Offre, String> colIdOffre;

    @FXML
    private void handlebtnNew() {
        // Load the AfficherOffre.fxml file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddOffreFXML.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Create a new stage for AfficherOffre and set the scene
            Stage afficherOffreStage = new Stage();
            afficherOffreStage.setScene(scene);
            afficherOffreStage.setTitle("Add New Offre");

            // Show the AfficherOffre stage
            afficherOffreStage.show();

            // Close the primary stage
            primaryStage.close();
        } catch (IOException ex) {
            System.out.println("Error loading FXML file");
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleCloseButton() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    private void searchDynamic() {
        // Initialize the filteredOffreList with the data from the table view
        filteredOffreList = new FilteredList<>(tableViewOffre.getItems(), o -> true);

        // Bind the filtered list to the table view
        tableViewOffre.setItems(filteredOffreList);

        // Add a listener to the search text field to perform filtering when the text changes
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredOffreList.setPredicate(offre -> {
                // If the search text is empty, show all items
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Convert the search text to lowercase for case-insensitive search
                String searchText = newValue.toLowerCase();

                // Iterate over all attributes of Offre class and check if any attribute contains the search text
                for (Field field : Offre.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(offre);
                        if (value != null && value.toString().toLowerCase().contains(searchText)) {
                            return true; // Return true if any attribute contains the search text
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                return false; // Return false if no attribute contains the search text
            });
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize table columns
        offreList = FXCollections.observableArrayList();

        // Bind the offreList to the tableViewOffre
        tableViewOffre.setItems(offreList);

        colJobHolder.setCellValueFactory(new PropertyValueFactory<>("jobHolder"));
        colLieuT.setCellValueFactory(new PropertyValueFactory<>("lieuT"));
        colDescriptif.setCellValueFactory(new PropertyValueFactory<>("descriptif"));
        colSkills.setCellValueFactory(new PropertyValueFactory<>("skills"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colDateDebutOffre.setCellValueFactory(new PropertyValueFactory<>("dateDebutOffre"));
        colDateFinOffre.setCellValueFactory(new PropertyValueFactory<>("dateFinOffre"));
        colTypeEmploi.setCellValueFactory(new PropertyValueFactory<>("typeEmploi"));
        colTypeLieuTravail.setCellValueFactory(new PropertyValueFactory<>("typeLieuTravail"));
        colReceptionOfApplication.setCellValueFactory(new PropertyValueFactory<>("receptionOfApplication"));
        colIdOffre.setCellValueFactory(new PropertyValueFactory<>("idOffre"));

        // Load data into table view
        loadData();

//        handleCloseButton();
        searchDynamic();
    }

    private void loadData() {
        OffreEmploiCrud Impl = new OffreEmploiCrud();
        List<Offre> offreList = Impl.displayEntities();
        tableViewOffre.getItems().addAll(offreList);
    }

   @FXML
private void handleUpdateButton(ActionEvent event) throws IOException {
    // Get the selected Offre from the table view
    Offre selectedOffre = tableViewOffre.getSelectionModel().getSelectedItem();

    if (selectedOffre == null) {
        // No Offre selected, display an error message or handle it accordingly
        return;
    }

    // Load the addOffre.fxml interface
    FXMLLoader loader = new FXMLLoader(getClass().getResource("AddOffreFXML.fxml"));
    Parent root = loader.load();
    AddOffreFXMLController addOffreController = loader.getController();

    // Set the existing Offre values in the addOffreController
    addOffreController.setOffre(selectedOffre);
    addOffreController.btnConfirmUpdate.setVisible(true);
    addOffreController.btnPost.setVisible(false);

    // Create a new stage for the addOffre interface
    Stage stage = new Stage();
    stage.setTitle("Update Offre");
    stage.setScene(new Scene(root));
    stage.initModality(Modality.APPLICATION_MODAL);

    // Show the addOffre interface and wait for it to close
    stage.showAndWait();

    // Refresh the table view to reflect the changes
    tableViewOffre.refresh();
}


    @FXML
    private void handleDeleteButton(ActionEvent event) {
        Offre selectedOffre = tableViewOffre.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Offre");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this Offre?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Remove the selected Offre from the database
                int id = selectedOffre.getIdOffre();
                System.out.println(selectedOffre.getIdOffre());
                OffreEmploiCrud impl = new OffreEmploiCrud();
                impl.supprimer(id);

                // Remove the selected Offre from the offreList
                if (offreList.contains(selectedOffre)) {
                    offreList.remove(selectedOffre);
                }
            }
        }

        tableViewOffre.refresh();
    }

}
