package com.incloud.hcp.sap.homologacion;

import com.incloud.hcp.domain.Proveedor;

import java.util.List;

/**
 * Created by USER on 27/09/2017.
 */
public interface HomologacionWebService {

    public HomologacionResponse actualizarHomologacionSAP(List<Proveedor> listaProveedorPotencial);
}
