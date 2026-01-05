package com.incloud.hcp.dto.estadistico;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ReporteStatusPeticionOfertaEntradaPaginadoDto {

    private Date fechaInicio;
    private Date fechaFin;
    private String ruc;

    private Integer nroRegistros;
    private Integer paginaMostrar;


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

    public Integer getNroRegistros() {
        return nroRegistros;
    }

    public void setNroRegistros(Integer nroRegistros) {
        this.nroRegistros = nroRegistros;
    }

    public Integer getPaginaMostrar() {
        return paginaMostrar;
    }

    public void setPaginaMostrar(Integer paginaMostrar) {
        this.paginaMostrar = paginaMostrar;
    }

    @Override
    public String toString() {
        return "ReporteStatusPeticionOfertaEntradaPaginadoDto{" +
                "fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", ruc='" + ruc + '\'' +
                ", nroRegistros=" + nroRegistros +
                ", paginaMostrar=" + paginaMostrar +
                '}';
    }
}
