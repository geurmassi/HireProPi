/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.api;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author LENOVO
 */
public class PDFViewer {

    public static void openPDFCv(String fileName) {
        String filePath = "C:/xampp/htdocs/cv/" + fileName;

        try {
            File file = new File(filePath);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("File not found: " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Error opening PDF file: " + e.getMessage());
        }
    }
     public static void openPDFLm(String fileName) {
        String filePath = "C:/xampp/htdocs/lettre_de_motivation/" + fileName;

        try {
            File file = new File(filePath);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("File not found: " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Error opening PDF file: " + e.getMessage());
        }
    }
}

