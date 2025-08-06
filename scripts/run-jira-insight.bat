@echo off
setlocal enabledelayedexpansion

REM JiraInsight Desktop Launcher Script for Windows
REM This script launches the JiraInsight Desktop application with proper JavaFX configuration

title JiraInsight Desktop Launcher

echo.
echo JiraInsight Desktop Launcher
echo ==============================
echo.

REM Check if Java is installed
echo [INFO] Checking Java installation...
java -version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Java is not installed or not in PATH
    echo [ERROR] Please install Java 17 or higher from https://adoptium.net/
    pause
    exit /b 1
)

REM Get Java version
for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVA_VERSION=%%g
)
set JAVA_VERSION=%JAVA_VERSION:"=%
for /f "delims=. tokens=1" %%v in ("%JAVA_VERSION%") do set JAVA_MAJOR=%%v

if %JAVA_MAJOR% LSS 17 (
    echo [ERROR] Java 17 or higher is required. Found: %JAVA_VERSION%
    echo [ERROR] Please upgrade Java from https://adoptium.net/
    pause
    exit /b 1
)

echo [SUCCESS] Java %JAVA_VERSION% found

REM Find the JAR file
echo [INFO] Looking for JAR file...
set JAR_FILE=
for %%f in (jira-insight-desktop-*.jar) do (
    if exist "%%f" (
        set JAR_FILE=%%f
        goto :found_jar
    )
)

for %%f in (target\jira-insight-desktop-*.jar) do (
    if exist "%%f" (
        set JAR_FILE=%%f
        goto :found_jar
    )
)

for %%f in (..\jira-insight-desktop-*.jar) do (
    if exist "%%f" (
        set JAR_FILE=%%f
        goto :found_jar
    )
)

for %%f in (*.jar) do (
    if exist "%%f" (
        set JAR_FILE=%%f
        goto :found_jar
    )
)

echo [ERROR] JAR file not found
echo [ERROR] Please ensure jira-insight-desktop-*.jar is in the current directory
pause
exit /b 1

:found_jar
echo [SUCCESS] Found JAR file: %JAR_FILE%

REM Parse command line arguments
set DEBUG_MODE=0
set SHOW_HELP=0

:parse_args
if "%~1"=="" goto :launch_app
if /i "%~1"=="-h" set SHOW_HELP=1
if /i "%~1"=="--help" set SHOW_HELP=1
if /i "%~1"=="--debug" set DEBUG_MODE=1
shift
goto :parse_args

:show_help
if %SHOW_HELP%==1 (
    echo.
    echo JiraInsight Desktop Launcher
    echo.
    echo Usage: %~nx0 [options]
    echo.
    echo Options:
    echo   -h, --help     Show this help message
    echo   --debug        Enable debug mode
    echo.
    echo Examples:
    echo   %~nx0                 # Launch the application
    echo   %~nx0 --debug         # Launch with debug output
    echo.
    echo Requirements:
    echo   - Java 17 or higher
    echo   - jira-insight-desktop-*.jar file in current directory
    echo.
    pause
    exit /b 0
)

:launch_app
echo [INFO] Launching JiraInsight Desktop...

REM JavaFX runtime arguments for compatibility
set JAVAFX_ARGS=--add-opens=javafx.controls/com.sun.javafx.scene.control.behavior=ALL-UNNAMED
set JAVAFX_ARGS=%JAVAFX_ARGS% --add-opens=javafx.controls/com.sun.javafx.scene.control=ALL-UNNAMED
set JAVAFX_ARGS=%JAVAFX_ARGS% --add-opens=javafx.base/com.sun.javafx.binding=ALL-UNNAMED
set JAVAFX_ARGS=%JAVAFX_ARGS% --add-opens=javafx.base/com.sun.javafx.event=ALL-UNNAMED
set JAVAFX_ARGS=%JAVAFX_ARGS% --add-opens=javafx.graphics/com.sun.javafx.util=ALL-UNNAMED
set JAVAFX_ARGS=%JAVAFX_ARGS% --add-opens=java.base/java.lang.reflect=ALL-UNNAMED

REM Additional JVM arguments
set JVM_ARGS=-Xmx1024m -Dfile.encoding=UTF-8 -Djava.awt.headless=false

REM Debug mode
if %DEBUG_MODE%==1 (
    echo [DEBUG] Debug mode enabled
    set JVM_ARGS=%JVM_ARGS% -Djava.util.logging.level=ALL
)

REM Launch the application
echo [INFO] Starting application...
java %JVM_ARGS% %JAVAFX_ARGS% -jar "%JAR_FILE%"

set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE%==0 (
    echo [SUCCESS] Application closed successfully
) else (
    echo [ERROR] Application exited with code: %EXIT_CODE%
    echo [ERROR] Check the console output above for error details
)

echo.
echo Press any key to close this window...
pause >nul
exit /b %EXIT_CODE%
