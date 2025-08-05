package com.jirainsight.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

/**
 * Represents a component of a Jira issue
 */
public class IssueComponent {
    
    @NotBlank(message = "Component name is required")
    @JsonProperty("name")
    private String name;
    
    // Default constructor
    public IssueComponent() {}
    
    // Constructor with name
    public IssueComponent(String name) {
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
        IssueComponent that = (IssueComponent) o;
        return Objects.equals(name, that.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
    @Override
    public String toString() {
        return "IssueComponent{" +
                "name='" + name + '\'' +
                '}';
    }
}
