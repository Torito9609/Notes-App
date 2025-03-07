# Notes Application

This is a full-stack application for managing notes (create, edit, archive, assign categories, and filtering) built with Spring Boot for the backend and React for the frontend.

## Table of Contents

- [Project Structure](#project-structure)
- [Requirements](#requirements)
- [Installation and Setup](#installation-and-setup)
- [Running the Application](#running-the-application)
- [Available Scripts](#available-scripts)
- [Configuration Details](#configuration-details)

## Project Structure

The repository is organized as follows:
```bash
root/ ├── backend/ │ └── notes/ // Spring Boot project for the backend ├── frontend/ │ └── react-notes-app/ // React project for the frontend ├── start.sh // Shell script to compile and run both backend and frontend (Linux/macOS) └── README.md
```

## Requirements

### Backend
- **Java:** JDK 21.  
  *If you use Java 21, ensure JAVA_HOME is correctly configured.*
- **Maven:** Maven wrapper (`./mvnw`) is used in the project.
- **Spring Boot:** Version 3.4.3.
- **Database:** MySQL (with JDBC URL configured to auto-create the database if it doesn't exist)
    Please remember to update username and password as your configuration for MySQL server.
  Example configuration in `application.properties`:
```bash
 spring.datasource.url=jdbc:mysql://localhost:3306/notesdb?createDatabaseIfNotExist=true&useSSL=false&serverTimeZone=UTC spring.jpa.hibernate.ddl-auto=update
 spring.datasource.username=root
 spring.datasource.password=
```
- Additional dependencies: Spring Boot Starter Web, Data JPA, Validation, Lombok, Spring Boot DevTools, Hibernate Validator, Jakarta Validation, H2 Database, MySQL Connector.

### Frontend
- **Node.js:** Version 22.14.0.
- **npm:** Version 10.9.2.
- **React:** Created using Create React App.
- Additional dependency: Bootstrap.

### Tools and Environment
- **OS:** The provided scripts are designed to work in a Linux/macOS environment.
- **For Windows:** You can use Git Bash or WSL to run the shell scripts.
- **Editors:** VSCode, IntelliJ IDEA, Notepad++.

## Installation and Setup

1. **Clone the Repository:**
 ```bash
 git clone <repository_url>
 cd <repository_folder>
```
2. **Backend Setup:**

Navigate to backend/notes.
Ensure your application.properties file has the correct database configuration.
The database will be created automatically if it does not exist, and default categories will be inserted at startup via a CommandLineRunner (see DataInitializer.java in com.backend.notes.config).

3. **Frontend Setup:**

Navigate to frontend/react-notes-app.
Run npm install to install all dependencies.

## Running the Application
A single shell script, start.sh, is provided to compile and run both the backend and the frontend.

**Using Linux/macOS or Git Bash/WSL on Windows:**
 1. Open a terminal in the repository root (where start.sh is located).4
 2. Make the script executable (if not already)
  ```bash
 chmod +x start.sh
```
3. Run the script:
```bash
 ./start.sh
```

This script performs the following:

 - Navigates to backend/notes, compiles the Spring Boot project with Maven, and runs the generated jar in the background (redirecting logs to backend.log).
 - Navigates to frontend/react-notes-app, installs dependencies if necessary, and starts the React development server in the background.
  
The backend logs will be available in the file backend.log in the repository root, and the frontend will be available at http://localhost:3000.

## Available Scripts
start.sh
 - Purpose: Compiles and launches both the backend and the frontend.
 - Usage:
```bash
 ./start.sh
```
## Configuration Details

**Database:**
The backend uses a MySQL database with the following JDBC URL:
```bash
jdbc:mysql://localhost:3306/notesdb?createDatabaseIfNotExist=true&useSSL=false&serverTimeZone=UTC
```
Hibernate is configured (e.g., spring.jpa.hibernate.ddl-auto=update) to automatically create or update tables based on the JPA entities.

**Please make sure your MySQL server is running before rund the start.sh script.**


**Default Data:**

 - At startup, a DataInitializer (implementing CommandLineRunner) runs and creates four default categories if none exist.

**CORS:**

 - The backend is configured with a global CORS policy (see WebConfig.java) to allow requests from http://localhost:3000.
