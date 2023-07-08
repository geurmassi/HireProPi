package edu.connection.Cloud;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private Button playquizbtn;

      private void playQuiz(ActionEvent event) throws IOException {
        Stage thisStage = (Stage) playquizbtn.getScene().getWindow();

        thisStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("QuizC.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Quiz");
        stage.show();
    }

}