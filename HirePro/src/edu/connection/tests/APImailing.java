package edu.connection.tests;

import edu.connection.services.EmailSender;
import java.io.IOException;

public class APImailing {
    public static void main(String[] args) throws IOException {
        EmailSender emailSender = new EmailSender();

        String recipient = "geurkamel@gmail.com";
        String subject = "HirePro";
        String template ="<html>\n" +
        "<head>\n" +
        "<style>\n" +
        "body { text-align: center; }\n" +
        "</style>\n" +
        "</head>\n" +
        "<body>\n" +
        "<h1>Dear {{recipient}},</h1>\n"+
        "<p>1 new message from {{receiver}} waiting for your answer</p>\n" +
        "<p>Best regards,</p>\n" +
        "<p>The HirePro Team</p>\n" +
        "</body>\n" +
        "</html>";
        String imagePath="C:\\Users\\haith\\Downloads\\HirePro-Kamel\\HirePro\\src\\images\\hireProLogo.jpg";
        String receiver="kamel";
        template = template.replace("{{receiver}}", receiver); // Replace placeholder with receiver value
        emailSender.sendEmail(recipient, subject, template, imagePath);
      
    }
}
