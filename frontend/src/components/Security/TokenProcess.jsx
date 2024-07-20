import axios from 'axios';

// Функция для получения токена и его сохранения
async function fetchToken(username, password) {
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
        
        const data = await response.json();
        saveJWT(data.access_token);

        console.log('Токен успешно получен и сохранен:', data);
    } catch (error) {
        console.error('Ошибка:', error);
    }
}

function saveJWT(token) {
    localStorage.setItem('jwt_token', token);
}

function getJWT() {
    const token = localStorage.getItem('jwt_token');
    return token ? `Bearer ${token}` : null;
}

axios.interceptors.request.use(function (config) {
    const token = getJWT();
    if (token) {
        config.headers.Authorization = token;
    }
    return config;
}, function (error) {
    return Promise.reject(error);
});

export { fetchToken, getJWT };
