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
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailNotificationService {
    private final String host = "smtp.gmail.com"; // or your email service provider's SMTP server
    private final int port = 587; // or the appropriate port for your email service provider
    private final String username = "testapi180@gmail.com"; // your email address
    private final String password = "finkziieicvdoknr"; // your email password

    public void sendEmailNotification(String recipientEmail, String subject, String content) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private static String loadHtmlTemplate() {
    String templatePath = "src/edu/connection/gui/EmailNotification.html";
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

