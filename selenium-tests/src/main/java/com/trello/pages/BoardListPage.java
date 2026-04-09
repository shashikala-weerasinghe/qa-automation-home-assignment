package com.trello.pages;

import com.trello.config.ConfigReader;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
     * Creates a new board by clicking the create area,
     * typing the name, and pressing Enter.
     * The app auto-redirects to the board detail page on success.
     */
    public void createBoard(String boardName) {
        waitForClickable(byCy("create-board")).click();

        WebElement input = waitForVisible(byCy("new-board-input"));
        input.clear();
        input.sendKeys(boardName);
        input.sendKeys(Keys.ENTER);
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
