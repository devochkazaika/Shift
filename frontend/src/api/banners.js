import api from "./api";

export const getStories = async (bankId, platform) => {
    try {
      const response = await api.get('/stories/get/all', {
        params: { bankId, platform },
        responseType: 'json',
      });
      return response.data;
    } catch (error) {
      console.error('Error fetching data:', error);
      return null;
    }
  };