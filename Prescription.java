/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doctorpateint;
import java.util.Date;

/**
 *
 * @author Ubaid Elitebook
 */
public class Prescription {
     private String prescriptionId;
    private String doctorId;
    private String patientId;
    private String medication;
    private String dosage;
    private String schedule;
    private Date prescriptionDate;
    private boolean active;

    public Prescription(String doctorId, String patientId, String medication, String dosage, String schedule) {
        this.prescriptionId = generatePrescriptionId();
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.medication = medication;
        this.dosage = dosage;
        this.schedule = schedule;
        this.prescriptionDate = new Date();
        this.active = true;
    }

    private String generatePrescriptionId() {
        return "RX-" + System.currentTimeMillis();
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getMedication() {
        return medication;
    }

    public String getDosage() {
        return dosage;
    }

    public String getSchedule() {
        return schedule;
    }

    public Date getPrescriptionDate() {
        return prescriptionDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Prescription [id=" + prescriptionId + ", medication=" + medication + ", dosage=" + dosage 
            + ", schedule=" + schedule + ", active=" + active + "]";
    }


   
    
}
