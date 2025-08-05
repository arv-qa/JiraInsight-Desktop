package com.jirainsight.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Window;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

/**
 * Utility class for showing various types of alerts and dialogs
 */
public class AlertUtils {
    
    /**
     * Show an information alert
     */
    public static void showInfo(String title, String message) {
        showInfo(null, title, message);
    }
    
    /**
     * Show an information alert with owner window
     */
    public static void showInfo(Window owner, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Show a warning alert
     */
    public static void showWarning(String title, String message) {
        showWarning(null, title, message);
    }
    
    /**
     * Show a warning alert with owner window
     */
    public static void showWarning(Window owner, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Show an error alert
     */
    public static void showError(String title, String message) {
        showError(null, title, message);
    }
    
    /**
     * Show an error alert with owner window
     */
    public static void showError(Window owner, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Show an error alert with exception details
     */
    public static void showError(String title, String message, Throwable exception) {
        showError(null, title, message, exception);
    }
    
    /**
     * Show an error alert with exception details and owner window
     */
    public static void showError(Window owner, String title, String message, Throwable exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText(exception.getMessage());
        
        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();
        
        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 0);
        
        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }
    
    /**
     * Show a confirmation dialog
     */
    public static boolean showConfirmation(String title, String message) {
        return showConfirmation(null, title, message);
    }
    
    /**
     * Show a confirmation dialog with owner window
     */
    public static boolean showConfirmation(Window owner, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    
    /**
     * Show a confirmation dialog with custom buttons
     */
    public static Optional<ButtonType> showConfirmation(String title, String message, ButtonType... buttonTypes) {
        return showConfirmation(null, title, message, buttonTypes);
    }
    
    /**
     * Show a confirmation dialog with custom buttons and owner window
     */
    public static Optional<ButtonType> showConfirmation(Window owner, String title, String message, ButtonType... buttonTypes) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(buttonTypes);
        
        return alert.showAndWait();
    }
}
