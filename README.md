# 🚀 Transmedia QA Recruitment – Technical Test

This repository contains the solution for the **Transmedia QA Recruitment Technical Assessment**, covering both **UI Automation** and **API Automation**.

---

## 📌 Tech Stack

### UI Automation

- Selenium WebDriver (Java)
- TestNG
- Maven

### API Automation

- Postman / Jest

### Other Tools

- Node.js
- GitHub

---

## 📂 Project Structure

project-root/
│── ui-tests/ # UI automation test cases
│── api-tests/ # API automation test cases
│── pages/ # Page Object Model (POM)
│── utils/ # Utility classes
│── test-data/ # Test data
│── screenshots/ # Test result images
│── pom.xml
│── README.md

---

## ⚙️ Setup Instructions

### 1️⃣ Clone the Application

```bash
git clone https://github.com/shashikala-weerasinghe/qa-automation-home-assignment

2️⃣ Install Dependencies
npm install
3️⃣ Run the Application
npm start

Application will run on:

http://localhost:3000

🧪 Test Scenarios

✅ UI Automation

Create a Board
Enter board name
Press Enter
Verify board created
Add Lists
Add two lists
Verify both lists created
Delete List
Delete one list
Verify deletion

🌐 API Automation

Create a List (POST)
Delete the Created List (DELETE)

▶️ Running Tests

UI Tests
mvn clean test
API Tests
Postman
Import collection from /api-tests
Run using Collection Runner
Jest
npm test

📸 Test Results
UI Automation Results
![alt text](image.png)



API Automation Results
![alt text](image-1.png)



✔ All test cases executed successfully

🧱 Design Pattern

Page Object Model (POM)
Reusable components
Clean separation of UI & API tests

✅ Best Practices
Clean and maintainable code
Reusable methods
Explicit waits (no hard waits)
Proper assertions
Organized folder structure

⚠️ Assumptions
Application runs on http://localhost:3000
API endpoints are accessible


👨‍💻 Author

Shashikala Weerasinghe

⭐ Quick Start
# Start app
npm install
npm start

# Run UI tests
mvn clean test

# Run API tests
npm test

---

```
