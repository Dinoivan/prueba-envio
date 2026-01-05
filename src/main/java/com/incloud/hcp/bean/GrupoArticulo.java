package com.incloud.hcp.bean;

import java.io.Serializable;

public class GrupoArticulo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idGrupo;
    private String codigoSap;
    private String descripcion;
    private String nivel;

    public GrupoArticulo() {
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getCodigoSap() {
        return codigoSap;
    }

    public void setCodigoSap(String codigoSap) {
        this.codigoSap = codigoSap;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "GrupoArticulo{" +
                "idGrupo=" + idGrupo +
                ", codigoSap='" + codigoSap + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", nivel='" + nivel + '\'' +
                '}';
    }
}
