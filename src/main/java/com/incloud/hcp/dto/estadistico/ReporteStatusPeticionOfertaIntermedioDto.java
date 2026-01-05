package com.incloud.hcp.dto.estadistico;

import java.util.Date;

public class ReporteStatusPeticionOfertaIntermedioDto {

    private Date fechaInicio;
    private Date fechaFin;
    private String ruc;

    private String razonSocial;
    private String estadoLicitacion;
    private String estadoNotLicitacion;

    private Integer nroRegistros;
    private Integer paginaMostrar;

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

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getEstadoLicitacion() {
        return estadoLicitacion;
    }

    public void setEstadoLicitacion(String estadoLicitacion) {
        this.estadoLicitacion = estadoLicitacion;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getEstadoNotLicitacion() {
        return estadoNotLicitacion;
    }

    public void setEstadoNotLicitacion(String estadoNotLicitacion) {
        this.estadoNotLicitacion = estadoNotLicitacion;
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
        return "ReporteStatusPeticionOfertaIntermedioDto{" +
                "fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", ruc='" + ruc + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", estadoLicitacion='" + estadoLicitacion + '\'' +
                ", estadoNotLicitacion='" + estadoNotLicitacion + '\'' +
                ", nroRegistros=" + nroRegistros +
                ", paginaMostrar=" + paginaMostrar +
                '}';
    }


}
