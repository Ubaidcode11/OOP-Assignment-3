package usermanagement;

import java.util.ArrayList;
import java.util.HashMap;

public class Administrator extends User {
    private ArrayList<String> systemLogs;
    private HashMap<String, User> users;

    public Administrator(String userId, String name, String contactInfo) {
        super(userId, name, contactInfo, "Administrator");
        this.systemLogs = new ArrayList<>();
        this.users = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.getUserId(), user);
        logAction("Added user: " + user.getUserId());
    }

    public void removeUser(String userId) {
        if (users.containsKey(userId)) {
            users.remove(userId);
            logAction("Removed user: " + userId);
        } else {
            System.out.println("User not found: " + userId);
        }
    }

    public User getUser(String userId) {
        return users.get(userId);  // Fetching a single user by ID
    }

    public HashMap<String, User> getUsers() {
        return users;  // Fetching the entire list of users as a map
    }

    private void logAction(String action) {
        String logEntry = System.currentTimeMillis() + ": " + action;
        systemLogs.add(logEntry);
        System.out.println("LOG: " + logEntry);
    }

    @Override
    public String toString() {
        return "Administrator [id=" + getUserId() + ", name=" + getName() + "]";
    }
}