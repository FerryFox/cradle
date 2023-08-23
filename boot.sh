#!/bin/bash

# Navigate to frontend directory
cd frontend

# Build React app
npm run build

# Copy build output to Spring Boot's static folder
cp -r build/* ../src/main/resources/static/