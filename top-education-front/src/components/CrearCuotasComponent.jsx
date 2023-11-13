import React, { useState } from 'react';
import CuotasService from '../services/CuotasService';
import { useNavigate } from 'react-router-dom';

const CrearCuotasComponent = () => {
    const [cuotas, setCuotas] = useState({
        monto: 0,
        fechaEmision: "",
        fechaPago: "",
        fechaVencimiento: "",
        estado: "No pagada",
        descuento: 0,
        interes: 0,
        descuentoNotas: 0,
        cant_cuotas: 0,
        rut: ""
    });

    const navigate = useNavigate();

    const handleChange = (e) => {
        setCuotas({
            ...cuotas,
            [e.target.name]: e.target.value
        });
    };

    const saveCuotas = (e) => {
        e.preventDefault();
        CuotasService.createCuotas(cuotas).then(() => {
            navigate('/cuotas');
        });
    };

    return (<div><div className="container">
        <br></br>
        <br></br>
        <div className="row">
            <div className="card col-md-4 offset-md-3 offset-md-3 bg-dark text-white rounded-4 mx-auto">
                <h3 className="text-center">Cuotas</h3>
                <div className="card-body">
                    <form>
                        <div className="form-group mb-3">
                            <label className="text-white"> Rut: </label>
                            <input placeholder="Rut: 12345678-9" name="rut" className="form-control"
                                type='text' value={cuotas.rut} onChange={handleChange} />
                        </div>
                        <div className="form-group mb-3">
                            <label className="text-white"> Cantidad de Cuotas: </label>
                            <input placeholder="Ingrese la cantidad de cuotas" name="cant_cuotas" className="form-control"
                                type='number' value={cuotas.cant_cuotas} onChange={handleChange} />
                        </div>
                        <div className="form-group mb-3">
                                <label className="text-white"> Fecha de Emision: </label>
                                <input placeholder="Fecha de Emision" name="fechaEmision" className="form-control"
                                    type='date' value={cuotas.fechaEmision} onChange={handleChange} />
                            </div>
                        <button className="btn btn-primary" onClick={saveCuotas}>Generar</button>
                    </form>
                </div>
            </div>
        </div>
    </div></div>);
};
export default CrearCuotasComponent;