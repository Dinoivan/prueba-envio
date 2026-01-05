package com.incloud.hcp.sap.proveedor;

import com.incloud.hcp.sap.SapLog;

import java.io.Serializable;
import java.util.List;

/**
 * Created by USER on 27/09/2017.
 */
public class ProveedorResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private SapLog sapLog;
    private List<ProveedorBeanSAP> listaProveedorSAPResult;
    private boolean tieneError;

    public ProveedorResponse() {
    }

    public SapLog getSapLog() {
        return sapLog;
    }

    public void setSapLog(SapLog sapLog) {
        this.sapLog = sapLog;
    }

    public List<ProveedorBeanSAP> getListaProveedorSAPResult() {
        return listaProveedorSAPResult;
    }

    public void setListaProveedorSAPResult(List<ProveedorBeanSAP> listaProveedorSAPResult) {
        this.listaProveedorSAPResult = listaProveedorSAPResult;
    }

    public boolean isTieneError() {
        return tieneError;
    }

    public void setTieneError(boolean tieneError) {
        this.tieneError = tieneError;
    }
}
