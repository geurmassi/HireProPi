/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.api;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class PDFReader {

    public static List<String> readPDFCv(String fileName) {
        String filePath = "C:/xampp/htdocs/cv/" + fileName;
        List<String> words = new ArrayList<>();

        try {
            PdfReader reader = new PdfReader(filePath);

            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                String pageContent = PdfTextExtractor.getTextFromPage(reader, i);
                String[] pageWords = pageContent.split("\\s+");
                words.addAll(Arrays.asList(pageWords));
            }

            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return words;
    }

    public static String readPDFLm(String fileName) {
        String filePath = "C:/xampp/htdocs/lettre_de_motivation/" + fileName;
        try {
            PdfReader reader = new PdfReader(filePath);
            StringBuilder content = new StringBuilder();

            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                content.append(PdfTextExtractor.getTextFromPage(reader, i));
            }

            reader.close();
            return content.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
