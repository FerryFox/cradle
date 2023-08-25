#!/bin/bash

# Navigate to frontend directory
cd frontend

# Build React app
npm run build

# Copy build output to Spring Boot's static folder
cp -r build/* ../src/main/resources/static/

# Navigate back to the root directory
cd ..

# Run Spring Boot application
./mvnw spring-boot:run