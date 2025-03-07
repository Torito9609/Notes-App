#!/bin/bash
# start.sh
# This script responsible for:
# 1. Compiling and running the Spring Boot project located in backend/notes.
# 2. Installing dependencies and launching the React development server located in frontend/react-notes-app.
#
# Requirements:
# - Java and Maven 
# - Node.js and npm
# - Linux/macOS environment

# --- Backend Configuration ---
echo "Starting the backend..."
cd backend/notes || { echo "Directory backend/notes not found"; exit 1; }

# Compile the project with Maven (using the wrapper)
./mvnw clean install

# Find the generated jar file (assumes it is created in the target directory)
JAR_FILE=$(find target -type f -name "*.jar" | head -n 1)
if [ -z "$JAR_FILE" ]; then
  echo "Jar file not found in target"
  exit 1
fi

# Run the backend in the background and redirect logs to ../../backend.log
nohup java -jar "$JAR_FILE" > ../../backend.log 2>&1 &

echo "Backend started. Logs available in backend.log"

# Return to the repository root
cd ../../

# --- Frontend Configuration ---
echo "Starting the frontend..."
cd frontend/react-notes-app || { echo "Directory frontend/react-notes-app not found"; exit 1; }

# Install dependencies if needed
npm install

# Start the React development server in the background
npm start &

echo "Frontend started. The application will open at http://localhost:3000"

# Return to the repository root
cd ../../

echo "Application is running. Check backend.log for backend logs."
