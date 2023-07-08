/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.Quiz;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dell
 */
public class NotificationAPI {
    private static final Logger LOGGER = Logger.getLogger(NotificationAPI.class.getName());
    private final NotificationService notificationService;

    public NotificationAPI() {
        // Initialize the NotificationService
        notificationService = new NotificationService();
    }

    /**
     * This function sends a notification using the Notification API
     *
     * @param message   (String): The message to be sent
     * @param recipient (String): The recipient of the message
     */
    public void sendNotification(String message, String recipient) {
        try {
            // Connect to the Notification service
            notificationService.connect();

            // Send the notification
            notificationService.send(message, recipient);

            // Close the connection
            notificationService.disconnect();
        } catch (Exception e) {
            // Log the error
            LOGGER.log(Level.SEVERE, "Error sending notification: {0}", e.getMessage());
        }
    }
}