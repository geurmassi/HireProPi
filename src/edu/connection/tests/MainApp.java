/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.tests;

import edu.connection.utils.Captcha;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    private String captcha;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Captcha Example");

        // Create UI elements
        Label captchaLabel = new Label();
        TextField userInputField = new TextField();
        Button verifyButton = new Button("Verify");

        // Generate and display a new captcha
        generateNewCaptcha(captchaLabel);

        // Set event handler for the verify button
        verifyButton.setOnAction(event -> {
            String userInput = userInputField.getText();

            // Check if the user input matches the captcha
            boolean isCaptchaValid = Captcha.authenticateCaptcha(userInput, captcha);
            if (isCaptchaValid) {
                System.out.println("Captcha is valid!");
                // TODO: Add your logic for a valid captcha
            } else {
                System.out.println("Captcha is invalid!");
                // TODO: Add your logic for an invalid captcha
            }

            // Generate and display a new captcha
            generateNewCaptcha(captchaLabel);
            userInputField.clear();
        });

        // Create the layout
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(captchaLabel, userInputField, verifyButton);

        // Set the scene and show the stage
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void generateNewCaptcha(Label captchaLabel) {
        // Generate a new captcha
        captcha = Captcha.generateCaptcha(6);

        // Update the captcha label
        captchaLabel.setText("Enter the captcha: " + captcha);
    }
}
