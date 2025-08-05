package com.jirainsight;

import com.jirainsight.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Main JavaFX Application class for JiraInsight Desktop
 */
public class JiraInsightApplication extends Application {
    
    private static final Logger logger = LoggerFactory.getLogger(JiraInsightApplication.class);
    
    private static final String APP_TITLE = "JiraInsight Desktop";
    private static final int MIN_WIDTH = 1200;
    private static final int MIN_HEIGHT = 800;
    private static final int DEFAULT_WIDTH = 1400;
    private static final int DEFAULT_HEIGHT = 900;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            logger.info("Starting JiraInsight Desktop Application");
            
            // Load FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
            
            // Get controller and set stage reference
            MainController controller = fxmlLoader.getController();
            controller.setStage(primaryStage);
            
            // Load CSS
            scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
            
            // Configure stage
            primaryStage.setTitle(APP_TITLE);
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(MIN_WIDTH);
            primaryStage.setMinHeight(MIN_HEIGHT);
            
            // Set application icon (if available)
            try {
                Image icon = new Image(getClass().getResourceAsStream("/images/icon.png"));
                primaryStage.getIcons().add(icon);
            } catch (Exception e) {
                logger.warn("Could not load application icon: {}", e.getMessage());
            }
            
            // Handle close request
            primaryStage.setOnCloseRequest(event -> {
                logger.info("Application closing");
                controller.handleApplicationClose();
            });
            
            // Show the stage
            primaryStage.show();
            
            logger.info("JiraInsight Desktop Application started successfully");
            
        } catch (IOException e) {
            logger.error("Failed to start application", e);
            throw new RuntimeException("Failed to start application", e);
        }
    }
    
    @Override
    public void stop() throws Exception {
        logger.info("JiraInsight Desktop Application stopped");
        super.stop();
    }
    
    /**
     * Main method to launch the application
     */
    public static void main(String[] args) {
        // Set system properties for better JavaFX experience
        System.setProperty("javafx.preloader", "com.sun.javafx.application.LauncherImpl");
        
        // Launch the application
        launch(args);
    }
}
