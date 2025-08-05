package com.jirainsight.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * Represents a subtask of a Jira issue
 */
public class IssueSubtask {
    
    @NotBlank(message = "Subtask key is required")
    @JsonProperty("key")
    private String key;
    
    @NotBlank(message = "Subtask summary is required")
    @JsonProperty("summary")
    private String summary;
    
    @NotNull(message = "Subtask status is required")
    @JsonProperty("status")
    private SubtaskStatus status;
    
    // Default constructor
    public IssueSubtask() {}
    
    // Constructor with required fields
    public IssueSubtask(String key, String summary, SubtaskStatus status) {
        this.key = key;
        this.summary = summary;
        this.status = status;
    }
    
    // Getters and Setters
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public SubtaskStatus getStatus() {
        return status;
    }
    
    public void setStatus(SubtaskStatus status) {
        this.status = status;
    }
    
    /**
     * Check if subtask is completed
     */
    public boolean isCompleted() {
        if (status == null || status.getName() == null) {
            return false;
        }
        
        String statusName = status.getName().toLowerCase();
        return statusName.contains("done") || statusName.contains("closed") || statusName.contains("resolved");
    }
    
    /**
     * Check if subtask is in progress
     */
    public boolean isInProgress() {
        if (status == null || status.getName() == null) {
            return false;
        }
        
        String statusName = status.getName().toLowerCase();
        return statusName.contains("progress") || statusName.contains("review");
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueSubtask that = (IssueSubtask) o;
        return Objects.equals(key, that.key);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
    
    @Override
    public String toString() {
        return "IssueSubtask{" +
                "key='" + key + '\'' +
                ", summary='" + summary + '\'' +
                ", status=" + status +
                '}';
    }
    
    /**
     * Represents the status of a subtask
     */
    public static class SubtaskStatus {
        
        @NotBlank(message = "Subtask status name is required")
        @JsonProperty("name")
        private String name;
        
        // Default constructor
        public SubtaskStatus() {}
        
        // Constructor with name
        public SubtaskStatus(String name) {
            this.name = name;
        }
        
        // Getters and Setters
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SubtaskStatus that = (SubtaskStatus) o;
            return Objects.equals(name, that.name);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
        
        @Override
        public String toString() {
            return "SubtaskStatus{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
