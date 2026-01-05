package com.incloud.hcp.rest.bean;

import com.incloud.hcp.domain.CotizacionDetalle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 06/09/2017.
 */
public class ProveedorAdjudicacionDTO {

    private Integer idProveedor;
    private String razonSocial;
    private List<CotizacionDetalle> listaCotizacionDetalle = new ArrayList<CotizacionDetalle>();

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public List<CotizacionDetalle> getListaCotizacionDetalle() {
        return listaCotizacionDetalle;
    }

    public void setListaCotizacionDetalle(List<CotizacionDetalle> listaCotizacionDetalle) {
        this.listaCotizacionDetalle = listaCotizacionDetalle;
    }
}
