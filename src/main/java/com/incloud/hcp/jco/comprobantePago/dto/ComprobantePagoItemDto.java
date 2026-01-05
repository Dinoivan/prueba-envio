package com.incloud.hcp.jco.comprobantePago.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ComprobantePagoItemDto implements Serializable {

    private String codigoDocumentoSap;
    private String ejercicio;
    private Integer numeroItem;
    private String numeroOrdenCompra;
    private String numeroPosicion;
    private String descripcionProducto;
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal importeItem;

    public String getCodigoDocumentoSap() {
        return codigoDocumentoSap;
    }

    public void setCodigoDocumentoSap(String codigoDocumentoSap) {
        this.codigoDocumentoSap = codigoDocumentoSap;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Integer getNumeroItem() {
        return numeroItem;
    }

    public void setNumeroItem(Integer numeroItem) {
        this.numeroItem = numeroItem;
    }

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

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getImporteItem() {
        return importeItem;
    }

    public void setImporteItem(BigDecimal importeItem) {
        this.importeItem = importeItem;
    }


    @Override
    public String toString() {
        return "ComprobantePagoItemDto{" +
                "codigoDocumentoSap='" + codigoDocumentoSap + '\'' +
                ", ejercicio='" + ejercicio + '\'' +
                ", numeroItem=" + numeroItem +
                ", numeroOrdenCompra='" + numeroOrdenCompra + '\'' +
                ", numeroPosicion='" + numeroPosicion + '\'' +
                ", descripcionProducto='" + descripcionProducto + '\'' +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", importeItem=" + importeItem +
                '}';
    }
}
