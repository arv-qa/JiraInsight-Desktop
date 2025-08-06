@echo off
echo ========================================
echo JiraInsight Desktop - CI/CD Pipeline
echo Committing CI/CD pipeline to GitHub
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

echo ========================================
echo STEP 1: Check repository status
echo ========================================
git status

echo ========================================
echo STEP 2: Add CI/CD pipeline files
echo ========================================
git add .github/workflows/
git add pom.xml

echo ========================================
echo STEP 3: Commit CI/CD pipeline
echo ========================================
git commit -m "feat: Add comprehensive CI/CD pipeline

- Complete GitHub Actions workflow for CI/CD
- Automated testing, building, and deployment
- Cross-platform build support (Windows, macOS, Linux)
- Code quality analysis with SonarCloud integration
- Security scanning with OWASP dependency check
- Automated dependency updates
- Performance testing and monitoring
- Release management with automated versioning
- Documentation deployment to GitHub Pages
- Updated to latest GitHub Actions v4 (upload-artifact, cache)

Workflows included:
- ci-cd.yml: Main CI/CD pipeline
- dependency-update.yml: Automated dependency updates
- code-quality.yml: Code formatting and linting
- performance.yml: Performance and compatibility testing
- release.yml: Release management and automation

Maven plugins added:
- JaCoCo for code coverage
- Spotless for code formatting
- SpotBugs for static analysis
- PMD for code quality
- OWASP dependency check for security
- SonarQube integration
- Versions plugin for dependency management

Features:
- Automated testing on push and PR
- Cross-platform compatibility testing
- Security vulnerability scanning
- Code coverage reporting
- Automated release creation
- Documentation generation and deployment
- Performance monitoring
- Dependency update automation"

echo ========================================
echo STEP 4: Push to GitHub
echo ========================================
git push origin main

echo ========================================
echo SUCCESS: CI/CD Pipeline committed!
echo ========================================
echo.
echo CI/CD Pipeline Features:
echo ✅ Automated testing and building
echo ✅ Cross-platform compatibility
echo ✅ Code quality analysis
echo ✅ Security scanning
echo ✅ Performance testing
echo ✅ Automated releases
echo ✅ Documentation deployment
echo ✅ Dependency management
echo.
echo Next steps:
echo 1. Go to GitHub repository Actions tab
echo 2. Verify workflows are running
echo 3. Configure SonarCloud token (optional)
echo 4. Enable GitHub Pages for documentation
echo 5. Create first release using Release workflow
echo.
echo Workflow files created:
echo - .github/workflows/ci-cd.yml
echo - .github/workflows/dependency-update.yml
echo - .github/workflows/code-quality.yml
echo - .github/workflows/performance.yml
echo - .github/workflows/release.yml
echo.
pause
