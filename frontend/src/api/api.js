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
            try {
                const response = await api.request(originalRequest);
                console.log(response);
                return response;
            }
            catch (_error) {
              keycloak.login();
              window.navigate('/login')
            }
        }

        return Promise.reject(error);
    }
);
export const getFlags = async  () =>{
  const flags = await api.get("/access/get", {responseType: 'json'});
  return flags.data;
}

export default api;