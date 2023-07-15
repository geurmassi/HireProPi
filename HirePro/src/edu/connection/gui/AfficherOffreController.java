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
import edu.connection.entities.UserConnect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import edu.connection.services.OffreEmploiCrud;
import edu.connection.services.PosteCrud;
import edu.connection.services.candidatureMETIEER;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableRow;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

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
    private Label idUser;
    @FXML
    private Label roleUser;
    private String labelIdUserValue;
    @FXML
    private Button btnPrintPdf;
    @FXML
    private ComboBox<String> comboSortBy;
    @FXML
    private Label idUser1;

    private MainController mainController;
    @FXML
    private Button btngetOffresByIdUser;

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PDF Generation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handlePrintPdfButton(ActionEvent event) {
        Offre selectedOffre = tableViewOffre.getSelectionModel().getSelectedItem();
        if (selectedOffre == null) {
            showAlert("No line selected!");
            return;
        }

        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            PDType0Font fontBold = PDType0Font.load(document, new File("C:\\Windows\\Fonts\\timesbd.ttf"));
            PDType0Font fontRegular = PDType0Font.load(document, new File("C:\\Windows\\Fonts\\times.ttf"));

            contentStream.setFont(fontBold, 16);

            float x = 50; // Set the x coordinate
            float y = page.getMediaBox().getHeight() - 50; // Set the y coordinate

            contentStream.beginText();
            contentStream.newLineAtOffset(x, y);

            // Append offer details to the content stream
            contentStream.setNonStrokingColor(64 / 255f, 64 / 255f, 64 / 255f); // Set text color
            contentStream.setFont(fontBold, 12);
            contentStream.showText("Job Holder: ");
            contentStream.setFont(fontRegular, 12);
            contentStream.showText(selectedOffre.getJobHolder());

            y -= 20;
            contentStream.newLineAtOffset(0, -20);
            contentStream.setFont(fontBold, 12);
            contentStream.showText("Lieu: ");
            contentStream.setFont(fontRegular, 12);
            contentStream.showText(selectedOffre.getLieuT());

            y -= 20;
            contentStream.newLineAtOffset(0, -20);
            contentStream.setFont(fontBold, 12);
            contentStream.showText("Descriptif: ");
            contentStream.setFont(fontRegular, 12);
            contentStream.showText(selectedOffre.getDescriptif().replace("\n", " "));

            y -= 20;
            contentStream.newLineAtOffset(0, -20);
            contentStream.setFont(fontBold, 12);
            contentStream.showText("Skills: ");
            contentStream.setFont(fontRegular, 12);
            contentStream.showText(selectedOffre.getSkills().replace("\n", " "));

            y -= 20;
            contentStream.newLineAtOffset(0, -20);
            contentStream.setFont(fontBold, 12);
            contentStream.showText("Company: ");
            contentStream.setFont(fontRegular, 12);
            contentStream.showText(selectedOffre.getCompany());

            y -= 20;
            contentStream.newLineAtOffset(0, -20);
            contentStream.setFont(fontBold, 12);
            contentStream.showText("Date de début: ");
            contentStream.setFont(fontRegular, 12);
            contentStream.showText(selectedOffre.getDateDebutOffre().toString());

            y -= 20;
            contentStream.newLineAtOffset(0, -20);
            contentStream.setFont(fontBold, 12);
            contentStream.showText("Date de fin: ");
            contentStream.setFont(fontRegular, 12);
            contentStream.showText(selectedOffre.getDateFinOffre().toString());

            y -= 20;
            contentStream.newLineAtOffset(0, -20);
            contentStream.setFont(fontBold, 12);
            contentStream.showText("Type d'emploi: ");
            contentStream.setFont(fontRegular, 12);
            contentStream.showText(selectedOffre.getTypeEmploi().toString());

            y -= 20;
            contentStream.newLineAtOffset(0, -20);
            contentStream.setFont(fontBold, 12);
            contentStream.showText("Type de lieu de travail: ");
            contentStream.setFont(fontRegular, 12);
            contentStream.showText(selectedOffre.getTypeLieuTravail().toString());

            y -= 20;
            contentStream.newLineAtOffset(0, -20);
            contentStream.setFont(fontBold, 12);
            contentStream.showText("Réception des candidatures: ");
            contentStream.setFont(fontRegular, 12);
            contentStream.showText(selectedOffre.getReceptionOfApplication().toString());

            contentStream.endText();
            contentStream.close();

            // Save the PDF document
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                document.save(file);
                showAlert("PDF generated successfully!");
            }

            document.close();
        } catch (IOException e) {
            showAlert("Error generating PDF: " + e.getMessage());
        }
    }

    public AfficherOffreController() {
    }

    public void setLabelIdUserValue(String value) {
        idUser.setText(value);
        System.out.println(idUser.getText());
    }

    public void setLabelRoleUserValue(String value) {
        roleUser.setText(value);
        System.out.println(roleUser.getText());

    }
