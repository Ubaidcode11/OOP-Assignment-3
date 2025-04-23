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
public class FeedBack {
    private String feedbackId;
    private String doctorId;
    private String patientId;
    private String feedbackText;
    private Date feedbackDate;

    public FeedBack(String doctorId, String patientId, String feedbackText) {
        this.feedbackId = generateFeedbackId();
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.feedbackText = feedbackText;
        this.feedbackDate = new Date();
    }

    private String generateFeedbackId() {
        return "FB-" + System.currentTimeMillis();
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    @Override
    public String toString() {
        return "Feedback [id=" + feedbackId + ", doctor=" + doctorId + ", patient=" + patientId 
            + ", date=" + feedbackDate + ", text=" + feedbackText + "]";
    }
    
}
