package edu.connection.Web;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
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
        FT_BUTTON.setOnAction(event -> displayScore());
        checkAnswerBtn.setDisable(true); // Disable the "checkAnswerBtn" button initially

        checkAnswerBtn.setOnAction(event -> {
            try {
                showCorrectAnswer();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(QuizCController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(QuizCController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
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

        if (FT_R12.isSelected() && FT_R23.isSelected() && FT_R33.isSelected() && FT_R43.isSelected()) {
            score = 4;
        } else if ((FT_R12.isSelected() && FT_R23.isSelected() && FT_R33.isSelected()) || (FT_R12.isSelected() && FT_R23.isSelected() && FT_R43.isSelected())
                || (FT_R12.isSelected() && FT_R33.isSelected() && FT_R43.isSelected()) || (FT_R23.isSelected() && FT_R33.isSelected() && FT_R43.isSelected())) {
            score = 3;
        } else if ((FT_R12.isSelected() && FT_R23.isSelected()) || (FT_R12.isSelected() && FT_R33.isSelected()) || (FT_R12.isSelected() && FT_R43.isSelected())
                || (FT_R23.isSelected() && FT_R33.isSelected()) || (FT_R23.isSelected() && FT_R43.isSelected()) || (FT_R33.isSelected() && FT_R43.isSelected())) {
            score = 2;
        } else if (FT_R12.isSelected() || FT_R23.isSelected() || FT_R33.isSelected() || FT_R43.isSelected()) {
            score = 1;
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

    private void showCorrectAnswer() throws FileNotFoundException, DocumentException {
        FT_R12.setSelected(true);
        FT_R12.setStyle("-fx-background-color: green;");
        FT_R23.setSelected(true);
        FT_R23.setStyle("-fx-background-color: green;");
        FT_R33.setSelected(true);
        FT_R33.setStyle("-fx-background-color: green;");
        FT_R43.setSelected(true);
        FT_R43.setStyle("-fx-background-color: green;");
        String totalScore = null;
        generatePDF(calculateScore(), getUserLevel(), "                                  ", totalScore, "Java Fundamentals", "Certificate of Completion");
    }

    private void generatePDF(int totalScore, String level, String UserName, String quizScore, String quizName, String testName) throws FileNotFoundException, DocumentException {
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
                    Image backgroundImage = Image.getInstance("C:\\Users\\Dell\\HireProPi\\cert.jpg");
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