// Setter method for the MainController

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handlebtnNew() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddOffreFXML.fxml"));
            Parent root = loader.load();
            AddOffreFXMLController addOffreController = loader.getController();

            if (addOffreController != null) {
                String id = idUser.getText();
                addOffreController.setLabelIdUserValue(id);
                mainController.loadPage("AddOffreFXML.fxml");

         
                tableViewOffre.refresh();
            } else {
                System.out.println("Error: Failed to load AddOffreFXML controller");
            }
        } catch (IOException ex) {
            System.out.println("Error: Failed to load AddOffreFXML.fxml");
            ex.printStackTrace();
        }
    }


    @FXML
    private void handleSearchByIdUserButton(ActionEvent event) {
        String id = idUser.getText();

        // Create a new ObservableList to hold the data
        ObservableList<Offre> data = FXCollections.observableArrayList();

        // Perform the search
        OffreEmploiCrud impl = new OffreEmploiCrud();
        List<Offre> offres = impl.getOffresByIdUser(Integer.parseInt(id));

        // Add the retrieved offres to the data list
        data.addAll(offres);

        // Set the data list as the items of the table view
        tableViewOffre.setItems(data);
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

    private void handleSortBy() {
        String sortBy = comboSortBy.getValue();
        if (sortBy == null) {
            return;
        }

        switch (sortBy) {
            case "DateDebutOffre":
                offreList.sort(Comparator.comparing(Offre::getDateDebutOffre));
                break;
            case "JobHolder":
                if(offreList.isEmpty()){
                offreList.sort(Comparator.comparing(Offre::getJobHolder));
                } else {
                     offreList.sort(Comparator.comparing(Offre::getJobHolder));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("-------------");
        System.out.println(roleUser.getText());

        if ("candidat".equals(roleUser.getText())) {
            btnDelete.setVisible(false);
            btnUpdate.setVisible(false);
            btnNew.setVisible(false);
        }

        // Initialize table columns
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

        offreList = FXCollections.observableArrayList();
        SortedList<Offre> sortedOffreList = new SortedList<>(offreList);
        sortedOffreList.comparatorProperty().bind(tableViewOffre.comparatorProperty());

        // Bind the sortedOffreList to the tableViewOffre
        tableViewOffre.setItems(sortedOffreList);

        // Load data into table view
        loadData();

        ObservableList<String> items = FXCollections.observableArrayList("DateDebutOffre", "JobHolder");
        comboSortBy.setItems(items);

        comboSortBy.setOnAction(event -> {
            handleSortBy();
        });

        colDateDebutOffre.setSortable(true);
        colJobHolder.setSortable(true);

        // ...
        searchDynamic();
        doubelClick();
    }
    
    private void doubelClick(){
         tableViewOffre.setRowFactory(tv -> {
            TableRow<Offre> myRow = new TableRow<>();
            myRow.setOnMouseClicked(eventv -> {
                if (eventv.getClickCount() == 2 && (!myRow.isEmpty())) {
                    int myIndex = tableViewOffre.getSelectionModel().getSelectedIndex();
                    Offre selectedOffre = tableViewOffre.getItems().get(myIndex);

                    // Get the current date
                    LocalDate currentDate = LocalDate.now();
                    candidatureMETIEER cm = new candidatureMETIEER();
                    String idoffrechoisistring ="";

                    // Check if the current date is after dateFinOffre
                    if (currentDate.isAfter(selectedOffre.getDateFinOffre())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Offre expirée");
                        alert.setHeaderText(null);
                        alert.setContentText("L'offre sélectionnée a expiré.");
                        alert.showAndWait();
                    } else if (cm.checkCondidatureExists(selectedOffre.getIdOffre(), UserConnect.idUserConnect)){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Candidature expirée");
                        alert.setHeaderText(null);
                        alert.setContentText("vous avez déja postulé");
                        alert.showAndWait();
                    }else {
                        String id_rdv = String.valueOf(selectedOffre.getIdOffre());
                        
                       // idoffrechoisistring.setText(id_rdv);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterCandidature.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                        idUser.getScene().setRoot(root);
                        AjouterCandidatureController acc = loader.getController();
                        acc.setLbIdOffre(id_rdv);
                        //acc.setLbiduser(lbiduser.getText());
                    }
                }
            });
            return myRow;
        });
    }

    private void loadData() {
        OffreEmploiCrud Impl = new OffreEmploiCrud();
        List<Offre> offreList = Impl.displayEntities();
        this.offreList.clear(); // Clear the existing data in the observable list
        this.offreList.addAll(offreList); // Add the new data to the observable list
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
