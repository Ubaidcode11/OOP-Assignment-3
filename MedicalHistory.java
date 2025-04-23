/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doctorpateint;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Ubaid Elitebook
 */
public class MedicalHistory {
      private String patientId;
    private ArrayList<Consultation> consultations;
    private ArrayList<Prescription> prescriptions;

    public MedicalHistory(String patientId) {
        this.patientId = patientId;
        this.consultations = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
    }

    public void addConsultation(String doctorId, String notes, Date consultationDate) {
        Consultation consultation = new Consultation(doctorId,consultationDate);
        consultations.add(consultation);
    }

    public void addPrescription(Prescription prescription) {
        if (prescription.getPatientId().equals(patientId)) {
            prescriptions.add(prescription);
        }
    }

    public ArrayList<Consultation> getConsultations() {
        return consultations;
    }

    public ArrayList<Prescription> getPrescriptions() {
        return prescriptions;
    }

    @Override
    public String toString() {
        return "MedicalHistory [patientId=" + patientId + ", consultations=" + consultations.size() 
            + ", prescriptions=" + prescriptions.size() + "]";
    }

    // Inner class for consultations
    public class Consultation {
        private String doctorId;
        private Date consultationDate;

        public Consultation(String doctorId, Date consultationDate) {
            this.doctorId = doctorId;
            this.consultationDate = consultationDate;
        }

        @Override
        public String toString() {
            return "Consultation [doctor=" + doctorId + ", date=" + consultationDate + "]";
        }
    }
}
