import api from "./api";

export const getRequestsUser = async () => {
    try {
      const response = await api.get('/history/getRequests', {
        responseType: 'json'
      });
    //   console.log(response.data);
      return response.data;
    } catch (error) {
      console.error('Error fetching data:', error);
      return null;
    }
  };