import React from 'react';
import './App.css';
import { BrowserRouter, Route, Routes} from 'react-router-dom';
import AlumnoComponent from './components/AlumnoComponent';
import AlumnosComponent from './components/AlumnosComponent';
import CrearCuotasComponent from './components/CrearCuotasComponent';
import CuotasComponent from './components/CuotasComponent';
import ReporteComponent from './components/ReporteComponent';
import ArchivoComponent from './components/ArchivoComponent';
import NavbarComponent from './components/NavbarComponent';
import HomeComponent from './components/HomeComponent';

function App() {
  return (
    <div>
      <NavbarComponent />
      <BrowserRouter>
        <div className="container">
          <Routes>
            <Route path="/" element={<HomeComponent />} />
            <Route path="/new-alumno" element={<AlumnoComponent />} />
            <Route path="/alumnos" element={<AlumnosComponent />} />
            <Route path="/new-cuotas" element={<CrearCuotasComponent />} />
            <Route path="/cuotas" element={<CuotasComponent />} />
            <Route path="/reporte" element={<ReporteComponent />} />
            <Route path="/archivo" element={<ArchivoComponent />} />
          </Routes>
        </div>
      </BrowserRouter>
    </div>
  );
}
export default App;
