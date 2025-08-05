package com.jirainsight.controller;

import com.jirainsight.model.JiraIssue;
import com.jirainsight.model.IssueComments.IssueComment;
import com.jirainsight.model.IssueSubtask;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Controller for displaying detailed issue information
 */
public class IssueDetailsController {
    
    private static final Logger logger = LoggerFactory.getLogger(IssueDetailsController.class);
    
    @FXML private VBox rootContainer;
    @FXML private Label issueKeyLabel;
    @FXML private Label issueSummaryLabel;
    @FXML private Label statusLabel;
    @FXML private Label priorityLabel;
    @FXML private Label assigneeLabel;
    @FXML private Label reporterLabel;
    @FXML private Label sprintLabel;
    @FXML private Label issueTypeLabel;
    @FXML private Label createdLabel;
    @FXML private Label updatedLabel;
    
    @FXML private VBox descriptionContainer;
    @FXML private WebView descriptionWebView;
    
    @FXML private VBox subtasksContainer;
    @FXML private VBox subtasksList;
    
    @FXML private VBox commentsContainer;
    @FXML private VBox commentsList;
    
    private JiraIssue currentIssue;
    
    /**
     * Create and load the issue details view
     */
    public static IssueDetailsController create() throws IOException {
        FXMLLoader loader = new FXMLLoader(IssueDetailsController.class.getResource("/fxml/issue-details.fxml"));
        loader.load();
        return loader.getController();
    }
    
    /**
     * Display the given issue
     */
    public void displayIssue(JiraIssue issue) {
        this.currentIssue = issue;
        
        if (issue == null) {
            clearDisplay();
            return;
        }
        
        updateBasicInfo(issue);
        updateDescription(issue);
        updateSubtasks(issue);
        updateComments(issue);
    }
    
    /**
     * Clear the display
     */
    private void clearDisplay() {
        issueKeyLabel.setText("");
        issueSummaryLabel.setText("");
        statusLabel.setText("");
        priorityLabel.setText("");
        assigneeLabel.setText("");
        reporterLabel.setText("");
        sprintLabel.setText("");
        issueTypeLabel.setText("");
        createdLabel.setText("");
        updatedLabel.setText("");
        
        descriptionWebView.getEngine().loadContent("");
        subtasksList.getChildren().clear();
        commentsList.getChildren().clear();
        
        descriptionContainer.setVisible(false);
        subtasksContainer.setVisible(false);
        commentsContainer.setVisible(false);
    }
    
    /**
     * Update basic issue information
     */
    private void updateBasicInfo(JiraIssue issue) {
        issueKeyLabel.setText(issue.getKey());
        issueSummaryLabel.setText(issue.getSummary());
        
        // Status with styling
        statusLabel.setText(issue.getStatus().getName());
        updateStatusStyle(statusLabel, issue.getStatus().getStatusCategoryKey());
        
        // Priority with styling
        priorityLabel.setText(issue.getPriority().getName());
        updatePriorityStyle(priorityLabel, issue.getPriority().getPriorityLevel());
        
        assigneeLabel.setText(issue.getAssigneeDisplayName());
        reporterLabel.setText(issue.getReporter().getDisplayName());
        sprintLabel.setText(issue.getSprintName());
        issueTypeLabel.setText(issue.getIssueType().getName());
        
        // Format dates
        createdLabel.setText(formatDate(issue.getCreated()));
        updatedLabel.setText(formatDate(issue.getUpdated()));
    }
    
    /**
     * Update description section
     */
    private void updateDescription(JiraIssue issue) {
        if (issue.getDescription() != null && !issue.getDescription().trim().isEmpty()) {
            String htmlContent = convertToHtml(issue.getDescription());
            descriptionWebView.getEngine().loadContent(htmlContent);
            descriptionContainer.setVisible(true);
            descriptionContainer.setManaged(true);
        } else {
            descriptionContainer.setVisible(false);
            descriptionContainer.setManaged(false);
        }
    }
    
    /**
     * Update subtasks section
     */
    private void updateSubtasks(JiraIssue issue) {
        subtasksList.getChildren().clear();
        
        if (issue.getSubtasks() != null && !issue.getSubtasks().isEmpty()) {
            for (IssueSubtask subtask : issue.getSubtasks()) {
                VBox subtaskItem = createSubtaskItem(subtask);
                subtasksList.getChildren().add(subtaskItem);
            }
            subtasksContainer.setVisible(true);
            subtasksContainer.setManaged(true);
        } else {
            subtasksContainer.setVisible(false);
            subtasksContainer.setManaged(false);
        }
    }
    
