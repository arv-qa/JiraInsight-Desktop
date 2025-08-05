package com.jirainsight.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents Jira authentication credentials
 */
public class JiraCredentials {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("userId")
    private String userId;
    
    @NotBlank(message = "Domain is required")
    @JsonProperty("domain")
    private String domain;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Valid email is required")
    @JsonProperty("email")
    private String email;
    
    @NotBlank(message = "API token is required")
    @JsonProperty("apiToken")
    private String apiToken;
    
    @JsonProperty("isActive")
    private boolean isActive = true;
    
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
    
    @JsonProperty("rememberCredentials")
    private boolean rememberCredentials = false;
    
    // Default constructor
    public JiraCredentials() {
        this.createdAt = LocalDateTime.now();
    }
    
    // Constructor with required fields
    public JiraCredentials(String domain, String email, String apiToken) {
        this();
        this.domain = domain;
        this.email = email;
        this.apiToken = apiToken;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getDomain() {
        return domain;
    }
    
    public void setDomain(String domain) {
        this.domain = domain;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getApiToken() {
        return apiToken;
    }
    
    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public boolean isRememberCredentials() {
        return rememberCredentials;
    }
    
    public void setRememberCredentials(boolean rememberCredentials) {
        this.rememberCredentials = rememberCredentials;
    }
    
    /**
     * Get the full Jira URL for this domain
     */
    public String getJiraUrl() {
        return "https://" + domain;
    }
    
    /**
     * Get the base64 encoded authentication string
     */
    public String getAuthenticationString() {
        String credentials = email + ":" + apiToken;
        return java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JiraCredentials that = (JiraCredentials) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(domain, that.domain) &&
               Objects.equals(email, that.email);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, domain, email);
    }
    
    @Override
    public String toString() {
        return "JiraCredentials{" +
                "id='" + id + '\'' +
                ", domain='" + domain + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                '}';
    }
}
