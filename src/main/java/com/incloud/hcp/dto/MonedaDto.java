package com.incloud.hcp.dto;

public class MonedaDto {
    private Integer idMoneda;
    private String descripcion;

    public MonedaDto() {
    }

    public Integer getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Integer idMoneda) {
        this.idMoneda = idMoneda;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
