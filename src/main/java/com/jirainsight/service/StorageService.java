package com.jirainsight.service;

import com.jirainsight.model.JiraCredentials;
import com.jirainsight.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory storage service for managing users and credentials
 * In a production application, this would be replaced with a proper database
 */
public class StorageService {
    
    private static final Logger logger = LoggerFactory.getLogger(StorageService.class);
    
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private final Map<String, JiraCredentials> credentials = new ConcurrentHashMap<>();
    private final Map<String, String> userCredentialsMapping = new ConcurrentHashMap<>();
    
    private static StorageService instance;
    
    private StorageService() {}
    
    /**
     * Get singleton instance
     */
    public static synchronized StorageService getInstance() {
        if (instance == null) {
            instance = new StorageService();
        }
        return instance;
    }
    
    /**
     * Create a new user
     */
    public User createUser(String username, String password) {
        User user = new User(username, password);
        user.setId(UUID.randomUUID().toString());
        
        users.put(user.getId(), user);
        logger.info("Created user: {}", username);
        
        return user;
    }
    
    /**
     * Get user by ID
     */
    public User getUserById(String userId) {
        return users.get(userId);
    }
    
    /**
     * Get user by username
     */
    public User getUserByUsername(String username) {
        return users.values().stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Save Jira credentials for a user
     */
    public JiraCredentials saveJiraCredentials(String userId, JiraCredentials jiraCredentials) {
        // Validate user exists
        User user = getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
        
        // Set IDs and user reference
        jiraCredentials.setId(UUID.randomUUID().toString());
        jiraCredentials.setUserId(userId);
        
        // Store credentials
        credentials.put(jiraCredentials.getId(), jiraCredentials);
        userCredentialsMapping.put(userId, jiraCredentials.getId());
        
        logger.info("Saved Jira credentials for user {} (domain: {})", userId, jiraCredentials.getDomain());
        
        return jiraCredentials;
    }
    
    /**
     * Get Jira credentials for a user
     */
    public JiraCredentials getJiraCredentials(String userId) {
        String credentialsId = userCredentialsMapping.get(userId);
        if (credentialsId == null) {
            return null;
        }
        
        return credentials.get(credentialsId);
    }
    
    /**
     * Update Jira credentials
     */
    public JiraCredentials updateJiraCredentials(JiraCredentials updatedCredentials) {
        if (updatedCredentials.getId() == null) {
            throw new IllegalArgumentException("Credentials ID is required for update");
        }
        
        JiraCredentials existing = credentials.get(updatedCredentials.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Credentials not found: " + updatedCredentials.getId());
        }
        
        // Update fields
        existing.setDomain(updatedCredentials.getDomain());
        existing.setEmail(updatedCredentials.getEmail());
        existing.setApiToken(updatedCredentials.getApiToken());
        existing.setActive(updatedCredentials.isActive());
        existing.setRememberCredentials(updatedCredentials.isRememberCredentials());
        
        logger.info("Updated Jira credentials: {}", updatedCredentials.getId());
        
        return existing;
    }
    
    /**
     * Delete Jira credentials for a user
     */
    public boolean deleteJiraCredentials(String userId) {
        String credentialsId = userCredentialsMapping.get(userId);
        if (credentialsId == null) {
            return false;
        }
        
        credentials.remove(credentialsId);
        userCredentialsMapping.remove(userId);
        
        logger.info("Deleted Jira credentials for user: {}", userId);
        
        return true;
    }
    
    /**
     * Check if user has Jira credentials
     */
    public boolean hasJiraCredentials(String userId) {
        return userCredentialsMapping.containsKey(userId);
    }
    
    /**
     * Get all users (for admin purposes)
     */
    public Map<String, User> getAllUsers() {
        return new ConcurrentHashMap<>(users);
    }
    
    /**
     * Get all credentials (for admin purposes)
     */
    public Map<String, JiraCredentials> getAllCredentials() {
        return new ConcurrentHashMap<>(credentials);
    }
    
    /**
     * Clear all data (for testing purposes)
     */
    public void clearAll() {
        users.clear();
        credentials.clear();
        userCredentialsMapping.clear();
        logger.info("Cleared all storage data");
    }
    
    /**
     * Get storage statistics
     */
    public StorageStats getStats() {
        return new StorageStats(users.size(), credentials.size());
    }
    
    /**
     * Storage statistics
     */
    public static class StorageStats {
        private final int userCount;
        private final int credentialsCount;
        
        public StorageStats(int userCount, int credentialsCount) {
            this.userCount = userCount;
            this.credentialsCount = credentialsCount;
        }
        
        public int getUserCount() {
            return userCount;
        }
        
        public int getCredentialsCount() {
            return credentialsCount;
        }
        
        @Override
        public String toString() {
            return "StorageStats{" +
                    "userCount=" + userCount +
                    ", credentialsCount=" + credentialsCount +
                    '}';
        }
    }
}
