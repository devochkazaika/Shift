import axios from 'axios';
import { refreshToken } from '../components/Security/TokenProcess';
import TokenService from '../components/Security/TokenService';

const api = axios.create({
    baseURL: 'http://localhost:8080',
 });

api.interceptors.request.use(function (config) {
    const token = TokenService.getLocalAccessToken();
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
            try {
                await refreshToken();
                const response = await api.request(originalRequest);
                console.log(response);
                return response;
            }
            catch (_error) {
                TokenService.updateLocalAccessToken(null);
                window.navigate('/login')
            }
        }

        return Promise.reject(error);
    }
);

export default api;