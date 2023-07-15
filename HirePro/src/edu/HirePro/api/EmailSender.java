/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.api;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 *
 * @author LENOVO
 */
public class EmailSender {

    /**
     * This method sends an email using the javax.mail library.
     *
     * @param recipientEmail1 The email address of the first recipient
     * @param subject1 The subject of the first email
     * @param message1 The content of the first email
     * @param recipientEmail2 The email address of the second recipient
     * @param subject2 The subject of the second email
     * @param message2 The content of the second email
     * @return true if the emails were sent successfully, false otherwise
     */
    public boolean sendEmails(String recipientEmail1, String subject1, String message1,
            String recipientEmail2, String subject2, String message2) {
        // Set up the properties for the mail server
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        System.out.println("yyyyyyyyyy");

        // Set up the authentication credentials
        String username = "athmouni.amina@esprit.tn";
        String password = "221SFT0050";

        // Create a session with the authentication credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                System.out.println("yyyyyyyyyy");
                return new PasswordAuthentication(username, password);

            }
        });

        try {
            // Send the first email
            Message emailMessage1 = createEmailMessage(session, username, recipientEmail1, subject1, message1);
            Transport.send(emailMessage1);
            System.out.println("message envoyé");

            // Send the second email
            Message emailMessage2 = createEmailMessage(session, username, recipientEmail2, subject2, message2);
            Transport.send(emailMessage2);

            return true;
        } catch (MessagingException e) {
            // Log the error
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendEmail(String recipientEmail, String subject, String message) {
        // Set up the properties for the mail server
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Set up the authentication credentials
        String username = "athmouni.amina@esprit.tn";
        String password = "221SFT0050";

        // Create a session with the authentication credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Send the email
            Message emailMessage = createEmailMessage(session, username, recipientEmail, subject, message);
            Transport.send(emailMessage);
            System.out.println("Message sent");

            return true;
        } catch (MessagingException e) {
            // Log the error
            e.printStackTrace();
            return false;
        }
    }

    private Message createEmailMessage(Session session, String senderEmail, String recipientEmail, String subject, String message) throws MessagingException {
        // Create a new email message
        Message emailMessage = new MimeMessage(session);
        emailMessage.setFrom(new InternetAddress(senderEmail));
        emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        emailMessage.setSubject(subject);
        emailMessage.setText(message);
        return emailMessage;
    }
}
