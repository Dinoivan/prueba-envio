package com.incloud.hcp.dto;

import java.math.BigDecimal;

public class PosicionCantidadDto {
    private Integer idDetalle;
    private String posicion;
    private BigDecimal cantidad;

    public String getPosicion() { return posicion; }
    public BigDecimal getCantidad() { return cantidad; }

    public void setPosicion(String posicion) { this.posicion = posicion; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }
}
