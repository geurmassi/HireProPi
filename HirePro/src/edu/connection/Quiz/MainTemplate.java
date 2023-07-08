package edu.connection.Quiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainTemplate extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/connection/Quiz/QT.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Test Quiz");
        primaryStage.show();
        
        NotificationAPI notificationAPI = new NotificationAPI();
        String recipientEmail = "nizar.mlaouihi@gmail.com"; // Replace with the actual recipient's email address
        notificationAPI.sendNotification("Hello, World!", recipientEmail);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
