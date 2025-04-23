package emergencyalert;

import usermanagement.Doctor;
import usermanagement.Patient;

/**
 * Abstract base class for all alert mechanisms in the system
 */
public abstract class Alert {
    protected AlertService alertService;
    protected boolean isActive;
    
    public Alert(AlertService alertService) {
        this.alertService = alertService;
        this.isActive = true;
    }
    
    public abstract void triggerAlert(Patient patient, Doctor doctor, String message);
    
    public void enable() {
        this.isActive = true;
        System.out.println(getAlertTypeName() + " enabled");
    }
    
    public void disable() {
        this.isActive = false;
        System.out.println(getAlertTypeName() + " disabled");
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    protected abstract String getAlertTypeName();
}