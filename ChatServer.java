package chatvideoconsultation;

import usermanagement.Doctor;
import usermanagement.Patient;
import usermanagement.User;
import java.util.*;

public class ChatServer implements Messaging {
    // Singleton instance
    private static ChatServer instance;

    // Store messages between users
    private Map<String, ArrayList<Message>> messageStore;

    // Registered users
    private Map<String, User> userRegistry;

    // Private constructor for Singleton pattern
    private ChatServer() {
        messageStore = new HashMap<>();
        userRegistry = new HashMap<>();
    }

    // Correct Singleton method
    public static synchronized ChatServer getInstance() {
        if (instance == null) {
            instance = new ChatServer();
        }
        return instance;
    }

    // Register a user into the system
    public void registerUser(User user) {
        userRegistry.put(user.getUserId(), user);
    }

    // Send a message from one user to another
    @Override
    public void sendMessage(String senderId, String receiverId, String message) {
        String conversationKey = getConversationKey(senderId, receiverId);
        messageStore.putIfAbsent(conversationKey, new ArrayList<>());
        messageStore.get(conversationKey).add(new Message(senderId, receiverId, message));
        System.out.println("Message sent from " + senderId + " to " + receiverId);
    }

    // Retrieve all messages between two users
    @Override
    public List<Message> getMessages(String userId1, String userId2) {
        return messageStore.getOrDefault(getConversationKey(userId1, userId2), new ArrayList<>());
    }


    private String getConversationKey(String userId1, String userId2) {
        return (userId1.compareTo(userId2) < 0) ? userId1 + "-" + userId2 : userId2 + "-" + userId1;
    }

   
    public boolean validateDoctorPatientRelationship(String doctorId, String patientId) {
        if (!userRegistry.containsKey(doctorId) || !userRegistry.containsKey(patientId)) {
            return false;
        }

        User doctorUser = userRegistry.get(doctorId);
        User patientUser = userRegistry.get(patientId);

        if (!(doctorUser instanceof Doctor) || !(patientUser instanceof Patient)) {
            return false;
        }

        Doctor doctor = (Doctor) doctorUser;
        Patient patient = (Patient) patientUser;

        // Check that both doctor and patient are linked
        return doctor.getPatients().contains(patient) && patient.getAssignedDoctors().contains(doctor);
    }

    // Get user by ID
    public User getUser(String userId) {
        return userRegistry.get(userId);
    }

    // Get the user's name by ID
    public String getUserName(String userId) {
        User user = userRegistry.get(userId);
        return user != null ? user.getName() : "Unknown User";
    }

    // Get all users in the system
    public Map<String, User> getUserRegistry() {
        return userRegistry;
    }
    // Add these methods to the ChatServer class
public void storeCallInvitation(String callId, String doctorId, String patientId, String meetingLink) {
    // Store the call information in a data structure (could be a Map or database)
    // For example, using a Map:
    Map<String, String> callInfo = new HashMap<>();
    callInfo.put("callId", callId);
    callInfo.put("doctorId", doctorId);
    callInfo.put("patientId", patientId);
    callInfo.put("meetingLink", meetingLink);
    callInfo.put("timestamp", String.valueOf(System.currentTimeMillis()));
    
    // Add to a List of pending calls for this patient
    List<Map<String, String>> patientCalls = pendingCallInvitations.getOrDefault(patientId, new ArrayList<>());
    patientCalls.add(callInfo);
    pendingCallInvitations.put(patientId, patientCalls);
}

// Add this field to the ChatServer class
private Map<String, List<Map<String, String>>> pendingCallInvitations = new HashMap<>();

// Method to get the pending calls for a patient
public List<Map<String, String>> getPendingCallInvitations(String patientId) {
    return pendingCallInvitations.getOrDefault(patientId, new ArrayList<>());
}

// Method to get the name of a user by ID
//public String getUserName(String userId) {
//    User user = getUser(userId);
//    return user != null ? user.getName() : "Unknown User";
//}

// Method to remove a call invitation once it's accepted or ended
public void removeCallInvitation(String patientId, String callId) {
    List<Map<String, String>> patientCalls = pendingCallInvitations.getOrDefault(patientId, new ArrayList<>());
    patientCalls.removeIf(call -> call.get("callId").equals(callId));
    pendingCallInvitations.put(patientId, patientCalls);
}

    public void updateCallStatus(String callId, String status) {
    // Find the call in all pending invitations and update its status
    for (String patientId : pendingCallInvitations.keySet()) {
        List<Map<String, String>> calls = pendingCallInvitations.get(patientId);
        for (Map<String, String> call : calls) {
            if (call.get("callId").equals(callId)) {
                call.put("status", status);
                break;
            }
        }
    }
}
}
