package com.incloud.hcp.jco.tipoCambio.dto;

import java.math.BigDecimal;

public class TipoCambioRFCDTO {

    private String codigoMonedaOrigen;
    private String codigoMonedaDestino;
    private BigDecimal valor;
    private String fecha;

    public String getCodigoMonedaOrigen() {
        return codigoMonedaOrigen;
    }

    public void setCodigoMonedaOrigen(String codigoMonedaOrigen) {
        this.codigoMonedaOrigen = codigoMonedaOrigen;
    }

    public String getCodigoMonedaDestino() {
        return codigoMonedaDestino;
    }

    public void setCodigoMonedaDestino(String codigoMonedaDestino) {
        this.codigoMonedaDestino = codigoMonedaDestino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "TipoCambioRFCDTO{" +
                "codigoMonedaOrigen='" + codigoMonedaOrigen + '\'' +
                ", codigoMonedaDestino='" + codigoMonedaDestino + '\'' +
                ", valor=" + valor +
                '}';
    }
}
