package com.incloud.hcp.rest.bean;

import com.incloud.hcp.domain.Cotizacion;
import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.LicitacionProveedor;

import java.io.Serializable;

/**
 * Created by USER on 18/09/2017.
 */
public class LicitacionCotizacionDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    /* Datos que se ingresaran para Grabar en Cotizacion */
    private Cotizacion cotizacion;
    private Licitacion licitacion;
    private LicitacionProveedor licitacionProveedor;

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public Licitacion getLicitacion() {
        return licitacion;
    }

    public void setLicitacion(Licitacion licitacion) {
        this.licitacion = licitacion;
    }

    public LicitacionProveedor getLicitacionProveedor() {
        return licitacionProveedor;
    }

    public void setLicitacionProveedor(LicitacionProveedor licitacionProveedor) {
        this.licitacionProveedor = licitacionProveedor;
    }

    @Override
    public String toString() {
        return "LicitacionCotizacionDTO{" +
                "cotizacion=" + cotizacion +
                ", licitacion=" + licitacion +
                ", licitacionProveedor=" + licitacionProveedor +
                '}';
    }
}
