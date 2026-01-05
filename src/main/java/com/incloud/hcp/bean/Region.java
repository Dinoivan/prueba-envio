package com.incloud.hcp.bean;

/**
 * Created by Administrador on 22/08/2017.
 */
public class Region {
    private int idRegion;
    private String codigo;
    private String descripcion;
    private int idPais;

    public Region() {
    }

    public int getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    @Override
    public String toString() {
        return "Region{" +
                "idRegion=" + idRegion +
                ", codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", idPais=" + idPais +
                '}';
    }
}
