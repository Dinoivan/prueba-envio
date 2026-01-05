package com.incloud.hcp.dto;

/**
 * Created by MARCELO on 21/11/2017.
 */
public class TipoLicitacionDto {

    private Integer idTipoLicitacion;
    private String descripcion;
    private Integer nivel;
    private Integer idPadre;
    private String descripcionPadre;

    public TipoLicitacionDto() {
    }

    public TipoLicitacionDto(Integer idTipoLicitacion, String descripcion, Integer nivel) {
        this.idTipoLicitacion = idTipoLicitacion;
        this.descripcion = descripcion;
        this.nivel = nivel;
    }

    public Integer getIdTipoLicitacion() {
        return idTipoLicitacion;
    }

    public void setIdTipoLicitacion(Integer idTipoLicitacion) {
        this.idTipoLicitacion = idTipoLicitacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Integer idPadre) {
        this.idPadre = idPadre;
    }

    public String getDescripcionPadre() {
        return descripcionPadre;
    }

    public void setDescripcionPadre(String descripcionPadre) {
        this.descripcionPadre = descripcionPadre;
    }
}
