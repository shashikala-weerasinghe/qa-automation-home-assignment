const axios = require('axios');
const { BASE_URL } = require('../config/config');

const apiClient = axios.create({
  baseURL: BASE_URL,
  headers: { 'Content-Type': 'application/json' },
  validateStatus: () => true,
});

module.exports = {
  resetDatabase: () => apiClient.post('/reset', {}),

  createBoard: (name) => apiClient.post('/boards', { name }),

  createList: (boardId, name) => apiClient.post('/lists', { boardId, name, order: 0 }),

  getLists: (params = {}) => apiClient.get('/lists', { params }),

  deleteList: (listId) => apiClient.delete(`/lists/${listId}`),
};
