

// Функция для получения токена и его сохранения
export const fetchToken = async (username, password) => {
    const url = 'http://localhost:8081/realms/content-maker/protocol/openid-connect/token';
    const params = new URLSearchParams();
    params.append('client_id', 'makerFront');
    params.append('client_secret', '**********'); // Не забудьте заменить на реальный secret
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
    const token = localStorage.getItem('jwt_token');
    if (token) {
      const response = await fetch('/api/refresh-token', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });
  
      if (response.ok) {
        const data = await response.json();
        localStorage.setItem('jwt_token', data.access_token);
      } else {
        console.error('Token refresh failed');
      }
    }
  };