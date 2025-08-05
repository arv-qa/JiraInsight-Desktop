package com.jirainsight.controller;

import com.jirainsight.model.JiraIssue;
import com.jirainsight.model.IssuePriority;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Custom ListCell for displaying Jira issues in the list view
 */
public class IssueListCell extends ListCell<JiraIssue> {
    
    private VBox content;
    private HBox headerBox;
    private HBox footerBox;
    private Label keyLabel;
    private Label statusLabel;
    private Label summaryLabel;
    private Label assigneeLabel;
    private Label priorityLabel;
    
    public IssueListCell() {
        createContent();
    }
    
    private void createContent() {
        // Create main content container
        content = new VBox();
        content.setSpacing(4);
        content.setPadding(new Insets(8));
        
        // Header box with key and status
        headerBox = new HBox();
        headerBox.setSpacing(8);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        
        keyLabel = new Label();
        keyLabel.getStyleClass().add("issue-key");
        
        statusLabel = new Label();
        statusLabel.getStyleClass().add("issue-status");
        
        headerBox.getChildren().addAll(keyLabel, statusLabel);
        
        // Summary label
        summaryLabel = new Label();
        summaryLabel.getStyleClass().add("issue-summary");
        summaryLabel.setWrapText(true);
        summaryLabel.setMaxWidth(Double.MAX_VALUE);
        
        // Footer box with assignee and priority
        footerBox = new HBox();
        footerBox.setSpacing(8);
        footerBox.setAlignment(Pos.CENTER_LEFT);
        
        assigneeLabel = new Label();
        assigneeLabel.getStyleClass().add("issue-assignee");
        
        priorityLabel = new Label();
        priorityLabel.getStyleClass().add("issue-priority");
        
        // Add spacer to push priority to the right
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        footerBox.getChildren().addAll(assigneeLabel, spacer, priorityLabel);
        
        // Add all components to main content
        content.getChildren().addAll(headerBox, summaryLabel, footerBox);
    }
    
    @Override
    protected void updateItem(JiraIssue issue, boolean empty) {
        super.updateItem(issue, empty);
        
        if (empty || issue == null) {
            setGraphic(null);
            setText(null);
        } else {
            updateContent(issue);
            setGraphic(content);
            setText(null);
        }
    }
    
    private void updateContent(JiraIssue issue) {
        // Update key
        keyLabel.setText(issue.getKey());
        
        // Update status with color coding
        statusLabel.setText(issue.getStatus().getName());
        updateStatusStyle(statusLabel, issue.getStatus().getStatusCategoryKey());
        
        // Update summary
        summaryLabel.setText(issue.getSummary());
        
        // Update assignee
        assigneeLabel.setText(issue.getAssigneeDisplayName());
        
        // Update priority with color coding
        priorityLabel.setText(issue.getPriority().getName());
        updatePriorityStyle(priorityLabel, issue.getPriority().getPriorityLevel());
    }
    
    private void updateStatusStyle(Label label, String statusCategory) {
        // Clear existing status styles
        label.getStyleClass().removeIf(style -> style.startsWith("status-"));
        
        // Add appropriate status style
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
    
    private void updatePriorityStyle(Label label, IssuePriority.PriorityLevel priorityLevel) {
        // Clear existing priority styles
        label.getStyleClass().removeIf(style -> style.startsWith("priority-"));
        
        // Add appropriate priority style
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
}
