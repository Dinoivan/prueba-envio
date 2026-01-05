package com.incloud.hcp.bean;

/**
 * Created by Administrador on 21/08/2017.
 */
public class Pais {
    private Integer idPais;
    private String codigo;
    private String descripcion;

    private Integer idUbigeo;
    private String codigoUbigeoSap;
    private Integer nivel;

    public Pais() {
    }

    public Integer getIdPais() {
        return idPais;
    }

    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
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

    public Integer getIdUbigeo() {
        return idUbigeo;
    }

    public void setIdUbigeo(Integer idUbigeo) {
        this.idUbigeo = idUbigeo;
    }

    public String getCodigoUbigeoSap() {
        return codigoUbigeoSap;
    }

    public void setCodigoUbigeoSap(String codigoUbigeoSap) {
        this.codigoUbigeoSap = codigoUbigeoSap;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Pais{" +
                "idPais=" + idPais +
                ", codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", idUbigeo=" + idUbigeo +
                ", codigoUbigeoSap='" + codigoUbigeoSap + '\'' +
                ", nivel=" + nivel +
                '}';
    }
}
