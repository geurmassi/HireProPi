package edu.connection.tests;

import edu.connection.gui.AddOffreFXMLController;
import edu.connection.gui.AfficherPosteController;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestFXMLPoste extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            String url = "../gui/AfficherPoste.fxml";
            URL realurl = getClass().getResource(url);
            FXMLLoader loader = new FXMLLoader(realurl);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            
            primaryStage.setScene(scene);
            primaryStage.setTitle("Create Poste");
            primaryStage.show();
            /*
            AfficherPosteController afficherPosteController = loader.getController();
            AfficherPosteController.setPrimaryStage(primaryStage);*/
        } catch (IOException ex) {
            System.out.println("Error loading FXML file");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
