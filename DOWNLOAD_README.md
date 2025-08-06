# 📦 JiraInsight Desktop - Download Guide

Welcome to JiraInsight Desktop! This guide will help you download, install, and run the application on your system.

## 🚀 Quick Start

### 1. Choose Your Download

#### 🖥️ **Native Installers (Recommended)**
- **Windows**: Download `JiraInsight-Desktop-X.X.X-Windows.msi`
- **macOS**: Download `JiraInsight-Desktop-X.X.X-macOS.dmg`
- **Linux**: Download `jirainsight-desktop-X.X.X-Linux.deb`

#### ☕ **Universal JAR (Cross-Platform)**
- **All Platforms**: Download `jira-insight-desktop-X.X.X.jar`
- Requires Java 17+ to be installed

### 2. Installation Instructions

#### Windows (.msi)
1. Download the `.msi` file
2. Double-click to run the installer
3. Follow the installation wizard
4. Launch from Start Menu or Desktop shortcut

#### macOS (.dmg)
1. Download the `.dmg` file
2. Double-click to mount the disk image
3. Drag JiraInsight Desktop to Applications folder
4. Launch from Applications or Launchpad

#### Linux (.deb)
```bash
# Download the .deb file, then:
sudo dpkg -i jirainsight-desktop-X.X.X-Linux.deb

# If dependencies are missing:
sudo apt-get install -f

# Launch from applications menu or terminal:
jirainsight-desktop
```

#### Universal JAR
1. Ensure Java 17+ is installed
2. Download the `.jar` file
3. Use provided launcher scripts or run directly:

**Windows:**
```cmd
java -jar jira-insight-desktop-X.X.X.jar
```

**macOS/Linux:**
```bash
java -jar jira-insight-desktop-X.X.X.jar
```

## 📋 System Requirements

### Minimum Requirements
- **RAM**: 512 MB available memory
- **Storage**: 200 MB free disk space
- **Display**: 1024x768 resolution

### Platform-Specific Requirements

#### Windows
- **OS**: Windows 10 or later (64-bit)
- **Java**: Not required for native installer

#### macOS
- **OS**: macOS 10.14 (Mojave) or later
- **Architecture**: Intel or Apple Silicon (M1/M2)
- **Java**: Not required for native installer

#### Linux
- **OS**: Ubuntu 18.04+ or equivalent distribution
- **Architecture**: x86_64 (64-bit)
- **Java**: Not required for native installer

#### Universal JAR
- **Java**: OpenJDK or Oracle JDK 17 or higher
- **JavaFX**: Included in the JAR file

## 🛠️ Troubleshooting

### Common Issues

#### "Application won't start"
1. **Check Java version** (for JAR):
   ```bash
   java -version
   ```
   Ensure version is 17 or higher

2. **Use launcher scripts**:
   - Windows: `scripts/run-jira-insight.bat`
   - macOS/Linux: `scripts/run-jira-insight.sh`

3. **Check system requirements** above

#### "JavaFX Runtime Error"
This usually happens with the JAR version. Solutions:
1. Use the native installer instead
2. Ensure you're using Java 17+ with JavaFX included
3. Use the provided launcher scripts

#### Windows Security Warning
If Windows shows a security warning:
1. Click "More info"
2. Click "Run anyway"
3. This is normal for new applications

#### macOS Gatekeeper Warning
If macOS blocks the application:
1. Go to System Preferences → Security & Privacy
2. Click "Open Anyway" for JiraInsight Desktop
3. Or run: `xattr -d com.apple.quarantine /Applications/JiraInsight\ Desktop.app`

### Getting Help

If you encounter issues:
1. Check the [GitHub Issues](https://github.com/arv-qa/JiraInsight-Desktop/issues)
2. Create a new issue with:
   - Your operating system and version
   - Java version (if using JAR)
   - Error messages or screenshots
   - Steps to reproduce the problem

## 🔧 Advanced Usage

### Command Line Options

The JAR version supports command line arguments:

```bash
# Show help
java -jar jira-insight-desktop-X.X.X.jar --help

# Enable debug logging
java -jar jira-insight-desktop-X.X.X.jar --debug

# Specify configuration file
java -jar jira-insight-desktop-X.X.X.jar --config=/path/to/config.properties
```

### Custom Java Arguments

For the JAR version, you can customize JVM settings:

```bash
java -Xmx2048m -Dfile.encoding=UTF-8 -jar jira-insight-desktop-X.X.X.jar
```

### Launcher Scripts

Use the provided launcher scripts for optimal experience:

**Windows:**
```cmd
scripts\run-jira-insight.bat
```

**macOS/Linux:**
```bash
chmod +x scripts/run-jira-insight.sh
./scripts/run-jira-insight.sh
```

## 📚 Next Steps

After installation:

1. **Launch the application**
2. **Connect to Jira**:
   - Enter your Jira domain (e.g., `company.atlassian.net`)
   - Provide your email and API token
   - [Generate API token here](https://id.atlassian.com/manage-profile/security/api-tokens)
3. **Start searching**:
   - Use quick issue lookup (e.g., `PROJ-123`)
   - Execute JQL queries for advanced searches
   - View detailed issue information

## 🔄 Updates

- **Native installers**: Check for updates within the application
- **JAR version**: Download the latest release from GitHub
- **Automatic updates**: Coming in future versions

---

**Need more help?** Visit our [GitHub repository](https://github.com/arv-qa/JiraInsight-Desktop) for documentation, tutorials, and support.
