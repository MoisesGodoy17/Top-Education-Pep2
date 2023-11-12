import axios from 'axios';

const ALUMNO_API_URL = "http://localhost:8080/alumno/";

class AlumnoService {

    getAlumnos(){
        return axios.get(ALUMNO_API_URL);
    }

    createAlumno(data){
        return axios.post(ALUMNO_API_URL, data);
    }

    getAlumoByRut(rut){
        return axios.get(ALUMNO_API_URL + rut, rut);
    }
}

export default new AlumnoService()