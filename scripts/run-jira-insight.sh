#!/bin/bash

# JiraInsight Desktop Launcher Script
# This script launches the JiraInsight Desktop application with proper JavaFX configuration

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if Java is installed
check_java() {
    if command -v java &> /dev/null; then
        JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
        JAVA_MAJOR_VERSION=$(echo $JAVA_VERSION | cut -d'.' -f1)
        
        if [ "$JAVA_MAJOR_VERSION" -ge 17 ]; then
            print_success "Java $JAVA_VERSION found"
            return 0
        else
            print_error "Java 17 or higher is required. Found: $JAVA_VERSION"
            return 1
        fi
    else
        print_error "Java is not installed or not in PATH"
        return 1
    fi
}

# Find the JAR file
find_jar() {
    # Look for JAR file in current directory and common locations
    JAR_LOCATIONS=(
        "jira-insight-desktop-*.jar"
        "target/jira-insight-desktop-*.jar"
        "../jira-insight-desktop-*.jar"
        "*.jar"
    )
    
    for location in "${JAR_LOCATIONS[@]}"; do
        JAR_FILE=$(ls $location 2>/dev/null | head -n 1)
        if [ -n "$JAR_FILE" ] && [ -f "$JAR_FILE" ]; then
            print_success "Found JAR file: $JAR_FILE"
            return 0
        fi
    done
    
    print_error "JAR file not found. Please ensure jira-insight-desktop-*.jar is in the current directory."
    return 1
}

# Launch the application
launch_app() {
    print_info "Launching JiraInsight Desktop..."
    
    # JavaFX runtime arguments for compatibility
    JAVAFX_ARGS=(
        "--add-opens=javafx.controls/com.sun.javafx.scene.control.behavior=ALL-UNNAMED"
        "--add-opens=javafx.controls/com.sun.javafx.scene.control=ALL-UNNAMED"
        "--add-opens=javafx.base/com.sun.javafx.binding=ALL-UNNAMED"
        "--add-opens=javafx.base/com.sun.javafx.event=ALL-UNNAMED"
        "--add-opens=javafx.graphics/com.sun.javafx.util=ALL-UNNAMED"
        "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
    )
    
    # Additional JVM arguments
    JVM_ARGS=(
        "-Xmx1024m"
        "-Dfile.encoding=UTF-8"
        "-Djava.awt.headless=false"
    )
    
    # Combine all arguments
    ALL_ARGS=("${JVM_ARGS[@]}" "${JAVAFX_ARGS[@]}")
    
    # Launch the application
    java "${ALL_ARGS[@]}" -jar "$JAR_FILE" "$@"
    
    EXIT_CODE=$?
    if [ $EXIT_CODE -eq 0 ]; then
        print_success "Application closed successfully"
    else
        print_error "Application exited with code: $EXIT_CODE"
    fi
    
    return $EXIT_CODE
}

# Show help
show_help() {
    echo "JiraInsight Desktop Launcher"
    echo ""
    echo "Usage: $0 [options]"
    echo ""
    echo "Options:"
    echo "  -h, --help     Show this help message"
    echo "  -v, --version  Show version information"
    echo "  --debug        Enable debug mode"
    echo ""
    echo "Examples:"
    echo "  $0                 # Launch the application"
    echo "  $0 --debug         # Launch with debug output"
    echo ""
    echo "Requirements:"
    echo "  - Java 17 or higher"
    echo "  - jira-insight-desktop-*.jar file in current directory"
    echo ""
}

# Main execution
main() {
    print_info "JiraInsight Desktop Launcher"
    print_info "=============================="
    
    # Parse command line arguments
    while [[ $# -gt 0 ]]; do
        case $1 in
            -h|--help)
                show_help
                exit 0
                ;;
            -v|--version)
                print_info "JiraInsight Desktop Launcher v1.0"
                exit 0
                ;;
            --debug)
                set -x
                shift
                ;;
            *)
                # Pass unknown arguments to the Java application
                break
                ;;
        esac
    done
    
    # Check prerequisites
    if ! check_java; then
        print_error "Java check failed. Please install Java 17 or higher."
        exit 1
    fi
    
    if ! find_jar; then
        print_error "JAR file not found. Please download the JAR file and place it in the current directory."
        exit 1
    fi
    
    # Launch the application
    launch_app "$@"
    exit $?
}

# Run main function with all arguments
main "$@"
