package com.trello.tests;

import com.trello.config.ConfigReader;
import com.trello.driver.DriverManager;
import com.trello.listeners.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Listeners(TestListener.class)
public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void suiteSetUp() {
        System.out.println("========== Test Suite Started ==========");
        System.out.println("Base URL: " + ConfigReader.getBaseUrl());
        System.out.println("Browser: " + ConfigReader.getBrowser());
        System.out.println("Headless: " + ConfigReader.isHeadless());
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        resetDatabase();
        driver = DriverManager.getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            System.err.println("FAILED: " + result.getName() + " — " + result.getThrowable().getMessage());
        }
        DriverManager.quitDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void suiteTearDown() {
        System.out.println("========== Test Suite Finished ==========");
    }

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
