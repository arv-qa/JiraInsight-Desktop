#!/bin/bash

echo "========================================"
echo "JiraInsight Java Desktop - GitHub CLI"
echo "Creating repository with GitHub CLI"
echo "========================================"

# Check if GitHub CLI is available
if ! command -v gh &> /dev/null; then
    echo "ERROR: GitHub CLI is not installed"
    echo "Please install from: https://cli.github.com/"
    echo "Or use the manual method in NEW_REPOSITORY_GUIDE.md"
    exit 1
fi

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
- Java 17+ runtime requirement"

echo "========================================"
echo "STEP 4: Set main branch"
echo "========================================"
git branch -M main

echo "========================================"
echo "STEP 5: Create GitHub repository"
echo "========================================"
echo "Creating repository on GitHub..."
gh repo create JiraInsight-Desktop --public --description "Modern JavaFX desktop application for Jira issue management - converted from React/Express web app" --source=. --remote=origin --push

echo "========================================"
echo "SUCCESS! Repository created and pushed!"
echo "========================================"
echo ""
echo "Repository URL: https://github.com/$(gh api user --jq .login)/JiraInsight-Desktop"
echo ""
echo "Next steps:"
echo "1. Visit your repository on GitHub"
echo "2. Add topics: java, javafx, jira, desktop-application, maven"
echo "3. Enable Issues, Projects, and Wiki if desired"
echo "4. Consider creating a release: gh release create v1.0.0"
echo ""
