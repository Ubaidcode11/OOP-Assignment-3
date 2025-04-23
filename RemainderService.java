package notificationsandremainders;

import appointment.Appointment;
import usermanagement.Patient;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.time.ZoneId;

public class RemainderService {
    private List<Notifiable> notificationChannels;
    public List<Notifiable> getNotificationChannels() {
    return this.notificationChannels;
}
    public Map<String, ReminderTask> scheduledReminders;

    private static final int APPOINTMENT_REMINDER_DAYS = 1;
    private static final int MEDICATION_REMINDER_HOURS = 1;

    public RemainderService() {
        this.notificationChannels = new ArrayList<>();
        this.scheduledReminders = new HashMap<>();
    }

    public void addNotificationChannel(Notifiable channel) {
        notificationChannels.add(channel);
        System.out.println("Added " + channel.getType() + " notification channel");
    }

    public List<String> scheduleAppointmentReminder(Patient patient, Appointment appointment) {
        List<String> reminderIds = new ArrayList<>();

        LocalDateTime appointmentDateTime = convertToLocalDateTime(appointment.getAppointmentDate());

        if (appointmentDateTime.isBefore(LocalDateTime.now())) {
            System.out.println("Cannot schedule reminder for a past appointment");
            return reminderIds;
        }

        LocalDateTime reminderTime = appointmentDateTime.minusDays(APPOINTMENT_REMINDER_DAYS);
        long minutesUntilReminder = java.time.Duration.between(LocalDateTime.now(), reminderTime).toMinutes();

        if (minutesUntilReminder < 1) {
            minutesUntilReminder = 1;
        }

        String doctorName = appointment.getDoctorId();
        String appointmentDate = appointmentDateTime.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        String subject = "Appointment Reminder";
        String message = "Reminder: You have an appointment with Dr. " + doctorName +
                " on " + appointmentDate + ". Please be on time.";

        for (Notifiable channel : notificationChannels) {
            String reminderId = channel.scheduleNotification(
                    patient.getContactInfo(),
                    subject,
                    message,
                    (int) minutesUntilReminder
            );

            ReminderTask task = new ReminderTask(
                    patient.getUserId(),
                    appointment.getAppointmentId(),
                    "APPOINTMENT",
                    channel.getType(),
                    reminderTime
            );

            scheduledReminders.put(reminderId, task);
            reminderIds.add(reminderId);
        }

        return reminderIds;
    }

    public List<String> scheduleMedicationReminder(Patient patient, String medicationName,
                                                   String dosage, LocalDateTime scheduledTime) {
        List<String> reminderIds = new ArrayList<>();

        if (scheduledTime.isBefore(LocalDateTime.now())) {
            System.out.println("Cannot schedule reminder for a past medication time");
            return reminderIds;
        }

        LocalDateTime reminderTime = scheduledTime.minusHours(MEDICATION_REMINDER_HOURS);
        long minutesUntilReminder = java.time.Duration.between(LocalDateTime.now(), reminderTime).toMinutes();

        if (minutesUntilReminder < 1) {
            minutesUntilReminder = 1;
        }

        String formattedTime = scheduledTime.format(
                DateTimeFormatter.ofPattern("HH:mm"));

        String subject = "Medication Reminder";
        String message = "Reminder: Please take " + medicationName + " (" + dosage + ") at " + formattedTime;

        for (Notifiable channel : notificationChannels) {
            String reminderId = channel.scheduleNotification(
                    patient.getContactInfo(),
                    subject,
                    message,
                    (int) minutesUntilReminder
            );

            ReminderTask task = new ReminderTask(
                    patient.getUserId(),
                    medicationName,
                    "MEDICATION",
                    channel.getType(),
                    reminderTime
            );

            scheduledReminders.put(reminderId, task);
            reminderIds.add(reminderId);
        }

        return reminderIds;
    }

    public boolean cancelReminder(String reminderId) {
        if (!scheduledReminders.containsKey(reminderId)) {
            System.out.println("Reminder not found: " + reminderId);
            return false;
        }

        ReminderTask task = scheduledReminders.get(reminderId);
        boolean cancelled = false;

        for (Notifiable channel : notificationChannels) {
            if (channel.getType() == task.getChannelType()) {
                cancelled = channel.cancelNotification(reminderId);
                break;
            }
        }

        if (cancelled) {
            scheduledReminders.remove(reminderId);
        }

        return cancelled;
    }

    public void sendImmediateAppointmentReminder(Patient patient, Appointment appointment) {
        LocalDateTime appointmentDateTime = convertToLocalDateTime(appointment.getAppointmentDate());

        String doctorName = appointment.getDoctorId();
        String appointmentDate = appointmentDateTime.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        String subject = "Upcoming Appointment";
        String message = "REMINDER: You have an appointment with Dr. " + doctorName +
                " on " + appointmentDate + ". Please be on time.";

        for (Notifiable channel : notificationChannels) {
            channel.sendNotification(
                    patient.getContactInfo(),
                    subject,
                    message
            );
        }
    }

    public void sendImmediateMedicationReminder(Patient patient, String medicationName, String dosage) {
        String subject = "Take Your Medication";
        String message = "REMINDER: It's time to take " + medicationName + " (" + dosage + ")";

        for (Notifiable channel : notificationChannels) {
            channel.sendNotification(
                    patient.getContactInfo(),
                    subject,
                    message
            );
        }
    }

    public List<ReminderTask> getPatientReminders(String patientId) {
        List<ReminderTask> patientReminders = new ArrayList<>();

        for (ReminderTask task : scheduledReminders.values()) {
            if (task.getPatientId().equals(patientId)) {
                patientReminders.add(task);
            }
        }

        return patientReminders;
    }

    public void processScheduledReminders() {
        for (Notifiable channel : notificationChannels) {
            if (channel instanceof EmailNotification) {
                ((EmailNotification) channel).processScheduledEmails();
            } else if (channel instanceof SMSNotification) {
                ((SMSNotification) channel).processScheduledMessages();
            }
        }
    }

    private LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public String scheduleReminder(String patientEmail, String subject, String message, int minutesBefore, Notifiable.NotificationType notificationType) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public class ReminderTask {
        private String patientId;
        private String targetId;
        private String reminderType;
        private Notifiable.NotificationType channelType;
        private LocalDateTime scheduledTime;

        public ReminderTask(String patientId, String targetId, String reminderType,
                            Notifiable.NotificationType channelType, LocalDateTime scheduledTime) {
            this.patientId = patientId;
            this.targetId = targetId;
            this.reminderType = reminderType;
            this.channelType = channelType;
            this.scheduledTime = scheduledTime;
        }

        public String getPatientId() {
            return patientId;
        }

        public String getTargetId() {
            return targetId;
        }

        public String getReminderType() {
            return reminderType;
        }

        public Notifiable.NotificationType getChannelType() {
            return channelType;
        }

        public LocalDateTime getScheduledTime() {
            return scheduledTime;
        }

        @Override
        public String toString() {
            return "Reminder [type=" + reminderType + ", patient=" + patientId +
                    ", channel=" + channelType + ", scheduled=" + scheduledTime + "]";
        }
    }
}
