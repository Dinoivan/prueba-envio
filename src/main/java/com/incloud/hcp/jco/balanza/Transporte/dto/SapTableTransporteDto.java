package com.incloud.hcp.jco.balanza.Transporte.dto;

import java.util.Date;

public class SapTableTransporteDto {
    private String placa;
    private String marca;
    private String modelo;
    private String civ;
    private String status;
    private String remolque;
    private String tipoVehiculo;
    private String nroAutorizacion;
    private String codAutorizacion;
    private String zmtc;
    private String createdBy;
    private String modifiedBy;
    private Date fechaRegistro;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCiv() {
        return civ;
    }

    public void setCiv(String civ) {
        this.civ = civ;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemolque() {
        return remolque;
    }

    public void setRemolque(String remolque) {
        this.remolque = remolque;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getNroAutorizacion() {
        return nroAutorizacion;
    }

    public void setNroAutorizacion(String nroAutorizacion) {
        this.nroAutorizacion = nroAutorizacion;
    }

    public String getCodAutorizacion() {
        return codAutorizacion;
    }

    public void setCodAutorizacion(String codAutorizacion) {
        this.codAutorizacion = codAutorizacion;
    }

    public String getZmtc() {
        return zmtc;
    }

    public void setZmtc(String zmtc) {
        this.zmtc = zmtc;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "SapTableTransporteDto{" +
                "placa='" + placa + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", civ='" + civ + '\'' +
                ", status='" + status + '\'' +
                ", remolque='" + remolque + '\'' +
                ", tipoVehiculo='" + tipoVehiculo + '\'' +
                ", nroAutorizacion='" + nroAutorizacion + '\'' +
                ", codAutorizacion='" + codAutorizacion + '\'' +
                ", zmtc='" + zmtc + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", fechaRegistro='" + fechaRegistro + '\'' +
                '}';
    }
}
