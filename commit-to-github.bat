@echo off
echo ========================================
echo JiraInsight Java Desktop Conversion
echo Committing to GitHub
echo ========================================

REM Check if Git is available
git --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Git is not installed or not in PATH
    echo Please install Git for Windows from: https://git-scm.com/download/win
    pause
    exit /b 1
)

echo Checking current directory...
if not exist "pom.xml" (
    echo ERROR: pom.xml not found. Please run this script from the project root directory.
    pause
    exit /b 1
)

echo Initializing Git repository...
git init

echo Adding remote origin...
REM Replace with your actual repository URL
git remote add origin https://github.com/arv-qa/JiraInsight.git

echo Checking out main branch...
git fetch origin
git checkout -b main origin/main 2>nul || git checkout main 2>nul || git checkout -b main

echo Creating new branch for Java desktop conversion...
git checkout -b java-desktop-conversion

echo Adding all files...
git add .

echo Committing changes...
git commit -m "feat: Convert JiraInsight to Java Desktop Application

- Complete conversion from React/Express web app to JavaFX desktop app
- Implemented all core functionality: authentication, search, issue display
- Added comprehensive data models with validation
- Built robust HTTP client for Jira REST API v3 integration
- Created modern JavaFX UI with FXML and CSS styling
- Added error handling, progress indicators, and user feedback
- Included unit tests and build configuration
- Enhanced with desktop-specific features and better performance

Technical Stack:
- JavaFX 21 for UI framework
- OkHttp 4.12 for HTTP client
- Jackson 2.16 for JSON processing
- Maven for build management
- Java 17+ runtime requirement

Features:
- Secure Jira API token authentication
- Quick issue search by key
- Advanced JQL query support
- Detailed issue view with subtasks and comments
- Rich text rendering for descriptions
- Status and priority color coding
- Responsive desktop interface"

echo Pushing to GitHub...
git push -u origin java-desktop-conversion

echo ========================================
echo SUCCESS: Java Desktop conversion committed to GitHub!
echo Branch: java-desktop-conversion
echo ========================================
echo.
echo Next steps:
echo 1. Go to GitHub repository
echo 2. Create a Pull Request from java-desktop-conversion to main
echo 3. Review and merge the changes
echo.
pause
