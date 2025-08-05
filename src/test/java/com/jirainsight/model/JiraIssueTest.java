package com.jirainsight.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for JiraIssue model
 */
public class JiraIssueTest {
    
    @Test
    public void testJiraIssueCreation() {
        JiraIssue issue = new JiraIssue();
        issue.setKey("TEST-123");
        issue.setSummary("Test Issue");
        
        assertEquals("TEST-123", issue.getKey());
        assertEquals("Test Issue", issue.getSummary());
    }
    
    @Test
    public void testAssigneeDisplayName() {
        JiraIssue issue = new JiraIssue();
        
        // Test with no assignee
        assertEquals("Unassigned", issue.getAssigneeDisplayName());
        
        // Test with assignee
        IssueUser assignee = new IssueUser("John Doe", "123");
        issue.setAssignee(assignee);
        assertEquals("John Doe", issue.getAssigneeDisplayName());
    }
    
    @Test
    public void testSprintName() {
        JiraIssue issue = new JiraIssue();
        
        // Test with no sprints
        assertEquals("No sprint", issue.getSprintName());
        
        // Test with sprint
        IssueSprint sprint = new IssueSprint("Sprint 1");
        issue.setSprints(java.util.List.of(sprint));
        assertEquals("Sprint 1", issue.getSprintName());
    }
}
