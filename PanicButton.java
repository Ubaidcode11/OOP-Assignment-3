package emergencyalert;

import usermanagement.Doctor;
import usermanagement.Patient;
import java.time.LocalDateTime;

/**
 * Allows patients to manually trigger emergency alerts
 */
public class PanicButton extends Alert {
    private String lastActivationTime;
    
    public PanicButton(AlertService alertService) {
        super(alertService);
        this.lastActivationTime = null;
    }
    
    @Override
    public void triggerAlert(Patient patient, Doctor doctor, String emergencyDescription) {
        if (!isActive) {
            System.out.println("Panic button is currently disabled. Please contact support.");
            return;
        }
        
        lastActivationTime = LocalDateTime.now().toString();
        String alertMessage = "PANIC BUTTON ACTIVATED by patient " + patient.getName();
        
        if (emergencyDescription != null && !emergencyDescription.isEmpty()) {
            alertMessage += ": " + emergencyDescription;
        } else {
            alertMessage += ": Patient requires immediate assistance";
        }
        
        System.out.println(alertMessage);
        
        // Send notification to doctor
        alertService.sendAlert(doctor, patient, alertMessage);
        
        triggerEmergencyProtocol(patient);
    }
    
    private void triggerEmergencyProtocol(Patient patient) {
        // This would implement additional emergency protocols
        System.out.println("Emergency protocol initiated for patient: " + patient.getName());
        System.out.println("- Emergency services may be contacted");
        System.out.println("- Geolocation data is being transmitted");
        System.out.println("- Medical history is being prepared for emergency responders");
    }
    
    public String getLastActivationTime() {
        return lastActivationTime != null ? 
               lastActivationTime : "Button has not been activated yet";
    }
    
    public void reset() {
        System.out.println("Panic button has been reset");
        this.lastActivationTime = null;
    }
    
    @Override
    protected String getAlertTypeName() {
        return "Panic button";
    }
    
   
    public void pressButton(Patient patient, Doctor doctor, String emergencyDescription) {
        triggerAlert(patient, doctor, emergencyDescription);
    }
}