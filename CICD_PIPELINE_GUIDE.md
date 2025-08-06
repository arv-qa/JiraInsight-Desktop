# CI/CD Pipeline Guide - JiraInsight Desktop

## 🚀 Overview

This document describes the comprehensive CI/CD pipeline implemented for the JiraInsight Desktop project using GitHub Actions. The pipeline provides automated testing, building, deployment, and release management.

## 📋 Pipeline Components

### 1. Main CI/CD Pipeline (`ci-cd.yml`)

**Triggers:**
- Push to `main` or `develop` branches
- Pull requests to `main` branch
- Release events

**Jobs:**
- **Test & Build**: Runs tests, builds application, generates reports
- **Code Quality**: SonarCloud analysis and quality gates
- **Security Scan**: OWASP dependency vulnerability scanning
- **Cross-Platform Build**: Builds on Windows, macOS, and Linux
- **Release**: Creates releases with automated asset upload
- **Deploy Docs**: Deploys Javadoc to GitHub Pages

### 2. Dependency Updates (`dependency-update.yml`)

**Triggers:**
- Weekly schedule (Mondays at 9 AM UTC)
- Manual workflow dispatch

**Features:**
- Automated Maven dependency updates
- Security audit with vulnerability detection
- Automatic PR creation for dependency updates
- Security issue creation on vulnerabilities

### 3. Code Quality (`code-quality.yml`)

**Triggers:**
- Push to `main` or `develop` branches
- Pull requests to `main` branch

**Features:**
- Code formatting checks with Spotless
- Static analysis with SpotBugs and PMD
- Javadoc generation and validation
- Test coverage analysis with JaCoCo
- Coverage threshold enforcement (70%)

### 4. Performance Testing (`performance.yml`)

**Triggers:**
- Push to `main` branch
- Pull requests to `main` branch
- Weekly schedule (Sundays at 2 AM UTC)

**Features:**
- Performance benchmarking
- Memory usage testing
- Startup time measurement
- JAR size monitoring
- Java compatibility testing (JDK 17-21)

### 5. Release Management (`release.yml`)

**Triggers:**
- Manual workflow dispatch with version input

**Features:**
- Automated version bumping
- CHANGELOG.md updates
- Git tag creation
- GitHub release creation with assets
- Release announcement issue creation

## 🔧 Maven Plugins Integration

### Code Quality Plugins

#### JaCoCo (Code Coverage)
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
</plugin>
```
- **Purpose**: Measures test coverage
- **Reports**: HTML and XML coverage reports
- **Threshold**: 70% minimum coverage

#### Spotless (Code Formatting)
```xml
<plugin>
    <groupId>com.diffplug.spotless</groupId>
    <artifactId>spotless-maven-plugin</artifactId>
    <version>2.43.0</version>
</plugin>
```
- **Purpose**: Enforces consistent code formatting
- **Style**: Google Java Format
- **Features**: Auto-formatting, import organization

#### SpotBugs (Static Analysis)
```xml
<plugin>
    <groupId>com.github.spotbugs</groupId>
    <artifactId>spotbugs-maven-plugin</artifactId>
    <version>4.8.2.0</version>
</plugin>
```
- **Purpose**: Finds potential bugs and code issues
- **Configuration**: Maximum effort, low threshold
- **Output**: XML reports for CI integration

#### PMD (Code Quality)
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-pmd-plugin</artifactId>
    <version>3.21.2</version>
</plugin>
```
- **Purpose**: Analyzes code for best practices
- **Rulesets**: Best practices, code style, design patterns
- **Target**: Java 17 compatibility

### Security Plugins

#### OWASP Dependency Check
```xml
<plugin>
    <groupId>org.owasp</groupId>
    <artifactId>dependency-check-maven</artifactId>
    <version>9.0.7</version>
</plugin>
```
- **Purpose**: Scans dependencies for known vulnerabilities
- **Output**: HTML and XML reports
- **Integration**: Automated security issue creation

### Utility Plugins

#### Versions Plugin
```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>versions-maven-plugin</artifactId>
    <version>2.16.2</version>
</plugin>
```
- **Purpose**: Manages dependency and plugin versions
- **Features**: Update checking, version bumping

#### SonarQube Integration
```xml
<plugin>
    <groupId>org.sonarsource.scanner.maven</groupId>
    <artifactId>sonar-maven-plugin</artifactId>
    <version>3.10.0.2594</version>
</plugin>
```
- **Purpose**: Integrates with SonarCloud for quality analysis
- **Configuration**: Requires SONAR_TOKEN secret

## 🎯 Workflow Features

