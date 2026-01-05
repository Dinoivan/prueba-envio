package com.incloud.hcp.dto.estadistico;

import com.incloud.hcp.domain.Proveedor;

import java.util.List;

public class ReporteEstadisticoParticipacionSalidaDto {

    private List<ReporteEstadisticoParticipacionDto> data;
    private Proveedor proveedor;

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<ReporteEstadisticoParticipacionDto> getData() {
        return data;
    }

    public void setData(List<ReporteEstadisticoParticipacionDto> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ReporteEstadisticoParticipacionSalidaDto{" +
                "proveedor=" + proveedor +
                ", data=" + data +
                '}';
    }
}
