const axios = require('axios');
const { BASE_URL } = require('../config/config');

/** Reusable API helper wrapping axios for clean test code. */
const apiClient = axios.create({
  baseURL: BASE_URL,
  headers: { 'Content-Type': 'application/json' },
  validateStatus: () => true, // don't throw on non-2xx — let tests assert status
});

module.exports = {
  /** POST /reset — clears all data for test isolation */
  resetDatabase: () => apiClient.post('/reset', {}),

  /** POST /boards — creates a board (needed to get a boardId for lists) */
  createBoard: (name) => apiClient.post('/boards', { name }),

  /** POST /lists — creates a list on a board */
  createList: (boardId, name) => apiClient.post('/lists', { boardId, name, order: 0 }),

  /** GET /lists — retrieves all lists (optional query params) */
  getLists: (params = {}) => apiClient.get('/lists', { params }),

  /** DELETE /lists/:id — deletes a single list by ID */
  deleteList: (listId) => apiClient.delete(`/lists/${listId}`),
};
