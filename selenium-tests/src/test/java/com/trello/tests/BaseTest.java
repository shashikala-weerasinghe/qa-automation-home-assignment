package com.trello.tests;

import com.trello.config.ConfigReader;
import com.trello.driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Base test class handling WebDriver lifecycle and database reset.
 * Every test method gets a fresh browser and clean database state.
 */
public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        resetDatabase();
        driver = DriverManager.getDriver();
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }

    /**
     * Resets the backend database to a clean empty state via the /api/reset endpoint.
     * This ensures test isolation — each test starts with no boards, lists, or cards.
     */
    private void resetDatabase() {
        try {
            URL url = new URL(ConfigReader.getBaseUrl() + "/api/reset");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.getOutputStream().write("{}".getBytes());
            conn.getResponseCode();
            conn.disconnect();
        } catch (IOException e) {
            System.err.println("Warning: Could not reset database — " + e.getMessage());
        }
    }
}
