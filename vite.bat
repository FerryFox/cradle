@echo off

REM Navigate to frontend directory
cd frontend

REM Build React app
call npm run build

REM Copy build output to Spring Boot's static folder
xcopy /E /I /Y "C:\FoxGate\Java\cradle\frontend\dist\*" "C:\FoxGate\Java\cradle\src\main\resources\static"
