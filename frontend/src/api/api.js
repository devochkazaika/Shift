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

let navigate;

export const setNavigate = (nav) => {
    navigate = nav;
};

api.interceptors.response.use(
    response => response,
    async error => {
        const originalRequest = error.config;
        if (error.response.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;
            try {
                await refreshToken();
                const response = await api.request(originalRequest);
                return response;
            }
            catch (_error) {
                navigate('/login');
                TokenService.updateLocalAccessToken(null);
            }
        }

        return Promise.reject(error);
    }
);

export default api;