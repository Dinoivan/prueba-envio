package com.incloud.hcp.dto;

import java.math.BigDecimal;

public class ProveedorVerNotaDto {

    private Integer idProveedor;
    private String indHomologado;
    private BigDecimal evaluacionHomologacion;

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getIndHomologado() {
        return indHomologado;
    }


    @Override
    public String toString() {
        return "ProveedorVerNotaDto{" +
                "idProveedor=" + idProveedor +
                ", indHomologado='" + indHomologado + '\'' +
                ", evaluacionHomologacion=" + evaluacionHomologacion +
                '}';
    }

    public void setIndHomologado(String indHomologado) {
        this.indHomologado = indHomologado;
    }

    public BigDecimal getEvaluacionHomologacion() {
        return evaluacionHomologacion;
    }

    public void setEvaluacionHomologacion(BigDecimal evaluacionHomologacion) {
        this.evaluacionHomologacion = evaluacionHomologacion;
    }
}
