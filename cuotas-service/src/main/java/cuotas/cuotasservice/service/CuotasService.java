package cuotas.cuotasservice.service;
import cuotas.cuotasservice.entity.CuotasEntity;
import cuotas.cuotasservice.models.AlumnoEntity;
import cuotas.cuotasservice.repository.CuotasRepository;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.List;



@SpringBootApplication
@Service
public class CuotasService {

    @Autowired
    private CuotasRepository cuotasRepository;

    @Autowired
    private RestTemplate restTemplate;


    public List<AlumnoEntity> getAlumno(String rut) {
        List<AlumnoEntity> alumno = restTemplate.getForObject("http://alumno-service/alumno/" + rut, List.class);
        return alumno;
    }

    public boolean alumnoConCuotas(AlumnoEntity alumnoEntity){
        return alumnoEntity.getTipo_pago().equals("Cuotas");
    }

    public void crearCuota(String rut, String cant_cuotas, LocalDate fechaEmision){
        List<AlumnoEntity> alumnoEntity = getAlumno(rut);
        int cantCuotas = Integer.parseInt(cant_cuotas);
        float descuento = 0.0f;
        descuento = calcularDescuentoAno(alumnoEntity.get(0), descuento) + calcularDescuentoColegio(alumnoEntity.get(0), descuento);
        int montoInt = (int) Math.floor(((float) 1500000 /cantCuotas) - ((float) 1500000 /cantCuotas)*descuento);

        if (alumnoConCuotas(alumnoEntity.get(0)) && verificaColegio(alumnoEntity.get(0), cantCuotas)){
            for (int i = 1; i < cantCuotas+1; i++){
                CuotasEntity cuotasEntity = new CuotasEntity();
                cuotasEntity.setMonto(montoInt);
                cuotasEntity.setFechaEmision(fechaEmision);
                cuotasEntity.setFechaPago(fechaEmision.withDayOfMonth(5).plusMonths(i));
                cuotasEntity.setFechaVencimiento(fechaEmision.withDayOfMonth(10).plusMonths(i));
                cuotasEntity.setEstado("No pagada");
                cuotasEntity.setCant_cuotas(cantCuotas);
                cuotasEntity.setDescuento(0);
                cuotasEntity.setRut(rut);
                cuotasRepository.save(cuotasEntity);
            }
        }
    }

    public boolean verificaColegio(AlumnoEntity alumnoEntity, int canCuotas){
        if (alumnoEntity.getTipo_colegio().equals("Municipal") && canCuotas <= 10){
            return true;
        }
        if (alumnoEntity.getTipo_colegio().equals("Subvencionado") && canCuotas <= 7){
            return true;
        }
        if (alumnoEntity.getTipo_colegio().equals("Privado") && canCuotas <= 4){
            return true;
        }
        return false;
    }

    //funcion que recibe el alumno y otros datos para calcular el descuento por años de egreso.
    public float calcularDescuentoAno(AlumnoEntity alumnoEntity, float descuento){
        int anoEgreso = alumnoEntity.getAno_egreso();
        if (anoEgreso  < 1){
            descuento = 0.15f;
        }
        if (anoEgreso >= 1 && anoEgreso <= 2) {
            descuento = 0.08f;
        }
        if (anoEgreso >= 3 && anoEgreso <= 4) {
            descuento = 0.04f;
        }
        return descuento;
    }

    @Generated
    public void generaCuotasAtrasadas(){
        List<CuotasEntity> cutasAtrasadas = cuotasRepository.findByEstado("No pagada");
        List<CuotasEntity> cuotaConRetraso = cuotasRepository.findByEstado("Atrasada");
        cutasAtrasadas.addAll(cuotaConRetraso);

        LocalDate fechaLocal = LocalDate.now();
        int multa;
        if(fechaLocal.getDayOfMonth()==11){
            for(CuotasEntity cuotas : cutasAtrasadas){
                LocalDate fechaVence = cuotas.getFechaVencimiento();
                if (fechaLocal.isAfter(fechaVence)){
                    multa = (int) Math.floor((cuotas.getMonto()*calculaIntereAtraso(fechaLocal, fechaVence)) + cuotas.getInteres());
                    System.out.println("diferencia" + multa);
                    cuotas.setEstado("Atrasada");
                    cuotas.setInteres(multa);
                    cuotas.setMonto(multa + cuotas.getMonto());
                    cuotasRepository.save(cuotas);
                }
            }
        }
    }

    public float calculaIntereAtraso(LocalDate fechaLocal, LocalDate fechaVence){
        int diferencia = fechaLocal.getMonthValue() - fechaVence.getMonthValue();
        // se resta la fecha de pago de cuota menos la fecha de facturacion para asi saber la diferencia y saber el retraso
        float deuda = 0.0f;

        if (diferencia == 1){
            deuda = 0.03f;
        }
        if (diferencia == 2){
            deuda = 0.06f;
        }
        if (diferencia == 3){
            deuda = 0.09f;
        }
        if (diferencia > 3){
            deuda = 0.12f;
        }
        return deuda;
    }

    public float calcularDescuentoColegio(AlumnoEntity alumnoEntity, float descuento){
        if (alumnoEntity.getTipo_colegio().equals("Municipal")){
            descuento = descuento + 0.20f;
        }
        if (alumnoEntity.getTipo_colegio().equals("Subvencionado")){
            descuento = descuento + 0.10f;
        }
        return descuento;
    }

    public void pagarCuotas(Long idCuota){
        CuotasEntity cuota = cuotasRepository.findByIdCuota(idCuota);
        LocalDate fechaLocal = LocalDate.now().withDayOfMonth(5); // si es el mes tanto en el dia 4, se podra pagar el dia siguiente
        LocalDate fechaVence = cuota.getFechaPago();
        if (fechaLocal.isEqual(fechaVence) || fechaLocal.isAfter(fechaVence)){
            cuota.setEstado("Pagada");
            cuota.setFechaEmision(LocalDate.now());
            cuotasRepository.save(cuota);
        }
    }

    public CuotasEntity buscarCuotaPorId(Long id){
        return cuotasRepository.findByIdCuota(id);
    }

    public List<CuotasEntity> obtenerCuotasPorRut(String rut) {
        return cuotasRepository.findByRut(rut);
    }
}
