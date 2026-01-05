package com.incloud.hcp.rest.bean;

import com.incloud.hcp.domain.Ccomparativo;
import com.incloud.hcp.domain.CcomparativoAdjudicado;
import com.incloud.hcp.jco.proveedor.dto.ProveedorResponseRFC;
import com.incloud.hcp.sap.ordenCompra.OrdenCompraResponse;

import java.util.List;

/**
 * Created by USER on 18/09/2017.
 */
public class CuadroComparativoGrabarDTO {

    /* Datos que se ingresaran para Grabar en Cuadro Comparativo */
    private Integer idLicitacion;
    private Integer idUsuario;
    private String claseDocumento;
    private List<CcomparativoAdjudicado> listaAdjudicacion;

    /* Datos que devolvera luego de Grabar en Cuadro Comparativo */
    private Ccomparativo cuadroComparativoResult;
    private ProveedorResponseRFC proveedorResponse;
    private OrdenCompraResponse ordenCompraResponse;


    public Integer getIdLicitacion() {
        return idLicitacion;
    }

    public void setIdLicitacion(Integer idLicitacion) {
        this.idLicitacion = idLicitacion;
    }

    public Ccomparativo getCuadroComparativoResult() {
        return cuadroComparativoResult;
    }

    public void setCuadroComparativoResult(Ccomparativo cuadroComparativoResult) {
        this.cuadroComparativoResult = cuadroComparativoResult;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<CcomparativoAdjudicado> getListaAdjudicacion() {
        return listaAdjudicacion;
    }

    public void setListaAdjudicacion(List<CcomparativoAdjudicado> listaAdjudicacion) {
        this.listaAdjudicacion = listaAdjudicacion;
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

    public String getClaseDocumento() {
        return claseDocumento;
    }

    public void setClaseDocumento(String claseDocumento) {
        this.claseDocumento = claseDocumento;
    }
}
