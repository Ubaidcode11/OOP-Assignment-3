package emergencyalert;

import usermanagement.Doctor;
import usermanagement.Patient;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Implements the AlertService interface to send notifications
 */
public class NotificationService implements AlertService {
    
    public NotificationService() {
        // Constructor for potential future initialization
    }
    
    @Override
    public void sendAlert(Doctor doctor, Patient patient, String message) {
        // In a real implementation, this would connect to an email or SMS service
        System.out.println("NOTIFICATION SENT to Dr. " + doctor.getName() + " via SMS and Email");
        System.out.println("Message: " + message);
        
        // Log the alert
        logAlert(doctor, patient, message);
        
        // In a real-world scenario, you might also notify emergency contacts
        sendEmergencyContactAlert(patient, message);
    }
    
    @Override
    public void sendEmergencyContactAlert(Patient patient, String message) {
        // This would notify the patient's emergency contacts
        System.out.println("Emergency contact for " + patient.getName() + " has been notified");
        System.out.println("Contact info: " + patient.getContactInfo());
    }
    
    private void logAlert(Doctor doctor, Patient patient, String message) {
        // In a real implementation, this would log to a database or file
        System.out.println("Alert logged: " + message);
        System.out.println("Time: " + LocalDateTime.now());
        System.out.println("Doctor: " + doctor.getName() + " (ID: " + doctor.getUserId() + ")");
        System.out.println("Patient: " + patient.getName() + " (ID: " + patient.getUserId() + ")");
    }
    
    @Override
    public void sendGeneralNotification(String recipient, String subject, String content) {
        // For non-emergency notifications
        System.out.println("Notification sent to: " + recipient);
        System.out.println("Subject: " + subject);
        System.out.println("Content: " + content);
    }
}
