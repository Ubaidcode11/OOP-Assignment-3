package emergencyalert;

/**
 * Factory class to create different types of alerts
 */
public class AlertFactory {
    private static final AlertService alertService = new NotificationService();
    
    public static EmergencyAlert createEmergencyAlert() {
        return new EmergencyAlert(alertService);
    }
    
    public static PanicButton createPanicButton() {
        return new PanicButton(alertService);
    }
    

}