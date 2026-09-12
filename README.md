# AttendWise - Smart Attendance and Academic Alert System

AttendWise is a command-line Java application for maintaining student records, recording attendance, analysing attendance risk, and generating simple reports.

## Requirements
- Java 17 or later
- Maven 3.8 or later

## Project Modules
1. Student Management
2. Attendance Management
3. Attendance Risk Analysis
4. Report Generation

## Run
Open a terminal in the project folder and run:

```bash
mvn clean test
mvn package
java -jar target/attendwise-1.0.0.jar
```

The application stores its data in the `data` directory.

## Testing
Run:

```bash
mvn test
```

## Notes
This is a console-based application, so it can be executed without a graphical interface.
