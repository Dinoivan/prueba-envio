package com.incloud.hcp.jco.balanza.Transporte.dto;

public class TransporteResponseDTO {
    private String placa;
    private String marca;
    private String modelo;
    private String civ;
    private String estado;
    private String remolque;
    private String tipoVehiculo;
    private String nroAutorizacion;
    private String codAutorizacion;
    private String zmtc;
    private String createdBy;
    private String modifiedBy;

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    @Override
    public String toString() {
        return "TransporteResponseDTO{" +
                "placa='" + placa + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", civ='" + civ + '\'' +
                ", estado='" + estado + '\'' +
                ", remolque='" + remolque + '\'' +
                ", tipoVehiculo='" + tipoVehiculo + '\'' +
                ", nroAutorizacion='" + nroAutorizacion + '\'' +
                ", codAutorizacion='" + codAutorizacion + '\'' +
                ", zmtc='" + zmtc + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }
}