    /**
     * Update comments section
     */
    private void updateComments(JiraIssue issue) {
        commentsList.getChildren().clear();
        
        if (issue.getComment() != null && issue.getComment().hasComments()) {
            for (IssueComment comment : issue.getComment().getComments()) {
                VBox commentItem = createCommentItem(comment);
                commentsList.getChildren().add(commentItem);
            }
            commentsContainer.setVisible(true);
            commentsContainer.setManaged(true);
        } else {
            commentsContainer.setVisible(false);
            commentsContainer.setManaged(false);
        }
    }
    
    /**
     * Create a subtask item UI
     */
    private VBox createSubtaskItem(IssueSubtask subtask) {
        VBox container = new VBox(5);
        container.getStyleClass().add("subtask-item");
        container.setPadding(new Insets(8));
        
        HBox header = new HBox(10);
        header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        Label keyLabel = new Label(subtask.getKey());
        keyLabel.getStyleClass().add("subtask-key");
        
        Label statusLabel = new Label(subtask.getStatus().getName());
        statusLabel.getStyleClass().add("subtask-status");
        
        if (subtask.isCompleted()) {
            statusLabel.getStyleClass().add("status-done");
        } else if (subtask.isInProgress()) {
            statusLabel.getStyleClass().add("status-in-progress");
        } else {
            statusLabel.getStyleClass().add("status-new");
        }
        
        header.getChildren().addAll(keyLabel, statusLabel);
        
        Label summaryLabel = new Label(subtask.getSummary());
        summaryLabel.getStyleClass().add("subtask-summary");
        summaryLabel.setWrapText(true);
        
        container.getChildren().addAll(header, summaryLabel);
        
        return container;
    }
    
    /**
     * Create a comment item UI
     */
    private VBox createCommentItem(IssueComment comment) {
        VBox container = new VBox(8);
        container.getStyleClass().add("comment-item");
        container.setPadding(new Insets(10));
        
        HBox header = new HBox(10);
        header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        Label authorLabel = new Label(comment.getAuthor().getDisplayName());
        authorLabel.getStyleClass().add("comment-author");
        
        Label dateLabel = new Label(formatDate(comment.getCreated()));
        dateLabel.getStyleClass().add("comment-date");
        
        header.getChildren().addAll(authorLabel, dateLabel);
        
        // Use WebView for rich text comment body
        WebView commentWebView = new WebView();
        commentWebView.setPrefHeight(100);
        commentWebView.setMaxHeight(200);
        String htmlContent = convertToHtml(comment.getBody());
        commentWebView.getEngine().loadContent(htmlContent);
        
        container.getChildren().addAll(header, commentWebView);
        
        return container;
    }
    
    /**
     * Convert text to HTML for WebView display
     */
    private String convertToHtml(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "<html><body></body></html>";
        }
        
        // Basic HTML conversion - replace newlines with <br>
        String htmlText = text.replace("\n", "<br>");
        
        return String.format(
            "<html><head><style>" +
            "body { font-family: 'Segoe UI', Arial, sans-serif; font-size: 13px; margin: 8px; }" +
            "p { margin: 8px 0; }" +
            "</style></head><body>%s</body></html>",
            htmlText
        );
    }
    
    /**
     * Format date string for display
     */
    private String formatDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return "Unknown";
        }
        
        try {
            // Parse ISO date format from Jira
            LocalDateTime dateTime = LocalDateTime.parse(dateString.substring(0, 19));
            return dateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"));
        } catch (DateTimeParseException e) {
            logger.warn("Could not parse date: {}", dateString);
            return dateString;
        }
    }
    
    /**
     * Update status label styling
     */
    private void updateStatusStyle(Label label, String statusCategory) {
        label.getStyleClass().removeIf(style -> style.startsWith("status-"));
        
        switch (statusCategory.toLowerCase()) {
            case "done":
                label.getStyleClass().add("status-done");
                break;
            case "indeterminate":
                label.getStyleClass().add("status-in-progress");
                break;
            case "new":
                label.getStyleClass().add("status-new");
                break;
            default:
                label.getStyleClass().add("status-default");
                break;
        }
    }
    
    /**
     * Update priority label styling
     */
    private void updatePriorityStyle(Label label, com.jirainsight.model.IssuePriority.PriorityLevel priorityLevel) {
        label.getStyleClass().removeIf(style -> style.startsWith("priority-"));
        
        switch (priorityLevel) {
            case HIGHEST:
                label.getStyleClass().add("priority-highest");
                break;
            case HIGH:
                label.getStyleClass().add("priority-high");
                break;
            case MEDIUM:
                label.getStyleClass().add("priority-medium");
                break;
            case LOW:
                label.getStyleClass().add("priority-low");
                break;
            case LOWEST:
                label.getStyleClass().add("priority-lowest");
                break;
            default:
                label.getStyleClass().add("priority-none");
                break;
        }
    }
    
    /**
     * Get the root container for embedding in other views
     */
    public VBox getRootContainer() {
        return rootContainer;
    }
}
