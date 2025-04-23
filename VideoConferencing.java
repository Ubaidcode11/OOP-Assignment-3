/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package chatvideoconsultation;

/**
 *
 * @author Ubaid Elitebook
 */

public interface VideoConferencing {
    String initiateVideoCall(String senderId, String receiverId);
    void endVideoCall(String callId);
}

