import React from 'react';
import './App.css';
import { BrowserRouter, Route, Routes} from 'react-router-dom';
import AlumnoComponent from './components/AlumnoComponent';
import AlumnosComponent from './components/AlumnosComponent';

function App() {
  return (
    <div>
      <BrowserRouter>
        <div className="container">
          <Routes>
            <Route path="/new-alumno" element={<AlumnoComponent />} />
            <Route path="/alumnos" element={<AlumnosComponent />} />
          </Routes>
        </div>
      </BrowserRouter>
    </div>
  );
}
export default App;
