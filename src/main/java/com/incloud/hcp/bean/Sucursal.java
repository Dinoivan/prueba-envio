package com.incloud.hcp.bean;

/**
 * Created by Administrador on 23/08/2017.
 */
public class Sucursal {
    private int idSucursal;
    private String descripcion;

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Sucursal{" +
                "idSucursal=" + idSucursal +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}