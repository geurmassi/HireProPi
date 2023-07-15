/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.tests;

import edu.connection.gui.AfficherOffreController;
import edu.connection.gui.LoginFXMLController;
import edu.connection.services.UserCRUD;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

/**
 *
 * @author hadil ibenhajfraj
 */
public class TestFXMLHadil extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/LoginFXML.fxml"));
       // FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AfficherUser.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hire Pro");
        LoginFXMLController loginController = loader.getController();
      //  AfficherOffreController afficher = loader.getController();
        primaryStage.show();
 primaryStage.setOnCloseRequest(e -> {
        // Get the user's email from the login controller (adjust this according to your code)
        //String email = loginController.getEmail();

        // Perform logout operations, e.g., update the user's status
        UserCRUD userService = new UserCRUD();
        //userService.updateUserInActiveStatus(email, 0);

        Platform.exit();
    });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
