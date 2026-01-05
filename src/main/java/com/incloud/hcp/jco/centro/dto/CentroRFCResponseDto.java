package com.incloud.hcp.jco.centro.dto;



import com.incloud.hcp.sap.SapLog;

import java.io.Serializable;
import java.util.List;

public class CentroRFCResponseDto implements Serializable {

    private SapLog sapLog;
    private Integer contador;
    private List<CentroRFCDto> listaCentro;


    public SapLog getSapLog() {
        return sapLog;
    }

    public void setSapLog(SapLog sapLog) {
        this.sapLog = sapLog;
    }

    public List<CentroRFCDto> getListaCentro() {
        return listaCentro;
    }

    public void setListaCentro(List<CentroRFCDto> listaCentro) {
        this.listaCentro = listaCentro;
    }

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }

    @Override
    public String toString() {
        return "CentroRFCResponseDto{" +
                "sapLog=" + sapLog +
                ", contador=" + contador +
                ", listaCentro=" + listaCentro +
                '}';
    }
}
