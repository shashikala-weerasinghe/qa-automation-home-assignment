package com.trello.pages;

import com.trello.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object for the home page that displays all boards.
 * URL: /
 */
public class BoardListPage extends BasePage {

    public BoardListPage(WebDriver driver) {
        super(driver);
    }

    /** Navigate to the application home page. */
    public void open() {
        driver.get(ConfigReader.getBaseUrl());
        waitForVisible(byCy("board-list"));
    }

    /**
     * Creates a new board.
     * When the board list is empty, the app shows an Emptylist UI with a direct
     * input [data-cy='first-board']. When boards already exist, the app shows
     * a BoardCreate button [data-cy='create-board'] that reveals an input on click.
     * This method handles both cases.
     */
    public void createBoard(String boardName) {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean isEmpty;
        try {
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(byCy("first-board")));
            isEmpty = true;
        } catch (Exception e) {
            isEmpty = false;
        }

        if (isEmpty) {
            WebElement input = waitForVisible(byCy("first-board"));
            input.clear();
            input.sendKeys(boardName);
            input.sendKeys(Keys.ENTER);
        } else {
            waitForClickable(byCy("create-board")).click();
            WebElement input = waitForVisible(byCy("new-board-input"));
            input.clear();
            input.sendKeys(boardName);
            input.sendKeys(Keys.ENTER);
        }
    }

    /** Returns all visible board item elements. */
    public List<WebElement> getBoards() {
        return waitForAllVisible(byCy("board-item"));
    }

    /** Checks if a board with the given name is visible on the page. */
    public boolean isBoardVisible(String boardName) {
        return getBoards().stream()
                .anyMatch(el -> el.getText().contains(boardName));
    }

    public int getBoardCount() {
        return driver.findElements(byCy("board-item")).size();
    }
}
