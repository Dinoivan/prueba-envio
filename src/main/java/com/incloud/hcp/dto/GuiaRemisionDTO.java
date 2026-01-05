package com.incloud.hcp.dto;

import com.incloud.hcp.domain.balanza.GuiaRemision;
import com.incloud.hcp.domain.balanza.GuiaRemisionDetalle;

import java.io.Serializable;
import java.util.List;

public class GuiaRemisionDTO implements Serializable {
    private GuiaRemision guiaRemision;
    private List<GuiaRemisionDetalle> guiaRemisionDetalleList;

    public GuiaRemision getGuiaRemision() {
        return guiaRemision;
    }

    public void setGuiaRemision(GuiaRemision guiaRemision) {
        this.guiaRemision = guiaRemision;
    }

    public List<GuiaRemisionDetalle> getGuiaRemisionDetalleList() {
        return guiaRemisionDetalleList;
    }

    public void setGuiaRemisionDetalleList(List<GuiaRemisionDetalle> guiaRemisionDetalleList) {
        this.guiaRemisionDetalleList = guiaRemisionDetalleList;
    }

    @Override
    public String toString() {
        return "GuiaRemisionDTO{" +
                "guiaRemision=" + guiaRemision +
                ", guiaRemisionDetalleList=" + guiaRemisionDetalleList +
                '}';
    }
}
