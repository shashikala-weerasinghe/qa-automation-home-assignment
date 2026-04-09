package com.trello.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object for the board detail view where lists and cards are managed.
 * URL: /board/:id
 */
public class BoardDetailPage extends BasePage {

    public BoardDetailPage(WebDriver driver) {
        super(driver);
    }

    /** Waits until the board detail container is fully loaded. */
    public void waitForPageLoad() {
        waitForVisible(byCy("board-detail"));
    }

    /** Returns the board title from the editable input field. */
    public String getBoardTitle() {
        return waitForVisible(byCy("board-title")).getAttribute("value");
    }

    /**
     * Creates a new list by typing the name and pressing Enter.
     * When the board has no lists, the input is already visible (createListInput=true).
     * When lists exist, the "Add another list" button must be clicked first.
     * Waits for the expected list count to confirm creation.
     */
    public void createList(String listName) {
        int currentCount = getLists().size();

        // If the input is not yet visible, click the "create-list" button to open it
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean inputAlreadyVisible;
        try {
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(byCy("add-list-input")));
            inputAlreadyVisible = true;
        } catch (Exception e) {
            inputAlreadyVisible = false;
        }

        if (!inputAlreadyVisible) {
            waitForClickable(byCy("create-list")).click();
        }

        WebElement input = waitForVisible(byCy("add-list-input"));
        input.clear();
        input.sendKeys(listName);
        input.sendKeys(Keys.ENTER);

        // Wait until the new list appears in the DOM (count increases by 1)
        waitForNumberOfElements(byCy("list"), currentCount + 1);
    }

    /** Returns all list elements currently displayed on the board. */
    public List<WebElement> getLists() {
        return driver.findElements(byCy("list"));
    }

    /** Returns the number of lists on the board. */
    public int getListCount() {
        return getLists().size();
    }

    /** Returns the name of the list at the given index (0-based). */
    public String getListName(int index) {
        List<WebElement> lists = getLists();
        WebElement nameInput = lists.get(index).findElement(byCy("list-name"));
        return nameInput.getAttribute("value");
    }

    /**
     * Deletes the list at the given index by:
     * 1. Clicking the list options button (three dots)
     * 2. Waiting for the dropdown to appear
     * 3. Clicking "Delete list"
     * 4. Waiting for the list count to decrease
     */
    public void deleteList(int index) {
        int currentCount = getLists().size();
        List<WebElement> lists = getLists();
        WebElement targetList = lists.get(index);

        // Open the options dropdown for the target list
        targetList.findElement(byCy("list-options")).click();
        waitForVisible(byCy("list-dropdown"));

        // Click delete and wait for the list to be removed from the DOM
        waitForClickable(byCy("delete-list")).click();
        waitForNumberOfElements(byCy("list"), currentCount - 1);
    }

    /** Checks if a list with the given name exists on the board. */
    public boolean isListVisible(String listName) {
        return getLists().stream()
                .anyMatch(list -> {
                    WebElement nameInput = list.findElement(byCy("list-name"));
                    return listName.equals(nameInput.getAttribute("value"));
                });
    }
}
