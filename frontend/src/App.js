import React, { useEffect, useState } from 'react';
import { BrowserRouter, Route, Routes, useNavigate } from 'react-router-dom';
import './styles/globals.scss';
import Sidebar from './components/Sidebar';
import Stories from './pages/Stories';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { getFlags } from './api/api';

import StoriesList from "./pages/StoriesList";
import { ReactKeycloakProvider } from '@react-keycloak/web';
import keycloak from './components/Security/Keycloak';
import PrivateRoute from './components/Security/PrivateRoute';
import { AdminRoute } from './components/Security/AdminRoute';
import Button from './components/ui/Button';
import StoryDeletedList from './pages/StoryDeletedList';
import StoryUnApprovedList from './pages/StoryUnApprovedList';

function App() {
  const [flags, setFlags] = useState(null);

  useEffect(() => {
    const fetchFlags = async () => {
      const fetchedFlags = await getFlags();
      setFlags(fetchedFlags);
    };
    fetchFlags();
  }, []);

  if (!flags) {
    return <div>Loading...</div>;
  }

  return (
    <ReactKeycloakProvider authClient={keycloak}>
      <BrowserRouter>
        <div className="main_container">
          <Sidebar flags={flags} />
          <main className="content">
            <ToastContainer />
            <Routes>
              {flags.stories && (
                <>
                  <Route
                    path="/"
                    element={
                      <PrivateRoute>
                        <StoriesList>
                          <AddButton />
                        </StoriesList>
                      </PrivateRoute>
                    }
                  />
                  <Route path="/story" element={<PrivateRoute><Stories /></PrivateRoute>} />
                  <Route path="/addStories" element={<Stories />} />
                </>
              )}
              <Route path="/unApproved" element={
                <PrivateRoute>
                  <AdminRoute>
                      <StoryUnApprovedList />
                  </AdminRoute>
                </PrivateRoute>} />
              <Route path="/deleted" element={<PrivateRoute><StoryDeletedList /></PrivateRoute>} />
              {/* <Route path="/banners" element={<Banners />} /> */}
            </Routes>
          </main>
        </div>
      </BrowserRouter>
    </ReactKeycloakProvider>
  );
}

const AddButton = () => {
  const navigate = useNavigate();

  return (
    <Button
      text="Добавить"
      type="button"
      color="green"
      handleOnClick={() => navigate("/addStories")}
    />
  );
};

export default App;
