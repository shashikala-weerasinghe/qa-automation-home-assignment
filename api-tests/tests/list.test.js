const { resetDatabase, createBoard, createList, getLists, deleteList } = require('../helpers/apiClient');

describe('List API', () => {
  let boardId;

  // Reset DB and create a board before each test for isolation
  beforeEach(async () => {
    await resetDatabase();
    const boardRes = await createBoard('Test Board');
    expect(boardRes.status).toBe(201);
    boardId = boardRes.data.id;
  });

  describe('POST /lists — Add a new list', () => {
    it('should create a new list successfully', async () => {
      const listName = 'To Do';

      const response = await createList(boardId, listName);

      expect(response.status).toBe(201);
      expect(response.data).toHaveProperty('id');
      expect(response.data.name).toBe(listName);
      expect(response.data.boardId).toBe(boardId);
    });

    it('should return the created list when fetching all lists', async () => {
      const listName = 'In Progress';

      const createRes = await createList(boardId, listName);
      const listId = createRes.data.id;

      const getRes = await getLists();

      expect(getRes.status).toBe(200);
      const createdList = getRes.data.find((list) => list.id === listId);
      expect(createdList).toBeDefined();
      expect(createdList.name).toBe(listName);
    });

    it('should return 400 when boardId is missing', async () => {
      const response = await createList(undefined, 'Invalid List');

      expect(response.status).toBe(400);
      expect(response.data.error).toContain('boardId');
    });
  });

  describe('DELETE /lists/:id — Delete the newly created list', () => {
    it('should delete the newly created list successfully', async () => {
      // Step 1: Create a list
      const listName = 'List to Delete';
      const createRes = await createList(boardId, listName);
      expect(createRes.status).toBe(201);
      const listId = createRes.data.id;

      // Step 2: Delete the list
      const deleteRes = await deleteList(listId);
      expect(deleteRes.status).toBe(200);

      // Step 3: Verify the list no longer exists
      const getRes = await getLists();
      const deletedList = getRes.data.find((list) => list.id === listId);
      expect(deletedList).toBeUndefined();
    });

    it('should not affect other lists when deleting one', async () => {
      // Create two lists
      const res1 = await createList(boardId, 'Keep This');
      const res2 = await createList(boardId, 'Delete This');
      const keepId = res1.data.id;
      const deleteId = res2.data.id;

      // Delete only the second list
      await deleteList(deleteId);

      // Verify first list still exists
      const getRes = await getLists();
      const remaining = getRes.data.find((list) => list.id === keepId);
      const deleted = getRes.data.find((list) => list.id === deleteId);

      expect(remaining).toBeDefined();
      expect(remaining.name).toBe('Keep This');
      expect(deleted).toBeUndefined();
    });
  });
});
