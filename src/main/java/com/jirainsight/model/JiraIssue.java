package com.jirainsight.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Jira issue with all its details
 */
public class JiraIssue {
    
    @NotBlank(message = "Issue key is required")
    @JsonProperty("key")
    private String key;
    
    @NotBlank(message = "Summary is required")
    @JsonProperty("summary")
    private String summary;
    
    @JsonProperty("description")
    private String description;
    
    @NotNull(message = "Status is required")
    @JsonProperty("status")
    private IssueStatus status;
    
    @JsonProperty("assignee")
    private IssueUser assignee;
    
    @NotNull(message = "Reporter is required")
    @JsonProperty("reporter")
    private IssueUser reporter;
    
    @NotNull(message = "Priority is required")
    @JsonProperty("priority")
    private IssuePriority priority;
    
    @NotNull(message = "Issue type is required")
    @JsonProperty("issueType")
    private IssueType issueType;
    
    @NotNull(message = "Project is required")
    @JsonProperty("project")
    private IssueProject project;
    
    @JsonProperty("created")
    private String created;
    
    @JsonProperty("updated")
    private String updated;
    
    @JsonProperty("components")
    private List<IssueComponent> components = new ArrayList<>();
    
    @JsonProperty("fixVersions")
    private List<IssueVersion> fixVersions = new ArrayList<>();
    
    @JsonProperty("customfield_10020")
    private List<IssueSprint> sprints;
    
    @JsonProperty("subtasks")
    private List<IssueSubtask> subtasks = new ArrayList<>();
    
    @JsonProperty("comment")
    private IssueComments comment = new IssueComments();
    
    // Default constructor
    public JiraIssue() {}
    
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public IssueStatus getStatus() {
        return status;
    }
    
    public void setStatus(IssueStatus status) {
        this.status = status;
    }
    
    public IssueUser getAssignee() {
        return assignee;
    }
    
    public void setAssignee(IssueUser assignee) {
        this.assignee = assignee;
    }
    
    public IssueUser getReporter() {
        return reporter;
    }
    
    public void setReporter(IssueUser reporter) {
        this.reporter = reporter;
    }
    
    public IssuePriority getPriority() {
        return priority;
    }
    
    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }
    
    public IssueType getIssueType() {
        return issueType;
    }
    
    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }
    
    public IssueProject getProject() {
        return project;
    }
    
    public void setProject(IssueProject project) {
        this.project = project;
    }
    
    public String getCreated() {
        return created;
    }
    
    public void setCreated(String created) {
        this.created = created;
    }
    
    public String getUpdated() {
        return updated;
    }
    
    public void setUpdated(String updated) {
        this.updated = updated;
    }
    
    public List<IssueComponent> getComponents() {
        return components;
    }
    
    public void setComponents(List<IssueComponent> components) {
        this.components = components != null ? components : new ArrayList<>();
    }
    
    public List<IssueVersion> getFixVersions() {
        return fixVersions;
    }
    
    public void setFixVersions(List<IssueVersion> fixVersions) {
        this.fixVersions = fixVersions != null ? fixVersions : new ArrayList<>();
    }
    
    public List<IssueSprint> getSprints() {
        return sprints;
    }
    
    public void setSprints(List<IssueSprint> sprints) {
        this.sprints = sprints;
    }
    
    public List<IssueSubtask> getSubtasks() {
        return subtasks;
    }
    
    public void setSubtasks(List<IssueSubtask> subtasks) {
        this.subtasks = subtasks != null ? subtasks : new ArrayList<>();
    }
    
    public IssueComments getComment() {
        return comment;
    }
    
    public void setComment(IssueComments comment) {
        this.comment = comment != null ? comment : new IssueComments();
    }
    
    /**
     * Get the first sprint name if available
     */
    public String getSprintName() {
        if (sprints != null && !sprints.isEmpty()) {
            return sprints.get(0).getName();
        }
        return "No sprint";
    }
    
    /**
     * Get assignee display name or "Unassigned"
     */
    public String getAssigneeDisplayName() {
        return assignee != null ? assignee.getDisplayName() : "Unassigned";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JiraIssue jiraIssue = (JiraIssue) o;
        return Objects.equals(key, jiraIssue.key);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
    
    @Override
    public String toString() {
        return "JiraIssue{" +
                "key='" + key + '\'' +
                ", summary='" + summary + '\'' +
                ", status=" + status +
                ", assignee=" + assignee +
                '}';
    }
}
