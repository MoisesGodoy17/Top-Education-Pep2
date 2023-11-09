package com.notas.notasservice.service;


import com.notas.notasservice.entity.NotasEntity;
import com.notas.notasservice.models.AlumnoEntity;
import com.notas.notasservice.models.CuotasEntity;
import com.notas.notasservice.repository.NotaRepository;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Service
public class NotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final Logger logg = LoggerFactory.getLogger(NotaService.class);

    @Generated
    public static List<LocalDate> obtenerPrimerosLunes() {
        List<LocalDate> primerosLunes = new ArrayList<>();
        primerosLunes.add(LocalDate.of(2023, 1, 2));
        primerosLunes.add(LocalDate.of(2023, 2, 6));
        primerosLunes.add(LocalDate.of(2023, 3, 6));
        primerosLunes.add(LocalDate.of(2023, 4, 3));
        primerosLunes.add(LocalDate.of(2023, 5, 1));
        primerosLunes.add(LocalDate.of(2023, 6, 5));
        primerosLunes.add(LocalDate.of(2023, 7, 3));
        primerosLunes.add(LocalDate.of(2023, 8, 7));
        primerosLunes.add(LocalDate.of(2023, 9, 4));
        primerosLunes.add(LocalDate.of(2023, 10, 2));
        primerosLunes.add(LocalDate.of(2023, 11, 6));
        primerosLunes.add(LocalDate.of(2023, 12, 4));
        return primerosLunes;
    }

    @Generated
    public static boolean contieneFecha(List<LocalDate> fechas, LocalDate fecha) {
        return fechas.contains(fecha);
    }


    public int verificaPrimerMesDelMes() {
        List<LocalDate> primerosLunes = obtenerPrimerosLunes();
        LocalDate fechaDada = LocalDate.now();
        if (contieneFecha(primerosLunes, fechaDada)) {//si la fecha coincide con el primer lunes del mes, entonces permite recibir archivos
            return 1;
        }
        return 0;
    }

    // Getter: trae como una lista de objetos AlumnoEntity a traves del controller de alumno-service
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

    public void actualizarCuota(CuotasEntity cuota) {
        // Configura los encabezados para la solicitud HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Convierte el objeto cuota a JSON y configura como cuerpo de la solicitud
        HttpEntity<CuotasEntity> request = new HttpEntity<>(cuota, headers);
        // Realiza la solicitud POST usando RestTemplate
        restTemplate.exchange(
                "http://cuotas-service/cuotas",  // La URL debe ser el endpoint correcto del servicio
                HttpMethod.POST,
                request,  // El objeto cuota convertido a JSON es el cuerpo de la solicitud
                Void.class
        );
    }


    public List<CuotasEntity> getCuotaPorRutAndFechaPago(String rut, LocalDate fechaPago){
        ResponseEntity<List<CuotasEntity>> responseEntity = restTemplate.exchange(
                "http://cuotas-service/cuotas/" + rut + "/" + fechaPago,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CuotasEntity>>() {}
        );
        return responseEntity.getBody();
    }

    @Generated
    public String guardarArchivo(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
            return "Archivo guardado con exito!";
        }
        else{
            return "No se pudo guardar el archivo";
        }
    }

    @Generated
    public void leerCsv(String nombreArchivo) {
        try (Scanner scanner = new Scanner(new File(nombreArchivo))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            System.out.println("Contenido del archivo:");
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] datos = linea.split(";");
                if (datos.length == 3) {
                    String fecha = datos[0];
                    String nota = datos[1];
                    String rut = datos[2];
                    escribirDatos(fecha,nota, rut);
                    //System.out.println("Fecha: " + fecha + ", Nota: " + nota + ", Rut: " + rut);
                } else {
                    System.out.println("Formato incorrecto en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }


    @Generated
    public void escribirDatos(String fecha, String nota, String rut) {
        List<AlumnoEntity> alumnoEntityList = getAlumno(rut);
        NotasEntity notasEntity = new NotasEntity();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Cambiado el formato de la fecha
        LocalDate fechaNota = LocalDate.parse(fecha, formatter);
        notasEntity.setFechaNota(fechaNota);
        int puntaje = Integer.parseInt(nota);
        notasEntity.setEstadoNota(0);
        notasEntity.setPuntajeNota(puntaje);
        notasEntity.setRut(alumnoEntityList.get(0).getRut());
        notaRepository.save(notasEntity);
    }

    @Generated
    public void generaDescuentoPorNota(){
        List<LocalDate> primerosLunes = obtenerPrimerosLunes();
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaPrueba = fechaActual.minusDays(3);
        LocalDate fechaCuotaDelMes = fechaActual.withDayOfMonth(5);

        List <NotasEntity> notasPorFecha = notaRepository.findByFechaNota(fechaPrueba);

        if (contieneFecha(primerosLunes, fechaActual)){
            for (NotasEntity notas : notasPorFecha){
                if (notas.getRut() != null){
                    List<NotasEntity> notasPorEstado = notaRepository.findByAlumnoRutAndEstadoNota(notas.getRut(), 0);
                    int i = 1, sumNota = 0;
                    if (notas.getEstadoNota() == 0){
                        for (NotasEntity notasDelAlumno : notasPorEstado){
                            notasDelAlumno.setEstadoNota(1);
                            sumNota = sumNota + notasDelAlumno.getPuntajeNota();
                            System.out.println("Acumulado Puntaje: "+ sumNota);
                            if (i == notasPorEstado.size()){
                                System.out.println("Largo i y Size:"+ i + notasPorEstado.size() );
                                float descuentoDeNota = calcularDescuentoNotas(sumNota/notasPorEstado.size());
                                //crear controler para esto en cuotas-service
                                List <CuotasEntity> cuota_f = getCuotaPorRutAndFechaPago(notasDelAlumno.getRut(), fechaCuotaDelMes);
                                System.out.println("Descuento: " + descuentoDeNota);
                                cuota_f.get(0).setDescuentoNotas((int) (descuentoDeNota*cuota_f.get(0).getMonto()));
                                actualizarCuota(cuota_f.get(0));
                            }
                            i++;
                        }
                    }
                }
            }
        }
    }

    @Generated
    public float calcularDescuentoNotas(int prom){
        float descuento = 0;
        if (prom >= 950 && prom <= 1000) {
            descuento = 0.10f;
        }
        if (prom >= 900 && prom <= 949) {
            descuento = 0.05f;
        }
        if (prom >= 850 && prom <= 899) {
            descuento = 0.02f;
        }
        return descuento;
    }
}
