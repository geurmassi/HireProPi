package edu.connection.AdminAddTest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.SQLException;

import edu.connection.services.TestCRUD;

public class QuestionCountFXMLController implements Initializable {

    @FXML
    private BarChart<String, Number> testChart;

    @FXML
    private BarChart<String, Number> questionChart;

    @FXML
    private GridPane rootGridPane;

    @Override
public void initialize(URL url, ResourceBundle rb) {
    try {
        final CategoryAxis testXAxis = new CategoryAxis();
        final NumberAxis testYAxis = new NumberAxis();
        testXAxis.setLabel("Test ID");
        testYAxis.setLabel("Test Count");

        BarChart<String, Number> testBarChart = new BarChart<>(testXAxis, testYAxis);
        testBarChart.setTitle("Test Count");

        ObservableList<XYChart.Data<String, Number>> testData = retrieveTestDataFromDatabase();

        XYChart.Series<String, Number> testSeries = new XYChart.Series<>(testData);
        testSeries.setName("Test Count");

        testChart = testBarChart; // Assign the created chart to the testChart variable
        testChart.setTitle("Test Count");
        testChart.getData().add(testSeries);

        setChartProperties(testChart);

        final CategoryAxis questionXAxis = new CategoryAxis();
        final NumberAxis questionYAxis = new NumberAxis();
        questionXAxis.setLabel("Test ID");
        questionYAxis.setLabel("Question Count");

        BarChart<String, Number> questionBarChart = new BarChart<>(questionXAxis, questionYAxis);
        questionBarChart.setTitle("Question Count");

        ObservableList<XYChart.Data<String, Number>> questionData = FXCollections.observableArrayList();
        questionData.add(new XYChart.Data<>("Test 1", 10));
        questionData.add(new XYChart.Data<>("Test 2", 15));
        questionData.add(new XYChart.Data<>("Test 3", 7));
        questionData.add(new XYChart.Data<>("Test 4", 12));

        XYChart.Series<String, Number> questionSeries = new XYChart.Series<>(questionData);
        questionSeries.setName("Question Count");

        questionChart = questionBarChart; // Assign the created chart to the questionChart variable
        questionChart.setTitle("Question Count");
        questionChart.getData().add(questionSeries);

        setChartProperties(questionChart);

        GridPane.setConstraints(testChart, 0, 0);
        GridPane.setConstraints(questionChart, 1, 0);
        rootGridPane.getChildren().addAll(testChart, questionChart);
    } catch (Exception e) {
    }
}

private ObservableList<XYChart.Data<String, Number>> retrieveTestDataFromDatabase() throws SQLException {
    ObservableList<XYChart.Data<String, Number>> testData = FXCollections.observableArrayList();

    // Retrieve test count data from the database
    TestCRUD testCRUD = new TestCRUD();
    testCRUD.displayEntities().stream().forEach((test) -> {
        int testCount = test.getTestCount(); // Assuming there is a method to retrieve the test count
        testData.add(new XYChart.Data<>(String.valueOf(test.getId()), testCount));
        });

    return testData;
}

private void setChartProperties(BarChart<String, Number> chart) {
    chart.setAnimated(false);
    chart.setBarGap(3);
    chart.setCategoryGap(20);
    chart.setVerticalGridLinesVisible(false);
    chart.setHorizontalGridLinesVisible(false);
}

}