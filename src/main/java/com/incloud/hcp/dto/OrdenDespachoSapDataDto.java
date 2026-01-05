package com.incloud.hcp.dto;

import com.incloud.hcp.domain.almacen.*;

import java.util.List;

public class OrdenDespachoSapDataDto {
    private List<OrdenDespacho> ordenDespachoSapList;
    private List<OrdenDespacho> ordenDespachoSapListValidacionLiberada;
    private List<OrdenDespachoDetalle> ordenDespachoDetalleSapList;
    private List<OrdenDespachoTextoCabecera> ordenDespachoTextoCabeceraSapList;
    private List<OrdenDespachoDetalleTexto> ordenDespachoDetalleTextoPosicionSapList;
    private List<OrdenDespachoDetalleTextoRegistroInfo> ordenDespachoDetalleTextoRegistroInfoSapList;
    private List<OrdenDespachoDetalleTextoMaterialAmpliado> ordenDespachoDetalleTextoMaterialAmpliadoSapList;


    public List<OrdenDespacho> getOrdenDespachoSapList() {
        return ordenDespachoSapList;
    }

    public void setOrdenDespachoSapList(List<OrdenDespacho> ordenDespachoSapList) {
        this.ordenDespachoSapList = ordenDespachoSapList;
    }

    public List<OrdenDespacho> getOrdenDespachoSapListValidacionLiberada() {
        return ordenDespachoSapListValidacionLiberada;
    }

    public void setOrdenDespachoSapListValidacionLiberada(List<OrdenDespacho> ordenDespachoSapListValidacionLiberada) {
        this.ordenDespachoSapListValidacionLiberada = ordenDespachoSapListValidacionLiberada;
    }

    public List<OrdenDespachoDetalle> getOrdenDespachoDetalleSapList() {
        return ordenDespachoDetalleSapList;
    }

    public void setOrdenDespachoDetalleSapList(List<OrdenDespachoDetalle> ordenDespachoDetalleSapList) {
        this.ordenDespachoDetalleSapList = ordenDespachoDetalleSapList;
    }

    public List<OrdenDespachoTextoCabecera> getOrdenDespachoTextoCabeceraSapList() {
        return ordenDespachoTextoCabeceraSapList;
    }

    public void setOrdenDespachoTextoCabeceraSapList(List<OrdenDespachoTextoCabecera> ordenDespachoTextoCabeceraSapList) {
        this.ordenDespachoTextoCabeceraSapList = ordenDespachoTextoCabeceraSapList;
    }

    public List<OrdenDespachoDetalleTexto> getOrdenDespachoDetalleTextoPosicionSapList() {
        return ordenDespachoDetalleTextoPosicionSapList;
    }

    public void setOrdenDespachoDetalleTextoPosicionSapList(List<OrdenDespachoDetalleTexto> ordenDespachoDetalleTextoPosicionSapList) {
        this.ordenDespachoDetalleTextoPosicionSapList = ordenDespachoDetalleTextoPosicionSapList;
    }

    public List<OrdenDespachoDetalleTextoRegistroInfo> getOrdenDespachoDetalleTextoRegistroInfoSapList() {
        return ordenDespachoDetalleTextoRegistroInfoSapList;
    }

    public void setOrdenDespachoDetalleTextoRegistroInfoSapList(List<OrdenDespachoDetalleTextoRegistroInfo> ordenDespachoDetalleTextoRegistroInfoSapList) {
        this.ordenDespachoDetalleTextoRegistroInfoSapList = ordenDespachoDetalleTextoRegistroInfoSapList;
    }

    public List<OrdenDespachoDetalleTextoMaterialAmpliado> getOrdenDespachoDetalleTextoMaterialAmpliadoSapList() {
        return ordenDespachoDetalleTextoMaterialAmpliadoSapList;
    }

    public void setOrdenDespachoDetalleTextoMaterialAmpliadoSapList(List<OrdenDespachoDetalleTextoMaterialAmpliado> ordenDespachoDetalleTextoMaterialAmpliadoSapList) {
        this.ordenDespachoDetalleTextoMaterialAmpliadoSapList = ordenDespachoDetalleTextoMaterialAmpliadoSapList;
    }
}
