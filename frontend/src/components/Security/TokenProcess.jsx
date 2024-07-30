
// import axios from 'axios';

import TokenService from "./TokenService";

// Функция для получения токена и его сохранения
export const fetchToken = async (username, password) => {
    const url = 'http://localhost:8081/realms/content-maker/protocol/openid-connect/token';
    const params = new URLSearchParams();
    params.append('client_id', 'makerFront');
    params.append('client_secret', '**********');
    params.append('username', username);
    params.append('password', password);
    params.append('grant_type', 'password');

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: params.toString()
        });
        
        if (!response.ok) {
            throw new Error(`Ошибка при получении токена: ${response.statusText}`);
        }
        
        return response;
    } catch (error) {
        console.error('Ошибка:', error);
    }
}


export const refreshToken = async () => {
    const form = new URLSearchParams();
    form.append('client_id', 'makerFront');
    form.append('client_secret', '**********');
    form.append('grant_type', 'refresh_token');
    const storedToken = JSON.parse(localStorage.getItem("refresh_token"));
    form.append('refresh_token', storedToken.refreshToken);
    
    const response = await fetch('http://localhost:8081/realms/content-maker/protocol/openid-connect/token', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: form.toString()
    });

    if (response.ok) {
      const data = await response.json();
      TokenService.updateLocalAccessToken(data.access_token);
      TokenService.updateLocalRefreshToken(data.refresh_token);
    } else {
      console.error('Token refresh failed');
    }
    return response;
  };