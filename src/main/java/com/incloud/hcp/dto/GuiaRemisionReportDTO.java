package com.incloud.hcp.dto;

import com.incloud.hcp.domain.balanza.GuiaRemision;
import com.incloud.hcp.domain.balanza.GuiaRemisionDetalle;

import java.io.Serializable;
import java.util.List;

public class GuiaRemisionReportDTO implements Serializable {
    private List<GuiaRemision> guiaRemision;
    private List<GuiaRemisionDetalle> guiaRemisionDetalleList;

    public void setGuiaRemision(List<GuiaRemision> guiaRemision) {
        this.guiaRemision = guiaRemision;
    }

    public void setGuiaRemisionDetalleList(List<GuiaRemisionDetalle> guiaRemisionDetalleList) {
        this.guiaRemisionDetalleList = guiaRemisionDetalleList;
    }

    public List<GuiaRemision> getGuiaRemision() {
        return guiaRemision;
    }

    public List<GuiaRemisionDetalle> getGuiaRemisionDetalleList() {
        return guiaRemisionDetalleList;
    }

    @Override
    public String toString() {
        return "GuiaRemisionReportDTO{" +
                "guiaRemision=" + guiaRemision +
                ", guiaRemisionDetalleList=" + guiaRemisionDetalleList +
                '}';
    }
}
