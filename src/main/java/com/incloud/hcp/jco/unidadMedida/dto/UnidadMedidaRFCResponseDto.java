package com.incloud.hcp.jco.unidadMedida.dto;

import com.incloud.hcp.sap.SapLog;

import java.io.Serializable;
import java.util.List;

public class UnidadMedidaRFCResponseDto implements Serializable {

    private SapLog sapLog;

    private List<UnidadMedidaRFCDTO> listaUnidadMedida;


    public SapLog getSapLog() {
        return sapLog;
    }

    public void setSapLog(SapLog sapLog) {
        this.sapLog = sapLog;
    }

    public List<UnidadMedidaRFCDTO> getListaUnidadMedida() {
        return listaUnidadMedida;
    }

    public void setListaUnidadMedida(List<UnidadMedidaRFCDTO> listaUnidadMedida) {
        this.listaUnidadMedida = listaUnidadMedida;
    }

    @Override
    public String toString() {
        return "UnidadMedidaRFCResponseDto{" +
                "sapLog=" + sapLog +
                ", listaUnidadMedida=" + listaUnidadMedida +
                '}';
    }
}
