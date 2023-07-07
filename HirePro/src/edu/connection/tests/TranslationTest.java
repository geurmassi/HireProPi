/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.tests;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import edu.connection.services.TranslationAPI;

public class TranslationTest {
      public static void main(String[] args) {
        String text = "comment allez vous ??";
        String targetLanguage = "en";

        String translatedText = TranslationAPI.translateText(text, targetLanguage);

        System.out.println("Original Text: " + text);
        System.out.println("Translated Text: " + translatedText);
    }
}

