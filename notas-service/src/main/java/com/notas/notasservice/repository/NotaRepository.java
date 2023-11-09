package com.notas.notasservice.repository;

import com.notas.notasservice.entity.NotasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<NotasEntity, Long> {
    List<NotasEntity> findByAlumnoRutAndEstadoNota(String rut, int estado);

    List<NotasEntity> findByFechaNota(LocalDate fechaNota);

    List<NotasEntity> findByAlumnoRut(String rut);
}
