package edu.connection.Mobile;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class NewFXMain extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/connection/Mobile/Home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home");
        primaryStage.show();

        Button playquizbtn = (Button) root.lookup("#playquizbtn");
        playquizbtn.setOnAction((ActionEvent event) -> {
            try {
                Stage thisStage = (Stage) playquizbtn.getScene().getWindow();
                thisStage.close();
                
                FXMLLoader fxmlLoader;
                fxmlLoader = new FXMLLoader(getClass().getResource("/edu/connection/Mobile/QuizCC.fxml"));
                Parent quizRoot = fxmlLoader.load();
                QuizCCController controller = fxmlLoader.getController(); // Get the controller instance
                Stage quizStage = new Stage();
                quizStage.setScene(new Scene(quizRoot));
                quizStage.setTitle("Quiz");
                quizStage.show();
            } catch (IOException e) {
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
