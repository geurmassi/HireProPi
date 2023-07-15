package edu.connection.Quiz;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StandardTemplateController implements Initializable {
    private BigDecimal decimalValue;

    @FXML
    private TitledPane question;

    @FXML
    private CheckBox txtrep1;

    @FXML
    private CheckBox txtrep2;

    @FXML
    private CheckBox txtrep3;

    @FXML
    private CheckBox txtrep4;

    @FXML
    private Button FT_Next;

    @FXML
    private Button FT_Submit;

    @FXML
    private Button PrintPDF;

    @FXML
    private ProgressBar progressBar;

    private int currentQuestionIndex;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private int clickCount;

    private int totalQuestions; // Set the total number of questions in your quiz
    private int questionsAnswered; // Track the number of questions answered
    private BigDecimal progress; // The BigDecimal class gives its user complete control over rounding behavior

    @FXML
    private TextArea pourcentage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clickCount = 1;
        pourcentage.setText("0%"); // Set initial progress percentage to 0%
        progressBar.setProgress(0); // Set initial progress to 0

        FT_Submit.setOnAction(this::handleSubmit);
        PrintPDF.setDisable(true); // Disable the "PrintPDF" button initially

        PrintPDF.setOnAction(this::handlePrintPDF);

        totalQuestions = 4; // Set the total number of questions in your quiz
        questionsAnswered = 0; // Initialize the number of questions answered to zero
        progress = BigDecimal.ZERO;

        // Disable copy functionality for the question text area
        question.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && (event.getCode() == KeyCode.C || event.getCode() == KeyCode.INSERT)) {
                event.consume();
            }
        });

        // Disable copy functionality for the checkboxes
        txtrep1.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && (event.getCode() == KeyCode.C || event.getCode() == KeyCode.INSERT)) {
                event.consume();
            }
        });
        txtrep2.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && (event.getCode() == KeyCode.C || event.getCode() == KeyCode.INSERT)) {
                event.consume();
            }
        });
        txtrep3.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && (event.getCode() == KeyCode.C || event.getCode() == KeyCode.INSERT)) {
                event.consume();
            }
        });
        txtrep4.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && (event.getCode() == KeyCode.C || event.getCode() == KeyCode.INSERT)) {
                event.consume();
            }
        });

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hirepro", "root", "");
            preparedStatement = connection.prepareStatement("SELECT question, reponse_1, reponse_2, reponse_3, reponse_4, rep_vrai " +
                    "FROM question " +
                    "JOIN test ON question.idTest = test.idTest " +
                    "WHERE test.idTest = 1");
            resultSet = preparedStatement.executeQuery();

            // Move to the first question in the result set and update UI
            if (resultSet != null && resultSet.next()) {
                currentQuestionIndex++;
                updateQuestionUI(resultSet);
            } else {
                // No questions found in the result set, display a message or perform another action
            }

            // Disable UI elements initially
            disableUI();
        } catch (SQLException e) {
            // Handle the exception appropriately
        }
    }

    @FXML
    private void handleNextQuestion(ActionEvent event) {
        try {
            if (resultSet != null && resultSet.next()) {
                currentQuestionIndex++;
                updateQuestionUI(resultSet);
                incrementQuestionsAnswered(); // Increment the number of questions answered

                if (questionsAnswered >= totalQuestions) {
                    FT_Next.setDisable(true); // Disable the "Next" button if all questions are answered
                    FT_Submit.setDisable(false); // Enable the "Submit" button
                    PrintPDF.setDisable(false); // Enable the "Print PDF" button
                }
            } else {
                FT_Next.setDisable(true);
                FT_Submit.setDisable(false);
                PrintPDF.setDisable(false);
            }
        } catch (SQLException e) {
            // Handle the exception appropriately
        }
    }

    private void disableUI() {
        FT_Next.setDisable(false); // Enable the FT_Next button
        FT_Submit.setDisable(true);
        PrintPDF.setDisable(true);
    }

    private void enableUI() {
        FT_Next.setDisable(false);
        PrintPDF.setDisable(false);
        FT_Submit.setDisable(false);
    }

    private void updateQuestionUI(ResultSet resultSet) throws SQLException {
        String questionText = resultSet.getString("question");
        String reponse1 = resultSet.getString("reponse_1");
        String reponse2 = resultSet.getString("reponse_2");
        String reponse3 = resultSet.getString("reponse_3");
        String reponse4 = resultSet.getString("reponse_4");
        String repVrai = resultSet.getString("rep_vrai");

        question.setText(questionText != null ? questionText : "");
        txtrep1.setText(reponse1 != null ? reponse1 : "");
        txtrep2.setText(reponse2 != null ? reponse2 : "");
        txtrep3.setText(reponse3 != null ? reponse3 : "");
        txtrep4.setText(reponse4 != null ? reponse4 : "");

        // Clear the selection of all checkboxes
        txtrep1.setSelected(false);
        txtrep2.setSelected(false);
        txtrep3.setSelected(false);
        txtrep4.setSelected(false);

        // Enable checkboxes for multiple choice
        txtrep1.setDisable(false);
        txtrep2.setDisable(false);
        txtrep3.setDisable(false);
        txtrep4.setDisable(false);

        // Enable UI elements after updating the question
        enableUI();
    }

    @FXML
    private void txtrep1(ActionEvent event) {
        if (txtrep1.isSelected()) {
            txtrep2.setSelected(false);
            txtrep3.setSelected(false);
            txtrep4.setSelected(false);
        }
    }

    @FXML
    private void txtrep2(ActionEvent event) {
        if (txtrep2.isSelected()) {
            txtrep1.setSelected(false);
            txtrep3.setSelected(false);
            txtrep4.setSelected(false);
        }
    }

    @FXML
    private void txtrep3(ActionEvent event) {
        if (txtrep3.isSelected()) {
            txtrep1.setSelected(false);
            txtrep2.setSelected(false);
            txtrep4.setSelected(false);
        }
    }

    @FXML
    private void txtrep4(ActionEvent event) {
        if (txtrep4.isSelected()) {
            txtrep1.setSelected(false);
            txtrep2.setSelected(false);
            txtrep3.setSelected(false);
        }
    }

    @FXML
    public void updateProgressBar() {
        if (questionsAnswered == 0) {
            progressBar.setProgress(0.25); // Set the progress to 25% for the first question
            pourcentage.setText("25%");
        } else if (questionsAnswered == totalQuestions) {
            progressBar.setProgress(1.0); // Set the progress to 100% when all questions are answered
            pourcentage.setText("100%");
            FT_Next.setDisable(true); // Disable the "Next" button if all questions are answered
            FT_Submit.setDisable(false); // Enable the "Submit" button
            PrintPDF.setDisable(false); // Enable the "Print PDF" button
        } else {
            double stepSize = 0.75 / (totalQuestions - 1); // Calculate the step size for the remaining questions
            double progressValue = 0.25 + (stepSize * questionsAnswered); // Calculate the progress value
            progressBar.setProgress(progressValue);
            int progressPercentage = (int) Math.round(progressValue * 100);
            pourcentage.setText(progressPercentage + "%");
            FT_Next.setDisable(false); // Enable the "Next" button if there are more questions
            FT_Submit.setDisable(true); // Disable the "Submit" button
            PrintPDF.setDisable(true); // Disable the "Print PDF" button
        }
    }

    private void incrementQuestionsAnswered() {
        questionsAnswered++;
        updateProgressBar();
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        int totalScore = calculateScore();
        String level = getUserLevel();

        displayScore();
        PrintPDF.setDisable(false); // Enable the "PRINT PDF" button

        updateProgressBar(); // Add this line
    }

    @FXML
    private void handlePrintPDF(ActionEvent event) {
        try {
            // Retrieve data from the database
            List<String> questionData = retrieveQuestionDataFromDatabase(); // Replace with your own method for retrieving the question data

            // Create a new Document
            Document document = new Document();

            // Create a PdfWriter instance to write the document to a file
            PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\ASUS\\Dropbox\\PC\\Documents\\printNizar\\output.pdf"));

            // Open the Document
            document.open();

            // Process the question data and add it to the Document
            for (String question : questionData) {
                // Add the question to the Document
                document.add(new Paragraph(question));
                document.add(new Paragraph("")); // Add empty line for separation
            }

            // Close the Document
            document.close();

            // Show a success message to the user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Print PDF");
            alert.setHeaderText(null);
            alert.setContentText("PDF printed successfully!");
            alert.showAndWait();
        } catch (IOException | DocumentException e) {
            // Handle the exceptions appropriately
            e.printStackTrace();
        }
    }

    private List<String> retrieveQuestionDataFromDatabase() {
    List<String> questionData = new ArrayList<>();

    try {
        // Establish a database connection
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hirePro", "root", "");

        // Prepare a SQL query
        PreparedStatement statement = connection.prepareStatement("SELECT question FROM question");

        // Execute the query and retrieve the result set
        ResultSet resultSet = statement.executeQuery();

        // Process the result set and populate the questionData list
        while (resultSet.next()) {
            String questionText = resultSet.getString("question");
            questionData.add(questionText);
        }

        // Close the result set, statement, and connection
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately
    }

    return questionData;
}


    private void questionAnswered() {
        incrementQuestionsAnswered();
        updateProgressBar(); // Add this line
        // Additional logic for handling the answered question, if needed
    }

    private void displayScore() {
        int totalScore = calculateScore();
        String level = getUserLevel();

        // Create an Alert to display the score
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz Score");
        alert.setHeaderText("Your Score: " + totalScore);
        alert.setContentText("Level: " + level);
        alert.showAndWait();

        PrintPDF.setDisable(false); // Enable the "PRINT PDF" button
    }

    private int calculateScore() {
        int score = 0;

        if (txtrep1.isSelected()) {
            // Increase score if txtrep1 is selected
            score++;
        }

        if (txtrep2.isSelected()) {
            // Increase score if txtrep2 is selected
            score++;
        }

        if (txtrep3.isSelected()) {
            // Increase score if txtrep3 is selected
            score++;
        }

        if (txtrep4.isSelected()) {
            // Increase score if txtrep4 is selected
            score++;
        }

        return score;
    }

    private String getUserLevel() {
        int totalScore = calculateScore();
        String level;

        if (totalScore == 4) {
            level = "Professional: You have answered all fields correctly!";
        } else if (totalScore >= 2) {
            level = "Intermediate: You have answered correctly in some fields.";
        } else if (totalScore >= 1) {
            level = "Amateur: You have answered correctly in one field.";
        } else {
            level = "Unknown: Your answers are not correct.";
        }

        return level;
    }

    @FXML
    private void FT_Submit(ActionEvent event) {
    }

    @FXML
    private void pourcentage(MouseEvent event) {
    }

    @FXML
    private void PrintPDF(ActionEvent event) {
        try {
            // Retrieve data from the database
            List<String> questionData = retrieveQuestionDataFromDatabase(); // Replace with your own method for retrieving the question data

            // Create a new Document
            Document document = new Document();

            // Create a PdfWriter instance to write the document to a file
            PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Dell/Documents/NetBeansProjects/HirePro/output.pdf"));

            // Open the Document
            document.open();

            // Process the question data and add it to the Document
            for (String question : questionData) {
                // Add the question to the Document
                document.add(new Paragraph(question));
                document.add(new Paragraph("")); // Add empty line for separation
            }

            // Close the Document
            document.close();

            // Show a success message to the user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Print PDF");
            alert.setHeaderText(null);
            alert.setContentText("PDF printed successfully!");
            alert.showAndWait();
        } catch (IOException | DocumentException e) {
            // Handle the exceptions appropriately
            e.printStackTrace();
        }
    }
}
