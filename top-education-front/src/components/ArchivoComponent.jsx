import React, { useState } from 'react';
import axios from 'axios';

const ArchivoComponent = () => {
  const [archivo, setArchivo] = useState(null);
  const [mensaje, setMensaje] = useState("");

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setArchivo(file);
  };

  const handleFileSubmit = async (e) => {
    e.preventDefault();

    if (!archivo) {
      setMensaje("Por favor selecciona un archivo.");
      return;
    }

    const formData = new FormData();
    formData.append('archivo', archivo);

    try {
      const response = await axios.post('http://localhost:8080/notas/subir', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });

      setMensaje(response.data);
    } catch (error) {
      console.error('Error al subir el archivo', error);
      setMensaje('Error al subir el archivo. Por favor, intenta nuevamente.');
    }
  };

  return (
    <div className="container">
      <h1 className="mt-5">Subir Archivo de Texto</h1>
      <form onSubmit={handleFileSubmit} encType="multipart/form-data" className="mt-3">
        <div className="mb-3">
          <label htmlFor="archivo" className="form-label">
            Selecciona un archivo Excel (.csv)
          </label>
          <input
            type="file"
            className="form-control"
            id="archivo"
            name="archivo"
            accept=".csv"
            onChange={handleFileChange}
          />
        </div>
        <button type="submit" className="btn btn-primary">
          Subir Archivo
        </button>
      </form>
      <h3>{mensaje}</h3>
    </div>
  );
};

export default ArchivoComponent;
