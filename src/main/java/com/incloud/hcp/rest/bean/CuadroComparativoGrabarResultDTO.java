package com.incloud.hcp.rest.bean;

import com.incloud.hcp.jco.proveedor.dto.ProveedorResponseRFC;
import com.incloud.hcp.sap.ordenCompra.OrdenCompraResponse;

/**
 * Created by USER on 18/09/2017.
 */
public class CuadroComparativoGrabarResultDTO {


    /* Datos que devolvera luego de Grabar en Cuadro Comparativo */
    private Integer idCComparativo;
    private ProveedorResponseRFC proveedorResponse;
    private OrdenCompraResponse ordenCompraResponse;


    public Integer getIdCComparativo() {
        return idCComparativo;
    }

    public void setIdCComparativo(Integer idCComparativo) {
        this.idCComparativo = idCComparativo;
    }

    public ProveedorResponseRFC getProveedorResponse() {
        return proveedorResponse;
    }

    public void setProveedorResponse(ProveedorResponseRFC proveedorResponse) {
        this.proveedorResponse = proveedorResponse;
    }

    public OrdenCompraResponse getOrdenCompraResponse() {
        return ordenCompraResponse;
    }

    public void setOrdenCompraResponse(OrdenCompraResponse ordenCompraResponse) {
        this.ordenCompraResponse = ordenCompraResponse;
    }
}
