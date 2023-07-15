/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.gui;

import com.itextpdf.text.DocumentException;
import edu.HirePro.api.PDFCreatorAllCandidatures;
import edu.HirePro.api.PDFViewer;
import edu.HirePro.entities.Condidature;
import edu.HirePro.services.CondidatureCRUD;
import edu.HirePro.services.candidatureMETIEER;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class CondidatureController implements Initializable {

    private ObservableList<Condidature> liste;

    @FXML
    private TableView<Condidature> tbcan;

    @FXML
    private TableColumn<Condidature, Integer> tcIdc;
    @FXML
    private TableColumn<Condidature, String> tcCv;
    @FXML
    private TableColumn<Condidature, String> tcLm;
    @FXML
    private TableColumn<Condidature, Timestamp> tcD;
    @FXML
    private TableColumn<Condidature, Integer> tcU;
    @FXML
    private TableColumn<Condidature, Integer> tcO;
    @FXML
    private TableColumn<Condidature, String> tcP;
    @FXML
    private TableColumn<Condidature, String> tcUserName;
    @FXML
    private TableColumn<Condidature, String> tcOfferName;
    @FXML
    private Label lbCount;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CondidatureCRUD cc = new CondidatureCRUD();
        liste = FXCollections.observableList(cc.displayEntities());
        tcIdc.setCellValueFactory(new PropertyValueFactory<>("idC"));

        tcCv.setCellValueFactory(cellData -> {
            String filePath = cellData.getValue().getCv();
            String fileName = "";
            if (filePath != null && !filePath.isEmpty()) {
                File file = new File(filePath);
                fileName = file.getName();
            }
            return new SimpleStringProperty(fileName);
        });

        tcCv.setCellFactory(column -> {
            return new TableCell<Condidature, String>() {
                private final Hyperlink hyperlink = new Hyperlink();

                @Override
                protected void updateItem(String cv, boolean empty) {
                    super.updateItem(cv, empty);
                    if (empty || cv == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        hyperlink.setText(cv); // Set the text of the hyperlink to the CV file name
                        hyperlink.setOnAction(event -> {
                            String cvFilePath = cv;
                            System.out.println("CV file path: " + cvFilePath);
                            if (!cvFilePath.isEmpty()) {
                                File file = new File(cvFilePath);
                                String fileName = file.getName();
                                PDFViewer.openPDFCv(fileName);
                                System.out.println("CV clicked: " + cv);
                            }
                        });
                        setGraphic(hyperlink);
                        setText(null);
                    }
                }
            };
        });

        tcLm.setCellValueFactory(cellData -> {
            String filePath = cellData.getValue().getLettreMotivation();
            String fileName = "";
            if (filePath != null && !filePath.isEmpty()) {
                File file = new File(filePath);
                fileName = file.getName();
            }
            return new SimpleStringProperty(fileName);
        });

        tcLm.setCellFactory(column -> {
            return new TableCell<Condidature, String>() {
                private final Hyperlink hyperlink = new Hyperlink();

                @Override
                protected void updateItem(String lettreMotivation, boolean empty) {
                    super.updateItem(lettreMotivation, empty);

                    if (empty || lettreMotivation == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        hyperlink.setText(lettreMotivation); // Set the text of the hyperlink to the CV file name
                        hyperlink.setOnAction(event -> {
                            String lmFilePath = lettreMotivation;
                            System.out.println("lettre de motivation file path: " + lmFilePath);
                            if (!lmFilePath.isEmpty()) {
                                File file = new File(lmFilePath);
                                String fileName = file.getName();
                                PDFViewer.openPDFLm(fileName);
                                System.out.println("lettre de motivation" + lettreMotivation);
                            }
                        });
                        setGraphic(hyperlink);
                        setText(null);
                    }
                }
            };
        });

        tcD.setCellValueFactory(new PropertyValueFactory<>("dateCandidature"));
        tcO.setCellValueFactory(new PropertyValueFactory<>("idOffre"));
        tcU.setCellValueFactory(new PropertyValueFactory<>("idUtilisateur"));
        tcP.setCellValueFactory(new PropertyValueFactory<>("portfolio"));
        tcP.setCellFactory(column -> {
            return new TableCell<Condidature, String>() {
                private final Hyperlink hyperlink = new Hyperlink();

                @Override
                protected void updateItem(String portfolio, boolean empty) {
                    super.updateItem(portfolio, empty);

                    if (empty || portfolio == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        hyperlink.setText(portfolio); // Set the text of the hyperlink to the portfolio value
                        hyperlink.setOnAction(event -> {
                            // Open the portfolio link in a browser
                            try {
                                Desktop.getDesktop().browse(new URI(portfolio));
                            } catch (IOException | URISyntaxException e) {
                                System.out.println(e.getMessage());
                            }
                        });
                        setGraphic(hyperlink);
                        setText(null);
                    }
                }
            };
        });

        candidatureMETIEER cm = new candidatureMETIEER();
        tcUserName.setCellValueFactory(cellData -> {
            int id = cellData.getValue().getIdUtilisateur();
            String name = cm.rechercheNameUserbyId(id);
            return new SimpleStringProperty(name);
        });

        tcOfferName.setCellValueFactory(cellData -> {
            int id = cellData.getValue().getIdOffre();
            String posteName = cm.recherchePosteNameByIdOffre(id);
            return new SimpleStringProperty(posteName);
        });

        tbcan.setItems(liste);
        int count = cm.conterCondidature();
        lbCount.setText("Total Condidature : " + count);

    }

    private void loadReclamations() {
        // Retrieve the reclamation entities from the data source (e.g., CondidatureCRUD)
        CondidatureCRUD cc = new CondidatureCRUD();
        List<Condidature> candidatures = cc.displayEntities();
        // Clear the ObservableList and add the retrieved entities
        liste.clear();
        liste.addAll(candidatures);
    }

    @FXML
    private void crationPdfAction(ActionEvent event) {
        try {
            PDFCreatorAllCandidatures p = new PDFCreatorAllCandidatures();
            Path pdfFilePath = p.createPDF(tbcan, "Liste de candidature.pdf");
            File file = new File(pdfFilePath.toString());
            Desktop.getDesktop().open(file);
        } catch (DocumentException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void goStatistique(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StatisticCandidature.fxml"));
            Parent root = loader.load();
            tbcan.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
