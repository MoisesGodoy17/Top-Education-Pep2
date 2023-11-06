import React, { useEffect, useState } from 'react';
import AlumnoService from '../services/AlumnoService';

const AlumnosComponent = () => {
    const [alumnos, setAlumnos] = useState([]);

    useEffect(() => {
        AlumnoService.getAlumnos().then((res) => {
            setAlumnos(res.data);
        });
    }, []);

    return (
        <div className="container">
            <br></br>
            <br></br>
            <div className="card bg-dark rounded-4 border">
                <h2 className="text-center text-white mb-4">Lista de Alumnos</h2>
                <div className="table-responsive ">
                    <table className="table table-dark table-borderless text-center">
                        <thead>
                            <tr>
                                <th className='border-bottom border-white'>Rut </th>
                                <th className='border-bottom border-white'>Apellidos</th>
                                <th className='border-bottom border-white'>Nombres</th>
                                <th className='border-bottom border-white'>Fecha de nacimiento</th>
                                <th className='border-bottom border-white'>Tipo de colegio</th>
                                <th className='border-bottom border-white'>Nombre del colegio</th>
                                <th className='border-bottom border-white'>Año de egreso</th>
                                <th className='border-bottom border-white'>Arancel</th>
                                <th className='border-bottom border-white'>Tipo de pago</th>
                            </tr>
                        </thead>
                        <tbody>
                            {alumnos.map((alumno) => (
                                <tr key={alumno.rut}>
                                    <td>{alumno.rut}</td>
                                    <td>{alumno.apellidos}</td>
                                    <td>{alumno.nombres}</td>
                                    <td>{alumno.fecha_nacimiento}</td>
                                    <td>{alumno.tipo_colegio}</td>
                                    <td>{alumno.nombre_colegio}</td>
                                    <td>{alumno.ano_egreso}</td>
                                    <td>{alumno.arancel}</td>
                                    <td>{alumno.tipo_pago}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default AlumnosComponent;
