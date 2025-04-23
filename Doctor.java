package usermanagement;

import java.util.ArrayList;
import appointment.Appointment;
import doctorpateint.FeedBack;
import healthdata.VitalSigns;

/**
 * Represents a Doctor in the system.
 * Handles patient management, feedback, and appointments.
 * Ensures bidirectional relationship with patients.
 */
public class Doctor extends User {
    private ArrayList<Patient> patients;
    private ArrayList<Appointment> appointments;

    public Doctor(String userId, String name, String contactInfo) {
        super(userId, name, contactInfo, "Doctor");
        this.patients = new ArrayList<>();
        this.appointments = new ArrayList<>();
    }

    /**
     * Adds a patient to the doctor's list and ensures bidirectional relationship.
     */
    public void addPatient(Patient patient) {
        if (!patients.contains(patient)) {
            patients.add(patient);
            patient.assignDoctor(this); // ✅ Ensures patient knows about the doctor too
            System.out.println("Patient " + patient.getName() + " assigned to Dr. " + getName());
        }
    }

    /**
     * Checks if a patient is already assigned to the doctor.
     */
    public boolean hasPatient(Patient patient) {
        return patients.contains(patient);
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    /**
     * View detailed data of an assigned patient.
     */
    public void viewPatientData(Patient patient) {
        if (hasPatient(patient)) {
            System.out.println("Viewing data for patient: " + patient.getName());
            System.out.println("Patient ID: " + patient.getUserId());
            System.out.println("Contact Info: " + patient.getContactInfo());

            System.out.println("\nVital Signs:");
            ArrayList<VitalSigns> vitalSigns = patient.getVitalSigns();
            if (vitalSigns.isEmpty()) {
                System.out.println("No vital signs recorded.");
            } else {
                for (VitalSigns vs : vitalSigns) {
                    System.out.println("- " + vs);
                }
            }

            System.out.println("\nAppointments:");
            ArrayList<Appointment> appts = patient.getAppointments();
            if (appts.isEmpty()) {
                System.out.println("No appointments scheduled.");
            } else {
                for (Appointment appt : appts) {
                    System.out.println("- " + appt);
                }
            }
        } else {
            System.out.println("Not authorized to view this patient's data.");
        }
    }

    /**
     * Allows doctor to provide feedback for an assigned patient.
     */
    public void provideFeedback(Patient patient, String feedbackText) {
        if (hasPatient(patient)) {
            FeedBack feedback = new FeedBack(getUserId(), patient.getUserId(), feedbackText);
            patient.addFeedback(feedback);
            System.out.println("Feedback submitted successfully.");
        } else {
            System.out.println("Error: Patient not found.");
        }
    }

    /**
     * Schedule a new appointment.
     */
    public void scheduleAppointment(Appointment appointment) {
        appointments.add(appointment);
        System.out.println("Appointment scheduled with Dr. " + getName() + " on " + appointment.getAppointmentDate());
    }

    /**
     * Cancel an existing appointment.
     */
    public void cancelAppointment(Appointment appointment) {
        if (appointments.remove(appointment)) {
            System.out.println("Appointment with Dr. " + getName() + " cancelled.");
        } else {
            System.out.println("Appointment not found for Dr. " + getName());
        }
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * View all appointments scheduled with this doctor.
     */
    public void viewAllAppointments() {
        System.out.println("All appointments for Dr. " + getName() + ":");
        if (appointments.isEmpty()) {
            System.out.println("No appointments scheduled.");
        } else {
            for (Appointment appointment : appointments) {
                System.out.println("- " + appointment);
            }
        }
    }

    @Override
    public String toString() {
        return "Doctor [id=" + getUserId() + ", name=" + getName() +
                ", patients=" + patients.size() +
                ", appointments=" + appointments.size() + "]";
    }

    public String getOfficeLocation() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
