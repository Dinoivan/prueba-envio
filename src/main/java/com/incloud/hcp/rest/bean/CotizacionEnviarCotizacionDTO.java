package com.incloud.hcp.rest.bean;

import com.incloud.hcp.domain.Cotizacion;

/**
 * Created by USER on 18/09/2017.
 */
public class CotizacionEnviarCotizacionDTO {

    /* Datos que se ingresaran para Grabar en Cotizacion */
    private Cotizacion cotizacion;
    private Integer idUsuario;

    /* Datos que devolvera luego de Grabar en Cuadro Comparativo */
    private Cotizacion cotizacionResult;


    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Cotizacion getCotizacionResult() {
        return cotizacionResult;
    }

    public void setCotizacionResult(Cotizacion cotizacionResult) {
        this.cotizacionResult = cotizacionResult;
    }
}
