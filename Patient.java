package usermanagement;

import java.util.ArrayList;
import java.util.List;
import healthdata.VitalSigns;
import doctorpateint.FeedBack;
import appointment.Appointment;

/**
 *
 * @author Ubaid Elitebook
 */
public class Patient extends User {
    private ArrayList<VitalSigns> vitalSigns;
    private ArrayList<FeedBack> feedbacks;
    private ArrayList<Appointment> appointments;
    private ArrayList<Doctor> assignedDoctors;  // ✅ Added this field

    public Patient(String userId, String name, String contactInfo) {
        super(userId, name, contactInfo, "Patient");
        this.vitalSigns = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.assignedDoctors = new ArrayList<>(); // ✅ Initialize assignedDoctors
    }

    public void addVitalSign(VitalSigns vitalSign) {
        vitalSigns.add(vitalSign);
        System.out.println("Vital sign uploaded for patient: " + getName());
    }

    public ArrayList<VitalSigns> getVitalSigns() {
        return vitalSigns;
    }

    public void addFeedback(FeedBack feedback) {
        feedbacks.add(feedback);
    }

    public ArrayList<FeedBack> getFeedbacks() {
        return feedbacks;
    }

    public void viewFeedback() {
        System.out.println("Viewing feedback for patient: " + getName());
        for (FeedBack feedback : feedbacks) {
            System.out.println("- " + feedback);
        }
    }

    public void scheduleAppointment(Appointment appointment) {
        appointments.add(appointment);
        System.out.println("Appointment scheduled for patient: " + getName() + " on " + appointment.getAppointmentDate());
    }

    public void cancelAppointment(Appointment appointment) {
        if (appointments.remove(appointment)) {
            System.out.println("Appointment cancelled for patient: " + getName());
        } else {
            System.out.println("Appointment not found for patient: " + getName());
        }
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void viewAppointments() {
        System.out.println("Appointments for patient: " + getName());
        for (Appointment appointment : appointments) {
            System.out.println("- " + appointment);
        }
    }

    // ✅ Assign a doctor to this patient
    public void assignDoctor(Doctor doctor) {
        if (!assignedDoctors.contains(doctor)) {
            assignedDoctors.add(doctor);
        }
    }

    // ✅ Return the list of assigned doctors
    public List<Doctor> getAssignedDoctors() {
        return assignedDoctors;
    }

    @Override
    public String toString() {
        return "Patient [id=" + getUserId() + ", name=" + getName() + ", appointments=" + appointments.size() 
            + ", vitalSigns=" + vitalSigns.size() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Patient other = (Patient) obj;
        return this.getUserId().equals(other.getUserId());
    }

    @Override
    public int hashCode() {
        return getUserId().hashCode();
    }
}
