#Intelligent Bus Driver Guidance System
Assignment 4 – Group R12
Team Members
Pranavan Sivakumar
Team Member 2
Team Member 3
Project Description

The Intelligent Bus Driver Guidance System is a Java-based application designed to assist bus drivers by managing and validating driver and bus information. The system supports data storage, validation, unit testing, integration testing, and continuous integration through GitHub Actions.

Technologies Used
Java 17
Maven
JUnit 5
Git
GitHub
GitHub Actions
Project Structure
src/
├── main/java/com/busguidance
│   ├── model
│   ├── repository
│   └── validation
│
└── test/java/com/busguidance
├── unit
└── integration

data/
├── drivers.txt
└── buses.txt
Running the Project
Clone the repository:
git clone https://github.com/rmit-computing-technologies/IntelligentBusDriverGuidanceSystem-Assignment-4-Group_R12.git
Open the project in IntelliJ IDEA.
Allow Maven to download dependencies.
Build the project.
Running Tests

Run all tests using Maven:

mvn test

Or run individual test classes through IntelliJ.

Continuous Integration

GitHub Actions is configured to automatically:

Build the project
Execute unit tests
Verify project integrity on every push to the main branch

Workflow file:

.github/workflows/maven.yml
Repository

GitHub Repository:

https://github.com/rmit-computing-technologies/IntelligentBusDriverGuidanceSystem-Assignment-4-Group_R12

Version

Current Version: 1.0
