package com.incloud.hcp.dto;

public class TipoComprobanteDto {
    private Integer idTipoComprobante;
    private String descripcion;

    public TipoComprobanteDto() {
    }

    public Integer getIdTipoComprobante() {
        return idTipoComprobante;
    }

    public void setIdTipoComprobante(Integer idTipoComprobante) {
        this.idTipoComprobante = idTipoComprobante;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
