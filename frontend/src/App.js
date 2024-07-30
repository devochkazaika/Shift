import { BrowserRouter, Route, Routes } from "react-router-dom";

import "./styles/globals.scss";

import Sidebar from "./components/Sidebar";
import Stories from "./pages/Stories";
import Banners from "./pages/Banners";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import React from "react";
import StoriesList from "./pages/StoriesList";

function App() {
  return (
    <BrowserRouter>
      <div className="main_container">
        <Sidebar />
        <main className="content">
          <ToastContainer />
          <Routes>
            <Route path="/" element={<Stories />} />
            <Route path="/stories" element={<Stories />} />
            <Route path="/storiesList" element={<StoriesList />} />
            <Route path="/banners" element={<Banners />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );
}

export default App;
