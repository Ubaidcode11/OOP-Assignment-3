package appointment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class AppointmentManager {
    private HashMap<String, Appointment> appointments;

    public AppointmentManager() {
        this.appointments = new HashMap<>();
    }

    public Appointment createAppointment(String patientId, String doctorId, Date appointmentDate, int durationMinutes) {
        String appointmentId = generateAppointmentId();
        Appointment appointment = new Appointment(appointmentId, patientId, doctorId, appointmentDate, durationMinutes);
        appointments.put(appointmentId, appointment);
        System.out.println("Appointment created: " + appointment);
        return appointment;
    }

    private String generateAppointmentId() {
        return "APT-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public Appointment getAppointment(String appointmentId) {
        return appointments.get(appointmentId);
    }

    public ArrayList<Appointment> getPatientAppointments(String patientId) {
        ArrayList<Appointment> patientAppointments = new ArrayList<>();
        for (Appointment appointment : appointments.values()) {
            if (appointment.getPatientId().equals(patientId)) {
                patientAppointments.add(appointment);
            }
        }
        return patientAppointments;
    }

    public ArrayList<Appointment> getDoctorAppointments(String doctorId) {
        ArrayList<Appointment> doctorAppointments = new ArrayList<>();
        for (Appointment appointment : appointments.values()) {
            if (appointment.getDoctorId().equals(doctorId)) {
                doctorAppointments.add(appointment);
            }
        }
        return doctorAppointments;
    }

    public boolean cancelAppointment(String appointmentId) {
        Appointment appointment = appointments.get(appointmentId);
        if (appointment != null) {
            appointment.setStatus("CANCELLED");
            System.out.println("Appointment cancelled: " + appointmentId);
            return true;
        }
        System.out.println("Appointment not found: " + appointmentId);
        return false;
    }

    public boolean updateAppointmentStatus(String appointmentId, String status) {
        Appointment appointment = appointments.get(appointmentId);
        if (appointment != null) {
            appointment.setStatus(status);
            System.out.println("Appointment " + appointmentId + " status updated to: " + status);
            return true;
        }
        System.out.println("Appointment not found: " + appointmentId);
        return false;
    }

    public int getTotalAppointments() {
        return appointments.size();
    }

    public HashMap<String, Appointment> getAppointments() {
        return appointments;
    }

    @Override
    public String toString() {
        return "AppointmentManager [totalAppointments=" + appointments.size() + "]";
    }
}
