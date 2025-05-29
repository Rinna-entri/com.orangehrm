             OrangeHRM Automated Test Project
 
This project contains automated test cases for the [OrangeHRM](https://opensource-demo.orangehrmlive.com/) web application using Selenium WebDriver, TestNG and Maven. It also includes performance testing using Apache JMeter. It focuses on validating key functionalities such as Login, Signup, Home page interactions, and Link validations, following best practices in test automation.

            Project Description
OrangeHRM is an open-source HR management tool that offers features like employee management, leave tracking, time and attendance, recruitment, and performance modules. It helps organizations streamline and automate HR tasks efficiently.

              Project Highlights

Integrated ExtentReports for rich, detailed HTML reporting.

Captured screenshots on test failures to aid debugging.

Executed cross-browser testing on Chrome, Firefox, and Edge.

Performed SQL injection vulnerability checks during signup tests.

TestNG Framework: Structured execution with assertions and parameterization.

Reusable Utilities: Common functions for browser setup, waits, and element actions.

Maven Integration: Simplified build and dependency management.

Cross-Browser Ready: Supports Chrome, Firefox, and Edge (configurable).

Test Reporting: Integrated TestNG and Extent Reports.

CI/CD Ready: Easily integrable with Jenkins or GitHub Actions.

Incorporated Apache JMeter scripts for  Spike and Stress performance testing.

           Project Structure
           
OrangeHRM-Automation/
├── src/
│   ├── main/java/
│   └── test/java/
│       ├── tests/              # Test classes
│                  # Page Object Model classes
│       └── utils/              # Reusable utility classes
├── testng.xml                  # TestNG suite configuration
├── pom.xml                     # Maven dependencies
└── README.md                   # Project documentation




PerformanceTesting/ ├──  SpikeTest

pom.xml README.md testng.xml

           Technologies Used
Java Selenium WebDriver TestNG Maven ExtentReports (for reporting)  Listeners & ScreenshotUtil (for failed test capture) Apache jmeter for performance testing GitHub

           Test Case Summary
Login Tests to validate login functionality using valid and invalid credential combinations      Valid username & password – login successful      Invalid credentials – should show error      

Signup Tests to validate new user registration      Focused on SQL injection in the username field to check system vulnerability        Tested across Chrome, Firefox, Edge

Add user, search user, edit user, delete user     Add employee, search employee, edit employee, delete employee

LApply leave, view leave list, leave status check     	Page title verification, dashboard widgets visibility  

Utility Features       Captures screenshots on test failures      Generates detailed reports using ExtentReports      Test execution tracking via TestNG Listeners

           How to Run the Tests
Clone the repository.

Import as a Maven project in Eclipse.

Open testng.xml and run as TestNG Suite.

Reports will be generated in the test-output folder.

         
           Manual Testing Artifacts           
Manual test cases, Test Scenarios, bug reports, and execution reports are maintained separately. [View Manual Testing Documents (Google Drive)] ([https://drive.google.com/drive/folders/1F4To6Lra6nlvCYeUMe5hDC14YbSx-GUM?usp=drive_link](https://drive.google.com/drive/folders/1p9jEfgRoF0qO86rSL7ARfmlQSc1Qt7qE?usp=sharing))

[Test Scenarios] (https://docs.google.com/spreadsheets/d/1nF6uzwvjMEwEHiZAlcCyFG4iF9PSlKjipNx-xaI1TRs/edit?usp=sharing)

[TestCases] (https://docs.google.com/spreadsheets/d/1IhTVRwblvxYubpCzs0ghxzvb3jPVwFhWtRb6Ke4cISQ/edit?usp=sharing)

[Bug Report] (https://docs.google.com/spreadsheets/d/1lerskz6nQ8VdPCUYGmNpDPri8kMNtGLRbDnRCSn9vUM/edit?usp=sharing)

[Test Execution Report] (https://docs.google.com/spreadsheets/d/1VprwHlAvcXkc9ttxTWjGp8Pplz_q4V441nrD0reqIyY/edit?usp=sharing)

        Performance Testing (JMeter)
This section includes performance testing scenarios for the Kerala Tourism website using Apache JMeter.

Types of Performance Tests Conducted

Spike Test • Purpose: Tests sudden spike in user load and observes recovery behavior. • Files: spiketest.jmx, spiketest.jtl, SpikeTest_Report/

         Notes & Best Practices


Utilities implemented for logging, screenshots

Tests designed for cross-browser compatibility.

Continuous integration-friendly structure with Maven and TestNG.

Performance testing complements functional test coverage.

Modular Code Structure: Follow the Page Object Model (POM) to separate test logic from UI locators, making the framework scalable and maintainable.

Reusable Utility Methods: Common functions such as login, wait utilities, screenshot capture, and dropdown handling should be implemented as reusable methods.

Assertion Strategy: Use both hard and soft assertions as appropriate to ensure test validations are robust and meaningful.

Use ExtentReports for rich and visually informative HTML reports.

Include screenshots on test failures to ease debugging.

Exclude IDE-specific or system-specific files using .gitignore.

Parallel Execution: Configure TestNG XML to run tests in parallel for faster execution (if applicable).

Continuous Integration: Integrate with CI tools like  GitHub Actions for automated test execution on every push or PR.
