package com.incloud.hcp.jco.tipoCambio.dto;

import com.incloud.hcp.sap.SapLog;

import java.io.Serializable;
import java.util.List;

public class TipoCambioRFCResponseDto implements Serializable {

    private SapLog sapLog;

    private List<TipoCambioRFCDTO> listaTasaCambio;


    public SapLog getSapLog() {
        return sapLog;
    }

    public void setSapLog(SapLog sapLog) {
        this.sapLog = sapLog;
    }

    public List<TipoCambioRFCDTO> getListaTasaCambio() {
        return listaTasaCambio;
    }

    public void setListaTasaCambio(List<TipoCambioRFCDTO> listaTasaCambio) {
        this.listaTasaCambio = listaTasaCambio;
    }

    @Override
    public String toString() {
        return "TipoCambioRFCResponseDto{" +
                "sapLog=" + sapLog +
                ", listaTasaCambio=" + listaTasaCambio +
                '}';
    }
}
