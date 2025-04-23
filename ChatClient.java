package chatvideoconsultation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatClient {
    private String userId;
    private ChatServer server;
    private VideoCall videoCallService;

    public ChatClient(String userId) {
        this.userId = userId;
        this.server = ChatServer.getInstance();
        this.videoCallService = new VideoCall();
    }

    public void sendMessage(String receiverId, String message) {
        server.sendMessage(userId, receiverId, message);
    }

    public List<Message> getConversationHistory(String otherUserId) {
        return server.getMessages(userId, otherUserId);
    }

    public void displayConversation(String otherUserId) {
        List<Message> messages = getConversationHistory(otherUserId);
        System.out.println("Conversation with " + otherUserId + ":");
        if (messages.isEmpty()) {
            System.out.println("No messages yet.");
        } else {
            for (Message message : messages) {
                String sender = message.getSenderId().equals(userId) ? "You" : message.getSenderId();
                System.out.println(sender + " (" + message.getTimestamp() + "): " + message.getContent());
            }
        }
    }

    public Map<String, String> initiateVideoCall(String receiverId) {
        String callId = videoCallService.initiateVideoCall(userId, receiverId);
        VideoCallSession session = videoCallService.getCallSession(callId);

        Map<String, String> callInfo = new HashMap<>();
        callInfo.put("callId", callId);
        callInfo.put("meetingLink", session.getMeetingLink());

        // Store the call information in the server for the patient to retrieve
        server.storeCallInvitation(callId, userId, receiverId, session.getMeetingLink());

        return callInfo;
    }

    public void endVideoCall(String callId) {
        videoCallService.endVideoCall(callId);
        // Clean up any pending invitations
        VideoCallSession session = videoCallService.getCallSession(callId);
        if (session != null) {
            server.removeCallInvitation(session.getReceiverId(), callId);
        }
    }

    public List<Map<String, String>> getPendingCallInvitations() {
        return server.getPendingCallInvitations(userId);
    }

    public String getCallerName(String callId) {
        VideoCallSession session = videoCallService.getCallSession(callId);
        if (session != null) {
            String callerId = session.getInitiatorId();
            return server.getUserName(callerId);
        }
        return "Unknown";
    }

    public void joinVideoCall(String callId) {
        VideoCallSession session = videoCallService.getCallSession(callId);
        if (session != null && session.getReceiverId().equals(userId)) {
            videoCallService.joinCall(callId);
            // Notify the server that the call has been joined
            server.updateCallStatus(callId, "joined");
        } else {
            System.out.println("Cannot join call. You are not the intended recipient.");
        }
    }

    public void leaveVideoCall(String callId) {
        videoCallService.leaveCall(callId);
        // Notify the server that the patient has left the call
        server.updateCallStatus(callId, "left");
    }

    public List<VideoCallSession> getActiveCalls() {
        return videoCallService.getActiveCallsForUser(userId);
    }
}
