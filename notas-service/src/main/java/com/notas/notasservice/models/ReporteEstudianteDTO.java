package com.notas.notasservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ReporteEstudianteDTO {
    private String rut;
    private String nombres;
    private int montoTotal;
    private String tipoPago;
    private int pruebasRendidas;
    private int cuotasPactadas;
    private int cuotasPagadas;
    private int cuotasAtrasadas;
    private int totalPagado;
}
