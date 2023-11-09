package com.notas.notasservice.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="notas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idNota", unique = true, nullable = false)
    private Long idNota;

    @Column(name = "fechaNota")
    private LocalDate fechaNota;

    @Column(name = "puntajeNota")
    private int puntajeNota;

    @Column(name = "estadoNota")
    private int estadoNota;

    @Column(name = "rut")
    private String rut;
}
