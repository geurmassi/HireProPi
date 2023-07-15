/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.gui;

import edu.HirePro.api.EmailSender;
import edu.HirePro.api.PDFReader;
import edu.HirePro.api.PDFViewer;
import edu.HirePro.entities.Condidature;
import edu.HirePro.entities.Etat;
import edu.HirePro.services.CondidatureCRUD;
import edu.HirePro.services.candidatureMETIEER;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class CandidatureRefuseeController implements Initializable {

     @FXML
    private TableView<Condidature> tableCan;
    @FXML
    private TableColumn<Condidature, String> colCv;
    @FXML
    private TableColumn<Condidature, String> colLm;
    @FXML
    private TableColumn<Condidature, String> colPor;
    @FXML
    private TableColumn<Condidature, String> colPoste;
    @FXML
    private TableColumn<Condidature, Timestamp> colDate;
    @FXML
    private TableColumn<Condidature, String> colNom;
    @FXML
    private Label lbNom;
    @FXML
    private Label lbCv;
    @FXML
    private Label lbLm;
    @FXML
    private Label lbPor;
    @FXML
    private Label lbPoste;
    @FXML
    private Label lbDate;
    @FXML
    private ComboBox<Etat> comboEtat;
    @FXML
    private Label iduser;
    @FXML
    private Label lbEtat;
    @FXML
    private Button btApplay;
    @FXML
    private ComboBox<String> comboBoxOffre;
    @FXML
    private Button evalButton;
    
    public void setIdUser(String iduserm) {
        iduser.setText(iduserm);
        System.out.println("iduserm: " + iduserm);
    }
    
    candidatureMETIEER cm = new candidatureMETIEER();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         String iduserm = iduser.getText();
        String labelValueUser = iduserm;

        if (!labelValueUser.isEmpty()) {
            String numericValueUser = labelValueUser.replaceAll("[^0-9]", "");
            if (!numericValueUser.isEmpty()) {
                int id = Integer.parseInt(numericValueUser);
                List<String> postesWithDate = cm.getPostesWithDateByUserId(id);
                comboBoxOffre.getItems().addAll(postesWithDate);
                comboBoxOffre.setOnAction(event -> {
                    lbNom.setVisible(false);
                    lbCv.setVisible(false);
                    lbLm.setVisible(false);
                    lbPor.setVisible(false);
                    lbPoste.setVisible(false);
                    lbDate.setVisible(false);
                    lbEtat.setVisible(false);
                    comboEtat.setVisible(false);
                    btApplay.setVisible(false);
                    String selectedPosteWithDate = comboBoxOffre.getValue();
                    String selectedPoste = selectedPosteWithDate.split(" \\(Date Debut: ")[0]; // Extract the poste value
                    System.out.println(selectedPoste);
                    tableCan.setVisible(true);
                    evalButton.setVisible(true);
                    ObservableList<Etat> etatList = FXCollections.observableArrayList(Etat.values());
                    etatList.remove(Etat.en_attente); // Remove the "en_attente" value from the list
                    comboEtat.setItems(etatList);
                    // Perform the necessary logic to fetch the candidatures based on the selected poste
                    ObservableList<Condidature> liste = FXCollections.observableList(cm.getCandidaturesByPosteRefuser(selectedPoste));
                    colCv.setCellValueFactory(new PropertyValueFactory<>("cv"));
                    colLm.setCellValueFactory(new PropertyValueFactory<>("lettreMotivation"));
                    colPor.setCellValueFactory(new PropertyValueFactory<>("portfolio"));
                    colPoste.setCellValueFactory(cellData -> {
                        int idoffre = cellData.getValue().getIdOffre();
                        String namePoste = cm.recherchePosteNameByIdOffre(idoffre);
                        return new SimpleStringProperty(namePoste);
                    });
                    colPoste.setStyle("-fx-font-weight: bold");
                    colDate.setCellValueFactory(new PropertyValueFactory<>("dateCandidature"));
                    colNom.setCellValueFactory(cellData -> {
                        int idUtilisateur = cellData.getValue().getIdUtilisateur();
                        String name = cm.rechercheNameUserbyId(idUtilisateur);
                        return new SimpleStringProperty(name);
                    });
                    tableCan.setItems(liste);
                });
            } else {
                System.out.println("numericValueUser is empty");
                System.out.println("numericValueUser: " + numericValueUser);
            }
        } else {
            System.out.println("labelValueUser is empty");
        }
        tableCan.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Condidature>() {
            @Override
            public void changed(ObservableValue<? extends Condidature> obs, Condidature oldSelection, Condidature newSelection) {
                if (newSelection != null) {
                    // Perform your action here
                    // You can access the selected Condidature using 'newSelection'
                    // For example, you can display its details in the text fields or perform any other logic
                    lbNom.setVisible(true);
                    lbNom.setText("Nom et Prénom          : " + cm.rechercheNameUserbyId(newSelection.getIdUtilisateur()));
                    lbNom.setOnMouseEntered(event -> {
                        lbNom.setUnderline(true);
                        lbNom.setCursor(Cursor.HAND);
                    });
                    lbNom.setOnMouseExited(event -> {
                        lbNom.setUnderline(false);
                        lbNom.setCursor(Cursor.DEFAULT);
                    });
                    lbNom.setOnMouseClicked(event -> {
                        String name = cm.rechercheNameUserbyId(newSelection.getIdUtilisateur());
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Label Value");
                        alert.setHeaderText(null);
                        alert.setContentText("Value of lbNom: " + name);
                        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                        alert.getButtonTypes().setAll(okButton);
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == okButton) {
                            // OK button is clicked
                            alert.close();
                        }
                    });
                    lbCv.setVisible(true);
                    lbCv.setText("Cv                                : " + newSelection.getCv());
                    lbCv.setOnMouseEntered(event -> {
                        lbCv.setUnderline(true);
                        lbCv.setCursor(Cursor.HAND);
                    });
                    lbCv.setOnMouseExited(event -> {
                        lbCv.setUnderline(false);
                        lbCv.setCursor(Cursor.DEFAULT);
                    });
                    lbCv.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            String cv = newSelection.getCv();
                            if (cv != null && !cv.isEmpty()) {
                                PDFViewer.openPDFCv(cv);
                            } else {
                                // Show an alert if the cv is not available
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("No CV");
                                alert.setHeaderText(null);
                                alert.setContentText("CV is not available");
                                alert.showAndWait();
                            }
                        }
                    });
                    lbLm.setVisible(true);
                    lbLm.setText("Lettre de Motivation : " + newSelection.getLettreMotivation());
                    lbLm.setOnMouseEntered(event -> {
                        lbLm.setUnderline(true);
                        lbLm.setCursor(Cursor.HAND);
                    });
                    lbLm.setOnMouseExited(event -> {
                        lbLm.setUnderline(false);
                        lbLm.setCursor(Cursor.DEFAULT);
                    });
                    lbLm.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            String lettreMotivation = newSelection.getLettreMotivation();
                            if (lettreMotivation != null && !lettreMotivation.isEmpty()) {
                                PDFViewer.openPDFLm(lettreMotivation);
                            } else {
                                // Show an alert if the lettreMotivation is not available
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("No Lettre de Motivation");
                                alert.setHeaderText(null);
                                alert.setContentText("Lettre de Motivation is not available");
                                alert.showAndWait();
                            }
                        }
                    });
                    String portfolio = newSelection.getPortfolio();
                    if (portfolio != null && !portfolio.isEmpty()) {
                        lbPor.setVisible(true);
                        lbPor.setText("Portfilio                       : " + portfolio);
                        lbPor.setOnMouseEntered(event -> {
                            lbPor.setUnderline(true);
                            lbPor.setCursor(Cursor.HAND);
                        });
                        lbPor.setOnMouseExited(event -> {
                            lbPor.setUnderline(false);
                            lbPor.setCursor(Cursor.DEFAULT);
                        });
                        // Event handler for lbPor label
                        lbPor.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                // Open the portfolio link using the default browser
                                try {
                                    Desktop.getDesktop().browse(new URI(portfolio));
                                } catch (IOException | URISyntaxException e) {
                                    e.printStackTrace();
                                    // Show an error alert if there's an issue opening the portfolio link
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Failed to open portfolio link");
                                    alert.showAndWait();
                                }
                            }
                        });
                    } else {
                        lbPor.setVisible(false);
                    }
                    lbPoste.setVisible(true);
                    lbPoste.setText("Poste                           : " + cm.recherchePosteNameByIdOffre(newSelection.getIdOffre()));
                    lbDate.setVisible(true);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String formattedDate = dateFormat.format(newSelection.getDateCandidature());
                    lbDate.setText("Date de condidature : " + formattedDate);
                    lbEtat.setVisible(true);
                    comboEtat.setVisible(true);
                    btApplay.setVisible(true);
                }
            }
        });
        // TODO
    }    

    @FXML
    private void candidatureAccepter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CandidatureAccepte.fxml"));
            Parent root = loader.load();
            lbPoste.getScene().setRoot(root);
            CandidatureAccepteController controller = loader.getController();
            String ids = iduser.getText();
            controller.setIdUser(ids);
            controller.initialize(null, null);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @FXML
    private void updateEtat(ActionEvent event) {
        Condidature selectedCondidature = tableCan.getSelectionModel().getSelectedItem();
        Etat selectedEtat = comboEtat.getValue();

        if (selectedCondidature != null && selectedEtat != null) {
            // Compare the selectedEtat with the previous etat
            Etat previousEtat = selectedCondidature.getEtat();
            if (!selectedEtat.equals(previousEtat)) {
                // Update the dateCandidature to the current timestamp
                selectedCondidature.setDateCandidature(new Timestamp(System.currentTimeMillis()));
                // Update the etat of the candidature
                selectedCondidature.setEtat(selectedEtat);
                // Call the method to update the candidature in your data layer
                CondidatureCRUD cc = new CondidatureCRUD();
                cc.updateEntity(selectedCondidature);
                // Reload the TableView with the updated list of candidatures
                String selectedPosteWithDate = comboBoxOffre.getValue();
                String selectedPoste = selectedPosteWithDate.split(" \\(Date Debut: ")[0]; // Extract the poste value
                ObservableList<Condidature> liste = FXCollections.observableList(cm.getCandidaturesByPosteRefuser(selectedPoste));
                tableCan.setItems(liste);
                candidatureMETIEER mc = new candidatureMETIEER();
                String name = cm.rechercheNameUserbyId(selectedCondidature.getIdUtilisateur());
                String selectedPosteWithDateMessage = comboBoxOffre.getValue();
                String selectedPosteMessage = selectedPosteWithDateMessage.split(" \\(Date Debut: ")[0];
                String nameRecruteur = mc.retrieveUserNameByIdOffre(selectedCondidature.getIdOffre());
                EmailSender emailSender = new EmailSender();
                if (selectedEtat == Etat.accepté) {
                    String objetacc = "Félicitations ! Votre candidature a été acceptée  - " + selectedPoste;
                    String messageaccepter = "Cher(e) " + name + ",\n"
                            + "\n"
                            + "Nous avons le plaisir de vous informer que votre candidature pour le poste de " + selectedPosteMessage + " a été sélectionnée par"
                            + " notre équipe de recrutement. Votre profil a retenu notre attention parmi de nombreux candidats.\n"
                            + "\n"
                            + "Nous souhaitons donc vous inviter à passer un entretien téléphonique afin de mieux vous connaître et d'évaluer"
                            + " votre adéquation avec le poste. L'entretien téléphonique est une étape importante dans le processus de recrutement"
                            + " et nous permettra d'échanger sur votre parcours, vos compétences et vos motivations.\n"
                            + "\n"
                            + "Nous vous proposons de fixer une date et une heure pour cet entretien téléphonique. Veuillez nous indiquer vos "
                            + "disponibilités dans les prochains jours, afin que nous puissions convenir d'un créneau horaire qui vous convienne.\n"
                            + "\n"
                            + "Si l'entretien téléphonique se déroule favorablement, nous serons ravis de vous rencontrer pour un entretien physique "
                            + "dans nos locaux. Cette étape nous permettra d'approfondir notre évaluation et de discuter plus en détail des aspects"
                            + " spécifiques du poste.\n"
                            + "\n"
                            + "Nous vous remercions encore pour l'intérêt que vous avez porté à notre entreprise et nous sommes impatients de vous"
                            + " rencontrer.\n"
                            + "\n"
                            + "Cordialement,\n"
                            + "Mensieur " + nameRecruteur;
                    emailSender.sendEmail(mc.retrieveUserEmailByIdUser(selectedCondidature.getIdUtilisateur()), objetacc, messageaccepter);
                    System.out.println("mail succée");
                }

                // Show a confirmation message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Candidature mise à jour");
                alert.setHeaderText(null);
                alert.setContentText("TL'état de la candidature a été mis à jour pour : " + selectedEtat);
                alert.showAndWait();
                comboEtat.setValue(Etat.en_attente);
                comboEtat.setVisible(false);

                // Hide all other labels and set their text to empty
                lbNom.setVisible(false);
                lbNom.setText("");
                lbCv.setVisible(false);
                lbCv.setText("");
                lbLm.setVisible(false);
                lbLm.setText("");
                lbPor.setVisible(false);
                lbPor.setText("");
                lbPoste.setVisible(false);
                lbPoste.setText("");
                lbDate.setVisible(false);
                lbDate.setText("");
                lbEtat.setVisible(false);
                btApplay.setVisible(false);
            } else {
                // The etat is the same as the previous etat
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucune mise à jour n'a été remarquée");
                alert.setHeaderText(null);
                alert.setContentText("L'état de la candidature est déjà " + selectedEtat);
                alert.showAndWait();
            }
        } else {
            // No candidature or etat selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a candidature and an etat");
            alert.showAndWait();
        }
    }

    @FXML
    private void evaluerAction(ActionEvent event) {
         ObservableList<Condidature> condidatures = tableCan.getItems();

        // Iterate through each Condidature
        for (Condidature condidature : condidatures) {
            String cvPath = condidature.getCv();
            if (cvPath != null && !cvPath.isEmpty()) {
                List<String> cvContent = PDFReader.readPDFCv(cvPath);
                if (cvContent != null) {
                    System.out.println("******");
                    List<String> skills = cm.getSkillsByIdOffre(condidature.getIdOffre());
                    System.out.println("******");
                    int matchCount = 0;
                    // Split the CV content into individual words
                    List<String> cvWords = new ArrayList<>();
                    for (String line : cvContent) {
                        String[] wordsInLine = line.split("\\s+|\\p{Punct}");
                        for (String word : wordsInLine) {
                            if (!word.isEmpty()) {
                                cvWords.add(word);
                            }
                        }
                    }
                    // Compare each word in the CV content with the skills
                    for (String word : cvWords) {
                        for (String skill : skills) {
                            String[] skillWords = skill.split("\\s+|\\p{Punct}");
                            for (String skillWord : skillWords) {
                                if (word.equalsIgnoreCase(skillWord)) {
                                    matchCount++;
                                    break; // Move to the next word in the CV content
                                }
                            }
                        }
                    }
                    condidature.setMatchCount(matchCount);
                } else {
                    System.out.println("Failed to read the CV file for Condidature ID " + condidature.getIdC() + ": " + cvPath);
                }
            } else {
                System.out.println("CV file path is empty for the Condidature ID " + condidature.getIdC());
            }
        }
        // Sort the list of Condidatures based on the match count
        condidatures.sort(Comparator.comparingInt(Condidature::getMatchCount));
        // Create a new sorted list without the match count
        List<Condidature> sortedList = new ArrayList<>(condidatures);
        sortedList.forEach(condidature -> condidature.setMatchCount(0));
        // Clear the existing items in the table
        tableCan.getItems().clear();
        // Add the sorted Condidatures to the table
        tableCan.getItems().addAll(sortedList);
    }

    @FXML
    private void condidatureEnattent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RecruteurCandidature.fxml"));
            Parent root = loader.load();
            lbPoste.getScene().setRoot(root);
            RecruteurCandidatureController controller = loader.getController();
            String ids = iduser.getText();
            controller.setIdUser(ids);
            controller.initialize(null, null);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
