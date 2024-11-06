import React, { useEffect, useState } from "react";
import { BrowserRouter, Route, Routes, useNavigate } from "react-router-dom";
import "./styles/globals.scss";
import Sidebar from "./components/Sidebar";
import Stories from "./pages/Stories";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getFlags } from "./api/api";

import StoriesList from "./pages/StoriesList";
import { ReactKeycloakProvider } from "@react-keycloak/web";
import keycloak from "./components/Security/Keycloak";
import PrivateRoute from "./components/Security/PrivateRoute";
import { AdminRoute } from "./components/Security/AdminRoute";
import Button from "./components/ui/Button";
import StoryDeletedList from "./pages/StoryDeletedList";
import StoryUnApprovedList from "./pages/StoryUnApprovedList";
import History from "./pages/HistoryList";
import BannersAdd from "./pages/BannersAdd";
import RequestUser from "./pages/RequestUser";
import { UserRoute } from "./components/Security/UserRoute";
import StoryUnApprovedChangeList from "./pages/StoryUnApprovedChangeList";

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
                        <StoriesList showStory={flags.show_story}>
                          <AddButton />
                        </StoriesList>
                      </PrivateRoute>
                    }
                  />
                  <Route
                    path="/story"
                    element={
                      <PrivateRoute>
                        <Stories />
                      </PrivateRoute>
                    }
                  />
                  <Route
                    path="/history"
                    element={
                      <PrivateRoute>
                        <History />
                      </PrivateRoute>
                    }
                  />
                  <Route
                    path="/addStories"
                    element={
                      <PrivateRoute>
                        <Stories />
                      </PrivateRoute>
                    }
                  />
                  {flags.user_requests ? 
                    <Route
                    path="/requestUser"
                    element={
                      <PrivateRoute>
                        <UserRoute>
                          <RequestUser />
                        </UserRoute>
                      </PrivateRoute>
                    }
                  />
                  : <></>}
                </>
              )}
              <Route
                path="/unApproved"
                element={
                  <PrivateRoute>
                    <AdminRoute>
                      <StoryUnApprovedList />
                    </AdminRoute>
                  </PrivateRoute>
                }
              />
              <Route
                path="/unApprovedChange"
                element={
                  <PrivateRoute>
                    <AdminRoute>
                      <StoryUnApprovedChangeList />
                    </AdminRoute>
                  </PrivateRoute>
                }
              />
              <Route
                path="/deleted"
                element={
                  <PrivateRoute>
                    <StoryDeletedList />
                  </PrivateRoute>
                }
              />
              <Route path="/banners" element={<BannersAdd />} />
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
