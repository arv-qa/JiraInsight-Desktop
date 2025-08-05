package com.jirainsight.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

/**
 * Represents a sprint associated with a Jira issue
 */
public class IssueSprint {
    
    @NotBlank(message = "Sprint name is required")
    @JsonProperty("name")
    private String name;
    
    // Default constructor
    public IssueSprint() {}
    
    // Constructor with name
    public IssueSprint(String name) {
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
        IssueSprint that = (IssueSprint) o;
        return Objects.equals(name, that.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
    @Override
    public String toString() {
        return "IssueSprint{" +
                "name='" + name + '\'' +
                '}';
    }
}
