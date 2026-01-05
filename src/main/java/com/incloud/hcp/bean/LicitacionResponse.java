package com.incloud.hcp.bean;

import java.io.Serializable;

/**
 * Created by USER on 29/08/2017.
 */
public class LicitacionResponse implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer idLicitacion;
    private String nroLicitacion;
    private String claseDocumento;
    private String fechaCreacion;
    private String fechaInicioRecepcionOferta;
    private String fechaCierreRecepcionOferta;
    private String estadoLicitacion;
    private String fechaPublicacion;
    private String usuarioCreacion;
    private String fechaUltimaRenegociacionString;


    public LicitacionResponse() {
    }

    public Integer getIdLicitacion() {
        return idLicitacion;
    }

    public void setIdLicitacion(Integer idLicitacion) {
        this.idLicitacion = idLicitacion;
    }

    public String getNroLicitacion() {
        return nroLicitacion;
    }

    public void setNroLicitacion(String nroLicitacion) {
        this.nroLicitacion = nroLicitacion;
    }

    public String getClaseDocumento() {
        return claseDocumento;
    }

    public void setClaseDocumento(String claseDocumento) {
        this.claseDocumento = claseDocumento;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaCierreRecepcionOferta() {
        return fechaCierreRecepcionOferta;
    }

    public void setFechaCierreRecepcionOferta(String fechaCierreRecepcionOferta) {
        this.fechaCierreRecepcionOferta = fechaCierreRecepcionOferta;
    }

    public String getEstadoLicitacion() {
        return estadoLicitacion;
    }

    public void setEstadoLicitacion(String estadoLicitacion) {
        this.estadoLicitacion = estadoLicitacion;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getFechaUltimaRenegociacionString() {
        return fechaUltimaRenegociacionString;
    }

    public void setFechaUltimaRenegociacionString(String fechaUltimaRenegociacionString) {
        this.fechaUltimaRenegociacionString = fechaUltimaRenegociacionString;
    }

    public String getFechaInicioRecepcionOferta() {
        return fechaInicioRecepcionOferta;
    }

    public void setFechaInicioRecepcionOferta(String fechaInicioRecepcionOferta) {
        this.fechaInicioRecepcionOferta = fechaInicioRecepcionOferta;
    }

    @Override
    public String toString() {
        return "LicitacionResponse{" +
                "idLicitacion=" + idLicitacion +
                ", nroLicitacion='" + nroLicitacion + '\'' +
                ", claseDocumento='" + claseDocumento + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", fechaInicioRecepcionOferta='" + fechaInicioRecepcionOferta + '\'' +
                ", fechaCierreRecepcionOferta='" + fechaCierreRecepcionOferta + '\'' +
                ", estadoLicitacion='" + estadoLicitacion + '\'' +
                ", fechaPublicacion='" + fechaPublicacion + '\'' +
                ", usuarioCreacion='" + usuarioCreacion + '\'' +
                ", fechaUltimaRenegociacionString='" + fechaUltimaRenegociacionString + '\'' +
                '}';
    }
}
