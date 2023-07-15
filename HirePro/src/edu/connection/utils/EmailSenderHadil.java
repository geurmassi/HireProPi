/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSenderHadil {
    public static void sendEmail(String recipient, String subject, String message,String newPassword) {
        /**
         * This function sends an email message to a recipient.
         * 
         * Parameters:
         * recipient (String): The email address of the recipient
         * subject (String): The subject of the email message
         * message (String): The body of the email message
         * 
         * Returns:
         * None
         */
        
        // Sender's email address and password
        String senderEmail = "testapi180@gmail.com";
        String senderPassword = "finkziieicvdoknr";
        
        // Set the host and port for the SMTP server
        String host = "smtp.gmail.com";
        int port = 587;
        
        // Set the properties for the email session
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        
        // Create a new session with the properties and authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
        
           try {
        // Créer un nouveau message et définir le destinataire, le sujet et le corps
        MimeMessage emailMessage = new MimeMessage(session);
        emailMessage.setFrom(new InternetAddress(senderEmail));
        emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        emailMessage.setSubject(subject);

        // Charger le modèle HTML personnalisé depuis un fichier
        String htmlTemplate = loadHtmlTemplate();

        // Remplacer la balise {{password}} dans le modèle avec le mot de passe généré
        String htmlBody = htmlTemplate.replace("{{password}}", newPassword);

        // Définir le contenu du message comme HTML
        emailMessage.setContent(htmlBody, "text/html; charset=utf-8");

        // Envoyer le message
        Transport.send(emailMessage);
        System.out.println("Email message sent successfully.");
    } catch (MessagingException e) {
        // Gérer les erreurs d'envoi de l'e-mail
        System.out.println("Error sending email message: " + e.getMessage());
    }
}

private static String loadHtmlTemplate() {
    String templatePath = "src/edu/connection/gui/EmailTemplate.html";
    try {
        // Charger le contenu du fichier du modèle HTML
        byte[] encodedBytes = Files.readAllBytes(Paths.get(templatePath));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    } catch (IOException e) {
        // Gérer les erreurs de chargement du modèle
        System.out.println("Error loading HTML template: " + e.getMessage());
        return "";
    }
}
}
