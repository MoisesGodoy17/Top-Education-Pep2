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

@RequestMapping("/notas")
@RestController
public class NotasController {

    @Autowired
    private NotaService notaService;

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/subir")
    public ResponseEntity<String> handleFileUpload(@RequestParam("archivo") MultipartFile file) {
        if (notaService.verificaPrimerMesDelMes() == 1) {
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
    @GetMapping( "/reporte/{rut}")
    @ResponseBody
    public ResponseEntity<ReporteEstudianteDTO> verReporte(@PathVariable("rut") String rut) {
        if (reporteService.verificaEstudiante(rut)) {
            ReporteEstudianteDTO reporteDTO = new ReporteEstudianteDTO();
            reporteDTO.setRut(rut);
            reporteDTO.setNombres(reporteService.NombresEstudiante(rut));
            reporteDTO.setMontoTotal(reporteService.MontoTotalporPagar(rut));
            reporteDTO.setTipoPago(reporteService.TipoDePago(rut));
            reporteDTO.setPruebasRendidas(0);
            reporteDTO.setCuotasPactadas(reporteService.cuotasPactadas(rut));
            reporteDTO.setCuotasPagadas(reporteService.cuotasPagadas(rut));
            reporteDTO.setCuotasAtrasadas(reporteService.cuotasAtrasadas(rut));
            reporteDTO.setTotalPagado(reporteService.totalPagado(rut));

            return ResponseEntity.ok(reporteDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
