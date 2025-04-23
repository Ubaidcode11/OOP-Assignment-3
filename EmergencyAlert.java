package emergencyalert;

import healthdata.VitalSigns;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import usermanagement.Doctor;
import usermanagement.Patient;

/**
 * Monitors vital signs and triggers alerts when values exceed critical thresholds
 */
public class EmergencyAlert extends Alert implements Monitorable {
    private final Map<String, Double> thresholds;

    public EmergencyAlert(AlertService alertService) {
        super(alertService);
        this.thresholds = new HashMap<>();

        // Set default thresholds
        thresholds.put("heartRateMax", 100.0);
        thresholds.put("heartRateMin", 50.0);
        thresholds.put("oxygenLevelMin", 92.0);
        thresholds.put("bloodPressureSystolicMax", 140.0);
        thresholds.put("bloodPressureDiastolicMax", 90.0);
        thresholds.put("temperatureMax", 38.5);
        thresholds.put("temperatureMin", 35.0);
    }

    public void setThreshold(String vitalSign, double value) {
        if (thresholds.containsKey(vitalSign)) {
            thresholds.put(vitalSign, value);
            System.out.println("Threshold for " + vitalSign + " updated to " + value);
        } else {
            System.out.println("Invalid vital sign type");
        }
    }

    public Map<String, Double> getThresholds() {
        return Collections.unmodifiableMap(thresholds);
    }

    public void monitorVitalSigns(Patient patient, Doctor doctor) {
        System.out.println("Monitoring vital signs for patient: " + patient.getName());

        if (patient.getVitalSigns().isEmpty()) {
            System.out.println("No vital signs data available for patient");
            return;
        }

        // Get latest vitals
        VitalSigns vitalSigns = patient.getVitalSigns().get(patient.getVitalSigns().size() - 1);

        String alertMessage = checkVitalSigns(patient, vitalSigns);
        if (!alertMessage.isEmpty()) {
            triggerAlert(patient, doctor, alertMessage);
        } else {
            System.out.println("Vital signs for patient " + patient.getName() + " are within normal ranges");
        }
    }

    // ✅ FIXED: changed return type to String instead of void
    @Override
    public String checkVitalSigns(Patient patient, VitalSigns vitalSigns) {
        StringBuilder alertMessage = new StringBuilder("ALERT for patient " + patient.getName() + ": ");
        boolean isEmergency = false;

        // Heart rate
        if (!isWithinNormalRange("heartRate", vitalSigns.getHeartRate())) {
            alertMessage.append(vitalSigns.getHeartRate() > thresholds.get("heartRateMax") ?
                "High heart rate (" + vitalSigns.getHeartRate() + " bpm)! " :
                "Low heart rate (" + vitalSigns.getHeartRate() + " bpm)! ");
            isEmergency = true;
        }

        // Oxygen level
        if (!isWithinNormalRange("oxygenLevel", vitalSigns.getOxygenLevel())) {
            alertMessage.append("Low oxygen level (" + vitalSigns.getOxygenLevel() + "%)! ");
            isEmergency = true;
        }

        // Blood pressure
        String[] bp = vitalSigns.getBloodPressure().split("/");
        double systolic = Double.parseDouble(bp[0]);
        double diastolic = Double.parseDouble(bp[1]);

        if (!isWithinNormalRange("bloodPressureSystolic", systolic)) {
            alertMessage.append("High systolic pressure (" + systolic + " mmHg)! ");
            isEmergency = true;
        }

        if (!isWithinNormalRange("bloodPressureDiastolic", diastolic)) {
            alertMessage.append("High diastolic pressure (" + diastolic + " mmHg)! ");
            isEmergency = true;
        }

        // Temperature
        if (!isWithinNormalRange("temperature", vitalSigns.getTemperature())) {
            alertMessage.append(vitalSigns.getTemperature() > thresholds.get("temperatureMax") ?
                "High temperature (" + vitalSigns.getTemperature() + "°C)! " :
                "Low temperature (" + vitalSigns.getTemperature() + "°C)! ");
            isEmergency = true;
        }

        return isEmergency ? alertMessage.toString() : "";
    }

    @Override
    public boolean isWithinNormalRange(String vitalSignType, double value) {
        switch (vitalSignType) {
            case "heartRate":
                return value >= thresholds.get("heartRateMin") && value <= thresholds.get("heartRateMax");
            case "oxygenLevel":
                return value >= thresholds.get("oxygenLevelMin");
            case "bloodPressureSystolic":
                return value <= thresholds.get("bloodPressureSystolicMax");
            case "bloodPressureDiastolic":
                return value <= thresholds.get("bloodPressureDiastolicMax");
            case "temperature":
                return value >= thresholds.get("temperatureMin") && value <= thresholds.get("temperatureMax");
            default:
                System.out.println("Unknown vital sign type: " + vitalSignType);
                return true;
        }
    }

    public void triggerAlert(Patient patient, Doctor doctor, String message) {
        if (!isActive) {
            System.out.println("Alert system is currently disabled");
            return;
        }

        System.out.println(message);
        alertService.sendAlert(doctor, patient, message);
    }

    @Override
    protected String getAlertTypeName() {
        return "Vital signs monitoring";
    }
}
