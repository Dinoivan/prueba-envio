package com.incloud.hcp.rest.bean;

import com.incloud.hcp.domain.*;

import java.util.List;

/**
 * Created by USER on 18/09/2017.
 */
public class CotizacionGrabarDTO {

    /* Datos que se ingresaran para Grabar en Cotizacion */
    private Integer idLicitacionGrupoCondicion;
    private Cotizacion cotizacion;
    private List<CotizacionDetalle> listaCotizacionDetalle;
    private List<CotizacionCampoRespuesta> listaCotizacionCondicion;
    private List<CotizacionAdjunto> listaCotizacionAdjunto;
    private List<LicitacionProveedorPregunta> licitacionProveedorPregunta;

    /* Datos que devolvera luego de Grabar en Cuadro Comparativo */
    private Cotizacion cotizacionResult;


    public Integer getIdLicitacionGrupoCondicion() {
        return idLicitacionGrupoCondicion;
    }

    public void setIdLicitacionGrupoCondicion(Integer idLicitacionGrupoCondicion) {
        this.idLicitacionGrupoCondicion = idLicitacionGrupoCondicion;
    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public List<CotizacionDetalle> getListaCotizacionDetalle() {
        return listaCotizacionDetalle;
    }

    public void setListaCotizacionDetalle(List<CotizacionDetalle> listaCotizacionDetalle) {
        this.listaCotizacionDetalle = listaCotizacionDetalle;
    }

    public List<CotizacionCampoRespuesta> getListaCotizacionCondicion() {
        return listaCotizacionCondicion;
    }

    public void setListaCotizacionCondicion(List<CotizacionCampoRespuesta> listaCotizacionCondicion) {
        this.listaCotizacionCondicion = listaCotizacionCondicion;
    }

    public Cotizacion getCotizacionResult() {
        return cotizacionResult;
    }

    public void setCotizacionResult(Cotizacion cotizacionResult) {
        this.cotizacionResult = cotizacionResult;
    }

    public List<CotizacionAdjunto> getListaCotizacionAdjunto() {
        return listaCotizacionAdjunto;
    }

    public void setListaCotizacionAdjunto(List<CotizacionAdjunto> listaCotizacionAdjunto) {
        this.listaCotizacionAdjunto = listaCotizacionAdjunto;
    }

    public List<LicitacionProveedorPregunta> getLicitacionProveedorPregunta() {
        return licitacionProveedorPregunta;
    }

    public void setLicitacionProveedorPregunta(List<LicitacionProveedorPregunta> licitacionProveedorPregunta) {
        this.licitacionProveedorPregunta = licitacionProveedorPregunta;
    }

    @Override
    public String toString() {
        return "CotizacionGrabarDTO{" +
                "idLicitacionGrupoCondicion=" + idLicitacionGrupoCondicion +
                ", cotizacion=" + cotizacion +
                ", listaCotizacionDetalle=" + listaCotizacionDetalle +
                ", listaCotizacionCondicion=" + listaCotizacionCondicion +
                ", listaCotizacionAdjunto=" + listaCotizacionAdjunto +
                ", licitacionProveedorPregunta=" + licitacionProveedorPregunta +
                ", cotizacionResult=" + cotizacionResult +
                '}';
    }
}
