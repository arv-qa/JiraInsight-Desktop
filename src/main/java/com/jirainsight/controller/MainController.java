package com.jirainsight.controller;

import com.jirainsight.model.JiraCredentials;
import com.jirainsight.model.JiraIssue;
import com.jirainsight.model.JiraSearchResult;
import com.jirainsight.model.User;
import com.jirainsight.service.JiraApiService;
import com.jirainsight.service.StorageService;
import com.jirainsight.util.AlertUtils;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Main controller for the JiraInsight Desktop application
 */
public class MainController implements Initializable {
    
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    
    // Services
    private final JiraApiService jiraApiService = new JiraApiService();
    private final StorageService storageService = StorageService.getInstance();
    
    // Application state
    private Stage stage;
    private User currentUser;
    private JiraCredentials currentCredentials;
    
    // FXML Components
    @FXML private MenuBar menuBar;
    @FXML private Menu fileMenu;
    @FXML private MenuItem connectMenuItem;
    @FXML private MenuItem disconnectMenuItem;
    @FXML private MenuItem exitMenuItem;
    @FXML private Menu helpMenu;
    @FXML private MenuItem aboutMenuItem;
    
    @FXML private ToolBar toolBar;
    @FXML private Button connectButton;
    @FXML private Label connectionStatusLabel;
    
    @FXML private VBox welcomePane;
    @FXML private Label welcomeTitle;
    @FXML private Label welcomeMessage;
    @FXML private Button welcomeConnectButton;
    
    @FXML private VBox mainContentPane;
    @FXML private Label connectedDomainLabel;
    
    // Search components
    @FXML private TabPane searchTabPane;
    @FXML private Tab quickSearchTab;
    @FXML private Tab jqlSearchTab;
    
    @FXML private TextField quickSearchField;
    @FXML private Button quickSearchButton;
    
    @FXML private TextArea jqlQueryArea;
    @FXML private Button jqlSearchButton;
    
    // Results components
    @FXML private SplitPane resultsSplitPane;
    @FXML private VBox issueListPane;
    @FXML private Label resultsLabel;
    @FXML private ListView<JiraIssue> issueListView;
    
    @FXML private VBox issueDetailsPane;
    @FXML private Label issueDetailsLabel;
    @FXML private ScrollPane issueDetailsScrollPane;

    // Issue details controller
    private IssueDetailsController issueDetailsController;
    
    // Status and progress
    @FXML private ProgressBar progressBar;
    @FXML private Label statusLabel;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Initializing MainController");
        
        setupMenuActions();
        setupToolbarActions();
        setupSearchActions();
        setupIssueListView();
        setupIssueDetailsView();

        updateConnectionState(false);
        
