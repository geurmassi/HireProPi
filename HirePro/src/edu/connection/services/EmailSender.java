package edu.connection.services;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSender {

    public static void sendEmail(String recipient, String subject,String  template,String imagePath) throws IOException {
        // Sender's email address and password
        String senderEmail = "kamel.guermassi@esprit.tn";
        String senderPassword = "223AMT0734";

        // Set the properties for the email session
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a new session with the properties and authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a new message and set the recipient, subject, and body
            MimeMessage emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(senderEmail));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            emailMessage.setSubject(subject);

            // Create the HTML part of the email
            MimeBodyPart htmlPart = new MimeBodyPart();
            String emailContent = replacePlaceholders(template, recipient, subject);
            htmlPart.setContent(emailContent, "text/html");

            
            
          // Create the image part of the email
            MimeBodyPart imagePart = new MimeBodyPart();
            imagePart.attachFile(new File(imagePath));

            // Create the multipart message and add the parts
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);
            multipart.addBodyPart(imagePart);
            
            
            
            
            
            // Set the multipart message as the content of the email
            emailMessage.setContent(multipart);

            // Send the message
            Transport.send(emailMessage);
            System.out.println("Email message sent successfully.");
        } catch (MessagingException e) {
            // Log the error
            System.out.println("Error sending email message: " + e.getMessage());
        }
    }

    private static String replacePlaceholders(String template, String recipient, String appointment) {
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("{{recipient}}", recipient);
        placeholders.put("{{appointment}}", appointment);

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            template = template.replace(entry.getKey(), entry.getValue());
        }

        return template;
    }
}
