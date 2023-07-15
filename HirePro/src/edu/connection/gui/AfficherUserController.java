package edu.connection.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import edu.connection.entities.User;

import edu.connection.entities.Role;
import edu.connection.services.UserCRUD;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;


public class AfficherUserController implements Initializable {

    @FXML
    private TableView<User> tableViewUser;
    @FXML
    private TableColumn<User, String> colNom;
    @FXML
    private TableColumn<User, String> colPrenom;
    @FXML
    private TableColumn<User, String> colTel;
    @FXML
    private TableColumn<User, String> colEmail;
    @FXML
    private TableColumn<User, String> colAdresse;
    @FXML
    private TableColumn<User, LocalDate> colDateDNaissance;
   
    @FXML
    private TableColumn<User, Role> colRole;
  
     
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
    private ObservableList<User> offresList = FXCollections.observableArrayList();
    private ObservableList<User> offreList;

    private FilteredList<User> filteredOffreList;

    private Stage primaryStage;
    @FXML
    private TableColumn<User, String> colUser;
    private Label idUser;
    private Label roleUser;
    private String labelIdUserValue;
    private ComboBox<String> comboSortBy;
   

    /*  private void setIdUser(String id) {
        idUser.setText(id);
    }

    private void setRoleUser(String id) {
        roleUser.setText(id);
    }
     */
    
   
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PDF Generation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

   

    public AfficherUserController() {
    }

   

  public void setLabelIdUserValue(String labelIdUserValue) {
        this.labelIdUserValue = labelIdUserValue;
    }

    @FXML
    private void handlebtnNew() {
        // Load the AddOffreFXML.fxml file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddUserFXML.fxml"));
            Parent root = loader.load();
            AddUserFXMLController addUserController = loader.getController();
            //addOffreController.setLabelIdUserValue(labelIdUserValue);

            // Create a new stage for the AddOffre and set the scene
            Stage addOffreStage = new Stage();
            addOffreStage.setScene(new Scene(root));
            addOffreStage.setTitle("Add New User");

            // Show the AddOffre stage
            addOffreStage.show();

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
        filteredOffreList = new FilteredList<>(tableViewUser.getItems(), o -> true);

        // Bind the filtered list to the table view
        tableViewUser.setItems(filteredOffreList);

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
                for (Field field : User.class.getDeclaredFields()) {
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
        System.out.println("-------------");
        //System.out.println(roleUser.getText());


        // Initialize table columns
        colNom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("Adresse"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("Tel"));
        
        colDateDNaissance.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
     
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
     
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colUser.setCellValueFactory(new PropertyValueFactory<>("id"));

        offreList = FXCollections.observableArrayList();
        SortedList<User> sortedOffreList = new SortedList<>(offreList);
        sortedOffreList.comparatorProperty().bind(tableViewUser.comparatorProperty());

        // Bind the sortedOffreList to the tableViewOffre
        tableViewUser.setItems(sortedOffreList);

        // Load data into table view
        loadData();

        ObservableList<String> items = FXCollections.observableArrayList("dateNaissance", "Nom");
        //comboSortBy.setItems(items);

        //comboSortBy.setOnAction(event -> {
          //  handleSortBy();
        //});

        colDateDNaissance.setSortable(true);
        colNom.setSortable(true);

        // ...
        searchDynamic();
    }

    private void loadData() {
        UserCRUD Impl = new UserCRUD();
        List<User> offreList = Impl.displayEntities();
        this.offreList.clear(); // Clear the existing data in the observable list
        this.offreList.addAll(offreList); // Add the new data to the observable list
    }

    @FXML
      private void handleUpdateButton(ActionEvent event) throws IOException {
        // Get the selected User from the table view
        User selectedUser = tableViewUser.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            // No User selected, display an error message or handle it accordingly
            return;
        }

        // Load the AddOffreFXML.fxml interface
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddUserFXML.fxml"));
        Parent root = loader.load();
        AddUserFXMLController addUserController = loader.getController();

        // Pass the selected User to the AddOffreFXMLController
        addUserController.setSelectedUser(selectedUser);

        // Create a new stage for the AddOffre interface
        Stage stage = new Stage();
        stage.setTitle("Update User");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);

        // Show the AddOffre interface and wait for it to close
        stage.showAndWait();

        // Refresh the table view to reflect the changes
        tableViewUser.refresh();
    }
 

    @FXML
    private void handleDeleteButton(ActionEvent event) {
        User selectedOffre = tableViewUser.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete User");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this user?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Remove the selected Offre from the database
                int id = selectedOffre.getId();
                System.out.println(selectedOffre.getId());
                UserCRUD impl = new UserCRUD();
                impl.supprimer(id);

                // Remove the selected Offre from the offreList
                if (offreList.contains(selectedOffre)) {
                    offreList.remove(selectedOffre);
                }
            }
        }

        tableViewUser.refresh();
    }

}
