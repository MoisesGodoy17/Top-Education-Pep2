import React, { useState } from 'react';
import CuotasService from '../services/CuotasService';
import { useNavigate } from 'react-router-dom';

const CuotasComponent = () => {
    const [rut, setRut] = useState("");
    const [idCuota, pagarC] = useState("");
    const [cuotas, setCuotas] = useState([]);

    const getCuotas = (e) => {
        e.preventDefault();
        if (rut.trim() !== '') {
            CuotasService.getCuotasByRut(rut).then((res) => {
                setCuotas(res.data);
            }).catch((error) => {
                console.error('Error al obtener cuotas:', error);
            });
        } else {
            console.error('El campo del RUT está en blanco');
        }
    };
    const navigate = useNavigate();
    const pagarCuota = (idCuota) => (e) => {
        e.preventDefault();
        CuotasService.pagarCuota(idCuota)
        navigate('/cuotas');
    }
    

    return (
        <div>
            <br></br>
            <br></br>
            <div className="container1">
                <h1 className="text-center">Ver Cuotas</h1>
                <div className="col-lg-4 col-md-6 col-sm-12 mx-auto">
                    <div className="card-body">
                        <form>
                            <div className="form-group">
                                <label>Rut:</label>
                                <input
                                    type="text"
                                    name="rut"
                                    className="form-control"
                                    value={rut}
                                    placeholder="12345678-9"
                                    onChange={(e) => setRut(e.target.value)}
                                />
                            </div>
                            <br />
                            <button className="btn btn-primary" onClick={getCuotas}>Ver Cuotas</button>
                        </form>
                    </div>
                </div>
            </div>

            <div className="container">
                <br></br>
                <br></br>
                <div className="card bg-dark rounded-4 border">
                    <h2 className="text-center text-white mb-4">Lista de Alumnos</h2>
                    <div className="table-responsive">
                        <table className="table table-dark table-borderless text-center">
                            <thead>
                                <tr>
                                    <th className='border-bottom border-white'>Monto </th>
                                    <th className='border-bottom border-white'>Interes</th>
                                    <th className='border-bottom border-white'>Descuento</th>
                                    <th className='border-bottom border-white'>Fecha de Emisión</th>
                                    <th className='border-bottom border-white'>Fecha de Pago</th>
                                    <th className='border-bottom border-white'>Fecha de Vencimiento</th>
                                    <th className='border-bottom border-white'>Estado</th>
                                    <th className='border-bottom border-white'>Pagar</th>
                                </tr>
                            </thead>
                            <tbody>
                                {cuotas.map((cuota) => (
                                    <tr key={cuota.idCuota}>
                                        <td>{cuota.monto}</td>
                                        <td>{cuota.interes}</td>
                                        <td>{cuota.descuento}</td>
                                        <td>{cuota.fechaEmision}</td>
                                        <td>{cuota.fechaPago}</td>
                                        <td>{cuota.fechaVencimiento}</td>
                                        <td>{cuota.estado}</td>
                                        <td>
                                            <button className="btn btn-primary" onClick={pagarCuota(cuota.idCuota)}>
                                                Pagar
                                            </button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default CuotasComponent;

