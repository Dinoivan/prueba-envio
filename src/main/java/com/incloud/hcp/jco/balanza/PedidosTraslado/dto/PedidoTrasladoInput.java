package com.incloud.hcp.jco.balanza.PedidosTraslado.dto;

import jakarta.validation.constraints.Null;

public class PedidoTrasladoInput {
    @Null
    public String piReswk; // Centro
    public String ekpoMatnr; // Material

    public String flagTraslado;
    public String getPiReswk() {
        return piReswk;
    }

    public void setPiReswk(String piReswk) {
        this.piReswk = piReswk;
    }

    public String getEkpoMatnr() {
        return ekpoMatnr;
    }

    public void setEkpoMatnr(String ekpoMatnr) {
        this.ekpoMatnr = ekpoMatnr;
    }

    public String getFlagTraslado() {
        return flagTraslado;
    }

    public void setFlagTraslado(String flagTraslado) {
        this.flagTraslado = flagTraslado;
    }

    @Override
    public String toString() {
        return "PedidoTrasladoInput{" +
                "piReswk='" + piReswk + '\'' +
                ", ekpoMatnr='" + ekpoMatnr + '\'' +
                ", flagTraslado='" + flagTraslado + '\'' +
                '}';
    }
}
