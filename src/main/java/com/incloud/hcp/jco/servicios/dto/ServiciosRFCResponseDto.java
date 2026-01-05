package com.incloud.hcp.jco.servicios.dto;

import com.incloud.hcp.domain.TempBienServicio;
import com.incloud.hcp.sap.SapLog;

import java.io.Serializable;
import java.util.List;

public class ServiciosRFCResponseDto implements Serializable {

    private SapLog sapLog;
    private Integer contador;
    private List<TempBienServicio> listaBienServicio;


    public SapLog getSapLog() {
        return sapLog;
    }

    public void setSapLog(SapLog sapLog) {
        this.sapLog = sapLog;
    }

    public List<TempBienServicio> getListaBienServicio() {
        return listaBienServicio;
    }

    public void setListaBienServicio(List<TempBienServicio> listaBienServicio) {
        this.listaBienServicio = listaBienServicio;
    }

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }

    @Override
    public String toString() {
        return "ServiciosRFCResponseDto{" +
                "sapLog=" + sapLog +
                ", contador=" + contador +
                ", listaBienServicio=" + listaBienServicio +
                '}';
    }
}
