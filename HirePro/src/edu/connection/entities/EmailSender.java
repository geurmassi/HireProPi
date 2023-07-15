/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.entities;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {

    //private String from = "marwen.achouri@etudiant-isi.utm.tn";
    private String from = "marwen.achouri@esprit.tn";
    
    private boolean sessionDebug = false;

    public void sendEmailToSAMU(String to, String subject, String messageText, Offre offre) {
        String username = "marwenachouri1000@gmail.com";
        String password = "221SMT2220+++";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(from));
            emailMessage.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(to));
            emailMessage.setSubject(subject);

            // Create the HTML message body part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(messageText, "text/html");

            // Create the multipart object for combining text and HTML parts
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Append the offre details as a text part
            String offreDetails = "<div style=\"font-family: Arial, sans-serif; padding: 20px;\">"
                    + "<h3 style=\"color: #333333;\">Offre Details:</h3>"
                    + "<table style=\"width: 100%; border-collapse: collapse;\">"
                    + "<tr>"
                    + "<td style=\"font-weight: bold; padding: 8px;\">Job Holder:</td>"
                    + "<td style=\"padding: 8px;\">" + offre.getJobHolder() + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"font-weight: bold; padding: 8px;\">Description:</td>"
                    + "<td style=\"padding: 8px;\">" + offre.getDescriptif() + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"font-weight: bold; padding: 8px;\">Skills:</td>"
                    + "<td style=\"padding: 8px;\">" + offre.getSkills() + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"font-weight: bold; padding: 8px;\">Company:</td>"
                    + "<td style=\"padding: 8px;\">" + offre.getCompany() + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"font-weight: bold; padding: 8px;\">Start Date:</td>"
                    + "<td style=\"padding: 8px;\">" + offre.getDateDebutOffre() + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"font-weight: bold; padding: 8px;\">End Date:</td>"
                    + "<td style=\"padding: 8px;\">" + offre.getDateFinOffre() + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"font-weight: bold; padding: 8px;\">Type of Employment:</td>"
                    + "<td style=\"padding: 8px;\">" + offre.getTypeEmploi() + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"font-weight: bold; padding: 8px;\">Type of Workplace:</td>"
                    + "<td style=\"padding: 8px;\">" + offre.getTypeLieuTravail() + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"font-weight: bold; padding: 8px;\">Reception of Applications:</td>"
                    + "<td style=\"padding: 8px;\">" + offre.getReceptionOfApplication() + "</td>"
                    + "</tr>"
                    + "</table>"
                    + "</div>";

            MimeBodyPart offreDetailsBodyPart = new MimeBodyPart();
            offreDetailsBodyPart.setContent(offreDetails, "text/html");

            multipart.addBodyPart(offreDetailsBodyPart);

            // Set the message content to the multipart object
            emailMessage.setContent(multipart);

            // Send the email
            Transport.send(emailMessage);
            System.out.println("Message sent successfully");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}

