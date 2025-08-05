package com.jirainsight.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the result of a Jira search query
 */
public class JiraSearchResult {
    
    @NotNull(message = "Issues list is required")
    @JsonProperty("issues")
    private List<JiraIssue> issues = new ArrayList<>();
    
    @JsonProperty("total")
    private int total;
    
    @JsonProperty("startAt")
    private int startAt;
    
    @JsonProperty("maxResults")
    private int maxResults;
    
    // Default constructor
    public JiraSearchResult() {}
    
    // Constructor with required fields
    public JiraSearchResult(List<JiraIssue> issues, int total, int startAt, int maxResults) {
        this.issues = issues != null ? issues : new ArrayList<>();
        this.total = total;
        this.startAt = startAt;
        this.maxResults = maxResults;
    }
    
    // Getters and Setters
    public List<JiraIssue> getIssues() {
        return issues;
    }
    
    public void setIssues(List<JiraIssue> issues) {
        this.issues = issues != null ? issues : new ArrayList<>();
    }
    
    public int getTotal() {
        return total;
    }
    
    public void setTotal(int total) {
        this.total = total;
    }
    
    public int getStartAt() {
        return startAt;
    }
    
    public void setStartAt(int startAt) {
        this.startAt = startAt;
    }
    
    public int getMaxResults() {
        return maxResults;
    }
    
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }
    
    /**
     * Get the number of issues returned in this result
     */
    public int getIssueCount() {
        return issues.size();
    }
    
    /**
     * Check if there are more results available
     */
    public boolean hasMoreResults() {
        return (startAt + issues.size()) < total;
    }
    
    /**
     * Check if this is an empty result
     */
    public boolean isEmpty() {
        return issues.isEmpty();
    }
    
    /**
     * Add an issue to the result
     */
    public void addIssue(JiraIssue issue) {
        if (issue != null) {
            this.issues.add(issue);
        }
    }
    
    /**
     * Get a summary string of the search result
     */
    public String getSummary() {
        if (isEmpty()) {
            return "No issues found";
        } else if (total == issues.size()) {
            return String.format("%d issue%s found", total, total == 1 ? "" : "s");
        } else {
            return String.format("Showing %d of %d issues", issues.size(), total);
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JiraSearchResult that = (JiraSearchResult) o;
        return total == that.total &&
               startAt == that.startAt &&
               maxResults == that.maxResults &&
               Objects.equals(issues, that.issues);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(issues, total, startAt, maxResults);
    }
    
    @Override
    public String toString() {
        return "JiraSearchResult{" +
                "issues=" + issues.size() +
                ", total=" + total +
                ", startAt=" + startAt +
                ", maxResults=" + maxResults +
                '}';
    }
}