### Automated Testing
- **Unit Tests**: Runs all JUnit tests
- **Test Reports**: Generates detailed test reports
- **Coverage**: Measures and enforces code coverage
- **Cross-Platform**: Tests on multiple operating systems

### Code Quality Assurance
- **Formatting**: Enforces consistent code style
- **Static Analysis**: Identifies potential issues
- **Documentation**: Validates Javadoc completeness
- **Best Practices**: Enforces coding standards

### Security Monitoring
- **Dependency Scanning**: Checks for vulnerable dependencies
- **Automated Updates**: Creates PRs for security updates
- **Vulnerability Alerts**: Creates issues for security problems

### Performance Monitoring
- **Benchmarking**: Measures application performance
- **Resource Usage**: Monitors memory and startup time
- **Size Monitoring**: Tracks JAR file size
- **Compatibility**: Tests across Java versions

### Release Automation
- **Version Management**: Automated version bumping
- **Asset Creation**: Builds and uploads release artifacts
- **Documentation**: Generates release notes
- **Notifications**: Creates announcement issues

## 🔐 Required Secrets

### GitHub Secrets Configuration

1. **GITHUB_TOKEN** (Automatic)
   - Provided automatically by GitHub
   - Used for repository operations

2. **SONAR_TOKEN** (Optional)
   - SonarCloud authentication token
   - Required for code quality analysis
   - Configure at: https://sonarcloud.io/

### Setting Up Secrets

1. Go to repository Settings → Secrets and variables → Actions
2. Click "New repository secret"
3. Add required secrets with appropriate values

## 📊 Monitoring and Reports

### GitHub Actions Dashboard
- **Location**: Repository → Actions tab
- **Features**: Workflow runs, logs, artifacts
- **Monitoring**: Success/failure rates, execution times

### Artifacts and Reports
- **Test Reports**: JUnit XML and HTML reports
- **Coverage Reports**: JaCoCo HTML reports
- **Security Reports**: OWASP dependency check reports
- **Build Artifacts**: JAR files for each platform

### GitHub Pages Documentation
- **URL**: `https://username.github.io/repository-name/javadoc/`
- **Content**: Auto-generated Javadoc
- **Updates**: Automatic on main branch pushes

## 🚀 Usage Instructions

### Running Workflows

#### Manual Workflow Dispatch
```bash
# Using GitHub CLI
gh workflow run "Release Management" -f version=1.0.0 -f release_type=minor

# Using GitHub Web Interface
# Go to Actions → Select workflow → Run workflow
```

#### Triggering Automatic Workflows
```bash
# Trigger CI/CD on push
git push origin main

# Trigger CI/CD on PR
git checkout -b feature-branch
git push origin feature-branch
# Create PR via GitHub interface
```

### Local Development

#### Code Formatting
```bash
# Check formatting
mvn spotless:check

# Apply formatting
mvn spotless:apply
```

#### Code Quality
```bash
# Run static analysis
mvn spotbugs:check
mvn pmd:check

# Generate reports
mvn site
```

#### Security Scanning
```bash
# Run dependency check
mvn org.owasp:dependency-check-maven:check
```

#### Coverage Testing
```bash
# Run tests with coverage
mvn clean test jacoco:report

# View coverage report
open target/site/jacoco/index.html
```

## 🔧 Customization

### Modifying Workflows

1. **Edit workflow files** in `.github/workflows/`
2. **Commit changes** to trigger updates
3. **Test workflows** with sample commits/PRs

### Adding New Jobs

1. **Create new workflow file** or add to existing
2. **Define triggers** and job dependencies
3. **Add required secrets** if needed
4. **Test thoroughly** before merging

### Configuring Quality Gates

1. **Update plugin configurations** in `pom.xml`
2. **Adjust thresholds** in workflow files
3. **Test with sample code** to verify gates

## 🎉 Benefits

### Development Efficiency
- **Automated Testing**: Catch issues early
- **Consistent Quality**: Enforce standards automatically
- **Fast Feedback**: Quick CI/CD cycles

### Security & Reliability
- **Vulnerability Scanning**: Proactive security monitoring
- **Dependency Management**: Automated updates
- **Cross-Platform Testing**: Ensure compatibility

### Release Management
- **Automated Releases**: Streamlined release process
- **Version Control**: Consistent versioning
- **Documentation**: Auto-generated release notes

### Monitoring & Insights
- **Performance Tracking**: Monitor application metrics
- **Quality Metrics**: Track code quality trends
- **Security Alerts**: Immediate vulnerability notifications

---

**The CI/CD pipeline provides a robust, automated development workflow that ensures code quality, security, and reliable releases for the JiraInsight Desktop application.**
