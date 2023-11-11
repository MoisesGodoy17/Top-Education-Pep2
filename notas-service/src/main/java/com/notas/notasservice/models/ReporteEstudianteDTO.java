package com.notas.notasservice.models;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ReporteEstudianteDTO {
    @JsonProperty("rut")
    private String rut;
    @JsonProperty("nombres")
    private String nombres;
    @JsonProperty("montoTotal")
    private int montoTotal;
    @JsonProperty("tipoPago")
    private String tipoPago;
    @JsonProperty("pruebasRendidas")
    private int pruebasRendidas;
    @JsonProperty("cuotasPactadas")
    private int cuotasPactadas;
    @JsonProperty("cuotasPagadas")
    private int cuotasPagadas;
    @JsonProperty("cuotasAtrasadas")
    private int cuotasAtrasadas;
    @JsonProperty("totalPagado")
    private int totalPagado;
}
