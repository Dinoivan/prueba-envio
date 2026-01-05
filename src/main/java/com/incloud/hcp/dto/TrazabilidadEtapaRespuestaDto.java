package com.incloud.hcp.dto;

import java.util.List;

public class TrazabilidadEtapaRespuestaDto {

    private String icon;
    private String description;
    private List<TrazabilidadFechaEtapaRespuestaDto> etapas;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TrazabilidadFechaEtapaRespuestaDto> getEtapas() {
        return etapas;
    }

    public void setEtapas(List<TrazabilidadFechaEtapaRespuestaDto> etapas) {
        this.etapas = etapas;
    }

    @Override
    public String toString() {
        return "TrazabilidadEtapaRespuestaDto{" +
                "icon='" + icon + '\'' +
                ", description='" + description + '\'' +
                ", etapas=" + etapas +
                '}';
    }
}
