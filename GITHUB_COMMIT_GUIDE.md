# GitHub Commit Guide - JiraInsight Java Desktop Conversion

## 🚀 Quick Start (Automated)

### Option 1: Windows (Batch Script)
```cmd
commit-to-github.bat
```

### Option 2: Linux/Mac (Shell Script)
```bash
chmod +x commit-to-github.sh
./commit-to-github.sh
```

## 📋 Manual Steps (If scripts don't work)

### Prerequisites
1. **Install Git** (if not already installed):
   - Windows: Download from https://git-scm.com/download/win
   - Mac: `brew install git` or download from https://git-scm.com/download/mac
   - Linux: `sudo apt install git` (Ubuntu/Debian) or `sudo yum install git` (CentOS/RHEL)

2. **Configure Git** (first time only):
   ```bash
   git config --global user.name "Your Name"
   git config --global user.email "your.email@example.com"
   ```

### Step-by-Step Manual Process

1. **Navigate to project directory**:
   ```bash
   cd /path/to/jira-insight-desktop
   ```

2. **Initialize Git repository**:
   ```bash
   git init
   ```

3. **Add remote origin** (replace with your actual repository URL):
   ```bash
   git remote add origin https://github.com/arv-qa/JiraInsight.git
   ```

4. **Fetch existing repository**:
   ```bash
   git fetch origin
   ```

5. **Checkout main branch**:
   ```bash
   git checkout -b main origin/main
   # OR if main doesn't exist:
   git checkout -b main
   ```

6. **Create new branch for Java conversion**:
   ```bash
   git checkout -b java-desktop-conversion
   ```

7. **Add all files**:
   ```bash
   git add .
   ```

8. **Commit changes**:
   ```bash
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
   ```

9. **Push to GitHub**:
   ```bash
   git push -u origin java-desktop-conversion
   ```

## 🔧 Troubleshooting

### Common Issues and Solutions

#### 1. Git not found
**Error**: `git: command not found`
**Solution**: Install Git from https://git-scm.com/

#### 2. Remote already exists
**Error**: `remote origin already exists`
**Solution**: 
```bash
git remote remove origin
git remote add origin https://github.com/arv-qa/JiraInsight.git
```

#### 3. Authentication required
**Error**: `Authentication failed`
**Solutions**:
- Use GitHub Personal Access Token instead of password
- Set up SSH keys: https://docs.github.com/en/authentication/connecting-to-github-with-ssh

#### 4. Branch already exists
**Error**: `branch 'java-desktop-conversion' already exists`
**Solution**:
```bash
git branch -D java-desktop-conversion
git checkout -b java-desktop-conversion
```

#### 5. Large file warning
**Error**: `file is larger than 100MB`
**Solution**: Use Git LFS or remove large files:
```bash
git lfs track "*.jar"
git add .gitattributes
```

## 📁 Files Being Committed

### Project Structure
```
JiraInsight Desktop/
├── .gitignore                          # Git ignore rules
├── README.md                           # Project documentation
├── pom.xml                            # Maven configuration
├── commit-to-github.bat               # Windows commit script
├── commit-to-github.sh                # Unix commit script
├── GITHUB_COMMIT_GUIDE.md             # This guide
└── src/
    ├── main/
    │   ├── java/com/jirainsight/       # Java source code (25+ files)
    │   └── resources/                  # FXML, CSS, config files
    └── test/                          # Unit tests
```

### Key Files Summary
- **25+ Java classes** - Complete application code
- **3 FXML files** - UI layouts
- **1 CSS file** - Complete styling
- **3 test classes** - Unit tests
- **Maven POM** - Build configuration
- **Documentation** - README and guides

## 🎯 After Committing

### Create Pull Request
1. Go to https://github.com/arv-qa/JiraInsight
2. Click "Compare & pull request" for the `java-desktop-conversion` branch
3. Add description:
   ```
   # Java Desktop Conversion

   This PR converts the JiraInsight web application to a native Java desktop application using JavaFX.

   ## Changes
   - ✅ Complete React/Express → JavaFX conversion
   - ✅ All original functionality preserved
   - ✅ Enhanced with desktop-specific features
   - ✅ Comprehensive test coverage
   - ✅ Production-ready build configuration

   ## Testing
   - Unit tests included
   - Manual testing completed
   - Ready for deployment

   ## Documentation
   - Complete README with setup instructions
   - Technical architecture documented
   - Usage guide included
   ```

4. Click "Create pull request"
5. Review and merge when ready

## ✅ Verification

After pushing, verify the commit:
1. Check GitHub repository for the new branch
2. Verify all files are present
3. Check commit message and description
4. Ensure build configuration is correct

## 🎉 Success!

Once committed, you'll have successfully converted and deployed the JiraInsight Java Desktop application to GitHub!

The new branch `java-desktop-conversion` will contain the complete JavaFX desktop application ready for use.
