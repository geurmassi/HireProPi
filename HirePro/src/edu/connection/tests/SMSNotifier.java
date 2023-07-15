/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.tests;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import edu.connection.services.UserCRUD;
import java.sql.SQLException;
import java.util.List;
public class SMSNotifier {
    // Vos identifiants Twilio
    private static final String ACCOUNT_SID = "ACbc6a267578bfb70b54cda0a884f4833f";
    private static final String AUTH_TOKEN = "30f95e096c8bd61ac2df55625cb138e2";
    // Votre numéro de téléphone Twilio
    private static final String TWILIO_PHONE_NUMBER = "+12346573853";

   public static void sendBlockedNotification() {
    // Initialisation de la bibliothèque Twilio
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    try {
            
        UserCRUD CR = new UserCRUD();
        // Récupérer tous les numéros de téléphone depuis la base de données
        List<String> phoneNumbers = CR.getPhoneNumbersFromDatabase();

        for (String phoneNumber : phoneNumbers) {
            PhoneNumber to = new PhoneNumber(phoneNumber);
            PhoneNumber from = new PhoneNumber(TWILIO_PHONE_NUMBER);
            String messageBody = "Votre compte a été bloqué. Veuillez contacter l'administrateur pour plus d'informations.";

            // Envoi du SMS
            Message message = Message.creator(to, from, messageBody).create();

            // Vérification du statut de l'envoi
            if (message.getStatus().equals(Message.Status.SENT)) {
                System.out.println("SMS sent successfully to " + phoneNumber);
            } else {
                System.out.println("Failed to send SMS to " + phoneNumber);
            }
        }
    } catch (SQLException e) {
        // Gérer les exceptions ici (par exemple, les erreurs de connexion à la base de données)
        System.out.println("Failed to get phone numbers from the database.");
    }
}

}