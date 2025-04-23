package chatvideoconsultation;

import java.util.*;
import java.time.LocalDateTime;

public class VideoCall implements VideoConferencing {
    private Map<String, VideoCallSession> activeCalls;
    private int callCounter;

    public VideoCall() {
        activeCalls = new HashMap<>();
        callCounter = 0;
    }

    @Override
    public String initiateVideoCall(String senderId, String receiverId) {
        String callId = "call-" + (++callCounter);
        String meetingLink = generateMeetingLink(senderId, receiverId);
        VideoCallSession session = new VideoCallSession(callId, senderId, receiverId, meetingLink);
        activeCalls.put(callId, session);
        System.out.println("Video call initiated from " + senderId + " to " + receiverId);
        System.out.println("Meeting link: " + meetingLink);
        return callId;
    }

    @Override
    public void endVideoCall(String callId) {
        if (activeCalls.containsKey(callId)) {
            VideoCallSession session = activeCalls.get(callId);
            session.endCall();
            System.out.println("Video call ended: " + callId);
            System.out.println("Call duration: " + calculateDuration(session) + " minutes");
            // Remove call from active calls
            activeCalls.remove(callId);
        } else {
            System.out.println("Call not found: " + callId);
        }
    }
    
    // Add methods for patient to join a call
    public void joinCall(String callId) {
        if (activeCalls.containsKey(callId)) {
            VideoCallSession session = activeCalls.get(callId);
            session.setParticipantJoined(true);
            System.out.println("Joined video call: " + callId);
        } else {
            System.out.println("Call not found: " + callId);
        }
    }
    
    public void leaveCall(String callId) {
        if (activeCalls.containsKey(callId)) {
            VideoCallSession session = activeCalls.get(callId);
            session.setParticipantJoined(false);
            System.out.println("Left video call: " + callId);
        } else {
            System.out.println("Call not found: " + callId);
        }
    }

    public VideoCallSession getCallSession(String callId) {
        return activeCalls.get(callId);
    }

    public List<VideoCallSession> getActiveCallsForUser(String userId) {
        List<VideoCallSession> userCalls = new ArrayList<>();
        for (VideoCallSession session : activeCalls.values()) {
            if ((session.getInitiatorId().equals(userId) || 
                 session.getReceiverId().equals(userId)) && 
                 session.isActive()) {
                userCalls.add(session);
            }
        }
        return userCalls;
    }
    
    // Get all pending calls for a specific user
    public List<String> getPendingCallsForUser(String userId) {
        List<String> pendingCalls = new ArrayList<>();
        for (Map.Entry<String, VideoCallSession> entry : activeCalls.entrySet()) {
            VideoCallSession session = entry.getValue();
            if (session.getReceiverId().equals(userId) && session.isActive() && !session.isParticipantJoined()) {
                pendingCalls.add(entry.getKey());
            }
        }
        return pendingCalls;
    }

    // Get all active calls
    public Map<String, VideoCallSession> getActiveCalls() {
        return activeCalls;
    }

    private String generateMeetingLink(String senderId, String receiverId) {
        String meetId = senderId.substring(0, Math.min(3, senderId.length())) + 
                       receiverId.substring(0, Math.min(3, receiverId.length())) + 
                       System.currentTimeMillis() % 10000;
        return "https://meet.example.com/" + meetId;
    }

    private double calculateDuration(VideoCallSession session) {
        if (session.getEndTime() == null) return 0;
        long durationSeconds = java.time.Duration.between(session.getStartTime(), session.getEndTime()).getSeconds();
        return durationSeconds / 60.0;
    }


}