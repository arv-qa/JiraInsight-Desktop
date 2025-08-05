package com.jirainsight;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic test for the JiraInsight application
 */
public class JiraInsightApplicationTest {
    
    @Test
    public void testApplicationClassExists() {
        assertNotNull(JiraInsightApplication.class);
    }
    
    @Test
    public void testMainMethodExists() throws NoSuchMethodException {
        assertNotNull(JiraInsightApplication.class.getMethod("main", String[].class));
    }
}
