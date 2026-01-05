package com.incloud.hcp.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by USER on 25/09/2017.
 */
public class RespuestaCondicion implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer idLicitacionGrupoCondicion;
    private Integer idCondicionRespuesta;
    private String indPredefinido;
    private String opcion;
    private BigDecimal puntaje;
    private String usuarioCreacion;
    private String usuarioModificacion;
    private Timestamp fechaCreacion;
    private Timestamp fechaModificacion;

    public RespuestaCondicion() {
    }


    public Integer getIdLicitacionGrupoCondicion() {
        return idLicitacionGrupoCondicion;
    }

    public void setIdLicitacionGrupoCondicion(Integer idLicitacionGrupoCondicion) {
        this.idLicitacionGrupoCondicion = idLicitacionGrupoCondicion;
    }

    public Integer getIdCondicionRespuesta() {
        return idCondicionRespuesta;
    }

    public void setIdCondicionRespuesta(Integer idCondicionRespuesta) {
        this.idCondicionRespuesta = idCondicionRespuesta;
    }

    public String getIndPredefinido() {
        return indPredefinido;
    }

    public void setIndPredefinido(String indPredefinido) {
        this.indPredefinido = indPredefinido;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public BigDecimal getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(BigDecimal puntaje) {
        this.puntaje = puntaje;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }


    @Override
    public String toString() {
        return "RespuestaCondicion{" +
                "idLicitacionGrupoCondicion=" + idLicitacionGrupoCondicion +
                ", idCondicionRespuesta=" + idCondicionRespuesta +
                ", indPredefinido='" + indPredefinido + '\'' +
                ", opcion='" + opcion + '\'' +
                ", puntaje=" + puntaje +
                ", usuarioCreacion='" + usuarioCreacion + '\'' +
                ", usuarioModificacion='" + usuarioModificacion + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaModificacion=" + fechaModificacion +
                '}';
    }
}
