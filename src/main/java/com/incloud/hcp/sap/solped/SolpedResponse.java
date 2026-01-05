package com.incloud.hcp.sap.solped;

import com.incloud.hcp.bean.SolicitudPedido;
import com.incloud.hcp.sap.SapLog;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by USER on 17/09/2017.
 */
@Component
public class SolpedResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idCentro;
    private Integer idAlmacen;
    private Integer idClaseDocumento;
    private Date fechaEntrega;
    private List<SolicitudPedido> listaSolped;
    private SapLog sapLog;

    public SolpedResponse() {
    }

    public Integer getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(Integer idCentro) {
        this.idCentro = idCentro;
    }

    public Integer getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(Integer idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public Integer getIdClaseDocumento() {
        return idClaseDocumento;
    }

    public void setIdClaseDocumento(Integer idClaseDocumento) {
        this.idClaseDocumento = idClaseDocumento;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public List<SolicitudPedido> getListaSolped() {
        return listaSolped;
    }

    public void setListaSolped(List<SolicitudPedido> listaSolped) {
        this.listaSolped = listaSolped;
    }

    public SapLog getSapLog() {
        return sapLog;
    }

    public void setSapLog(SapLog sapLog) {
        this.sapLog = sapLog;
    }

}
