package com.incloud.hcp.dto.estadistico;

import com.incloud.hcp.domain.Licitacion;

import java.util.List;

public class ReporteStatusPeticionOfertaSalidaDto {

    private List<Licitacion> licitacionListTodos;
    private List<Licitacion> licitacionListEnProceso;
    private List<Licitacion> licitacionListAdjudicadas;

    public List<Licitacion> getLicitacionListTodos() {
        return licitacionListTodos;
    }

    public void setLicitacionListTodos(List<Licitacion> licitacionListTodos) {
        this.licitacionListTodos = licitacionListTodos;
    }

    public List<Licitacion> getLicitacionListEnProceso() {
        return licitacionListEnProceso;
    }

    public void setLicitacionListEnProceso(List<Licitacion> licitacionListEnProceso) {
        this.licitacionListEnProceso = licitacionListEnProceso;
    }

    public List<Licitacion> getLicitacionListAdjudicadas() {
        return licitacionListAdjudicadas;
    }

    public void setLicitacionListAdjudicadas(List<Licitacion> licitacionListAdjudicadas) {
        this.licitacionListAdjudicadas = licitacionListAdjudicadas;
    }

    @Override
    public String toString() {
        return "ReporteStatusPeticionOfertaSalidaDto{" +
                "licitacionListTodos=" + licitacionListTodos +
                ", licitacionListEnProceso=" + licitacionListEnProceso +
                ", licitacionListAdjudicadas=" + licitacionListAdjudicadas +
                '}';
    }
}
