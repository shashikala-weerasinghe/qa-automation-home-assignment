# Selenium Test Automation — Trello Board App

## Project Structure

```
selenium-tests/
├── pom.xml                          # Maven dependencies & build config
├── src/
│   ├── main/java/com/trello/
│   │   ├── config/
│   │   │   └── ConfigReader.java    # Loads test configuration
│   │   ├── driver/
│   │   │   └── DriverManager.java   # Thread-safe WebDriver factory
│   │   └── pages/
│   │       ├── BasePage.java        # Common waits & selector helpers
│   │       ├── BoardListPage.java   # Home page — board creation
│   │       └── BoardDetailPage.java # Board view — list CRUD
│   └── test/
│       ├── java/com/trello/tests/
│       │   ├── BaseTest.java        # Setup/teardown & DB reset
│       │   └── BoardTest.java       # Test cases
│       └── resources/
│           ├── config.properties    # Test configuration
│           └── testng.xml           # TestNG suite definition
└── README.md
```

## Prerequisites

- **Java 11+**
- **Maven 3.6+**
- **Chrome browser** (or configure another in `config.properties`)
- Application running on `http://localhost:3000` (`npm start` from project root)

## How to Run

1. Start the application from the project root:

   ```bash
   npm start
   ```

2. In a separate terminal, run the tests:

   ```bash
   cd selenium-tests
   mvn clean test
   ```

3. Run headless (no browser window):
   ```bash
   mvn clean test -Dheadless=true
   ```

## Test Cases

| #   | Test                 | Description                                                   |
| --- | -------------------- | ------------------------------------------------------------- |
| 1   | `testCreateBoard`    | Creates a board by name + Enter, verifies redirect and title  |
| 2   | `testCreateTwoLists` | Adds two lists to a board, verifies count and visibility      |
| 3   | `testDeleteList`     | Deletes one of two lists, verifies removal and remaining list |

## Design Decisions

- **Page Object Model** — separates UI interaction from test logic for maintainability
- **`data-cy` selectors** — uses the app's stable test attributes instead of fragile CSS/XPath
- **Database reset** — each test resets via `/api/reset` for full isolation
- **ThreadLocal WebDriver** — supports parallel test execution
- **WebDriverManager** — auto-downloads correct browser driver version
- **Explicit waits** — reliable synchronization instead of `Thread.sleep()`
