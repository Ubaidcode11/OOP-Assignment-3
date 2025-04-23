package emergencyalert;

import usermanagement.Doctor;
import usermanagement.Patient;

/**
 * Interface for any service that can send alerts
 */
public interface AlertService {
    void sendAlert(Doctor doctor, Patient patient, String message);
    void sendEmergencyContactAlert(Patient patient, String message);
    void sendGeneralNotification(String recipient, String subject, String content);
}
