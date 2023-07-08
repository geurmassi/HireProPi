/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.tests;

import java.io.IOException;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

/**
 *
 * @author hadil ibenhajfraj
 */
public class TestFxmlHadil extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/RegisterFXML.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/LoginFXML.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestion des personnes");
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }}