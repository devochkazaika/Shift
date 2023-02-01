import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';

import Sidebar from './components/Sidebar';
import Stories from './pages/Stories';
import Banners from './pages/Banners';

function App() {
  return (
    <BrowserRouter>
      <div className="container">
        <Sidebar />
        <main className="content">
          <Routes>
            <Route path="/" element={<Stories />} />
            <Route path="/stories" element={<Stories />} />
            <Route path="/banners" element={<Banners />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );
}

export default App;
