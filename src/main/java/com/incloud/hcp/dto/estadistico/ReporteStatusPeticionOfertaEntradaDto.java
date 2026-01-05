package com.incloud.hcp.dto.estadistico;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ReporteStatusPeticionOfertaEntradaDto {

    private Date fechaInicio;
    private Date fechaFin;
    private String ruc;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    @Override
    public String toString() {
        return "ReporteStatusPeticionOfertaEntradaDto{" +
                "fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", ruc='" + ruc + '\'' +
                '}';
    }
}
