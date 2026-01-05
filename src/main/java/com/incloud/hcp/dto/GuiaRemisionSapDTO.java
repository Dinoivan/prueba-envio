package com.incloud.hcp.dto;

import com.incloud.hcp.domain.balanza.GuiaRemision;
import com.incloud.hcp.domain.balanza.GuiaRemisionDetalle;
import com.incloud.hcp.sap.SapLog;

import java.io.Serializable;
import java.util.List;

public class GuiaRemisionSapDTO implements Serializable {
    private GuiaRemision guiaRemision;
    private List<GuiaRemisionDetalle> guiaRemisionDetalleList;
    public List<SapLog> sapLogList;

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

    public List<SapLog> getSapLogList() {
        return sapLogList;
    }

    public void setSapLogList(List<SapLog> sapLogList) {
        this.sapLogList = sapLogList;
    }

    @Override
    public String toString() {
        return "GuiaRemisionSapDTO{" +
                "guiaRemision=" + guiaRemision +
                ", guiaRemisionDetalleList=" + guiaRemisionDetalleList +
                ", sapLogList=" + sapLogList +
                '}';
    }
}
