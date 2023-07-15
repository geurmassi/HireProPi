/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.gui;

import edu.HirePro.entities.Offre;
import edu.HirePro.services.OffreCRUD;
import edu.HirePro.services.candidatureMETIEER;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class AfficheOffreEtPosteController implements Initializable {

    @FXML
    private TableColumn<Offre, Integer> idOffre;
    @FXML
    private TableColumn<Offre, String> lieuT;
    @FXML
    private TableColumn<Offre, String> descriptif;
    @FXML
    private TableColumn<Offre, Date> dateDebutOffre;
    @FXML
    private TableColumn<Offre, Date> dateFinOffre;
    @FXML
    private TableColumn<Offre, String> typeEmploi;
    @FXML
    private TableColumn<Offre, String> TypeLieuTravail;
    @FXML
    private TableColumn<Offre, Integer> idUser;
    @FXML
    private TableColumn<Offre, Integer> question;
    @FXML
    private TableView<Offre> tableFP;
    @FXML
    private TableColumn<Offre, Integer> idposte;
    @FXML
    private Label idoffrechoisistring;

    private int idoffrechoisi;
    @FXML
    private Label lbname;
    @FXML
    private Label lbiduser;
    int idUtilisateur;

    public void setLbname(String nom) {
        lbname.setText(nom);
    }

    public void setLbIdUser(String iduser) {
        lbiduser.setText(iduser);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        OffreCRUD rc = new OffreCRUD();
        candidatureMETIEER cm = new candidatureMETIEER();
        ObservableList<Offre> liste = FXCollections.observableList(rc.displayEntities());
        idOffre.setCellValueFactory(new PropertyValueFactory<>("idOffre"));
        lieuT.setCellValueFactory(new PropertyValueFactory<>("lieuT"));
        descriptif.setCellValueFactory(new PropertyValueFactory<>("descriptif"));
        dateDebutOffre.setCellValueFactory(new PropertyValueFactory<>("dateDebutOffre"));
        dateFinOffre.setCellValueFactory(new PropertyValueFactory<>("dateFinOffre"));
        typeEmploi.setCellValueFactory(new PropertyValueFactory<>("typeEmploi"));
        TypeLieuTravail.setCellValueFactory(new PropertyValueFactory<>("TypeLieuTravail"));
        idUser.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        idposte.setCellValueFactory(new PropertyValueFactory<>("idposte"));
        question.setCellValueFactory(new PropertyValueFactory<>("question"));

        tableFP.setItems(liste);
        tableFP.setRowFactory(tv -> {
            TableRow<Offre> myRow = new TableRow<>();
            try {
                idUtilisateur = Integer.parseInt(idUser.getText().trim());
                // Rest of the code
            } catch (NumberFormatException e) {
                // Handle the case when the text field value is not a valid integer
                System.out.println("Error");
            }
            myRow.setOnMouseClicked(eventv -> {
                if (eventv.getClickCount() == 2 && (!myRow.isEmpty())) {
                    int myIndex = tableFP.getSelectionModel().getSelectedIndex();
                    Offre selectedOffre = tableFP.getItems().get(myIndex);

                    // Get the current date
                    LocalDate currentDate = LocalDate.now();

                    // Check if the current date is after dateFinOffre
                    if (currentDate.isAfter(selectedOffre.getDateFinOffre().toLocalDate())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Offre expirée");
                        alert.setHeaderText(null);
                        alert.setContentText("L'offre sélectionnée a expiré.");
                        alert.showAndWait();
                    } else if (cm.checkCondidatureExists(selectedOffre.getIdOffre(), idUtilisateur)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Candidature expirée");
                        alert.setHeaderText(null);
                        alert.setContentText("vous avez déja postulé");
                        alert.showAndWait();
                    } else {
                        String id_rdv = String.valueOf(selectedOffre.getIdOffre());
                        idoffrechoisistring.setText(id_rdv);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterCandidature.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                        idoffrechoisistring.getScene().setRoot(root);
                        AjouterCandidatureController acc = loader.getController();
                        acc.setLbIdOffre(idoffrechoisistring.getText());
                        acc.setLbiduser(lbiduser.getText());
                    }
                }
            });
            return myRow;
        });

        // TODO
    }

}
