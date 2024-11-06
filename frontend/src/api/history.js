import api from "./api";

export const getRequestsUser = async () => {
    try {
      const response = await api.get('/history/getRequests', {
        responseType: 'json'
      });
      return response.data;
    } catch (error) {
      console.error('Error fetching data:', error);
      return null;
    }
  };

export const rollBackOperation = async (id) => {
  try {
    const response = await api.patch('/history/rollback?id='+id, id, {
      responseType: 'json'
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching data:', error);
    return null;
  }
}