package com.incloud.hcp.dto;

public class CondicionPagoDto {
    private Integer idCondicionPago;
    private String descripcion;

    public CondicionPagoDto() {
    }

    public Integer getIdCondicionPago() {
        return idCondicionPago;
    }

    public void setIdCondicionPago(Integer idCondicionPago) {
        this.idCondicionPago = idCondicionPago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
