package com.incloud.hcp.dto;

import java.util.Date;

public class FiltroDocumentoDto {
    private String numeroGuia;
    private String numeroOc;
    private Date fechaInicio;
    private Date fechaFin;
    private String ruc;
    private Integer nroRegistros;
    private Integer paginaMostrar;
    private Integer tipoDocumento;
    //Aprobación
    private Date fechaEmisionInicio;
    private Date fechaEmisionFin;
    private Date fechaEntradaInicio;
    private Date fechaEntradaFin;
    private String referencia;
    private String comprador;
    private String centro;
    private Integer idEstado;


    public String getNumeroGuia() {
        return numeroGuia;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public String getNumeroOc() {
        return numeroOc;
    }

    public void setNumeroOc(String numeroOc) {
        this.numeroOc = numeroOc;
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

    public Integer getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Integer tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Date getFechaEmisionInicio() {
        return fechaEmisionInicio;
    }

    public void setFechaEmisionInicio(Date fechaEmisionInicio) {
        this.fechaEmisionInicio = fechaEmisionInicio;
    }

    public Date getFechaEmisionFin() {
        return fechaEmisionFin;
    }

    public void setFechaEmisionFin(Date fechaEmisionFin) {
        this.fechaEmisionFin = fechaEmisionFin;
    }

    public Date getFechaEntradaInicio() {
        return fechaEntradaInicio;
    }

    public void setFechaEntradaInicio(Date fechaEntradaInicio) {
        this.fechaEntradaInicio = fechaEntradaInicio;
    }

    public Date getFechaEntradaFin() {
        return fechaEntradaFin;
    }

    public void setFechaEntradaFin(Date fechaEntradaFin) {
        this.fechaEntradaFin = fechaEntradaFin;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getComprador() {
        return comprador;
    }

    public void setComprador(String comprador) {
        this.comprador = comprador;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    @Override
    public String toString() {
        return "FiltroDocumentoDto{" +
                "numeroGuia='" + numeroGuia + '\'' +
                ", numeroOc='" + numeroOc + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", ruc='" + ruc + '\'' +
                ", nroRegistros=" + nroRegistros +
                ", paginaMostrar=" + paginaMostrar +
                ", tipoDocumento=" + tipoDocumento +
                ", fechaEmisionInicio=" + fechaEmisionInicio +
                ", fechaEmisionFin=" + fechaEmisionFin +
                ", fechaEntradaInicio=" + fechaEntradaInicio +
                ", fechaEntradaFin=" + fechaEntradaFin +
                ", referencia='" + referencia + '\'' +
                ", comprador='" + comprador + '\'' +
                ", centro='" + centro + '\'' +
                ", idEstado=" + idEstado +
                '}';
    }
}
