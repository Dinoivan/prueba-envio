package com.incloud.hcp.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuiaDespachoDto {
    private String numeroOrdenDespacho;
    private String rucProveedor;
    private String numeroGuiaRemision;
    private Timestamp fechaCreacion;
    private Date fechaEntrega;
    private String lugarEntrega;
    private String centro;
    private String denominacionCentro;
    private String observacion;
    private Timestamp fechaContabilizacion;
    private List<PosicionCantidadDto> listCantPos = new ArrayList<>();


    public String getNumeroOrdenDespacho() {
        return numeroOrdenDespacho;
    }

    public void setNumeroOrdenDespacho(String numeroOrdenDespacho) {
        this.numeroOrdenDespacho = numeroOrdenDespacho;
    }

    public String getRucProveedor() {
        return rucProveedor;
    }

    public void setRucProveedor(String rucProveedor) {
        this.rucProveedor = rucProveedor;
    }

    public String getNumeroGuiaRemision() {
        return numeroGuiaRemision;
    }

    public void setNumeroGuiaRemision(String numeroGuiaRemision) {
        this.numeroGuiaRemision = numeroGuiaRemision;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getLugarEntrega() {
        return lugarEntrega;
    }

    public void setLugarEntrega(String lugarEntrega) {
        this.lugarEntrega = lugarEntrega;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getDenominacionCentro() {
        return denominacionCentro;
    }

    public void setDenominacionCentro(String denominacionCentro) {
        this.denominacionCentro = denominacionCentro;
    }

    public List<PosicionCantidadDto> getListCantPos() {
        return listCantPos;
    }

    public void setListCantPos(List<PosicionCantidadDto> listCantPos) {
        this.listCantPos = listCantPos;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Timestamp getFechaContabilizacion() {
        return fechaContabilizacion;
    }

    public void setFechaContabilizacion(Timestamp fechaContabilizacion) {
        this.fechaContabilizacion = fechaContabilizacion;
    }

    @Override
    public String toString() {
        return "GuiaDespachoDto{" +
                "numeroOrdenDespacho='" + numeroOrdenDespacho + '\'' +
                ", rucProveedor='" + rucProveedor + '\'' +
                ", numeroGuiaRemision='" + numeroGuiaRemision + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaEntrega=" + fechaEntrega +
                ", lugarEntrega='" + lugarEntrega + '\'' +
                ", centro='" + centro + '\'' +
                ", denominacionCentro='" + denominacionCentro + '\'' +
                ", observacion='" + observacion + '\'' +
                ", fechaContabilizacion=" + fechaContabilizacion +
                ", listCantPos=" + listCantPos +
                '}';
    }
}
