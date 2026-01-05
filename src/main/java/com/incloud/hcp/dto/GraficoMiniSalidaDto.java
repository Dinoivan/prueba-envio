package com.incloud.hcp.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class GraficoMiniSalidaDto implements Serializable {

    private String descripcion;
    private BigDecimal valor;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "GraficoMiniSalidaDto{" +
                "descripcion='" + descripcion + '\'' +
                ", valor=" + valor +
                '}';
    }
}
