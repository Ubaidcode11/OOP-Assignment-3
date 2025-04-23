package emergencyalert; // ✅ include this only if you're using packages

import healthdata.VitalSigns;
import usermanagement.Patient;

public interface Monitorable {
    String checkVitalSigns(Patient patient, VitalSigns vitalSigns);  // ✅ changed return type to String
    boolean isWithinNormalRange(String vitalSignType, double value);
}
