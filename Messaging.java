/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package chatvideoconsultation;

/**
 *
 * @author Ubaid Elitebook
 */
import java.util.List;

public interface Messaging {
    void sendMessage(String senderId, String receiverId, String message);
    List<Message> getMessages(String userId1, String userId2);
}
