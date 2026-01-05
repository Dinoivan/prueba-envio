package com.incloud.hcp.jco.balanza.Transportista.dto;

import java.util.Date;

public class TransportistaBlzFiltroDTO {
    private String ruc;
    private Date fechaInicio;
    private Date fechaFin;

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
}
