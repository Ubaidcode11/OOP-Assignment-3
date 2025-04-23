/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package healthdata;
import java.util.Date;

/**
 *
 * @author Ubaid Elitebook
 */
public class VitalSigns {
    private String patientId;
    private Date recordTime;
    private double heartRate;
    private double oxygenLevel;
    private double bloodPressureSystolic;
    private double bloodPressureDiastolic;
    private double temperature;

    public VitalSigns(String patientId, double heartRate, double oxygenLevel, 
                     double bloodPressureSystolic, double bloodPressureDiastolic, double temperature) {
        this.patientId = patientId;
        this.recordTime = new Date();
        this.heartRate = heartRate;
        this.oxygenLevel = oxygenLevel;
        this.bloodPressureSystolic = bloodPressureSystolic;
        this.bloodPressureDiastolic = bloodPressureDiastolic;
        this.temperature = temperature;
    }

    public String getPatientId() {
        return patientId;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public double getHeartRate() {
        return heartRate;
    }

    public double getOxygenLevel() {
        return oxygenLevel;
    }

    public String getBloodPressure() {
        return bloodPressureSystolic + "/" + bloodPressureDiastolic;
    }

    public double getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return "VitalSign [patientId=" + patientId + ", time=" + recordTime + ", HR=" + heartRate + ", O2=" + oxygenLevel 
            + ", BP=" + getBloodPressure() + ", temp=" + temperature + "]";
    }
    
}
