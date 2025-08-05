# Changelog

All notable changes to the JiraInsight Desktop project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2025-08-05

### Added
- **Initial Release**: Complete JavaFX desktop application
- **Authentication System**: Secure Jira API token authentication with validation
- **Quick Search**: Search for issues by key (e.g., PROJ-123)
- **JQL Search**: Advanced JQL query support with up to 50 results
- **Issue Display**: Comprehensive issue view with all metadata
- **Rich Content**: HTML rendering for descriptions and comments
- **Subtasks**: Full subtask display with status indicators
- **Comments**: Complete comment history with timestamps
- **Modern UI**: JavaFX interface with responsive design
- **Error Handling**: Comprehensive error handling and user feedback
- **Progress Indicators**: Loading states and status messages
- **Status Coding**: Color-coded status and priority indicators
- **Offline Storage**: In-memory credential storage
- **Build System**: Complete Maven configuration
- **Testing**: Unit tests for core functionality
- **Documentation**: Comprehensive README and guides

### Technical Features
- **JavaFX 21**: Modern UI framework
- **OkHttp 4.12**: Robust HTTP client for Jira API
- **Jackson 2.16**: JSON processing and serialization
- **Maven Build**: Complete build and dependency management
- **Java 17+**: Modern Java runtime requirement
- **MVC Architecture**: Clean separation of concerns
- **FXML Layouts**: Declarative UI definitions
- **CSS Styling**: Modern visual design
- **Logging**: Configurable logging with Logback

### Architecture
- **Controllers**: JavaFX controllers for UI logic
- **Services**: Business logic and API integration
- **Models**: Data models with validation
- **Utilities**: Helper classes and utilities
- **Resources**: FXML, CSS, and configuration files
- **Tests**: Unit tests for core components

### Conversion Notes
- **Source**: Converted from React/Express web application
- **Functionality**: All original features preserved and enhanced
- **Performance**: Native desktop performance improvements
- **Security**: Enhanced security with no web vulnerabilities
- **UX**: Desktop-specific user experience patterns
- **Integration**: Better system integration capabilities

## [Unreleased]

### Planned Features
- **Persistent Storage**: Database integration for credential storage
- **Issue Management**: Edit issues, create comments, update status
- **Advanced Views**: Sprint planning, Kanban boards, burndown charts
- **Export Functionality**: Export search results to various formats
- **Keyboard Shortcuts**: Productivity enhancements
- **Dark Mode**: Theme customization
- **Notifications**: System notifications for updates
- **Auto-Update**: Automatic application updates
- **Plugins**: Extension system for custom functionality
- **Multi-Instance**: Support for multiple Jira instances

### Technical Improvements
- **Performance**: Further optimization and caching
- **Memory**: Memory usage optimization
- **Startup**: Faster application startup
- **Packaging**: Native installers for different platforms
- **CI/CD**: Automated build and deployment pipeline
- **Testing**: Expanded test coverage
- **Documentation**: Enhanced user and developer documentation
