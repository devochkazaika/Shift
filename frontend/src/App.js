import React, { useEffect, useState } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './styles/globals.scss';
import Sidebar from './components/Sidebar';
import Stories from './pages/Stories';
// import Banners from './pages/Banners';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { BrowserRouter, Route, Routes } from "react-router-dom";

import "./styles/globals.scss";

import Sidebar from "./components/Sidebar";
import Stories from "./pages/Stories";
import Banners from "./pages/Banners";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import PrivateRoute from './components/Security/PrivateRoute';
import StoryAdmin from './pages/StoryAdmin';
import { AdminRoute } from './components/Security/AdminRoute';
import { ReactKeycloakProvider } from '@react-keycloak/web';
import keycloak from './components/Security/Keycloak';
import { getFlags } from './api/api';

import React from "react";
import StoriesList from "./pages/StoriesList";

function App() {
  const [flags, setFlags] = useState(null); // Initialize with null

  useEffect(() => {
    const fetchFlags = async () => {
      const fetchedFlags = await getFlags();
      setFlags(fetchedFlags); // Set flags after fetching
    };
    fetchFlags();
  }, []); // Run only once on mount

  if (!flags) {
    return <div>Loading...</div>; // Optional: Loading state while flags are being fetched
  }

  return (
    <BrowserRouter>
      <div className="main_container">
        <Sidebar />
        <main className="content">
          <ToastContainer />
          <Routes>
            <Route path="/" element={<StoriesList />} />
            <Route path="/stories" element={<StoriesList />} />
            <Route path="/addStories" element={<Stories />} />
            <Route path="/banners" element={<Banners />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );
}

export default App;
