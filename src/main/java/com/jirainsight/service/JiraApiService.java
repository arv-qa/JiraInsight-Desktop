package com.jirainsight.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jirainsight.model.*;
import com.jirainsight.model.IssueComments.IssueComment;
import com.jirainsight.model.IssueStatus.StatusCategory;
import com.jirainsight.model.IssueSubtask.SubtaskStatus;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Service for interacting with Jira REST API v3
 */
public class JiraApiService {
    
    private static final Logger logger = LoggerFactory.getLogger(JiraApiService.class);
    
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    
    public JiraApiService() {
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }
    
    /**
     * Test connection to Jira with the provided credentials
     */
    public CompletableFuture<Boolean> testConnection(JiraCredentials credentials) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String url = credentials.getJiraUrl() + "/rest/api/3/myself";
                
                Request request = new Request.Builder()
                        .url(url)
                        .header("Authorization", "Basic " + credentials.getAuthenticationString())
                        .header("Accept", "application/json")
                        .build();
                
                try (Response response = httpClient.newCall(request).execute()) {
                    boolean success = response.isSuccessful();
                    logger.info("Connection test to {} - {}", credentials.getDomain(), 
                              success ? "SUCCESS" : "FAILED (" + response.code() + ")");
                    return success;
                }
                
            } catch (Exception e) {
                logger.error("Connection test failed for {}: {}", credentials.getDomain(), e.getMessage());
                return false;
            }
        });
    }
    
    /**
     * Get a single issue by key
     */
    public CompletableFuture<JiraIssue> getIssueByKey(String issueKey, JiraCredentials credentials) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String url = credentials.getJiraUrl() + "/rest/api/3/issue/" + issueKey + 
                           "?expand=subtasks,comments";
                
                Request request = new Request.Builder()
                        .url(url)
                        .header("Authorization", "Basic " + credentials.getAuthenticationString())
                        .header("Accept", "application/json")
                        .build();
                
                try (Response response = httpClient.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new RuntimeException("Failed to fetch issue: " + response.code() + " - " + response.message());
                    }
                    
                    String responseBody = response.body().string();
                    JsonNode jsonNode = objectMapper.readTree(responseBody);
                    
                    JiraIssue issue = parseIssueFromJson(jsonNode);
                    logger.info("Successfully fetched issue: {}", issueKey);
                    return issue;
                }
                
            } catch (Exception e) {
                logger.error("Failed to fetch issue {}: {}", issueKey, e.getMessage());
                throw new RuntimeException("Failed to fetch issue: " + e.getMessage(), e);
            }
        });
    }
    
    /**
     * Search for issues using JQL
     */
    public CompletableFuture<JiraSearchResult> searchIssues(String jql, JiraCredentials credentials) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String url = credentials.getJiraUrl() + "/rest/api/3/search";
                
                // Create request body
                String requestBody = objectMapper.writeValueAsString(new SearchRequest(jql, 50, getSearchFields()));
                
                Request request = new Request.Builder()
                        .url(url)
                        .header("Authorization", "Basic " + credentials.getAuthenticationString())
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .post(RequestBody.create(requestBody, MediaType.get("application/json")))
                        .build();
                
                try (Response response = httpClient.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new RuntimeException("Search failed: " + response.code() + " - " + response.message());
                    }
                    
                    String responseBody = response.body().string();
                    JsonNode jsonNode = objectMapper.readTree(responseBody);
                    
                    JiraSearchResult result = parseSearchResultFromJson(jsonNode);
                    logger.info("Search completed: {} issues found", result.getTotal());
                    return result;
                }
                
            } catch (Exception e) {
                logger.error("Search failed for JQL '{}': {}", jql, e.getMessage());
                throw new RuntimeException("Search failed: " + e.getMessage(), e);
            }
        });
    }
    
    /**
     * Parse a single issue from JSON response
     */
    private JiraIssue parseIssueFromJson(JsonNode jsonNode) {
        JiraIssue issue = new JiraIssue();
        
        // Basic fields
        issue.setKey(jsonNode.path("key").asText());
        
        JsonNode fields = jsonNode.path("fields");
        issue.setSummary(fields.path("summary").asText());
        issue.setDescription(fields.path("description").asText(""));
        issue.setCreated(fields.path("created").asText());
        issue.setUpdated(fields.path("updated").asText());
        
        // Status
        JsonNode statusNode = fields.path("status");
        IssueStatus status = new IssueStatus();
        status.setName(statusNode.path("name").asText());
        StatusCategory statusCategory = new StatusCategory();
        statusCategory.setKey(statusNode.path("statusCategory").path("key").asText());
        status.setStatusCategory(statusCategory);
        issue.setStatus(status);
        
        // Assignee
        JsonNode assigneeNode = fields.path("assignee");
        if (!assigneeNode.isNull()) {
            IssueUser assignee = new IssueUser();
            assignee.setDisplayName(assigneeNode.path("displayName").asText());
            assignee.setAccountId(assigneeNode.path("accountId").asText());
            issue.setAssignee(assignee);
        }
        
        // Reporter
        JsonNode reporterNode = fields.path("reporter");
        IssueUser reporter = new IssueUser();
        reporter.setDisplayName(reporterNode.path("displayName").asText());
        reporter.setAccountId(reporterNode.path("accountId").asText());
        issue.setReporter(reporter);
        
        // Priority
        JsonNode priorityNode = fields.path("priority");
        IssuePriority priority = new IssuePriority();
        priority.setName(priorityNode.path("name").asText("None"));
        issue.setPriority(priority);
        
        // Issue Type
        JsonNode issueTypeNode = fields.path("issuetype");
        IssueType issueType = new IssueType();
        issueType.setName(issueTypeNode.path("name").asText());
        issue.setIssueType(issueType);
        
        // Project
        JsonNode projectNode = fields.path("project");
        IssueProject project = new IssueProject();
        project.setKey(projectNode.path("key").asText());
        project.setName(projectNode.path("name").asText());
        issue.setProject(project);
        
        // Components
        JsonNode componentsNode = fields.path("components");
        List<IssueComponent> components = new ArrayList<>();
        if (componentsNode.isArray()) {
            for (JsonNode componentNode : componentsNode) {
                IssueComponent component = new IssueComponent();
                component.setName(componentNode.path("name").asText());
                components.add(component);
            }
        }
        issue.setComponents(components);
        
        // Fix Versions
        JsonNode fixVersionsNode = fields.path("fixVersions");
        List<IssueVersion> fixVersions = new ArrayList<>();
        if (fixVersionsNode.isArray()) {
            for (JsonNode versionNode : fixVersionsNode) {
                IssueVersion version = new IssueVersion();
                version.setName(versionNode.path("name").asText());
                fixVersions.add(version);
            }
        }
        issue.setFixVersions(fixVersions);
        
        // Sprints (customfield_10020)
        JsonNode sprintsNode = fields.path("customfield_10020");
        List<IssueSprint> sprints = new ArrayList<>();
        if (sprintsNode.isArray()) {
            for (JsonNode sprintNode : sprintsNode) {
                IssueSprint sprint = new IssueSprint();
                sprint.setName(sprintNode.path("name").asText());
                sprints.add(sprint);
            }
        }
        issue.setSprints(sprints);
        
        // Subtasks
        JsonNode subtasksNode = fields.path("subtasks");
        List<IssueSubtask> subtasks = new ArrayList<>();
        if (subtasksNode.isArray()) {
            for (JsonNode subtaskNode : subtasksNode) {
                IssueSubtask subtask = new IssueSubtask();
                subtask.setKey(subtaskNode.path("key").asText());
                subtask.setSummary(subtaskNode.path("fields").path("summary").asText());
                
                SubtaskStatus subtaskStatus = new SubtaskStatus();
                subtaskStatus.setName(subtaskNode.path("fields").path("status").path("name").asText());
                subtask.setStatus(subtaskStatus);
                
                subtasks.add(subtask);
            }
        }
        issue.setSubtasks(subtasks);
        
        // Comments
        JsonNode commentsNode = fields.path("comment").path("comments");
        IssueComments comments = new IssueComments();
        List<IssueComment> commentList = new ArrayList<>();
        if (commentsNode.isArray()) {
            for (JsonNode commentNode : commentsNode) {
                IssueComment comment = new IssueComment();
                
                JsonNode authorNode = commentNode.path("author");
                IssueUser author = new IssueUser();
                author.setDisplayName(authorNode.path("displayName").asText());
                author.setAccountId(authorNode.path("accountId").asText());
                comment.setAuthor(author);
                
                comment.setBody(commentNode.path("body").asText());
                comment.setCreated(commentNode.path("created").asText());
                
                commentList.add(comment);
            }
        }
        comments.setComments(commentList);
        issue.setComment(comments);
        
        return issue;
    }
    
    /**
     * Parse search result from JSON response
     */
    private JiraSearchResult parseSearchResultFromJson(JsonNode jsonNode) {
        JiraSearchResult result = new JiraSearchResult();
        
        result.setTotal(jsonNode.path("total").asInt());
        result.setStartAt(jsonNode.path("startAt").asInt());
        result.setMaxResults(jsonNode.path("maxResults").asInt());
        
        JsonNode issuesNode = jsonNode.path("issues");
        List<JiraIssue> issues = new ArrayList<>();
        if (issuesNode.isArray()) {
            for (JsonNode issueNode : issuesNode) {
                JiraIssue issue = parseIssueFromJson(issueNode);
                issues.add(issue);
            }
        }
        result.setIssues(issues);
        
        return result;
    }
    
    /**
     * Get the list of fields to include in search requests
     */
    private List<String> getSearchFields() {
        List<String> fields = new ArrayList<>();
        fields.add("summary");
        fields.add("status");
        fields.add("assignee");
        fields.add("reporter");
        fields.add("priority");
        fields.add("issuetype");
        fields.add("project");
        fields.add("created");
        fields.add("updated");
        fields.add("components");
        fields.add("fixVersions");
        fields.add("customfield_10020");
        fields.add("subtasks");
        fields.add("comment");
        return fields;
    }
    
    /**
     * Request body for search API
     */
    private static class SearchRequest {
        public String jql;
        public int maxResults;
        public List<String> fields;
        
        public SearchRequest(String jql, int maxResults, List<String> fields) {
            this.jql = jql;
            this.maxResults = maxResults;
            this.fields = fields;
        }
    }
}
