package healthdata;

import java.util.ArrayList;
import java.util.HashMap;

public class VItalDataBase {
    private HashMap<String, ArrayList<VitalSigns>> patientVitals;

    public VItalDataBase() {
        this.patientVitals = new HashMap<>();
    }

    public void addVitalSign(VitalSigns vitalSign) {
        String patientId = vitalSign.getPatientId();
        if (!patientVitals.containsKey(patientId)) {
            patientVitals.put(patientId, new ArrayList<>());
        }
        patientVitals.get(patientId).add(vitalSign);
        System.out.println("Vital sign recorded in database for patient: " + patientId);
    }

    public ArrayList<VitalSigns> getPatientVitals(String patientId) {
        return patientVitals.getOrDefault(patientId, new ArrayList<>());
    }

    public VitalSigns getLatestVitals(String patientId) {
        ArrayList<VitalSigns> vitals = patientVitals.get(patientId);
        if (vitals != null && !vitals.isEmpty()) {
            return vitals.get(vitals.size() - 1);
        }
        return null;
    }

    public int getTotalRecords() {
        // Sum of all vital signs for all patients
        return patientVitals.values().stream().mapToInt(ArrayList::size).sum();
    }

    @Override
    public String toString() {
        return "VitalsDatabase [patients=" + patientVitals.size() + ", totalRecords=" 
            + getTotalRecords() + "]";
    }

    // You can delete or implement this if needed
    public Object getVitalSigns() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
