import React from 'react';

const NavbarComponent = () => {
    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-dark">
            <div className="container-fluid">
                <button
                    className="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarNavAltMarkup"
                    aria-controls="navbarNavAltMarkup"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
                >
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div className="navbar-nav">
                        <a className="nav-link text-white" href="/">
                            Inicio
                        </a>
                        <a className="nav-link text-white" href="/new-alumno">
                            Agregar alumno
                        </a>
                        <a className="nav-link text-white" href="/alumnos">
                            Listado de alumnos
                        </a>
                        <a className="nav-link text-white" href="/cuotas">
                            Cuotas
                        </a>
                        <a className="nav-link text-white" href="/new-cuotas">
                            Crear cuotas
                        </a>
                        <a className="nav-link text-white" href="/archivo">
                            Subir notas
                        </a>
                        <a className="nav-link text-white" href="/reporte">
                            Ver reporte
                        </a>
                    </div>
                </div>
            </div>
        </nav>
    );
};

export default NavbarComponent;
