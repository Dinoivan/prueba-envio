package com.incloud.hcp.dto;

public class TipoProveedorDto {
    private Integer idTipoProveedor;
    private String descripcion;

    public TipoProveedorDto() {
    }

    public Integer getIdTipoProveedor() {
        return idTipoProveedor;
    }

    public void setIdTipoProveedor(Integer idTipoProveedor) {
        this.idTipoProveedor = idTipoProveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
