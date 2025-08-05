package com.jirainsight.controller;

import com.jirainsight.model.JiraCredentials;
import com.jirainsight.util.AlertUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Controller for the Jira credentials dialog
 */
public class CredentialsDialogController {
    
    private static final Logger logger = LoggerFactory.getLogger(CredentialsDialogController.class);
    
    @FXML private TextField domainField;
    @FXML private TextField emailField;
    @FXML private PasswordField apiTokenField;
    @FXML private CheckBox rememberCredentialsCheckBox;
    @FXML private Hyperlink apiTokenHelpLink;
    @FXML private Button cancelButton;
    @FXML private Button connectButton;
    @FXML private ProgressIndicator progressIndicator;
    
    private Stage dialogStage;
    private JiraCredentials result;
    private boolean cancelled = false;
    
    /**
     * Show the credentials dialog and wait for user input
     */
    public JiraCredentials showAndWait(Window owner) throws IOException {
        // Load FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/credentials-dialog.fxml"));
        loader.setController(this);
        
        // Create dialog stage
        dialogStage = new Stage();
        dialogStage.setTitle("Connect to Jira");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(owner);
        dialogStage.setResizable(false);
        
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
        dialogStage.setScene(scene);
        
        // Initialize after FXML is loaded
        initialize();
        
        // Show and wait
        dialogStage.showAndWait();
        
        return cancelled ? null : result;
    }
    
    /**
     * Initialize the dialog
     */
    private void initialize() {
        logger.info("Initializing credentials dialog");
        
        // Setup button actions
        cancelButton.setOnAction(e -> handleCancel());
        connectButton.setOnAction(e -> handleConnect());
        
        // Setup help link
        apiTokenHelpLink.setOnAction(e -> {
            // Open browser to API token help page
            try {
                java.awt.Desktop.getDesktop().browse(
                    java.net.URI.create("https://support.atlassian.com/atlassian-account/docs/manage-api-tokens-for-your-atlassian-account/")
                );
            } catch (Exception ex) {
                logger.warn("Could not open browser for help link", ex);
                AlertUtils.showInfo("API Token Help", 
                    "To generate an API token:\n\n" +
                    "1. Go to https://id.atlassian.com/manage-profile/security/api-tokens\n" +
                    "2. Click 'Create API token'\n" +
                    "3. Give it a label and click 'Create'\n" +
                    "4. Copy the token and paste it here");
            }
        });
        
        // Setup form validation
        setupValidation();
        
        // Set initial focus
        Platform.runLater(() -> domainField.requestFocus());
        
        // Hide progress indicator initially
        progressIndicator.setVisible(false);
    }
    
    /**
     * Setup form validation
     */
    private void setupValidation() {
        // Enable/disable connect button based on form validity
        connectButton.disableProperty().bind(
            domainField.textProperty().isEmpty()
                .or(emailField.textProperty().isEmpty())
                .or(apiTokenField.textProperty().isEmpty())
        );
        
        // Add input validation
        domainField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Remove protocol if user enters it
                if (newValue.startsWith("https://")) {
                    domainField.setText(newValue.substring(8));
                } else if (newValue.startsWith("http://")) {
                    domainField.setText(newValue.substring(7));
                }
                
                // Ensure it ends with .atlassian.net if not already
                if (!newValue.isEmpty() && !newValue.contains(".") && !newValue.endsWith(".atlassian.net")) {
                    // Don't auto-complete while user is typing
                }
            }
        });
        
        // Email validation
        emailField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && !emailField.getText().isEmpty()) {
                String email = emailField.getText();
                if (!email.contains("@")) {
                    emailField.getStyleClass().add("error");
                } else {
                    emailField.getStyleClass().remove("error");
                }
            }
        });
    }
    
    /**
     * Handle cancel button
     */
    private void handleCancel() {
        logger.info("Credentials dialog cancelled");
        cancelled = true;
        dialogStage.close();
    }
    
    /**
     * Handle connect button
     */
    private void handleConnect() {
        if (!validateForm()) {
            return;
        }
        
        // Create credentials object
        String domain = domainField.getText().trim();
        String email = emailField.getText().trim();
        String apiToken = apiTokenField.getText().trim();
        boolean rememberCredentials = rememberCredentialsCheckBox.isSelected();
        
        // Ensure domain has proper format
        if (!domain.contains(".")) {
            domain = domain + ".atlassian.net";
        }
        
        result = new JiraCredentials(domain, email, apiToken);
        result.setRememberCredentials(rememberCredentials);
        
        logger.info("Credentials dialog completed for domain: {}", domain);
        
        cancelled = false;
        dialogStage.close();
    }
    
    /**
     * Validate the form
     */
    private boolean validateForm() {
        StringBuilder errors = new StringBuilder();
        
        String domain = domainField.getText().trim();
        if (domain.isEmpty()) {
            errors.append("• Domain is required\n");
        }
        
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            errors.append("• Email is required\n");
        } else if (!email.contains("@")) {
            errors.append("• Please enter a valid email address\n");
        }
        
        String apiToken = apiTokenField.getText().trim();
        if (apiToken.isEmpty()) {
            errors.append("• API token is required\n");
        }
        
        if (errors.length() > 0) {
            AlertUtils.showError(dialogStage, "Validation Error", 
                "Please correct the following errors:\n\n" + errors.toString());
            return false;
        }
        
        return true;
    }
    
    /**
     * Set loading state
     */
    private void setLoading(boolean loading) {
        Platform.runLater(() -> {
            progressIndicator.setVisible(loading);
            connectButton.setDisable(loading);
            cancelButton.setDisable(loading);
            
            if (loading) {
                connectButton.setText("Connecting...");
            } else {
                connectButton.setText("Connect");
            }
        });
    }
}
