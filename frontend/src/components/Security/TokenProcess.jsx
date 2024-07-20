async function fetchToken(username, password) {
    const url = 'http://localhost:8081/realms/content-maker/protocol/openid-connect/token';
    const params = new URLSearchParams();
    params.append('client_id', 'maker');
    params.append('client_secret', '**********');
    params.append('username', 'test');
    params.append('password', 'password');
    params.append('grant_type', 'password');

    try {
        console.log(username + password)
        const response = await fetch(url, {
            mode: 'no-cors',
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: params,
        });

        if (!response.ok) {
            throw new Error('Ошибка при получении токена');
        }

        const data = await response.json();
        saveJWT(data.access_token);

        console.log('Токен успешно получен и сохранен:', data.access_token);
    } catch (error) {
        console.error('Ошибка:', error);
    }
}

// Функция для сохранения токена в localStorage
function saveJWT(response) {
    response.headers.forEach((value, name) => {
        if (name.toLowerCase() === 'authorization') {
            localStorage.setItem('jwt_token', value);
        }
    });
}

// Функция для получения токена из localStorage и добавления его в заголовки
function getJWT(headers) {
    headers['Authorization'] = localStorage.getItem('jwt_token');
}


export {fetchToken, getJWT}