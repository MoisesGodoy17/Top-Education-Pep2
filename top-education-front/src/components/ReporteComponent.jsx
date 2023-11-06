import React, { useState } from 'react';
import ReporteService from '../services/ReporteService';

const ReporteComponent = () => {
    const [rut, setRut] = useState("");
    const [reportes, setReporte] = useState([]);

    const getReporte = (e) => {
        e.preventDefault();
        if (rut.trim() !== '') {
            ReporteService.getReportet(rut).then((res) => {
                setReporte(res.data);
            }).catch((error) => {
                console.error('Error al obtener el Reporte', error);
            });
        } else {
            console.error('El campo del RUT está en blanco');
        }
    };
    return (
        <div>
        <div className="container1">
            <h1 className="text-center">Reporte</h1>
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
                        <button className="btn btn-primary" onClick={getReporte}>Ver Reporte</button>
                    </form>
                </div>
            </div>
        </div>

        <div className="container">
                <br></br>
                <br></br>
                <div className="card bg-dark rounded-4 border">
                    <h2 className="text-center text-white mb-4">Reporte del alumno</h2>
                    <div className="table-responsive">
                        <table className="table table-dark table-borderless text-center">
                            <thead>
                                <tr>
                                    <th className='border-bottom border-white'>Nombres </th>
                                    <th className='border-bottom border-white'>Monto a pagar</th>
                                    <th className='border-bottom border-white'>Tipo de pago</th>
                                    <th className='border-bottom border-white'>Cuotas pactadas</th>
                                    <th className='border-bottom border-white'>Cuotas pagadas</th>
                                    <th className='border-bottom border-white'>Cuotas atrasdas</th>
                                    <th className='border-bottom border-white'>Total pagado</th>
                                </tr>
                            </thead>
                            <tbody>
                            {reportes.map((reporte) => (
                                    <tr key={reporte.rut}>
                                        <td>{reporte.nombres}</td>
                                        <td>{reporte.montoTotal}</td>
                                        <td>{reporte.tipoPago}</td>
                                        <td>{reporte.cuotasPactadas}</td>
                                        <td>{reporte.cuotasPagadas}</td>
                                        <td>{reporte.cuotasAtrasadas}</td>
                                        <td>{reporte.totalPagado}</td>
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

export default ReporteComponent;