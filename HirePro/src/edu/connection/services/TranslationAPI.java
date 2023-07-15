/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.services;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class TranslationAPI {
    // Set your API key here
    private static final String API_KEY = "AIzaSyDNEbO4xwSSxe3vVJuGB5H-T2W8TYbLTAs";

    public static String translateText(String text, String targetLanguage) {
        try {
            // Create an instance of the translation service with the API key
            Translate translate = TranslateOptions.newBuilder().setApiKey(API_KEY).build().getService();

            // Translate the text
            Translation translation = translate.translate(text, Translate.TranslateOption.targetLanguage(targetLanguage));

            // Return the translated text
            return translation.getTranslatedText();
        } catch (Exception e) {
            // Log any errors that occur
            System.out.println("Error: " + e.getMessage());
            return "";
        }
    }
}
