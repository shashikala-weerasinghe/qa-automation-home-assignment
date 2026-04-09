package com.trello.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        System.out.println(">>> Suite: " + context.getName() + " started");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println(">>> Suite: " + context.getName() + " finished");
        System.out.println("    Passed: " + context.getPassedTests().size());
        System.out.println("    Failed: " + context.getFailedTests().size());
        System.out.println("    Skipped: " + context.getSkippedTests().size());
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("[TEST START] " + result.getMethod().getMethodName()
                + " | " + result.getMethod().getDescription());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("[PASSED] " + result.getMethod().getMethodName()
                + " (" + getElapsedTime(result) + "ms)");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.err.println("[FAILED] " + result.getMethod().getMethodName()
                + " (" + getElapsedTime(result) + "ms)");
        System.err.println("    Cause: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("[SKIPPED] " + result.getMethod().getMethodName());
    }

    private long getElapsedTime(ITestResult result) {
        return result.getEndMillis() - result.getStartMillis();
    }
}
