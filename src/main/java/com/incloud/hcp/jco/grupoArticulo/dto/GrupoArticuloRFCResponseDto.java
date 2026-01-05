package com.incloud.hcp.jco.grupoArticulo.dto;

import com.incloud.hcp.domain.TempRubroBien;
import com.incloud.hcp.sap.SapLog;

import java.io.Serializable;
import java.util.List;

public class GrupoArticuloRFCResponseDto implements Serializable {

    private SapLog sapLog;

    private List<TempRubroBien> listaGrupoArticulo;


    public SapLog getSapLog() {
        return sapLog;
    }

    public void setSapLog(SapLog sapLog) {
        this.sapLog = sapLog;
    }

    public List<TempRubroBien> getListaGrupoArticulo() {
        return listaGrupoArticulo;
    }

    public void setListaGrupoArticulo(List<TempRubroBien> listaGrupoArticulo) {
        this.listaGrupoArticulo = listaGrupoArticulo;
    }

    @Override
    public String toString() {
        return "GrupoArticuloRFCResponseDto{" +
                "sapLog=" + sapLog +
                ", listaGrupoArticulo=" + listaGrupoArticulo +
                '}';
    }
}
