package com.incloud.hcp.sap.ordenCompra;

import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.sap.SapLog;

import java.io.Serializable;

/**
 * Created by USER on 27/09/2017.
 */
public class OrdenCompraBeanSAP implements Serializable {

    private static final long serialVersionUID = 1L;

    private String numeroOrdenCompra;
    private Proveedor proveedorSAP;
    private SapLog sapLogOrdenCompra;


    public String getNumeroOrdenCompra() {
        return numeroOrdenCompra;
    }

    public void setNumeroOrdenCompra(String numeroOrdenCompra) {
        this.numeroOrdenCompra = numeroOrdenCompra;
    }

    public Proveedor getProveedorSAP() {
        return proveedorSAP;
    }

    public void setProveedorSAP(Proveedor proveedorSAP) {
        this.proveedorSAP = proveedorSAP;
    }

    public SapLog getSapLogOrdenCompra() {
        return sapLogOrdenCompra;
    }

    public void setSapLogOrdenCompra(SapLog sapLogOrdenCompra) {
        this.sapLogOrdenCompra = sapLogOrdenCompra;
    }
}
