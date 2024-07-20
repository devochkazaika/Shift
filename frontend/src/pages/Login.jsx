import React, { useState } from 'react';
import { fetchToken } from '../components/Security/TokenProcess';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();

        const response = await fetchToken(username, password);

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('jwt_token', data.access_token);
            history.push('/');
        } else {
            console.error('Login failed');
        }
    };

    return (
        <form onSubmit={handleLogin}>
            <h1>Login</h1>
            <input
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                placeholder="Username"
            />
            <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Password"
            />
            <button type="submit">Login</button>
        </form>
    );
};

export default Login;