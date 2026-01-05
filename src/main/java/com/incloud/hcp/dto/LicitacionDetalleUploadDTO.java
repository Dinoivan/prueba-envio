package com.incloud.hcp.dto;

import java.math.BigDecimal;

public class LicitacionDetalleUploadDTO {

    private String codigoSap;
    private String numeroParte;
    private String descripcion;
    private BigDecimal cantidad;

    private String codigoSapCentro;
    private String codigoSapAlmacen;

    public String getCodigoSap() {
        return codigoSap;
    }

    public void setCodigoSap(String codigoSap) {
        this.codigoSap = codigoSap;
    }

    public String getNumeroParte() {
        return numeroParte;
    }

    public void setNumeroParte(String numeroParte) {
        this.numeroParte = numeroParte;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigoSapCentro() {
        return codigoSapCentro;
    }

    public void setCodigoSapCentro(String codigoSapCentro) {
        this.codigoSapCentro = codigoSapCentro;
    }

    public String getCodigoSapAlmacen() {
        return codigoSapAlmacen;
    }

    public void setCodigoSapAlmacen(String codigoSapAlmacen) {
        this.codigoSapAlmacen = codigoSapAlmacen;
    }
}
