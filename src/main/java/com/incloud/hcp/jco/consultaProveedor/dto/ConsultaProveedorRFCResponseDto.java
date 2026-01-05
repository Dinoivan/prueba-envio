package com.incloud.hcp.jco.consultaProveedor.dto;

import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.domain.ProveedorCuentaBancaria;
import com.incloud.hcp.sap.SapLog;

import java.io.Serializable;
import java.util.List;

public class ConsultaProveedorRFCResponseDto implements Serializable {

    private SapLog sapLog;
    private List<Proveedor> datosGenerales;
    private List<ProveedorCuentaBancaria> listaCuentaBancaria;

    public SapLog getSapLog() {
        return sapLog;
    }

    public void setSapLog(SapLog sapLog) {
        this.sapLog = sapLog;
    }

    public List<Proveedor> getDatosGenerales() {
        return datosGenerales;
    }

    public void setDatosGenerales(List<Proveedor> datosGenerales) {
        this.datosGenerales = datosGenerales;
    }

    public List<ProveedorCuentaBancaria> getListaCuentaBancaria() {
        return listaCuentaBancaria;
    }

    public void setListaCuentaBancaria(List<ProveedorCuentaBancaria> listaCuentaBancaria) {
        this.listaCuentaBancaria = listaCuentaBancaria;
    }

    @Override
    public String toString() {
        return "ConsultaProveedorRFCResponseDto{" +
                "sapLog=" + sapLog +
                ", datosGenerales=" + datosGenerales +
                ", listaCuentaBancaria=" + listaCuentaBancaria +
                '}';
    }
}
