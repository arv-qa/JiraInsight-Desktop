package com.jirainsight.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

/**
 * Represents a user associated with a Jira issue (assignee, reporter, etc.)
 */
public class IssueUser {
    
    @NotBlank(message = "Display name is required")
    @JsonProperty("displayName")
    private String displayName;
    
    @NotBlank(message = "Account ID is required")
    @JsonProperty("accountId")
    private String accountId;
    
    // Default constructor
    public IssueUser() {}
    
    // Constructor with required fields
    public IssueUser(String displayName, String accountId) {
        this.displayName = displayName;
        this.accountId = accountId;
    }
    
    // Getters and Setters
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public String getAccountId() {
        return accountId;
    }
    
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    
    /**
     * Get initials from display name for avatar display
     */
    public String getInitials() {
        if (displayName == null || displayName.trim().isEmpty()) {
            return "??";
        }
        
        String[] parts = displayName.trim().split("\\s+");
        StringBuilder initials = new StringBuilder();
        
        for (String part : parts) {
            if (!part.isEmpty()) {
                initials.append(part.charAt(0));
                if (initials.length() >= 2) {
                    break;
                }
            }
        }
        
        return initials.toString().toUpperCase();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueUser issueUser = (IssueUser) o;
        return Objects.equals(accountId, issueUser.accountId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }
    
    @Override
    public String toString() {
        return "IssueUser{" +
                "displayName='" + displayName + '\'' +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}
