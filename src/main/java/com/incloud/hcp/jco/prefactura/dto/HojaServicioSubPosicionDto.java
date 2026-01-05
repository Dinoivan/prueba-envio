package com.incloud.hcp.jco.prefactura.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class HojaServicioSubPosicionDto implements Serializable {

    private String codigoServicio;
    private String descripcionServicio;
    private String indicadorDetraccion;
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal importe;


    public String getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(String codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    public String getIndicadorDetraccion() {
        return indicadorDetraccion;
    }

    public void setIndicadorDetraccion(String indicadorDetraccion) {
        this.indicadorDetraccion = indicadorDetraccion;
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

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }


    @Override
    public String toString() {
        return "HojaServicioSubPosicionDto{" +
                "codigoServicio='" + codigoServicio + '\'' +
                ", descripcionServicio='" + descripcionServicio + '\'' +
                ", indicadorDetraccion='" + indicadorDetraccion + '\'' +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", importe=" + importe +
                '}';
    }
}
