package com.trello.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
     * Creates a new list by clicking "Add a list", typing the name,
     * and pressing Enter.
     */
    public void createList(String listName) {
        waitForClickable(byCy("create-list")).click();

        WebElement input = waitForVisible(byCy("add-list-input"));
        input.clear();
        input.sendKeys(listName);
        input.sendKeys(Keys.ENTER);

        // Wait until the newly created list is rendered
        waitForVisible(byCy("list"));
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
     */
    public void deleteList(int index) {
        List<WebElement> lists = getLists();
        WebElement targetList = lists.get(index);

        // Open the options dropdown for the target list
        targetList.findElement(byCy("list-options")).click();
        waitForVisible(byCy("list-dropdown"));

        // Click delete
        waitForClickable(byCy("delete-list")).click();
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
