/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hirepro.gui;

import com.itextpdf.text.DocumentException;
import edu.hirepro.entities.Etat;
import edu.hirepro.entities.Reclamation;
import edu.hirepro.pi.PDFCreator;
import edu.hirepro.services.reclamationMetier;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author samsung
 */
public class ReclamationVueController implements Initializable {

   @FXML
    private TableColumn<Reclamation, String> contenue;
    @FXML
    private TableColumn<Reclamation, String> objet;
    @FXML
    private TableColumn<Reclamation, Timestamp> time;
    @FXML
    private TableView<Reclamation> tableaurec;

    private ObservableList<Reclamation> reclamationInstance;
    @FXML
    private Label lbcount;
    @FXML
    private Button buttDesactiver;
    @FXML
    private Button buttonrep;
    @FXML
    private Label lbCon;
    @FXML
    private Label lbobj;

    Reclamation selectedReclamation;
  
    reclamationMetier rm = new reclamationMetier();
    @FXML
    private Label lbcount1;
    @FXML
    private TableColumn<Reclamation, Integer> idR;
    @FXML
    private TableColumn<Reclamation, Integer> idUser;
    @FXML
    private TableColumn<Reclamation, Integer> idUserReclame;
    @FXML
    private TableColumn<Reclamation, Etat> etat;
    @FXML
    private TableColumn<Reclamation, String> nomavecmail;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        reclamationInstance = FXCollections.observableList(rm.displayEntitiesVue());
        contenue.setCellValueFactory(new PropertyValueFactory<>("contenue"));
        objet.setCellValueFactory(new PropertyValueFactory<>("objet"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        idR.setCellValueFactory(new PropertyValueFactory<>("idR"));
        idUser.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        idUserReclame.setCellValueFactory(new PropertyValueFactory<>("idUserReclame"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        nomavecmail.setCellValueFactory(cellData -> {
            Reclamation reclamation = cellData.getValue();
            int idUserReclame = reclamation.getIdUserReclame();
            String fullNameWithEmail = rm.getFullNameById(idUserReclame);
            return new SimpleStringProperty(fullNameWithEmail);
        });
        tableaurec.setItems(reclamationInstance);
        lbobj.setVisible(false);
        lbCon.setVisible(false);
        
         tableaurec.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Retrieve the selected reclamation entity
                selectedReclamation = tableaurec.getSelectionModel().getSelectedItem();
                // Retrieve the data from the selected reclamation entity
                String selectedContenue = selectedReclamation.getContenue();
                String selectedObjet = selectedReclamation.getObjet();
                System.out.println(selectedReclamation);
                lbobj.setVisible(true);
                lbCon.setVisible(true);
                lbCon.setText("Continue   : " + selectedContenue);
                lbobj.setText("Objet      : " + selectedObjet);
                buttDesactiver.setVisible(true);
                buttonrep.setVisible(true);

            }
        });
        int count = rm.conterReclamation();
        lbcount.setText("Total Reclamation : " + count);
        int count1 = rm.conterReclamationVue();
        lbcount1.setText("Total Reclamation vue : " + count1);
    }    

    @FXML
    private void CreationPdf(ActionEvent event) {
        try {
            PDFCreator p = new PDFCreator();
            Path pdfFilePath = p.createPDF(tableaurec, "Liste de reclamation.pdf");
            java.io.File file = new java.io.File(pdfFilePath.toString());
            Desktop.getDesktop().open(file);
        } catch (DocumentException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @FXML
    private void goRecIn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReclamationInactive.fxml"));
            Parent root = loader.load();
            lbCon.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void goRecNonVue(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReclamationFXML.fxml"));
            Parent root = loader.load();
            lbCon.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
