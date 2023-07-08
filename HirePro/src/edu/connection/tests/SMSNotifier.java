/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.tests;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSNotifier {
    // Vos identifiants Twilio
    private static final String ACCOUNT_SID = "AC34b6f1b3f8f72745a57faf5ffd1b44e4";
    private static final String AUTH_TOKEN = "38f8201a4e5d9a894a398e4cbd6b2b13";
    // Votre numéro de téléphone Twilio
    private static final String TWILIO_PHONE_NUMBER = "+16187243177";

    public static void sendBlockedNotification(String phoneNumber) {
        // Initialisation de la bibliothèque Twilio
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

     PhoneNumber to = new PhoneNumber("+216" + phoneNumber); // Ajoutez le préfixe du pays
    PhoneNumber from = new PhoneNumber(TWILIO_PHONE_NUMBER);
    String messageBody = "Votre compte a été bloqué. Veuillez contacter l'administrateur pour plus d'informations.";

        // Message à envoyer
  
        // Envoi du SMS
        Message message = Message.creator(to, from, messageBody).create();

        // Vérification du statut de l'envoi
        if (message.getStatus().equals(Message.Status.SENT)) {
            System.out.println("SMS sent successfully.");
        } else {
            System.out.println("Failed to send SMS.");
        }
    }
}
