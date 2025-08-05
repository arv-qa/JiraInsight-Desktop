package com.jirainsight.service;

import com.jirainsight.model.JiraCredentials;
import com.jirainsight.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for StorageService
 */
public class StorageServiceTest {
    
    private StorageService storageService;
    
    @BeforeEach
    public void setUp() {
        storageService = StorageService.getInstance();
        storageService.clearAll();
    }
    
    @Test
    public void testCreateUser() {
        User user = storageService.createUser("testuser", "password");
        
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password", user.getPassword());
    }
    
    @Test
    public void testGetUserById() {
        User user = storageService.createUser("testuser", "password");
        User retrieved = storageService.getUserById(user.getId());
        
        assertNotNull(retrieved);
        assertEquals(user.getId(), retrieved.getId());
        assertEquals(user.getUsername(), retrieved.getUsername());
    }
    
    @Test
    public void testSaveAndGetJiraCredentials() {
        User user = storageService.createUser("testuser", "password");
        JiraCredentials credentials = new JiraCredentials("test.atlassian.net", "test@example.com", "token123");
        
        JiraCredentials saved = storageService.saveJiraCredentials(user.getId(), credentials);
        
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(user.getId(), saved.getUserId());
        
        JiraCredentials retrieved = storageService.getJiraCredentials(user.getId());
        assertNotNull(retrieved);
        assertEquals(saved.getId(), retrieved.getId());
        assertEquals("test.atlassian.net", retrieved.getDomain());
    }
    
    @Test
    public void testHasJiraCredentials() {
        User user = storageService.createUser("testuser", "password");
        
        assertFalse(storageService.hasJiraCredentials(user.getId()));
        
        JiraCredentials credentials = new JiraCredentials("test.atlassian.net", "test@example.com", "token123");
        storageService.saveJiraCredentials(user.getId(), credentials);
        
        assertTrue(storageService.hasJiraCredentials(user.getId()));
    }
    
    @Test
    public void testDeleteJiraCredentials() {
        User user = storageService.createUser("testuser", "password");
        JiraCredentials credentials = new JiraCredentials("test.atlassian.net", "test@example.com", "token123");
        storageService.saveJiraCredentials(user.getId(), credentials);
        
        assertTrue(storageService.hasJiraCredentials(user.getId()));
        
        boolean deleted = storageService.deleteJiraCredentials(user.getId());
        assertTrue(deleted);
        assertFalse(storageService.hasJiraCredentials(user.getId()));
    }
}
