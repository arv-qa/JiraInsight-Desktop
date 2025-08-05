package com.jirainsight.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

/**
 * Represents the priority of a Jira issue
 */
public class IssuePriority {
    
    @NotBlank(message = "Priority name is required")
    @JsonProperty("name")
    private String name;
    
    // Default constructor
    public IssuePriority() {}
    
    // Constructor with name
    public IssuePriority(String name) {
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
     * Get priority level for sorting and color coding
     */
    public PriorityLevel getPriorityLevel() {
        if (name == null) {
            return PriorityLevel.NONE;
        }
        
        String lowerName = name.toLowerCase();
        if (lowerName.contains("highest") || lowerName.contains("critical")) {
            return PriorityLevel.HIGHEST;
        } else if (lowerName.contains("high")) {
            return PriorityLevel.HIGH;
        } else if (lowerName.contains("medium") || lowerName.contains("normal")) {
            return PriorityLevel.MEDIUM;
        } else if (lowerName.contains("low")) {
            return PriorityLevel.LOW;
        } else if (lowerName.contains("lowest") || lowerName.contains("trivial")) {
            return PriorityLevel.LOWEST;
        }
        
        return PriorityLevel.NONE;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssuePriority that = (IssuePriority) o;
        return Objects.equals(name, that.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
    @Override
    public String toString() {
        return "IssuePriority{" +
                "name='" + name + '\'' +
                '}';
    }
    
    /**
     * Enum for priority levels
     */
    public enum PriorityLevel {
        HIGHEST(5, "#d04437"),
        HIGH(4, "#f79232"),
        MEDIUM(3, "#ffd351"),
        LOW(2, "#14892c"),
        LOWEST(1, "#707070"),
        NONE(0, "#707070");
        
        private final int level;
        private final String color;
        
        PriorityLevel(int level, String color) {
            this.level = level;
            this.color = color;
        }
        
        public int getLevel() {
            return level;
        }
        
        public String getColor() {
            return color;
        }
    }
}
