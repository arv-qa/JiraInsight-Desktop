package com.jirainsight.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

/**
 * Represents the type of a Jira issue (Story, Bug, Task, etc.)
 */
public class IssueType {
    
    @NotBlank(message = "Issue type name is required")
    @JsonProperty("name")
    private String name;
    
    // Default constructor
    public IssueType() {}
    
    // Constructor with name
    public IssueType(String name) {
        this.name = name;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Get icon name for the issue type
     */
    public String getIconName() {
        if (name == null) {
            return "task";
        }
        
        String lowerName = name.toLowerCase();
        if (lowerName.contains("story")) {
            return "story";
        } else if (lowerName.contains("bug")) {
            return "bug";
        } else if (lowerName.contains("epic")) {
            return "epic";
        } else if (lowerName.contains("subtask") || lowerName.contains("sub-task")) {
            return "subtask";
        } else if (lowerName.contains("improvement")) {
            return "improvement";
        }
        
        return "task";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueType issueType = (IssueType) o;
        return Objects.equals(name, issueType.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
    @Override
    public String toString() {
        return "IssueType{" +
                "name='" + name + '\'' +
                '}';
    }
}
