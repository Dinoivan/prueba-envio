package com.incloud.hcp.sap.homologacion;

import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.sap.SapLog;

import java.io.Serializable;

/**
 * Created by USER on 27/09/2017.
 */
public class HomologacionBeanSAP implements Serializable {

    private static final long serialVersionUID = 1L;

    private SapLog sapLogProveedor;
    private Proveedor proveedorSAP;

    public SapLog getSapLogProveedor() {
        return sapLogProveedor;
    }

    public void setSapLogProveedor(SapLog sapLogProveedor) {
        this.sapLogProveedor = sapLogProveedor;
    }

    public Proveedor getProveedorSAP() {
        return proveedorSAP;
    }

    public void setProveedorSAP(Proveedor proveedorSAP) {
        this.proveedorSAP = proveedorSAP;
    }
}
