package com.jirainsight.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

/**
 * Represents a version/fix version of a Jira issue
 */
public class IssueVersion {
    
    @NotBlank(message = "Version name is required")
    @JsonProperty("name")
    private String name;
    
    // Default constructor
    public IssueVersion() {}
    
    // Constructor with name
    public IssueVersion(String name) {
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
        IssueVersion that = (IssueVersion) o;
        return Objects.equals(name, that.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
    @Override
    public String toString() {
        return "IssueVersion{" +
                "name='" + name + '\'' +
                '}';
    }
}
