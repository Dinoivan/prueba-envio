package com.incloud.hcp.rest.bean;

import com.incloud.hcp.domain.Proveedor;

import java.io.Serializable;

/**
 * Created by USER on 30/11/2017.
 */
public class ProveedorDatosGeneralesDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Proveedor proveedor;
    private Integer licitacionesGanadas = 0;
    private Integer licitacionesPerdidas = 0;
    private Integer licitacionesParticipo = 0;
    private Integer noConformesRegistrados = 0;

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getLicitacionesGanadas() {
        return licitacionesGanadas;
    }

    public void setLicitacionesGanadas(Integer licitacionesGanadas) {
        this.licitacionesGanadas = licitacionesGanadas;
    }

    public Integer getLicitacionesPerdidas() {
        return licitacionesPerdidas;
    }

    public void setLicitacionesPerdidas(Integer licitacionesPerdidas) {
        this.licitacionesPerdidas = licitacionesPerdidas;
    }

    public Integer getLicitacionesParticipo() {
        return licitacionesParticipo;
    }

    public void setLicitacionesParticipo(Integer licitacionesParticipo) {
        this.licitacionesParticipo = licitacionesParticipo;
    }

    public Integer getNoConformesRegistrados() {
        return noConformesRegistrados;
    }

    public void setNoConformesRegistrados(Integer noConformesRegistrados) {
        this.noConformesRegistrados = noConformesRegistrados;
    }
}
