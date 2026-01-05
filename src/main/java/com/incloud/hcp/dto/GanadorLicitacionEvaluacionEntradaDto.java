package com.incloud.hcp.dto;

import com.incloud.hcp.domain.Proveedor;

import java.io.Serializable;
import java.math.BigDecimal;

public class GanadorLicitacionEvaluacionEntradaDto implements Serializable {

    private Proveedor proveedor;
    private Integer idProveedor;
    private BigDecimal puntajeEvaluacionTecnica;

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public BigDecimal getPuntajeEvaluacionTecnica() {
        return puntajeEvaluacionTecnica;
    }

    public void setPuntajeEvaluacionTecnica(BigDecimal puntajeEvaluacionTecnica) {
        this.puntajeEvaluacionTecnica = puntajeEvaluacionTecnica;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public String toString() {
        return "GanadorLicitacionEvaluacionEntradaDto{" +
                "proveedor=" + proveedor +
                ", idProveedor=" + idProveedor +
                ", puntajeEvaluacionTecnica=" + puntajeEvaluacionTecnica +
                '}';
    }
}
