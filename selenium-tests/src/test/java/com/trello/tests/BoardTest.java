package com.trello.tests;

import com.trello.pages.BoardDetailPage;
import com.trello.pages.BoardListPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * End-to-end tests for Board and List operations:
 * 1. Create a board
 * 2. Add two lists and verify
 * 3. Delete a list
 */
public class BoardTest extends BaseTest {

    private BoardListPage boardListPage;
    private BoardDetailPage boardDetailPage;

    @BeforeMethod
    public void initPages() {
        boardListPage = new BoardListPage(driver);
        boardDetailPage = new BoardDetailPage(driver);
    }

    @Test(priority = 1, description = "Create a board by typing a name and pressing Enter, verify it is created")
    public void testCreateBoard() {
        String boardName = "Test Board";

        boardListPage.open();
        boardListPage.createBoard(boardName);

        // App auto-redirects to board detail page after creation
        boardDetailPage.waitForPageLoad();
        String actualTitle = boardDetailPage.getBoardTitle();

        Assert.assertEquals(actualTitle, boardName,
                "Board title on the detail page should match the entered name");
    }

    @Test(priority = 2, description = "Add two lists to a board and verify both are created successfully")
    public void testCreateTwoLists() {
        String boardName = "Board With Lists";
        String firstListName = "To Do";
        String secondListName = "In Progress";

        // Step 1: Create a board
        boardListPage.open();
        boardListPage.createBoard(boardName);
        boardDetailPage.waitForPageLoad();

        // Step 2: Create the first list
        boardDetailPage.createList(firstListName);

        Assert.assertEquals(boardDetailPage.getListCount(), 1,
                "There should be exactly 1 list after creating the first list");
        Assert.assertTrue(boardDetailPage.isListVisible(firstListName),
                "First list '" + firstListName + "' should be visible on the board");

        // Step 3: Create the second list
        boardDetailPage.createList(secondListName);

        Assert.assertEquals(boardDetailPage.getListCount(), 2,
                "There should be exactly 2 lists after creating the second list");
        Assert.assertTrue(boardDetailPage.isListVisible(secondListName),
                "Second list '" + secondListName + "' should be visible on the board");
    }

    @Test(priority = 3, description = "Delete a list from a board and verify it is removed")
    public void testDeleteList() {
        String boardName = "Board For Deletion";
        String listToKeep = "Keep This List";
        String listToDelete = "Delete This List";

        // Step 1: Create a board and add two lists
        boardListPage.open();
        boardListPage.createBoard(boardName);
        boardDetailPage.waitForPageLoad();

        boardDetailPage.createList(listToKeep);
        boardDetailPage.createList(listToDelete);

        Assert.assertEquals(boardDetailPage.getListCount(), 2,
                "There should be 2 lists before deletion");

        // Step 2: Delete the second list (index 1)
        boardDetailPage.deleteList(1);

        // Step 3: Verify only one list remains
        Assert.assertEquals(boardDetailPage.getListCount(), 1,
                "There should be 1 list remaining after deletion");
        Assert.assertTrue(boardDetailPage.isListVisible(listToKeep),
                "The kept list '" + listToKeep + "' should still be visible");
        Assert.assertFalse(boardDetailPage.isListVisible(listToDelete),
                "The deleted list '" + listToDelete + "' should no longer be visible");
    }
}
