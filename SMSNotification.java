package notificationsandremainders;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Implements Notifiable for sending SMS alerts
 * @author Student
 */
public class SMSNotification implements Notifiable {
    // Encapsulation - private fields
    private String senderPhoneNumber;
    private Map<String, ScheduledSMS> scheduledMessages;
    private int characterLimit;
    
    public SMSNotification(String senderPhoneNumber) {
        this.senderPhoneNumber = senderPhoneNumber;
        this.scheduledMessages = new HashMap<>();
        this.characterLimit = 160; // Standard SMS character limit
    }
    
    @Override
    public boolean sendNotification(String recipientId, String subject, String message) {
        // In a real implementation, this would use a third-party SMS API
        
        // Check if message exceeds character limit
        if (message.length() > characterLimit) {
            message = message.substring(0, characterLimit - 3) + "...";
        }
        
        System.out.println("Sending SMS to: " + recipientId);
        System.out.println("From: " + senderPhoneNumber);
        System.out.println("Message: " + message);
        System.out.println("SMS sent successfully");
        return true;
    }
    
    @Override
    public String scheduleNotification(String recipientId, String subject, String message, int delayInMinutes) {
        String notificationId = "SMS-" + System.currentTimeMillis();
        LocalDateTime scheduledTime = LocalDateTime.now().plusMinutes(delayInMinutes);
        
        // Check if message exceeds character limit
        if (message.length() > characterLimit) {
            message = message.substring(0, characterLimit - 3) + "...";
        }
        
        ScheduledSMS sms = new ScheduledSMS(recipientId, message, scheduledTime);
        scheduledMessages.put(notificationId, sms);
        
        System.out.println("SMS scheduled to be sent to " + recipientId + " at " + scheduledTime);
        return notificationId;
    }
    
 
    @Override
    public boolean cancelNotification(String notificationId) {
        if (scheduledMessages.containsKey(notificationId)) {
            scheduledMessages.remove(notificationId);
            System.out.println("SMS notification " + notificationId + " cancelled");
            return true;
        }
        System.out.println("SMS notification " + notificationId + " not found");
        return false;
    }
    
 
    @Override
    public NotificationType getType() {
        return NotificationType.SMS;
    }
    
   
    public void processScheduledMessages() {
        LocalDateTime now = LocalDateTime.now();
        Map<String, ScheduledSMS> pendingMessages = new HashMap<>(scheduledMessages);
        
        for (Map.Entry<String, ScheduledSMS> entry : pendingMessages.entrySet()) {
            String notificationId = entry.getKey();
            ScheduledSMS sms = entry.getValue();
            
            if (sms.getScheduledTime().isBefore(now) || sms.getScheduledTime().isEqual(now)) {
                sendNotification(sms.getRecipientId(), null, sms.getMessage());
                scheduledMessages.remove(notificationId);
            }
        }
    }
    
  
    public void setCharacterLimit(int characterLimit) {
        this.characterLimit = characterLimit;
    }
    
   
    public int getCharacterLimit() {
        return characterLimit;
    }
    
    /**
     * Private inner class for storing scheduled SMS data
     * Demonstrates encapsulation and composition
     */
    private class ScheduledSMS {
        private String recipientId;
        private String message;
        private LocalDateTime scheduledTime;
        
        public ScheduledSMS(String recipientId, String message, LocalDateTime scheduledTime) {
            this.recipientId = recipientId;
            this.message = message;
            this.scheduledTime = scheduledTime;
        }
        
        public String getRecipientId() {
            return recipientId;
        }
        
        public String getMessage() {
            return message;
        }
        
        public LocalDateTime getScheduledTime() {
            return scheduledTime;
        }
    }
}