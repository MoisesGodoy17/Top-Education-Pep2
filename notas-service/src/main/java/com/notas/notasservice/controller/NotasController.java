package com.notas.notasservice.controller;

import com.notas.notasservice.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping
public class NotasController {

    @Autowired
    private NotaService notaService;

    @GetMapping("/subir")
    public ResponseEntity<String> handleFileUpload(@RequestParam("archivo") MultipartFile file) {
        if (notaService.verificaPrimerMesDelMes() == 1) {
            notaService.guardarArchivo(file);
            notaService.leerCsv(file.getOriginalFilename()); // Antes: "prueba.csv"
            return ResponseEntity.status(HttpStatus.OK).body("Archivo cargado exitosamente!!! ");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo cargar el archivo o no es la fecha correcta :( ");
    }

    @GetMapping("/descuento")
    public ResponseEntity<String> generarDescuentoNotas(){
        notaService.generaDescuentoPorNota();
        return ResponseEntity.status(HttpStatus.OK).body("Descuento generado de forma exitoza!!! ");
    }
}
