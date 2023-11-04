package cuotas.cuotasservice.controller;

import cuotas.cuotasservice.models.AlumnoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cuotas.cuotasservice.service.CuotasService;
import cuotas.cuotasservice.entity.CuotasEntity;

import java.time.LocalDate;
import java.util.List;


@RequestMapping("/cuotas")
@RestController
public class CuotasController {

    @Autowired
    CuotasService cuotasService;

    @GetMapping("/{rut}")
    public ResponseEntity<List<CuotasEntity>> cuotas(@PathVariable("rut") String rut){
        List<CuotasEntity> cuotasEntities = cuotasService.obtenerCuotasPorRut(rut);
        System.out.println("11111");
        return ResponseEntity.ok(cuotasEntities);
    }

    @PostMapping("/crear")
    public ResponseEntity<CuotasEntity> crear(@RequestBody CuotasEntity cuotas) {
        System.out.println("pene");
        cuotasService.crearCuota(cuotas);
        return ResponseEntity.ok(null);
    }
}
