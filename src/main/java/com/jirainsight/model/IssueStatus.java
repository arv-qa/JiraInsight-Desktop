package com.jirainsight.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * Represents the status of a Jira issue
 */
public class IssueStatus {
    
    @NotBlank(message = "Status name is required")
    @JsonProperty("name")
    private String name;
    
    @NotNull(message = "Status category is required")
    @JsonProperty("statusCategory")
    private StatusCategory statusCategory;
    
    // Default constructor
    public IssueStatus() {}
    
    // Constructor with required fields
    public IssueStatus(String name, StatusCategory statusCategory) {
        this.name = name;
        this.statusCategory = statusCategory;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public StatusCategory getStatusCategory() {
        return statusCategory;
    }
    
    public void setStatusCategory(StatusCategory statusCategory) {
        this.statusCategory = statusCategory;
    }
    
    /**
     * Get the status category key for color coding
     */
    public String getStatusCategoryKey() {
        return statusCategory != null ? statusCategory.getKey() : "new";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueStatus that = (IssueStatus) o;
        return Objects.equals(name, that.name) &&
               Objects.equals(statusCategory, that.statusCategory);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, statusCategory);
    }
    
    @Override
    public String toString() {
        return "IssueStatus{" +
                "name='" + name + '\'' +
                ", statusCategory=" + statusCategory +
                '}';
    }
    
    /**
     * Represents the status category
     */
    public static class StatusCategory {
        
        @NotBlank(message = "Status category key is required")
        @JsonProperty("key")
        private String key;
        
        // Default constructor
        public StatusCategory() {}
        
        // Constructor with key
        public StatusCategory(String key) {
            this.key = key;
        }
        
        // Getters and Setters
        public String getKey() {
            return key;
        }
        
        public void setKey(String key) {
            this.key = key;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StatusCategory that = (StatusCategory) o;
            return Objects.equals(key, that.key);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
        
        @Override
        public String toString() {
            return "StatusCategory{" +
                    "key='" + key + '\'' +
                    '}';
        }
    }
}
