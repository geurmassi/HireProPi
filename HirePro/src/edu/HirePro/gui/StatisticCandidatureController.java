/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.gui;

import edu.HirePro.services.candidatureMETIEER;
import edu.HirePro.services.tNumCanParPos;
import edu.HirePro.services.tNumOffCanParPos;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class StatisticCandidatureController implements Initializable {

    @FXML
    private TableView<tNumCanParPos> tabNumCanParPos;
    @FXML
    private TableColumn<tNumCanParPos, Integer> cmNomPos;
    @FXML
    private TableColumn<tNumCanParPos, String> cmNumCan;
    @FXML
    private TableView<tNumOffCanParPos> tableauStatic2;
    @FXML
    private TableColumn<tNumOffCanParPos, String> columnOffreNumber;
    @FXML
    private TableColumn<tNumOffCanParPos, Integer> columnCandidatureNumber;
    @FXML
    private TableColumn<tNumOffCanParPos, Integer> columnPoste;
    @FXML
    private BarChart<String, Number> chartmetier1;
    @FXML
    private BarChart<String, Number> chartmetier2;
    
    private ObservableList<tNumCanParPos> rowDataList;
    private ObservableList<tNumOffCanParPos> rowPOCList;
    candidatureMETIEER cm = new candidatureMETIEER();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        rowDataList = FXCollections.observableList(cm.nombreCandidatureParPoste());
        cmNomPos.setCellValueFactory(new PropertyValueFactory<>("poste"));
        cmNumCan.setCellValueFactory(new PropertyValueFactory<>("candidatureCount"));
        tabNumCanParPos.setItems(rowDataList);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Candidature Count");
        rowDataList.stream().forEach((data) -> {
            series.getData().add(new XYChart.Data<>(data.getPoste(), data.getCandidatureCount()));
        });

        // Add the series to the chart
        chartmetier1.getData().add(series);

        rowPOCList = FXCollections.observableArrayList(cm.nombreCandidatureEtNumOffreParPoste());

        columnPoste.setCellValueFactory(new PropertyValueFactory<>("poste"));
        columnOffreNumber.setCellValueFactory(new PropertyValueFactory<>("offreCount"));
        columnCandidatureNumber.setCellValueFactory(new PropertyValueFactory<>("candidatureCount"));
        tableauStatic2.setItems(rowPOCList);
        XYChart.Series<String, Number> candidatureSeries = new XYChart.Series<>();
        candidatureSeries.setName("Candidature Count");
        XYChart.Series<String, Number> offreSeries = new XYChart.Series<>();
        offreSeries.setName("Offre Count");
        rowPOCList.stream().map((data) -> {
            candidatureSeries.getData().add(new XYChart.Data<>(data.getPoste(), data.getCandidatureCount()));
            return data;
        }).forEach((data) -> {
            offreSeries.getData().add(new XYChart.Data<>(data.getPoste(), data.getOffreCount()));
        });
        chartmetier2.getData().addAll(candidatureSeries, offreSeries);
        this.chartmetier2 = chartmetier2;
        // TODO
    }    

    @FXML
    private void Retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Condidature.fxml"));
            Parent root = loader.load();
            chartmetier2.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
