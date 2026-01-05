package com.incloud.hcp.jco.prefactura.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PrefacturaRFCPosicionDto implements Serializable {

    private String numeroOrdenCompra;
    private String numeroPosicion;
    private BigDecimal cantidadFacturada;
    private String unidadMedida;
    private BigDecimal valorFacturado;
    private String tipoDocumentoAceptacion;
    private String numeroDocumentoAceptacion;
    private String numeroItem;
    private String yearEmision; //"20XX"


    public String getNumeroOrdenCompra() {
        return numeroOrdenCompra;
    }

    public void setNumeroOrdenCompra(String numeroOrdenCompra) {
        this.numeroOrdenCompra = numeroOrdenCompra;
    }

    public String getNumeroPosicion() {
        return numeroPosicion;
    }

    public void setNumeroPosicion(String numeroPosicion) {
        this.numeroPosicion = numeroPosicion;
    }

    public BigDecimal getCantidadFacturada() {
        return cantidadFacturada;
    }

    public void setCantidadFacturada(BigDecimal cantidadFacturada) {
        this.cantidadFacturada = cantidadFacturada;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public BigDecimal getValorFacturado() {
        return valorFacturado;
    }

    public void setValorFacturado(BigDecimal valorFacturado) {
        this.valorFacturado = valorFacturado;
    }

    public String getTipoDocumentoAceptacion() {
        return tipoDocumentoAceptacion;
    }

    public void setTipoDocumentoAceptacion(String tipoDocumentoAceptacion) {
        this.tipoDocumentoAceptacion = tipoDocumentoAceptacion;
    }

    public String getNumeroDocumentoAceptacion() {
        return numeroDocumentoAceptacion;
    }

    public void setNumeroDocumentoAceptacion(String numeroDocumentoAceptacion) {
        this.numeroDocumentoAceptacion = numeroDocumentoAceptacion;
    }

    public String getNumeroItem() {
        return numeroItem;
    }

    public void setNumeroItem(String numeroItem) {
        this.numeroItem = numeroItem;
    }

    public String getYearEmision() {
        return yearEmision;
    }

    public void setYearEmision(String yearEmision) {
        this.yearEmision = yearEmision;
    }

    @Override
    public String toString() {
        return "PrefacturaRFCPosicionDto{" +
                "numeroOrdenCompra='" + numeroOrdenCompra + '\'' +
                ", numeroPosicion='" + numeroPosicion + '\'' +
                ", cantidadFacturada=" + cantidadFacturada +
                ", unidadMedida='" + unidadMedida + '\'' +
                ", valorFacturado=" + valorFacturado +
                ", tipoDocumentoAceptacion='" + tipoDocumentoAceptacion + '\'' +
                ", numeroDocumentoAceptacion='" + numeroDocumentoAceptacion + '\'' +
                ", numeroItem='" + numeroItem + '\'' +
                ", yearEmision='" + yearEmision + '\'' +
                '}';
    }
}
