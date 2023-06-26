package edu.connection.Cloud;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TitledPane;
import javafx.stage.FileChooser;

public class QuizCController implements Initializable {

    @FXML
    private TitledPane FTQ1;
    @FXML
    private TitledPane FTQ2;
    @FXML
    private TitledPane FTQ3;
    @FXML
    private TitledPane FTQ4;
    @FXML
    private CheckBox FT_R14;
    @FXML
    private CheckBox FT_R11;
    @FXML
    private CheckBox FT_R13;
    @FXML
    private CheckBox FT_R12;
    @FXML
    private CheckBox FT_R24;
    @FXML
    private CheckBox FT_R21;
    @FXML
    private CheckBox FT_R23;
    @FXML
    private CheckBox FT_R22;
    @FXML
    private CheckBox FT_R34;
    @FXML
    private CheckBox FT_R31;
    @FXML
    private CheckBox FT_R33;
    @FXML
    private CheckBox FT_R32;
    @FXML
    private CheckBox FT_R44;
    @FXML
    private CheckBox FT_R41;
    @FXML
    private CheckBox FT_R43;
    @FXML
    private CheckBox FT_R42;
    @FXML
    private Button FT_BUTTON;
    @FXML
    private Button checkAnswerBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FT_BUTTON.setOnAction(event -> displayScore());
        checkAnswerBtn.setDisable(true); // Disable the "checkAnswerBtn" button initially

        checkAnswerBtn.setOnAction(event -> showCorrectAnswer());
    }

    private void displayScore() {
        int totalScore = calculateScore();
        String level = getUserLevel();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz Result");
        alert.setHeaderText(null);
        alert.setContentText("Your score is: " + totalScore + "/4\n" + "Your level is: " + level);

        alert.showAndWait();

        checkAnswerBtn.setDisable(false); // Enable the "checkAnswerBtn" button
    }
    
    

    private int calculateScore() {
        int score = 0;

        if (FT_R14.isSelected()) {
            score++;
        }
        if (FT_R22.isSelected()) {
            score++;
        }
        if (FT_R31.isSelected()) {
            score++;
        }
        if (FT_R41.isSelected()) {
            score++;
        }

        return score;
    }

    private String getUserLevel() {
        int totalScore = calculateScore();
        String level;

        if (totalScore == 4) {
            level = "Professional: You have answered all fields correctly!";
        } else if (totalScore >= 3) {
            level = "Intermediate: You have answered correctly in most fields.";
        } else if (totalScore >= 1) {
            level = "Amateur: You have answered correctly in one or more fields.";
        } else {
            level = "Unknown: Your answers are not correct.";
        }

        return level;
    }

    private void showCorrectAnswer() {
    // Set the selected state of the checkboxes
    FT_R14.setSelected(true);
    FT_R11.setSelected(false);
    FT_R13.setSelected(false);
    FT_R12.setSelected(false);

    FT_R22.setSelected(true);
    FT_R21.setSelected(false);
    FT_R23.setSelected(false);
    FT_R24.setSelected(false);

    FT_R31.setSelected(true);
    FT_R32.setSelected(false);
    FT_R33.setSelected(false);
    FT_R34.setSelected(false);

    FT_R41.setSelected(true);
    FT_R42.setSelected(false);
    FT_R43.setSelected(false);
    FT_R44.setSelected(false);

    // Set the styles for correct answers
    FT_R14.setStyle("-fx-background-color: lightgreen;");
    FT_R22.setStyle("-fx-background-color: lightgreen;");
    FT_R31.setStyle("-fx-background-color: lightgreen;");
    FT_R41.setStyle("-fx-background-color: lightgreen;");

    int totalScore = calculateScore(); // Calculate the actual score

    generatePDF();
}


   private void generatePDF() {
    Document document = new Document();

    try {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("quiz_results.pdf");
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font font = FontFactory.getFont(FontFactory.COURIER, 16, Font.BOLD);
            Paragraph paragraph = new Paragraph("Quiz Results", font);
            document.add(paragraph);

            document.add(new Paragraph("\n"));

            Paragraph answersParagraph = new Paragraph("Correct Answers:", font);
            document.add(answersParagraph);

            document.add(new Paragraph("\n"));

            if (FT_R14.isSelected()) {
                Paragraph answer1 = new Paragraph("Q1: What is cloud computing?\nA type of software development framework\n(Correct)", font);
                document.add(answer1);
            } else {
                Paragraph answer1 = new Paragraph("Q1: What is cloud computing?\nA type of software development framework\n(Incorrect)", font);
                document.add(answer1);
            }

            document.add(new Paragraph("\n"));

            if (FT_R22.isSelected()) {
                Paragraph answer2 = new Paragraph("Q2: Which of the following is not a cloud computing service model?\nPlatform as a Service (PaaS)\n(Correct)", font);
                document.add(answer2);
            } else {
                Paragraph answer2 = new Paragraph("Q2: Which of the following is not a cloud computing service model?\nPlatform as a Service (PaaS)\n(Incorrect)", font);
                document.add(answer2);
            }

            document.add(new Paragraph("\n"));

            if (FT_R31.isSelected()) {
                Paragraph answer3 = new Paragraph("Q3: What is the primary benefit of cloud computing?\nIncreased security\n(Correct)", font);
                document.add(answer3);
            } else {
                Paragraph answer3 = new Paragraph("Q3: What is the primary benefit of cloud computing?\nIncreased security\n(Incorrect)", font);
                document.add(answer3);
            }

            document.add(new Paragraph("\n"));

            if (FT_R41.isSelected()) {
                Paragraph answer4 = new Paragraph("Q4: What is a virtual machine (VM) in cloud computing?\nA physical server hosted in a data center\n(Correct)", font);
                document.add(answer4);
            } else {
                Paragraph answer4 = new Paragraph("Q4: What is a virtual machine (VM) in cloud computing?\nA physical server hosted in a data center\n(Incorrect)", font);
                document.add(answer4);
            }

            document.close();

            System.out.println("PDF created successfully.");
        }
    } catch (DocumentException | IOException e) {
        e.printStackTrace();
    }
}

}
