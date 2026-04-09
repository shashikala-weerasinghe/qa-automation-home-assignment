package com.trello.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads test configuration from config.properties.
 * Supports environment variable overrides for CI flexibility.
 */
public final class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    private ConfigReader() {
    }

    public static String getBaseUrl() {
        return System.getProperty("base.url",
                properties.getProperty("base.url", "http://localhost:3000"));
    }

    public static String getBrowser() {
        return System.getProperty("browser",
                properties.getProperty("browser", "chrome"));
    }

    public static int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicit.wait", "10"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicit.wait", "15"));
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(
                System.getProperty("headless",
                        properties.getProperty("headless", "false")));
    }
}
