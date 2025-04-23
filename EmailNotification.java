package notificationsandremainders;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailNotification implements Notifiable {
    private String senderEmail;
    private String senderPassword;
    private Map<String, ScheduledNotification> scheduledEmails;
    private Session session;
    
    public EmailNotification(String senderEmail, String senderPassword) {
        this.senderEmail = senderEmail;
        this.senderPassword = senderPassword;
        this.scheduledEmails = new HashMap<>();
        
        // Setup properties and create session immediately in constructor
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        // Enable debug to see what's happening
        props.put("mail.debug", "true");
        
        // Create the session with authentication
        this.session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
    }
    
    @Override
    public boolean sendNotification(String recipientEmail, String subject, String message) {
        try {
            // Create a new message
            MimeMessage mimeMessage = new MimeMessage(session);
            
            // Set sender and recipient
            mimeMessage.setFrom(new InternetAddress(senderEmail));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            
            // Set email subject and content
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            
            // Send message
            Transport.send(mimeMessage);
            
            System.out.println("Email sent successfully to: " + recipientEmail);
            return true;
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    


    
   
   @Override
    public String scheduleNotification(String recipientId, String subject, String message, int delayInMinutes) {
        String notificationId = "EMAIL-" + System.currentTimeMillis();
        LocalDateTime scheduledTime = LocalDateTime.now().plusMinutes(delayInMinutes);
        
        ScheduledNotification notification = new ScheduledNotification(
            recipientId, subject, message, scheduledTime
        );
        
        scheduledEmails.put(notificationId, notification);
        
        System.out.println("Email scheduled to be sent to " + recipientId + " at " + scheduledTime);
        return notificationId;
    }
    
   
    @Override
    public boolean cancelNotification(String notificationId) {
        if (scheduledEmails.containsKey(notificationId)) {
            scheduledEmails.remove(notificationId);
            System.out.println("Email notification " + notificationId + " cancelled");
            return true;
        }
        System.out.println("Email notification " + notificationId + " not found");
        return false;
    }
    
  
    @Override
    public NotificationType getType() {
        return NotificationType.EMAIL;
    }
    
 
    public void processScheduledEmails() {
        LocalDateTime now = LocalDateTime.now();
        Map<String, ScheduledNotification> pendingEmails = new HashMap<>(scheduledEmails);
        
        for (Map.Entry<String, ScheduledNotification> entry : pendingEmails.entrySet()) {
            String notificationId = entry.getKey();
            ScheduledNotification notification = entry.getValue();
            
            if (notification.getScheduledTime().isBefore(now) || notification.getScheduledTime().isEqual(now)) {
                sendNotification(
                    notification.getRecipientId(),
                    notification.getSubject(),
                    notification.getMessage()
                );
                scheduledEmails.remove(notificationId);
            }
        }
    }
    
   
    private class ScheduledNotification {
        private String recipientId;
        private String subject;
        private String message;
        private LocalDateTime scheduledTime;
        
        public ScheduledNotification(String recipientId, String subject, String message, LocalDateTime scheduledTime) {
            this.recipientId = recipientId;
            this.subject = subject;
            this.message = message;
            this.scheduledTime = scheduledTime;
        }
        
        public String getRecipientId() {
            return recipientId;
        }
        
        public String getSubject() {
            return subject;
        }
        
        public String getMessage() {
            return message;
        }
        
        public LocalDateTime getScheduledTime() {
            return scheduledTime;
        }
    }
}