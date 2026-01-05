package com.incloud.hcp.sap.ordenCompra;

import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraResponseDto;
import com.incloud.hcp.sap.SapLog;

import java.io.Serializable;
import java.util.List;

/**
 * Created by USER on 27/09/2017.
 */
public class OrdenCompraResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private SapLog sapLogPrincipal;
    private List<OrdenCompraResponseDto> listaOrdenCompraResponseDto;
    private List<OrdenCompraBeanSAP> listaOrdenCompraBeanSAP;
    private boolean tieneError;



    public SapLog getSapLogPrincipal() {
        return sapLogPrincipal;
    }

    public void setSapLogPrincipal(SapLog sapLogPrincipal) {
        this.sapLogPrincipal = sapLogPrincipal;
    }

    public List<OrdenCompraBeanSAP> getListaOrdenCompraBeanSAP() {
        return listaOrdenCompraBeanSAP;
    }

    public void setListaOrdenCompraBeanSAP(List<OrdenCompraBeanSAP> listaOrdenCompraBeanSAP) {
        this.listaOrdenCompraBeanSAP = listaOrdenCompraBeanSAP;
    }

    public boolean isTieneError() {
        return tieneError;
    }

    public void setTieneError(boolean tieneError) {
        this.tieneError = tieneError;
    }

    public List<OrdenCompraResponseDto> getListaOrdenCompraResponseDto() {
        return listaOrdenCompraResponseDto;
    }

    public void setListaOrdenCompraResponseDto(List<OrdenCompraResponseDto> listaOrdenCompraResponseDto) {
        this.listaOrdenCompraResponseDto = listaOrdenCompraResponseDto;
    }

    @Override
    public String toString() {
        return "OrdenCompraResponse{" +
                "sapLogPrincipal=" + sapLogPrincipal +
                ", listaOrdenCompraResponseDto=" + listaOrdenCompraResponseDto +
                ", listaOrdenCompraBeanSAP=" + listaOrdenCompraBeanSAP +
                ", tieneError=" + tieneError +
                '}';
    }
}
