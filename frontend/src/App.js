import { BrowserRouter, Route, Routes, Navigate } from 'react-router-dom';

import './styles/globals.scss';

import Sidebar from './components/Sidebar';
import Stories from './pages/Stories';
import Banners from './pages/Banners';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import React from 'react';
import Login from './pages/Login';

function App() {
  const isAuthenticated = localStorage.getItem('jwt_token') !== null;

  return (
    <BrowserRouter>
      <div className="main_container">
        <Sidebar />
        <main className="content">
          <ToastContainer />
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/" element={isAuthenticated ? <Stories /> : <Navigate to="/login" />} />
            <Route path="/stories" element={isAuthenticated ? <Stories /> : <Navigate to="/login" />} />
            <Route path="/banners" element={isAuthenticated ? <Banners /> : <Navigate to="/login" />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );
}

export default App;
