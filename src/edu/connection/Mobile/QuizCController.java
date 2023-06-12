package edu.connection.Mobile;



import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TitledPane;

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

        if (FT_R14.isSelected() && FT_R22.isSelected() && FT_R31.isSelected() && FT_R41.isSelected()) {
            score = 4;
        } else if ((FT_R14.isSelected() && FT_R22.isSelected() && FT_R31.isSelected()) || (FT_R14.isSelected() && FT_R22.isSelected() && FT_R41.isSelected())
                || (FT_R14.isSelected() && FT_R31.isSelected() && FT_R41.isSelected()) || (FT_R22.isSelected() && FT_R31.isSelected() && FT_R41.isSelected())) {
            score = 3;
        } else if ((FT_R14.isSelected() && FT_R22.isSelected()) || (FT_R14.isSelected() && FT_R31.isSelected()) || (FT_R14.isSelected() && FT_R41.isSelected())
                || (FT_R22.isSelected() && FT_R31.isSelected()) || (FT_R22.isSelected() && FT_R41.isSelected()) || (FT_R31.isSelected() && FT_R41.isSelected())) {
            score = 2;
        } else if (FT_R14.isSelected() || FT_R22.isSelected() || FT_R31.isSelected() || FT_R41.isSelected()) {
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

    private void showCorrectAnswer() {
        FT_R14.setSelected(true);
        FT_R14.setStyle("-fx-background-color: green;");
        FT_R22.setSelected(true);
        FT_R22.setStyle("-fx-background-color: green;");
        FT_R31.setSelected(true);
        FT_R31.setStyle("-fx-background-color: green;");
        FT_R41.setSelected(true);
        FT_R41.setStyle("-fx-background-color: green;");
    }
}
