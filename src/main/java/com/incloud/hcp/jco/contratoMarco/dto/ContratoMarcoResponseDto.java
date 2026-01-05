package com.incloud.hcp.jco.contratoMarco.dto;

import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.sap.SapLog;

import java.io.Serializable;
import java.util.List;

public class ContratoMarcoResponseDto implements Serializable {

    private boolean exito = true;
    private List<SapLog> sapLogList;
    private String numeroContratoMarco;
    private String codigoAcreedorSap;
    private Proveedor proveedorSAP;


    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public List<SapLog> getSapLogList() {
        return sapLogList;
    }

    public void setSapLogList(List<SapLog> sapLogList) {
        this.sapLogList = sapLogList;
    }

    public String getNumeroContratoMarco() {
        return numeroContratoMarco;
    }

    public void setNumeroContratoMarco(String numeroContratoMarco) {
        this.numeroContratoMarco = numeroContratoMarco;
    }

    public String getCodigoAcreedorSap() {
        return codigoAcreedorSap;
    }

    public void setCodigoAcreedorSap(String codigoAcreedorSap) {
        this.codigoAcreedorSap = codigoAcreedorSap;
    }

    public Proveedor getProveedorSAP() {
        return proveedorSAP;
    }

    public void setProveedorSAP(Proveedor proveedorSAP) {
        this.proveedorSAP = proveedorSAP;
    }

    @Override
    public String toString() {
        return "ContratoMarcoResponseDto{" +
                "exito=" + exito +
                ", sapLogList=" + sapLogList +
                ", numeroContratoMarco='" + numeroContratoMarco + '\'' +
                ", codigoAcreedorSap='" + codigoAcreedorSap + '\'' +
                ", proveedorSAP=" + proveedorSAP +
                '}';
    }
}
