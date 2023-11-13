import React, { useState } from 'react';
import AlumnoService from '../services/AlumnoService';
import { useNavigate } from 'react-router-dom';

const AlumnoComponent = () => {
    const [alumno, setAlumno] = useState({
        rut: '',
        apellidos: '',
        nombres: '',
        fecha_nacimiento: '',
        tipo_colegio: '',
        nombre_colegio: '',
        ano_egreso: 0,
        tipo_pago: '',
        arancel: 0
    });

    const navigate = useNavigate();

    const handleChange = (e) => {
        setAlumno({
            ...alumno,
            [e.target.name]: e.target.value
        });
    };

    const saveAlumno = (e) => {
        e.preventDefault();
        AlumnoService.createAlumno(alumno).then(() => {
            navigate('/alumnos');
        });
    };

    return (
        <div className="container">
            <br></br>
            <br></br>
            <div className="row">
                <div className="card col-md-4 offset-md-3 offset-md-3 bg-dark text-white rounded-4 mx-auto">
                    <h3 className="text-center">Crear Alumno</h3>
                    <div className="card-body">
                        <form>
                            <div className="form-group mb-3">
                                <label className="text-white"> Rut: </label>
                                <input placeholder="Rut: 12345678-9" name="rut" className="form-control"
                                    type='text' value={alumno.rut} onChange={handleChange} />
                            </div>
                            <div className="form-group mb-3">
                                <label className="text-white"> Apellidos: </label>
                                <input placeholder="Ingrese sus Apellidos" name="apellidos" className="form-control"
                                    type='text' value={alumno.apellidos} onChange={handleChange} />
                            </div>
                            <div className="form-group mb-3">
                                <label className="text-white"> Nombres: </label>
                                <input placeholder="Ingrese sus Nombres" name="nombres" className="form-control"
                                    type='text' value={alumno.nombres} onChange={handleChange} />
                            </div>
                            <div className="form-group mb-3">
                                <label className="text-white"> Fecha de Nacimiento: </label>
                                <input placeholder="Ingrese la fecha de nacimiento" name="fecha_nacimiento" className="form-control"
                                    type='date' value={alumno.fecha_nacimiento} onChange={handleChange} />
                            </div>
                            <div className="form-group mb-3">
                                <label className="text-white"> Nombre del Colegio: </label>
                                <input placeholder="Nombre del Colegio de Egreso" name="nombre_colegio" className="form-control"
                                    type='text' value={alumno.nombre_colegio} onChange={handleChange} />
                            </div>
                            <div className="form-group mb-3">
                                <label className="text-white"> Tipo de Colegio: </label>
                                <select name="tipo_colegio" value={alumno.tipo_colegio} onChange={handleChange} className="form-control">
                                    <option value="Municipal">Municipal</option>
                                    <option value="Subvencionado">Subvencionado</option>
                                    <option value="Privado">Privado</option>
                                </select>
                            </div>
                            <div className="form-group mb-3">
                                <label className="text-white"> Año(s) desde que egresó: </label>
                                <input placeholder="Años desde que egresó" name="ano_egreso" className="form-control"
                                    type='number' value={alumno.ano_egreso} onChange={handleChange} />
                            </div>
                            <div className="form-group mb-3">
                                <label className="text-white">Tipo de Pago:</label>
                                <select name="tipo_pago" value={alumno.tipo_pago} onChange={handleChange} className="form-control">
                                    <option value="Contado">Contado</option>
                                    <option value="Cuotas">Cuotas</option>
                                </select>
                            </div>
                            <button className="btn btn-primary" onClick={saveAlumno}>Guardar</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};
export default AlumnoComponent;

