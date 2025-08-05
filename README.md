# JiraInsight Desktop

A modern JavaFX desktop application for viewing and searching Jira issues, converted from the original web-based JiraInsight application.

## 🎯 Features

- **Secure Authentication**: Connect using Jira API tokens with real-time credential validation
- **Quick Issue Lookup**: Search for specific issues by key (e.g., JIRA-1234)
- **Advanced JQL Search**: Execute custom JQL queries with up to 50 results
- **Detailed Issue View**: Comprehensive issue information including subtasks and comments
- **Modern Desktop UI**: Native JavaFX interface with responsive design
- **Offline Capabilities**: Local credential storage and caching

## 🏗️ Technical Architecture

### Technology Stack
- **Frontend**: JavaFX 21 with FXML and CSS
- **HTTP Client**: OkHttp 4.12
- **JSON Processing**: Jackson 2.16
- **Storage**: In-memory with H2 database support
- **Build Tool**: Maven 3.x
- **Java Version**: 17+

### Project Structure
```
src/
├── main/
│   ├── java/com/jirainsight/
│   │   ├── controller/          # JavaFX controllers
│   │   ├── model/              # Data models (POJOs)
│   │   ├── service/            # Business logic services
│   │   ├── util/               # Utility classes
│   │   └── JiraInsightApplication.java
│   └── resources/
│       ├── fxml/               # JavaFX FXML files
│       ├── css/                # Stylesheets
│       └── logback.xml         # Logging configuration
└── test/                       # Unit tests
```

## 🚀 Getting Started

### Prerequisites
- **Java 17+** - Runtime environment
- **Maven 3.6+** - Build tool
- **Jira Cloud instance** - Active Atlassian account
- **Jira API token** - [Generate from Atlassian account](https://support.atlassian.com/atlassian-account/docs/manage-api-tokens-for-your-atlassian-account/)

### Building the Application

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd jira-insight-desktop
   ```

2. **Build with Maven**
   ```bash
   mvn clean compile
   ```

3. **Run tests**
   ```bash
   mvn test
   ```

4. **Create executable JAR**
   ```bash
   mvn clean package
   ```

### Running the Application

#### Development Mode
```bash
mvn javafx:run
```

#### Production JAR
```bash
java -jar target/jira-insight-desktop-1.0.0.jar
```

## 📖 Usage Guide

### Initial Setup
1. Launch the application
2. Click **"Connect to Jira"**
3. Enter your credentials:
   - **Domain**: your-company.atlassian.net
   - **Email**: Your Jira account email
   - **API Token**: Generated from your Atlassian account
4. Click **"Connect"** to validate and save credentials

### Searching for Issues

#### Quick Search
- Enter an issue key (e.g., "PROJ-123") in the Quick Search tab
- Press Enter or click "Search"

#### JQL Search
- Switch to the JQL Search tab
- Enter a JQL query, for example:
  ```
  project = "TEST" AND status = "In Progress"
  assignee = currentUser() AND sprint in openSprints()
  ```
- Click "Execute Query"

### Viewing Issue Details
- Click any issue from the search results
- View comprehensive information including:
  - Issue metadata (assignee, reporter, priority, etc.)
  - Description with rich text formatting
  - Subtasks with status indicators
  - Comments with timestamps

## 🔧 API Integration

The application integrates with **Jira Cloud REST API v3**:
- **Authentication**: Basic Auth using email + API token
- **Issue Lookup**: `/rest/api/3/issue/{issueKey}`
- **JQL Search**: `/rest/api/3/search`
- **Field Expansion**: Automatically includes subtasks and comments

## 🛠️ Development

### Available Maven Goals
```bash
mvn javafx:run          # Run in development mode
mvn clean compile       # Compile sources
mvn test               # Run unit tests
mvn package            # Create executable JAR
mvn javafx:jlink       # Create native runtime image
```

### Key Components

#### Models
- `JiraIssue` - Main issue data model
- `JiraCredentials` - Authentication credentials
- `JiraSearchResult` - Search result container
- Supporting models for status, priority, users, etc.

#### Services
- `JiraApiService` - HTTP client for Jira REST API
- `StorageService` - In-memory data storage

#### Controllers
- `MainController` - Primary application controller
- `CredentialsDialogController` - Authentication dialog
- `IssueDetailsController` - Detailed issue view
- `IssueListCell` - Custom list cell renderer

## 🎨 Customization

### Styling
The application uses CSS for styling. Main stylesheet: `src/main/resources/css/application.css`

Key style classes:
- `.primary-button` - Primary action buttons
- `.issue-key` - Issue key labels
- `.status-*` - Status indicators
- `.priority-*` - Priority indicators

### Configuration
- Logging: `src/main/resources/logback.xml`
- Maven: `pom.xml`

## ⚠️ Important Notes

- **Demo Storage**: Currently uses in-memory storage (credentials not persisted between sessions)
- **Result Limit**: JQL queries limited to 50 search results per request
- **Jira Cloud Only**: Requires Jira Cloud (does not support Jira Server)
- **API Token Required**: Must generate API token from Atlassian account

## 🔮 Future Enhancements

- **Persistent Storage**: Database integration for credential storage
- **Issue Management**: Edit issues, create comments, update status
- **Advanced Views**: Sprint planning, Kanban boards, burndown charts
- **Export Functionality**: Export search results to various formats
- **Keyboard Shortcuts**: Productivity enhancements
- **Dark Mode**: Theme customization

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

## 🔄 Migration from Web Version

This desktop application is a complete conversion of the original React/Express web application to JavaFX. Key improvements:

- **Native Performance**: No browser overhead
- **Better Integration**: System tray, notifications, file associations
- **Offline Capabilities**: Local storage and caching
- **Enhanced Security**: No web vulnerabilities
- **Improved UX**: Native desktop patterns and behaviors

---

Built with ❤️ using JavaFX, OkHttp, and modern Java technologies
