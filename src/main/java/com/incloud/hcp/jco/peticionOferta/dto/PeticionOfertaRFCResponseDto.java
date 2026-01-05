package com.incloud.hcp.jco.peticionOferta.dto;

import com.incloud.hcp.bean.ProveedorCustom;
import com.incloud.hcp.bean.SolicitudPedido;
import com.incloud.hcp.sap.SapLog;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PeticionOfertaRFCResponseDto implements Serializable {

    private SapLog sapLog;
    private List<SolicitudPedido> listaSolped;
    private List<ProveedorCustom> listaProveedorSeleccionado;

    private Integer idCentro;
    private Integer idAlmacen;
    private Integer idClaseDocumento;
    private Date    fechaEntrega;

    public SapLog getSapLog() {
        return sapLog;
    }

    public void setSapLog(SapLog sapLog) {
        this.sapLog = sapLog;
    }

    public List<SolicitudPedido> getListaSolped() {
        return listaSolped;
    }

    public void setListaSolped(List<SolicitudPedido> listaSolped) {
        this.listaSolped = listaSolped;
    }

    public List<ProveedorCustom> getListaProveedorSeleccionado() {
        return listaProveedorSeleccionado;
    }

    public void setListaProveedorSeleccionado(List<ProveedorCustom> listaProveedorSeleccionado) {
        this.listaProveedorSeleccionado = listaProveedorSeleccionado;
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

    @Override
    public String toString() {
        return "PeticionOfertaRFCResponseDto{" +
                "sapLog=" + sapLog +
                ", listaSolped=" + listaSolped +
                ", listaProveedorSeleccionado=" + listaProveedorSeleccionado +
                ", idCentro=" + idCentro +
                ", idAlmacen=" + idAlmacen +
                ", idClaseDocumento=" + idClaseDocumento +
                ", fechaEntrega=" + fechaEntrega +
                '}';
    }
}
