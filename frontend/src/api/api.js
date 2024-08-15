import axios from 'axios';
import keycloak from '../components/Security/Keycloak';

const api = axios.create({
    baseURL: 'http://localhost:8080',
 });

api.interceptors.request.use(function (config) {
    const token = keycloak.token;
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  }, function (error) {
    return Promise.reject(error);
  });


api.interceptors.response.use(
    response => response,
    async error => {
        const originalRequest = error.config;
        if (error.response.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;

              // keycloak.login();

        }
    }
);
export const getFlags = async () =>{
  try {
    const response = await api.get("/access/get", { responseType: 'json' });
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error("Error fetching flags:", error);
    throw error;
  }
}

export default api;