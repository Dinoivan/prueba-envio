package com.incloud.hcp.dto;

public class BancoDto {
    private Integer idBanco;
    private String descripcion;

    public BancoDto() {
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "BancoDto{" +
                "idBanco=" + idBanco +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
