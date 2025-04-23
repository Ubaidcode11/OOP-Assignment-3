/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package usermanagement;

/**
 *
 * @author Ubaid Elitebook
 */
public class User {
    private String userId;
    private String name;
    private String contactInfo;
    private String role;

    public User(String userId, String name, String contactInfo, String role) {
        this.userId = userId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", name=" + name + ", role=" + role + "]";
    }
    
    
}
