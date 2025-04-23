/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package notificationsandremainders;

public interface Notifiable {
   
    boolean sendNotification(String recipientId, String subject, String message);
    
    String scheduleNotification(String recipientId, String subject, String message, int delayInMinutes);
    
    boolean cancelNotification(String notificationId);
    

    NotificationType getType();
    
    /**
     * Enumeration for notification types
     */
    enum NotificationType {
        EMAIL,
        SMS
    }
}
    

