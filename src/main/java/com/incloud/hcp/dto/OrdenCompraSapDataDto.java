package com.incloud.hcp.dto;

import com.incloud.hcp.domain.*;

import java.util.List;

public class OrdenCompraSapDataDto {
    private List<OrdenCompra> ordenCompraSapList;
    private List<OrdenCompra> ordenCompraSapListValidacionLiberada;
    private List<OrdenCompraDetalle> ordenCompraDetalleSapList;
    private List<OrdenCompraTextoCabecera> ordenCompraTextoCabeceraSapList;
    private List<OrdenCompraDetalleTexto> ordenCompraDetalleTextoPosicionSapList;
    private List<OrdenCompraDetalleTextoRegistroInfo> ordenCompraDetalleTextoRegistroInfoSapList;
    private List<OrdenCompraDetalleTextoMaterialAmpliado> ordenCompraDetalleTextoMaterialAmpliadoSapList;


    public List<OrdenCompra> getOrdenCompraSapList() {
        return ordenCompraSapList;
    }

    public void setOrdenCompraSapList(List<OrdenCompra> ordenCompraSapList) {
        this.ordenCompraSapList = ordenCompraSapList;
    }

    public List<OrdenCompra> getOrdenCompraSapListValidacionLiberada() {
        return ordenCompraSapListValidacionLiberada;
    }

    public void setOrdenCompraSapListValidacionLiberada(List<OrdenCompra> ordenCompraSapListValidacionLiberada) {
        this.ordenCompraSapListValidacionLiberada = ordenCompraSapListValidacionLiberada;
    }

    public List<OrdenCompraDetalle> getOrdenCompraDetalleSapList() {
        return ordenCompraDetalleSapList;
    }

    public void setOrdenCompraDetalleSapList(List<OrdenCompraDetalle> ordenCompraDetalleSapList) {
        this.ordenCompraDetalleSapList = ordenCompraDetalleSapList;
    }

    public List<OrdenCompraTextoCabecera> getOrdenCompraTextoCabeceraSapList() {
        return ordenCompraTextoCabeceraSapList;
    }

    public void setOrdenCompraTextoCabeceraSapList(List<OrdenCompraTextoCabecera> ordenCompraTextoCabeceraSapList) {
        this.ordenCompraTextoCabeceraSapList = ordenCompraTextoCabeceraSapList;
    }

    public List<OrdenCompraDetalleTexto> getOrdenCompraDetalleTextoPosicionSapList() {
        return ordenCompraDetalleTextoPosicionSapList;
    }

    public void setOrdenCompraDetalleTextoPosicionSapList(List<OrdenCompraDetalleTexto> ordenCompraDetalleTextoPosicionSapList) {
        this.ordenCompraDetalleTextoPosicionSapList = ordenCompraDetalleTextoPosicionSapList;
    }

    public List<OrdenCompraDetalleTextoRegistroInfo> getOrdenCompraDetalleTextoRegistroInfoSapList() {
        return ordenCompraDetalleTextoRegistroInfoSapList;
    }

    public void setOrdenCompraDetalleTextoRegistroInfoSapList(List<OrdenCompraDetalleTextoRegistroInfo> ordenCompraDetalleTextoRegistroInfoSapList) {
        this.ordenCompraDetalleTextoRegistroInfoSapList = ordenCompraDetalleTextoRegistroInfoSapList;
    }

    public List<OrdenCompraDetalleTextoMaterialAmpliado> getOrdenCompraDetalleTextoMaterialAmpliadoSapList() {
        return ordenCompraDetalleTextoMaterialAmpliadoSapList;
    }

    public void setOrdenCompraDetalleTextoMaterialAmpliadoSapList(List<OrdenCompraDetalleTextoMaterialAmpliado> ordenCompraDetalleTextoMaterialAmpliadoSapList) {
        this.ordenCompraDetalleTextoMaterialAmpliadoSapList = ordenCompraDetalleTextoMaterialAmpliadoSapList;
    }
}
