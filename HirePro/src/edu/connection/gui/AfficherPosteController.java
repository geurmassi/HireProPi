
package edu.connection.gui;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import edu.connection.entities.Offre;
import edu.connection.entities.Poste;
import edu.connection.services.OffreEmploiCrud;
import edu.connection.services.PosteCrud;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AfficherPosteController implements Initializable {

    @FXML
    private TableView<Poste> tableViewPostes;
    @FXML
    private TableColumn<Poste, String> colIdPoste;
    @FXML
    private TableColumn<Poste, String> colPosteName;

    @FXML
    private Button btnADD;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private HBox HBox;
    @FXML
    private TextField searchTextField;

    private FilteredList<Poste> filteredPosteList;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
      private void searchDynamic() {
        // Initialize the filteredOffreList with the data from the table view
        filteredPosteList = new FilteredList<>(tableViewPostes.getItems(), p -> true);

        // Bind the filtered list to the table view
        tableViewPostes.setItems(filteredPosteList);

        // Add a listener to the search text field to perform filtering when the text changes
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPosteList.setPredicate(poste -> {
                // If the search text is empty, show all items
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Convert the search text to lowercase for case-insensitive search
                String searchText = newValue.toLowerCase();

                // Iterate over all attributes of Offre class and check if any attribute contains the search text
                for (Field field : Poste.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(poste);
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
        colIdPoste.setCellValueFactory(new PropertyValueFactory<>("idP"));
        colPosteName.setCellValueFactory(new PropertyValueFactory<>("poste"));

        // Load data into table view
        loadData();
        searchDynamic();
    }

    private void loadData() {
        PosteCrud Impl = new PosteCrud();
        List<Poste> posteList = Impl.displayEntities();
        tableViewPostes.getItems().addAll(posteList);
    }

    @FXML
    private void AddPoste(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPoste.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Create a new stage for AfficherOffre and set the scene
            Stage afficherPosteStage = new Stage();
            afficherPosteStage.setScene(scene);
            afficherPosteStage.setTitle("Add New Poste");

            // Show the AfficherOffre stage
            afficherPosteStage.show();

            // Close the primary stage
            primaryStage.close();
        } catch (IOException ex) {
            System.out.println("Error loading FXML file");
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateButton(ActionEvent event) {
        // Get the selected Poste from the table view
        Poste selectedPoste = tableViewPostes.getSelectionModel().getSelectedItem();

        if (selectedPoste == null) {
            // No Poste selected, display an error message or handle it accordingly
            return;
        }

        // Open a dialog or navigate to the update view
        // Pass the selected Poste object to the update view/controller
        // Update the Poste details and save the changes
        // For example, you can use a dialog to prompt for the updated Poste name
        TextInputDialog dialog = new TextInputDialog(selectedPoste.getPoste());
        dialog.setTitle("Update Poste");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the updated Poste name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String updatedPosteName = result.get();

            // Update the Poste object
            selectedPoste.setPoste(updatedPosteName);

            // Save the changes to the database using the PosteCrud class or your preferred method
            PosteCrud impl = new PosteCrud();
            impl.modifier(selectedPoste);

            // Refresh the table view to reflect the changes
            tableViewPostes.refresh();
        }
    }

   @FXML
private void handleDeleteButton(ActionEvent event) {
    // Get the selected Poste from the table view
    Poste selectedPoste = tableViewPostes.getSelectionModel().getSelectedItem();

    if (selectedPoste == null) {
        // No Poste selected, display an error message or handle it accordingly
        return;
    }

    // Confirmation dialog to confirm the deletion
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Delete Poste");
    alert.setHeaderText(null);
    alert.setContentText("Are you sure you want to delete this Poste?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        // Delete the Poste entity
        PosteCrud impl = new PosteCrud();
        impl.supprimer(selectedPoste.getIdP());

        // Remove the selected Poste from the table view
        tableViewPostes.getItems().remove(selectedPoste);
    }
}

}
