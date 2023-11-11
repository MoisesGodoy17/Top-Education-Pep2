package com.notas.notasservice.service;

import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import com.notas.notasservice.entity.NotasEntity;
import com.notas.notasservice.models.AlumnoEntity;
import com.notas.notasservice.models.CuotasEntity;
import com.notas.notasservice.models.ReporteEstudianteDTO;
import com.notas.notasservice.repository.NotaRepository;

import java.util.List;

import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReporteService {
    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<AlumnoEntity> getAlumno(String rut) {
        ResponseEntity<List<AlumnoEntity>> responseEntity = restTemplate.exchange(
                "http://alumno-service/alumno/" + rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AlumnoEntity>>() {}
        );
        return responseEntity.getBody();
    }

    // Getter: trae como una lista de objetos cuotaEntity a traves del controller de cuota-service
    public List<CuotasEntity> getCuotaPorRut(String rut) {
        ResponseEntity<List<CuotasEntity>> responseEntity = restTemplate.exchange(
                "http://cuotas-service/cuotas/" + rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CuotasEntity>>() {}
        );
        return responseEntity.getBody();
    }

    public boolean verificaEstudiante(String rut){
        AlumnoEntity alumno = getAlumno(rut).get(0);
        return alumno != null;
    }

    public String NombresEstudiante(String rut){
        AlumnoEntity alumnoEntity = getAlumno(rut).get(0);
        return alumnoEntity.getNombres();
    }

    public int MontoTotalporPagar(String rut) {
        List<CuotasEntity> cuotas = getCuotaPorRut(rut);
        int montoTotal = 0;
        for (CuotasEntity cuotasEntity : cuotas) {
            montoTotal = cuotasEntity.getMonto() + montoTotal;
        }
        return montoTotal;
    }

    public String TipoDePago(String rut){
        AlumnoEntity alumno = getAlumno(rut).get(0);
        return alumno.getTipo_pago();
    }

    /*
    @Generated
    public int nroPruebasRendidas(String rut){
        List<NotasEntity> notas = notaRepository.findByAlumnoRut(rut);
        if (notas.isEmpty()){
            return 0;
        }
        return notas.size();
    }
    */

    public int cuotasPactadas(String rut){
        AlumnoEntity alumno = getAlumno(rut).get(0);
        if (alumno.getTipo_pago().equals("Contado")){
            return 0;
        }
        List<CuotasEntity> cuotas = getCuotaPorRut(rut);
        System.out.println("Cant cuotas:" + cuotas.size());
        return cuotas.size();
    }

    public int cuotasPagadas(String rut){
        AlumnoEntity alumno = getAlumno(rut).get(0);
        if (alumno.getTipo_pago().equals("Contado")){
            return 0;
        }
        int cuotasP = 0;
        List<CuotasEntity> cuotas = getCuotaPorRut(rut);
        for (CuotasEntity cuotasEntity : cuotas){
            if (cuotasEntity.getEstado().equals("Pagada")){
                cuotasP++;
            }
        }
        return cuotasP;
    }

    public int cuotasAtrasadas(String rut){
        AlumnoEntity alumno = getAlumno(rut).get(0);
        if (alumno.getTipo_pago().equals("Contado")){
            return 0;
        }
        List<CuotasEntity> cuotas = getCuotaPorRut(rut);
        int cuotasA = 0;
        for (CuotasEntity cuotasEntity : cuotas){
            if (cuotasEntity.getEstado().equals("Atrasada")){
                cuotasA++;
            }
        }
        return cuotasA;
    }

    public int totalPagado(String rut){
        AlumnoEntity alumno = getAlumno(rut).get(0);
        int total = 0;
        if (alumno.getTipo_pago().equals("Contado")){
            return alumno.getArancel(); //retornar lo que pago al contado
        }
        List<CuotasEntity> cuotas = getCuotaPorRut(rut);
        for (CuotasEntity cuotasEntity : cuotas){
            if (cuotasEntity.getEstado().equals("Pagada")){
                total = cuotasEntity.getMonto() + total;
            }
        }
        return total;
    }

    public ReporteEstudianteDTO generatedReport(String rut){
        String nombres = NombresEstudiante(rut);
        int montoTotal = MontoTotalporPagar(rut);
        String tipoPago = TipoDePago(rut);
        int pruebasRendidas = 0;
        int cuotasPactadas = cuotasPactadas(rut);
        int cuotasPagadas = cuotasPagadas(rut);
        int cuotasAtrasadas = cuotasAtrasadas(rut);
        int totalPagado = totalPagado(rut);

        ReporteEstudianteDTO reporte = new ReporteEstudianteDTO(
                rut,
                nombres,
                montoTotal,
                tipoPago,
                pruebasRendidas,
                cuotasPactadas,
                cuotasPagadas,
                cuotasAtrasadas,
                totalPagado);
        return reporte;
    }

}
