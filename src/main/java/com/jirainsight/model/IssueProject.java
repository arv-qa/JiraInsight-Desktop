package com.jirainsight.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

/**
 * Represents a Jira project
 */
public class IssueProject {
    
    @NotBlank(message = "Project key is required")
    @JsonProperty("key")
    private String key;
    
    @NotBlank(message = "Project name is required")
    @JsonProperty("name")
    private String name;
    
    // Default constructor
    public IssueProject() {}
    
    // Constructor with required fields
    public IssueProject(String key, String name) {
        this.key = key;
        this.name = name;
    }
    
    // Getters and Setters
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
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
        IssueProject that = (IssueProject) o;
        return Objects.equals(key, that.key);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
    
    @Override
    public String toString() {
        return "IssueProject{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
