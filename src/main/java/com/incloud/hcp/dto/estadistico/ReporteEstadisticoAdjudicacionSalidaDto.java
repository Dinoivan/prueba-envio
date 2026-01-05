package com.incloud.hcp.dto.estadistico;

import com.incloud.hcp.domain.Proveedor;

import java.util.List;

public class ReporteEstadisticoAdjudicacionSalidaDto {


    private List<ReporteEstadisticoAdjudicacionDto> data;
    private Proveedor proveedor;

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<ReporteEstadisticoAdjudicacionDto> getData() {
        return data;
    }

    public void setData(List<ReporteEstadisticoAdjudicacionDto> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ReporteEstadisticoAdjudicacionSalidaDto{" +
                "proveedor=" + proveedor +
                ", data=" + data +
                '}';
    }
}
