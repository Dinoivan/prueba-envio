package com.incloud.hcp.jco.centroAlmacen.dto;


import com.incloud.hcp.domain.balanza.CentroAlmacenBlz;
import com.incloud.hcp.sap.SapLog;

import java.io.Serializable;
import java.util.List;

public class CentroAlmacenRFCResponseDto implements Serializable {

    private SapLog sapLog;
    private Integer contador;
    //private List<TempCentroAlmacen> listaCentroAlmacen;
    //private List<CentroAlmacenBalanzaSap> listaCentroAlmacen;
    private List<CentroAlmacenBlz> listaCentroAlmacen;

    private List<CentroRFCDto> listaCentroAlmacenRFC;

    public SapLog getSapLog() {
        return sapLog;
    }

    public void setSapLog(SapLog sapLog) {
        this.sapLog = sapLog;
    }

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }

    public List<CentroAlmacenBlz> getListaCentroAlmacen() {
        return listaCentroAlmacen;
    }

    public void setListaCentroAlmacen(List<CentroAlmacenBlz> listaCentroAlmacen) {
        this.listaCentroAlmacen = listaCentroAlmacen;
    }

    public List<CentroRFCDto> getListaCentroAlmacenRFC() {
        return listaCentroAlmacenRFC;
    }

    public void setListaCentroAlmacenRFC(List<CentroRFCDto> listaCentroAlmacenRFC) {
        this.listaCentroAlmacenRFC = listaCentroAlmacenRFC;
    }

    @Override
    public String toString() {
        return "CentroAlmacenRFCResponseDto{" +
                "sapLog=" + sapLog +
                ", contador=" + contador +
                ", listaCentroAlmacen=" + listaCentroAlmacen +
                ", listaCentroAlmacenRFC=" + listaCentroAlmacenRFC +
                '}';
    }
}
