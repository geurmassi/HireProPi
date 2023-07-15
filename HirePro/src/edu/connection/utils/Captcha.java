/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.utils;

/**
 *
 * @author hadil ibenhajfraj
 */
import java.util.Random;

public class Captcha {
    /**
     * This function generates a random captcha string of the specified length.
     *
     * @param length (int): The length of the captcha string to be generated
     * @return String: The generated captcha string
     */
    public static String generateCaptcha(int length) {
        String captchaChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder captcha = new StringBuilder();
        Random random = new Random();

        // Generate the captcha string
        for (int i = 0; i < length; i++) {
            captcha.append(captchaChars.charAt(random.nextInt(captchaChars.length())));
        }

        return captcha.toString();
    }

    /**
     * This function checks if the user input matches the captcha string.
     *
     * @param userInput (String): The user input to be checked
     * @param captcha (String): The captcha string to be matched
     * @return boolean: True if the user input matches the captcha, False otherwise
     */
    public static boolean authenticateCaptcha(String userInput, String captcha) {
        return userInput.equals(captcha);
    }
}
