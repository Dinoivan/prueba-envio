package com.incloud.hcp.dto;

import java.util.Date;
import java.util.List;

public class TrazabilidadRespuestaDto {

    private Date startTime;
    private List<TrazabilidadEtapaRespuestaDto> etapas;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public List<TrazabilidadEtapaRespuestaDto> getEtapas() {
        return etapas;
    }

    public void setEtapas(List<TrazabilidadEtapaRespuestaDto> etapas) {
        this.etapas = etapas;
    }

    @Override
    public String toString() {
        return "TrazabilidadRespuestaDto{" +
                "startTime=" + startTime +
                ", etapas=" + etapas +
                '}';
    }


}
