import React, { useState } from 'react';
import CuotasService from '../services/CuotasService';

const HomeComponent = () => {
  const [mensaje, setMensaje] = useState('');

  const generarInteres = () => {
    CuotasService.cobrarCuotas()
      .then(response => {
        setMensaje(response.data);
      })
      .catch(error => {
        console.error('Error al generar interés', error);
        setMensaje('Error al generar interés');
      });
  };
  
    return (
        <div className="container mt-5">
            <h1 className="text-center mb-4">Top Education</h1>
            <div className="d-flex justify-content-center">
                <a href="/cuotas" className="btn btn-primary mx-2">
                    Ver Cuotas
                </a>
                <a href="/new-cuotas" className="btn btn-success mx-2">
                    Crear Cuotas
                </a>
                <a href="/alumnos" className="btn btn-info mx-2">
                    Listado de Alumnos
                </a>
                <a href="/new-alumno" className="btn btn-warning mx-2">
                    Agregar Alumno
                </a>
                <a href="/archivo" className="btn btn-secondary mx-2">
                    Subir Archivo
                </a>
                <a href="/reporte" className="btn btn-dark mx-2">
                    Ver Reporte
                </a>
                <button onClick={generarInteres} className="btn btn-danger">
                    Generar Interes
                </button>
            </div>
        </div>
    );
};

export default HomeComponent;