        logger.info("MainController initialized successfully");
    }
    
    /**
     * Set the primary stage reference
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /**
     * Setup menu actions
     */
    private void setupMenuActions() {
        connectMenuItem.setOnAction(e -> showConnectionDialog());
        disconnectMenuItem.setOnAction(e -> disconnect());
        exitMenuItem.setOnAction(e -> handleApplicationClose());
        aboutMenuItem.setOnAction(e -> showAboutDialog());
    }
    
    /**
     * Setup toolbar actions
     */
    private void setupToolbarActions() {
        connectButton.setOnAction(e -> showConnectionDialog());
        welcomeConnectButton.setOnAction(e -> showConnectionDialog());
    }
    
    /**
     * Setup search actions
     */
    private void setupSearchActions() {
        quickSearchButton.setOnAction(e -> performQuickSearch());
        jqlSearchButton.setOnAction(e -> performJqlSearch());
        
        // Enable search on Enter key
        quickSearchField.setOnAction(e -> performQuickSearch());
        
        // Set default JQL examples
        jqlQueryArea.setPromptText("Enter JQL query, e.g.:\n" +
                "project = \"TEST\" AND status = \"In Progress\"\n" +
                "assignee = currentUser() AND sprint in openSprints()");
    }
    
    /**
     * Setup issue list view
     */
    private void setupIssueListView() {
        issueListView.setCellFactory(listView -> new IssueListCell());
        issueListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> displayIssueDetails(newValue));
    }

    /**
     * Setup issue details view
     */
    private void setupIssueDetailsView() {
        try {
            issueDetailsController = IssueDetailsController.create();
            issueDetailsScrollPane.setContent(issueDetailsController.getRootContainer());
        } catch (Exception e) {
            logger.error("Failed to setup issue details view", e);
            AlertUtils.showError("Initialization Error", "Failed to setup issue details view: " + e.getMessage());
        }
    }
    
    /**
     * Show connection dialog
     */
    private void showConnectionDialog() {
        try {
            CredentialsDialogController dialogController = new CredentialsDialogController();
            JiraCredentials credentials = dialogController.showAndWait(stage);
            
            if (credentials != null) {
                connectToJira(credentials);
            }
        } catch (Exception e) {
            logger.error("Failed to show connection dialog", e);
            AlertUtils.showError("Connection Error", "Failed to show connection dialog: " + e.getMessage());
        }
    }
    
    /**
     * Connect to Jira with provided credentials
     */
    private void connectToJira(JiraCredentials credentials) {
        setStatus("Testing connection...", true);
        
        Task<Boolean> connectionTask = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return jiraApiService.testConnection(credentials).get();
            }
        };
        
        connectionTask.setOnSucceeded(e -> {
            boolean success = connectionTask.getValue();
            if (success) {
                // Create user and save credentials
                currentUser = storageService.createUser(credentials.getEmail(), "temp");
                currentCredentials = storageService.saveJiraCredentials(currentUser.getId(), credentials);
                
                updateConnectionState(true);
                setStatus("Connected to " + credentials.getDomain(), false);
                AlertUtils.showInfo("Connection Successful", "Successfully connected to " + credentials.getDomain());
            } else {
                setStatus("Connection failed", false);
                AlertUtils.showError("Connection Failed", "Failed to connect to Jira. Please check your credentials.");
            }
        });
        
        connectionTask.setOnFailed(e -> {
            Throwable exception = connectionTask.getException();
            logger.error("Connection failed", exception);
            setStatus("Connection failed", false);
            AlertUtils.showError("Connection Failed", "Failed to connect to Jira: " + exception.getMessage());
        });
        
        new Thread(connectionTask).start();
    }
    
    /**
     * Disconnect from Jira
     */
    private void disconnect() {
        if (currentUser != null) {
            storageService.deleteJiraCredentials(currentUser.getId());
        }
        
        currentUser = null;
        currentCredentials = null;
        
        updateConnectionState(false);
        clearResults();
        setStatus("Disconnected", false);
    }
    
    /**
     * Update UI based on connection state
     */
    private void updateConnectionState(boolean connected) {
        Platform.runLater(() -> {
            // Menu items
            connectMenuItem.setDisable(connected);
            disconnectMenuItem.setDisable(!connected);
            
            // Toolbar
            connectButton.setDisable(connected);
            connectionStatusLabel.setText(connected ? "Connected" : "Not Connected");
            connectionStatusLabel.getStyleClass().clear();
            connectionStatusLabel.getStyleClass().add(connected ? "status-connected" : "status-disconnected");
            
            // Main content
            welcomePane.setVisible(!connected);
            welcomePane.setManaged(!connected);
            mainContentPane.setVisible(connected);
            mainContentPane.setManaged(connected);
            
            if (connected && currentCredentials != null) {
                connectedDomainLabel.setText("Connected to " + currentCredentials.getDomain());
            }
            
            // Search controls
            quickSearchField.setDisable(!connected);
            quickSearchButton.setDisable(!connected);
            jqlQueryArea.setDisable(!connected);
            jqlSearchButton.setDisable(!connected);
        });
    }
    
    /**
     * Perform quick search by issue key
     */
    private void performQuickSearch() {
        String issueKey = quickSearchField.getText().trim();
        if (issueKey.isEmpty()) {
            AlertUtils.showWarning("Search Error", "Please enter an issue key");
            return;
        }
        
        if (currentCredentials == null) {
            AlertUtils.showError("Connection Error", "Not connected to Jira");
            return;
        }
        
        setStatus("Searching for issue " + issueKey + "...", true);
        
        Task<JiraIssue> searchTask = new Task<JiraIssue>() {
            @Override
            protected JiraIssue call() throws Exception {
                return jiraApiService.getIssueByKey(issueKey, currentCredentials).get();
            }
        };
        
        searchTask.setOnSucceeded(e -> {
            JiraIssue issue = searchTask.getValue();
            displaySearchResults(List.of(issue), "1 issue found");
            setStatus("Search completed", false);
        });
        
        searchTask.setOnFailed(e -> {
            Throwable exception = searchTask.getException();
            logger.error("Quick search failed", exception);
            setStatus("Search failed", false);
            AlertUtils.showError("Search Failed", "Failed to find issue: " + exception.getMessage());
        });
        
        new Thread(searchTask).start();
    }
    
    /**
     * Perform JQL search
     */
    private void performJqlSearch() {
        String jql = jqlQueryArea.getText().trim();
        if (jql.isEmpty()) {
            AlertUtils.showWarning("Search Error", "Please enter a JQL query");
            return;
        }
        
        if (currentCredentials == null) {
            AlertUtils.showError("Connection Error", "Not connected to Jira");
            return;
        }
        
        setStatus("Executing JQL query...", true);
        
        Task<JiraSearchResult> searchTask = new Task<JiraSearchResult>() {
            @Override
            protected JiraSearchResult call() throws Exception {
                return jiraApiService.searchIssues(jql, currentCredentials).get();
            }
        };
        
        searchTask.setOnSucceeded(e -> {
            JiraSearchResult result = searchTask.getValue();
            displaySearchResults(result.getIssues(), result.getSummary());
            setStatus("Search completed", false);
        });
        
        searchTask.setOnFailed(e -> {
            Throwable exception = searchTask.getException();
            logger.error("JQL search failed", exception);
            setStatus("Search failed", false);
            AlertUtils.showError("Search Failed", "Failed to execute JQL query: " + exception.getMessage());
        });
        
        new Thread(searchTask).start();
    }
    
    /**
     * Display search results
     */
    private void displaySearchResults(List<JiraIssue> issues, String summary) {
        Platform.runLater(() -> {
            resultsLabel.setText(summary);
            issueListView.getItems().clear();
            issueListView.getItems().addAll(issues);
            
            if (!issues.isEmpty()) {
                issueListView.getSelectionModel().selectFirst();
            } else {
                displayIssueDetails(null);
            }
        });
    }
    
    /**
     * Display issue details
     */
    private void displayIssueDetails(JiraIssue issue) {
        Platform.runLater(() -> {
            if (issue == null) {
                issueDetailsLabel.setText("Select an issue to view details");
                if (issueDetailsController != null) {
                    issueDetailsController.displayIssue(null);
                }
            } else {
                issueDetailsLabel.setText(issue.getKey() + " - " + issue.getSummary());
                if (issueDetailsController != null) {
                    issueDetailsController.displayIssue(issue);
                }
            }
        });
    }
    
    /**
     * Clear search results
     */
    private void clearResults() {
        Platform.runLater(() -> {
            resultsLabel.setText("No search results");
            issueListView.getItems().clear();
            displayIssueDetails(null);
        });
    }
    
    /**
     * Set status message and progress
     */
    private void setStatus(String message, boolean showProgress) {
        Platform.runLater(() -> {
            statusLabel.setText(message);
            progressBar.setVisible(showProgress);
        });
    }
    
    /**
     * Show about dialog
     */
    private void showAboutDialog() {
        AlertUtils.showInfo("About JiraInsight Desktop", 
                "JiraInsight Desktop v1.0.0\n\n" +
                "A modern JavaFX desktop application for viewing and searching Jira issues.\n\n" +
                "Built with JavaFX, OkHttp, and Jackson.");
    }
    
    /**
     * Handle application close
     */
    public void handleApplicationClose() {
        logger.info("Handling application close");
        
        if (currentUser != null) {
            // Optionally save state or cleanup
        }
        
        Platform.exit();
    }
}
