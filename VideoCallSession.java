package chatvideoconsultation;
import java.time.LocalDateTime;



public class VideoCallSession {
    private String callId;
    private String initiatorId;
    private String receiverId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isActive;
    private boolean participantJoined;
    private String meetingLink;

    public VideoCallSession(String callId, String initiatorId, String receiverId, String meetingLink) {
        this.callId = callId;
        this.initiatorId = initiatorId;
        this.receiverId = receiverId;
        this.startTime = LocalDateTime.now();
        this.isActive = true;
        this.participantJoined = false;
        this.meetingLink = meetingLink;
    }

    // Getter for meeting link
    public String getMeetingLink() {
        return meetingLink;
    }

    // Other getters and setters...
    public String getCallId() {
        return callId;
    }

    public String getInitiatorId() {
        return initiatorId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isParticipantJoined() {
        return participantJoined;
    }

    public void setParticipantJoined(boolean participantJoined) {
        this.participantJoined = participantJoined;
    }

    public void endCall() {
        this.endTime = LocalDateTime.now();
        this.isActive = false;
    }
}