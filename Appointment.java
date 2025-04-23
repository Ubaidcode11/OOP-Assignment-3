/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appointment;
import java.util.Date;

/**
 *
 * @author Ubaid Elitebook
 */
public class Appointment {
     private String appointmentId;
    private String patientId;
    private String doctorId;
    private Date appointmentDate;
    private int durationMinutes;
    private String status; // "SCHEDULED", "COMPLETED", "CANCELLED", "NO_SHOW"
    private String notes;

    public Appointment(String appointmentId, String patientId, String doctorId, Date appointmentDate, int durationMinutes) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.durationMinutes = durationMinutes;
        this.status = "SCHEDULED";
        this.notes = "";
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Appointment [id=" + appointmentId + ", patient=" + patientId + ", doctor=" + doctorId 
            + ", date=" + appointmentDate + ", status=" + status + "]";
    }
    
}
