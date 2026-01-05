package com.incloud.hcp.jco.prefactura.dto;

import com.incloud.hcp.sap.SapLog;
import java.io.Serializable;
import java.util.List;


public class PrefacturaRFCResponseDto implements Serializable {

    private String codigoDocumentoSap;
    private String ejercicio;
    private String numeroDocumentoContable;
    private String respuestaBasica;
    private List<SapLog> sapMessageList;


    public String getCodigoDocumentoSap() {
        return codigoDocumentoSap;
    }

    public void setCodigoDocumentoSap(String codigoDocumentoSap) {
        this.codigoDocumentoSap = codigoDocumentoSap;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getNumeroDocumentoContable() {
        return numeroDocumentoContable;
    }

    public void setNumeroDocumentoContable(String numeroDocumentoContable) {
        this.numeroDocumentoContable = numeroDocumentoContable;
    }

    public String getRespuestaBasica() {
        return respuestaBasica;
    }

    public void setRespuestaBasica(String respuestaBasica) {
        this.respuestaBasica = respuestaBasica;
    }

    public List<SapLog> getSapMessageList() {
        return sapMessageList;
    }

    public void setSapMessageList(List<SapLog> sapMessageList) {
        this.sapMessageList = sapMessageList;
    }


    @Override
    public String toString() {
        return "PrefacturaRFCResponseDto{" +
                "codigoDocumentoSap='" + codigoDocumentoSap + '\'' +
                ", ejercicio='" + ejercicio + '\'' +
                ", numeroDocumentoContable='" + numeroDocumentoContable + '\'' +
                ", respuestaBasica='" + respuestaBasica + '\'' +
                ", sapMessageList=" + sapMessageList +
                '}';
    }
}
