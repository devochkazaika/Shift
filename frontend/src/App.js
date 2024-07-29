import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './styles/globals.scss';
import Sidebar from './components/Sidebar';
import Stories from './pages/Stories';
import Banners from './pages/Banners';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import PrivateRoute from './components/Security/PrivateRoute';
import AuthProvider from './components/Security/AuthProvider';
import Login from './pages/Login';


function App() {
  return (
    <BrowserRouter>
      <div className="main_container">
        <Sidebar />
        <main className="content">
          <ToastContainer />
          <AuthProvider>
            <Routes>
                <Route path="/login" element={<Login />} />
                
                <Route
                path="/"
                element={
                  <PrivateRoute>
                    <Stories />
                  </PrivateRoute>
                }
                />
                <Route 
                  path="/stories" 
                  element={
                    <PrivateRoute>
                      <Stories />
                    </PrivateRoute>
                  } 
                />
                <Route 
                  path="/banners" 
                  element={
                    <PrivateRoute>
                      <Banners />
                    </PrivateRoute>
                  } 
                />
              </Routes>
          </AuthProvider>
        </main>
      </div>
    </BrowserRouter>
  );
}

export default App;
