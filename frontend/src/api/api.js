
import axios from 'axios';
import { refreshToken } from '../components/Security/TokenProcess';
import TokenService from '../components/Security/TokenService';

const api = axios.create({
    baseURL: 'http://localhost:8080',
 });

// Функция для добавления перехватчика (interceptor) с токеном
api.interceptors.request.use(function (config) {
    const token = TokenService.getLocalAccessToken();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  }, function (error) {
    return Promise.reject(error);
  });

api.interceptors.response.use(response => response, async error => {
   const originalRequest = error.config;
 
   if (error.response.status === 401) {
    try {
        await refreshToken();
        
      } catch (_error) {
        return Promise.reject(_error);
      }
      
   }
   return api.request(originalRequest);
   }
);

export default api;