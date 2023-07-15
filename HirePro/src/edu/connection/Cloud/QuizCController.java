package edu.connection.Cloud;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TitledPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        FT_BUTTON.setOnAction(event -> {
            try {
                displayScore();
            } catch (FileNotFoundException | DocumentException ex) {
                Logger.getLogger(QuizCController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        checkAnswerBtn.setDisable(true); // Disable the "checkAnswerBtn" button initially

        checkAnswerBtn.setOnAction(event -> {
            try {
                showCorrectAnswer();
                checkAnswerBtn.setDisable(true); // Disable the "checkAnswerBtn" button after showing the correct answers
            } catch (FileNotFoundException | DocumentException ex) {
                Logger.getLogger(QuizCController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void displayScore() throws FileNotFoundException, DocumentException {
        int totalScore = calculateScore();
        String level = getUserLevel();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz Result");
        alert.setHeaderText(null);
        alert.setContentText("Your score is: " + totalScore + "/4\n" + "Your level is: " + level);

        alert.showAndWait();

        checkAnswerBtn.setDisable(false); // Enable the "checkAnswerBtn" button

        // Generate the PDF certificate with the initial scores
        generatePDF(totalScore, level, "        ", "quizScore", "quizName", "testName");
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

    private void showCorrectAnswer() throws FileNotFoundException, DocumentException {
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
        String level = getUserLevel(); // Get the user level

        String UserName = "                                                    \n                                   \n                                                                     ";
        String quizScore = totalScore + "/4";
        String quizName = "Cloud Computing";
        String testName = "Final Quiz";

        generatePDF(totalScore, level, UserName, quizScore, quizName, testName); // Generate the PDF with updated values
    }

    public void generatePDF(int totalScore, String level, String UserName, String quizScore, String quizName, String testName) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("certificate.pdf");
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();

                // Add certificate background image
                try {
                    Image backgroundImage = Image.getInstance("D:\\esprit\\projet java_mobile\\IntegrationGitHubFXML\\hireProFinale\\HireProPi\\HirePro\\cert.jpg");
                    backgroundImage.setAbsolutePosition(0, 0);
                    backgroundImage.scaleAbsolute(PageSize.A4);
                    document.add(backgroundImage);
                } catch (IOException e) {
                }

                // Create a black border for the certificate frame
                PdfContentByte contentByte = writer.getDirectContent();
                contentByte.setColorStroke(BaseColor.BLACK);
                contentByte.setLineWidth(3f);

                float frameMargin = 50f;
                float frameWidth = PageSize.A4.getWidth() - 2 * frameMargin;
                float frameHeight = PageSize.A4.getHeight() - 2 * frameMargin;

                contentByte.rectangle(frameMargin, frameMargin, frameWidth, frameHeight);
                contentByte.stroke();

                // Add certificate title
                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 48, BaseColor.BLACK);
                Paragraph titleParagraph = new Paragraph("", titleFont); // Empty title
                titleParagraph.setAlignment(Element.ALIGN_CENTER);
                titleParagraph.setSpacingAfter(100f);
                document.add(titleParagraph);

                // Add user details
                Font detailsFont = FontFactory.getFont(FontFactory.HELVETICA, 24, BaseColor.BLACK);

                Paragraph nameParagraph = new Paragraph("\n                                                   \n                                               " + UserName, detailsFont);
                nameParagraph.setAlignment(Element.ALIGN_CENTER);
                nameParagraph.setSpacingAfter(20f);
                document.add(nameParagraph);

                Paragraph scoreParagraph = new Paragraph("Quiz Score: " + quizScore, detailsFont);
                scoreParagraph.setAlignment(Element.ALIGN_CENTER);
                scoreParagraph.setSpacingAfter(20f);
                document.add(scoreParagraph);

                Paragraph levelParagraph = new Paragraph("Level: \n  " + level, detailsFont);
                levelParagraph.setAlignment(Element.ALIGN_CENTER);
                levelParagraph.setSpacingAfter(20f);
                document.add(levelParagraph);

                Paragraph quizNameParagraph = new Paragraph("Quiz Name: " + quizName, detailsFont);
                quizNameParagraph.setAlignment(Element.ALIGN_CENTER);
                quizNameParagraph.setSpacingAfter(20f);
                document.add(quizNameParagraph);

                Paragraph testNameParagraph = new Paragraph("Test Name: " + testName, detailsFont);
                testNameParagraph.setAlignment(Element.ALIGN_CENTER);
                document.add(testNameParagraph);

                document.close();

                System.out.println("Certificate generated successfully.");
            } catch (DocumentException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
