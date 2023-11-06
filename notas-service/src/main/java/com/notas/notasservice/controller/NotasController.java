package com.notas.notasservice.controller;

import com.notas.notasservice.service.NotaService;
import com.notas.notasservice.models.ReporteEstudianteDTO;
import com.notas.notasservice.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/notas")
@RestController
public class NotasController {

    @Autowired
    private NotaService notaService;

    @Autowired
    private ReporteService reporteService;

    @PostMapping("/subir")
    public ResponseEntity<String> handleFileUpload(@RequestParam("archivo") MultipartFile file) {
        if (notaService.verificaPrimerLunesDelMes() == 1) {
            notaService.guardarArchivo(file);
            notaService.leerCsv(file.getOriginalFilename()); // Antes: "prueba.csv"
            return ResponseEntity.status(HttpStatus.OK).body("Archivo cargado exitosamente!!! ");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo cargar el archivo o no es la fecha correcta :( ");
    }

    /*
    @GetMapping("/descuento")
    public ResponseEntity<String> generarDescuentoNotas(){
        notaService.generaDescuentoPorNota();
        return ResponseEntity.status(HttpStatus.OK).body("Descuento generado de forma exitoza!!! ");
    }
    */
    @GetMapping("/reporte/{rut}")
    @ResponseBody
    public ResponseEntity<List<ReporteEstudianteDTO>> verReporte(@PathVariable("rut") String rut) {
        if (reporteService.verificaEstudiante(rut)) {
            List<ReporteEstudianteDTO> reportesDTO = new ArrayList<>();

            // Crear un ReporteEstudianteDTO para cada elemento en la lista, o ajustar según tus necesidades.
            ReporteEstudianteDTO reporteDTO1 = new ReporteEstudianteDTO();
            reporteDTO1.setRut(rut);
            reporteDTO1.setNombres(reporteService.NombresEstudiante(rut));
            reporteDTO1.setMontoTotal(reporteService.MontoTotalporPagar(rut));
            reporteDTO1.setTipoPago(reporteService.TipoDePago(rut));
            reporteDTO1.setPruebasRendidas(reporteService.nroPruebasRendidas(rut));
            reporteDTO1.setCuotasPactadas(reporteService.cuotasPactadas(rut));
            reporteDTO1.setCuotasPagadas(reporteService.cuotasPagadas(rut));
            reporteDTO1.setCuotasAtrasadas(reporteService.cuotasAtrasadas(rut));
            reporteDTO1.setTotalPagado(reporteService.totalPagado(rut));

            // Añadir el ReporteEstudianteDTO a la lista
            reportesDTO.add(reporteDTO1);

            // Puedes seguir añadiendo más ReporteEstudianteDTO a la lista según tus necesidades.

            return ResponseEntity.ok(reportesDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
