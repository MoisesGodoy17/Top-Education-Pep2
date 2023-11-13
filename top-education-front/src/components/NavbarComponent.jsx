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
                            Agregar Alumno
                        </a>
                        <a className="nav-link text-white" href="/alumnos">
                            Listado de Alumnos
                        </a>
                        <a className="nav-link text-white" href="/cuotas">
                            Cuotas
                        </a>
                        <a className="nav-link text-white" href="/new-cuotas">
                            Crear Cuotas
                        </a>
                        <a className="nav-link text-white" href="/archivo">
                            Subir Notas
                        </a>
                        <a className="nav-link text-white" href="/reporte">
                            Ver Reporte
                        </a>
                    </div>
                </div>
            </div>
        </nav>
    );
};

export default NavbarComponent;
