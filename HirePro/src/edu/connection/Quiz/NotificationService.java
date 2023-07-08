/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.Quiz;

/**
 *
 * @author Dell
 */
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotificationService {
    private static final Logger LOGGER = Logger.getLogger(NotificationService.class.getName());

    public void connect() {
        // Implement the logic to connect to the notification service
        LOGGER.info("Connecting to the notification service...");
        // Add your code here
    }

    public void send(String message, String recipient) {
        // Implement the logic to send the notification
        LOGGER.log(Level.INFO, "Sending notification to {0}: {1}", new Object[]{recipient, message});
        // Add your code here
    }

    public void disconnect() {
        // Implement the logic to disconnect from the notification service
        LOGGER.info("Disconnecting from the notification service...");
        // Add your code here
    }
}
