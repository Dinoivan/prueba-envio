package com.incloud.hcp.dto;

import com.incloud.hcp.domain.Moneda;
import com.incloud.hcp.domain.TipoLicitacion;

import java.io.Serializable;
import java.math.BigDecimal;

public class GanadorLicitacionSalidaTipoLicitacionDto implements Serializable {

    private TipoLicitacion tipoLicitacion;
    private TipoLicitacion tipoCuestionario;

    private BigDecimal pesoMoneda;
    private BigDecimal pesoCondicion;
    private BigDecimal pesoEvaluacionTecnica;

    private Moneda monedaLicitacion;

    public TipoLicitacion getTipoLicitacion() {
        return tipoLicitacion;
    }

    public void setTipoLicitacion(TipoLicitacion tipoLicitacion) {
        this.tipoLicitacion = tipoLicitacion;
    }

    public TipoLicitacion getTipoCuestionario() {
        return tipoCuestionario;
    }

    public void setTipoCuestionario(TipoLicitacion tipoCuestionario) {
        this.tipoCuestionario = tipoCuestionario;
    }

    public BigDecimal getPesoMoneda() {
        return pesoMoneda;
    }

    public void setPesoMoneda(BigDecimal pesoMoneda) {
        this.pesoMoneda = pesoMoneda;
    }

    public BigDecimal getPesoCondicion() {
        return pesoCondicion;
    }

    public void setPesoCondicion(BigDecimal pesoCondicion) {
        this.pesoCondicion = pesoCondicion;
    }

    public BigDecimal getPesoEvaluacionTecnica() {
        return pesoEvaluacionTecnica;
    }

    public void setPesoEvaluacionTecnica(BigDecimal pesoEvaluacionTecnica) {
        this.pesoEvaluacionTecnica = pesoEvaluacionTecnica;
    }

    public Moneda getMonedaLicitacion() {
        return monedaLicitacion;
    }

    public void setMonedaLicitacion(Moneda monedaLicitacion) {
        this.monedaLicitacion = monedaLicitacion;
    }


    @Override
    public String toString() {
        return "GanadorLicitacionSalidaTipoLicitacionDto{" +
                "tipoLicitacion=" + tipoLicitacion +
                ", tipoCuestionario=" + tipoCuestionario +
                ", pesoMoneda=" + pesoMoneda +
                ", pesoCondicion=" + pesoCondicion +
                ", pesoEvaluacionTecnica=" + pesoEvaluacionTecnica +
                ", monedaLicitacion=" + monedaLicitacion +
                '}';
    }
}
