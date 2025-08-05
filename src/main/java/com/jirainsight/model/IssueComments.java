package com.jirainsight.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the comments section of a Jira issue
 */
public class IssueComments {
    
    @JsonProperty("comments")
    private List<IssueComment> comments = new ArrayList<>();
    
    // Default constructor
    public IssueComments() {}
    
    // Constructor with comments list
    public IssueComments(List<IssueComment> comments) {
        this.comments = comments != null ? comments : new ArrayList<>();
    }
    
    // Getters and Setters
    public List<IssueComment> getComments() {
        return comments;
    }
    
    public void setComments(List<IssueComment> comments) {
        this.comments = comments != null ? comments : new ArrayList<>();
    }
    
    /**
     * Add a comment to the list
     */
    public void addComment(IssueComment comment) {
        if (comment != null) {
            this.comments.add(comment);
        }
    }
    
    /**
     * Get the number of comments
     */
    public int getCommentCount() {
        return comments.size();
    }
    
    /**
     * Check if there are any comments
     */
    public boolean hasComments() {
        return !comments.isEmpty();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueComments that = (IssueComments) o;
        return Objects.equals(comments, that.comments);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(comments);
    }
    
    @Override
    public String toString() {
        return "IssueComments{" +
                "comments=" + comments.size() + " comments" +
                '}';
    }
    
    /**
     * Represents a single comment on a Jira issue
     */
    public static class IssueComment {
        
        @NotNull(message = "Comment author is required")
        @JsonProperty("author")
        private IssueUser author;
        
        @NotBlank(message = "Comment body is required")
        @JsonProperty("body")
        private String body;
        
        @NotBlank(message = "Comment created date is required")
        @JsonProperty("created")
        private String created;
        
        // Default constructor
        public IssueComment() {}
        
        // Constructor with required fields
        public IssueComment(IssueUser author, String body, String created) {
            this.author = author;
            this.body = body;
            this.created = created;
        }
        
        // Getters and Setters
        public IssueUser getAuthor() {
            return author;
        }
        
        public void setAuthor(IssueUser author) {
            this.author = author;
        }
        
        public String getBody() {
            return body;
        }
        
        public void setBody(String body) {
            this.body = body;
        }
        
        public String getCreated() {
            return created;
        }
        
        public void setCreated(String created) {
            this.created = created;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IssueComment that = (IssueComment) o;
            return Objects.equals(author, that.author) &&
                   Objects.equals(body, that.body) &&
                   Objects.equals(created, that.created);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(author, body, created);
        }
        
        @Override
        public String toString() {
            return "IssueComment{" +
                    "author=" + author +
                    ", created='" + created + '\'' +
                    ", body='" + (body != null && body.length() > 50 ? body.substring(0, 50) + "..." : body) + '\'' +
                    '}';
        }
    }
}
