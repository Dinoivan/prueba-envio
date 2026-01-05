package com.incloud.hcp.jco.proveedor.dto;

import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.dto.ProveedorDto;
import com.incloud.hcp.sap.SapLog;

import java.io.Serializable;
import java.util.List;

public class ProveedorRFCResponseDto implements Serializable {

    private List<SapLog> listasapLog;
    private String nroAcreedor;
    private Proveedor proveedorSap;
    private ProveedorDto proveedorDto;


    public List<SapLog> getListasapLog() {
        return listasapLog;
    }

    public void setListasapLog(List<SapLog> listasapLog) {
        this.listasapLog = listasapLog;
    }

    public String getNroAcreedor() {
        return nroAcreedor;
    }

    public void setNroAcreedor(String nroAcreedor) {
        this.nroAcreedor = nroAcreedor;
    }

    public Proveedor getProveedorSap() {
        return proveedorSap;
    }

    public void setProveedorSap(Proveedor proveedorSap) {
        this.proveedorSap = proveedorSap;
    }

    public ProveedorDto getProveedorDto() {
        return proveedorDto;
    }

    public void setProveedorDto(ProveedorDto proveedorDto) {
        this.proveedorDto = proveedorDto;
    }

    @Override
    public String toString() {
        return "ProveedorRFCResponseDto{" +
                "listasapLog=" + listasapLog +
                ", nroAcreedor='" + nroAcreedor + '\'' +
                ", proveedorSap=" + proveedorSap +
                ", proveedorDto=" + proveedorDto +
                '}';
    }
}
