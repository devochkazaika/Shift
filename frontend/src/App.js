import React, { useEffect, useState } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './styles/globals.scss';
import Sidebar from './components/Sidebar';
import Stories from './pages/Stories';
// import Banners from './pages/Banners';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import PrivateRoute from './components/Security/PrivateRoute';
import StoryAdmin from './pages/StoryAdmin';
import { AdminRoute } from './components/Security/AdminRoute';
import { ReactKeycloakProvider } from '@react-keycloak/web';
import keycloak from './components/Security/Keycloak';
import { getFlags } from './api/api';


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
    <ReactKeycloakProvider authClient={keycloak}>
      <BrowserRouter>
        <div className="main_container">
          <Sidebar flags={flags} />
          <main className="content">
            <ToastContainer />
            <Routes>
              {flags.stories ? <Route path="/" element={<PrivateRoute><Stories /></PrivateRoute>} /> : <></>}
              {flags.stories ? <Route path="/story" element={<PrivateRoute><Stories /></PrivateRoute>} /> : <></> }
              <Route path="/unApproved" element={<AdminRoute><StoryAdmin /></AdminRoute>} />
            </Routes>
          </main>
        </div>
      </BrowserRouter>
    </ReactKeycloakProvider>
  );
}

export default App;
