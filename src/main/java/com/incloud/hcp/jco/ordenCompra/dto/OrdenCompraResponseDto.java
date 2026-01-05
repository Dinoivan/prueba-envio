package com.incloud.hcp.jco.ordenCompra.dto;

import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.sap.SapLog;

import java.io.Serializable;
import java.util.List;

public class OrdenCompraResponseDto implements Serializable {

    private boolean exito = true;
    private List<SapLog> sapLogList;
    private String numeroOrdenCompra;
    private String codigoAcreedorSap;
    private Proveedor proveedorSAP;

    public List<SapLog> getSapLogList() {
        return sapLogList;
    }

    public void setSapLogList(List<SapLog> sapLogList) {
        this.sapLogList = sapLogList;
    }

    public String getNumeroOrdenCompra() {
        return numeroOrdenCompra;
    }

    public void setNumeroOrdenCompra(String numeroOrdenCompra) {
        this.numeroOrdenCompra = numeroOrdenCompra;
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

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    @Override
    public String toString() {
        return "OrdenCompraResponseDto{" +
                "exito=" + exito +
                ", sapLogList=" + sapLogList +
                ", numeroOrdenCompra='" + numeroOrdenCompra + '\'' +
                ", codigoAcreedorSap='" + codigoAcreedorSap + '\'' +
                ", proveedorSAP=" + proveedorSAP +
                '}';
    }


}
