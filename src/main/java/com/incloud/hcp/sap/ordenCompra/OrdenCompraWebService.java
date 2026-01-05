package com.incloud.hcp.sap.ordenCompra;

import com.incloud.hcp.domain.CcomparativoAdjudicado;

import java.util.List;

/**
 * Created by USER on 29/09/2017.
 */
public interface OrdenCompraWebService {

    public OrdenCompraResponse grabarOrdenCompraSAP(List<CcomparativoAdjudicado> listaAdjudicacion, String claseDocumentoSAP);
}
