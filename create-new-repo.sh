#!/bin/bash

echo "========================================"
echo "JiraInsight Java Desktop - New Repository"
echo "Creating new GitHub repository"
echo "========================================"

# Check if Git is available
if ! command -v git &> /dev/null; then
    echo "ERROR: Git is not installed or not in PATH"
    echo "Please install Git from: https://git-scm.com/"
    exit 1
fi

echo "Checking current directory..."
if [ ! -f "pom.xml" ]; then
    echo "ERROR: pom.xml not found. Please run this script from the project root directory."
    exit 1
fi

echo "========================================"
echo "STEP 1: Initialize Git Repository"
echo "========================================"
git init

echo "========================================"
echo "STEP 2: Add all files to Git"
echo "========================================"
git add .

echo "========================================"
echo "STEP 3: Create initial commit"
echo "========================================"
git commit -m "Initial commit: JiraInsight Java Desktop Application

Complete JavaFX desktop application converted from React/Express web app.

Features:
- Secure Jira API token authentication
- Quick issue search by key (e.g., PROJ-123)
- Advanced JQL query support with up to 50 results
- Detailed issue view with subtasks and comments
- Rich text rendering for descriptions
- Status and priority color coding
- Modern JavaFX UI with responsive design
- Comprehensive error handling and user feedback

Technical Stack:
- JavaFX 21 for UI framework
- OkHttp 4.12 for HTTP client
- Jackson 2.16 for JSON processing
- Maven for build management
- Java 17+ runtime requirement

Architecture:
- MVC pattern with JavaFX controllers
- Service layer for business logic
- POJO models with validation
- FXML-based UI layouts
- CSS styling for modern appearance
- Unit tests for core functionality

Build & Run:
- mvn clean compile (build)
- mvn javafx:run (development)
- mvn clean package (create JAR)
- java -jar target/jira-insight-desktop-1.0.0.jar (production)"

echo "========================================"
echo "STEP 4: Set main branch"
echo "========================================"
git branch -M main

echo "========================================"
echo "REPOSITORY INITIALIZED SUCCESSFULLY!"
echo "========================================"
echo ""
echo "Next steps:"
echo "1. Go to GitHub.com and create a new repository"
echo "2. Repository name: JiraInsight-Desktop"
echo "3. Description: Modern JavaFX desktop application for Jira issue management"
echo "4. Make it Public or Private as needed"
echo "5. DO NOT initialize with README, .gitignore, or license (we have them)"
echo "6. Copy the repository URL (e.g., https://github.com/arv-qa/JiraInsight-Desktop.git)"
echo "7. Run: git remote add origin [YOUR_REPO_URL]"
echo "8. Run: git push -u origin main"
echo ""
echo "Example commands after creating GitHub repo:"
echo "git remote add origin https://github.com/arv-qa/JiraInsight-Desktop.git"
echo "git push -u origin main"
echo ""
