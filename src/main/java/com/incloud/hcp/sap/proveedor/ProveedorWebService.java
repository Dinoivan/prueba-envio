package com.incloud.hcp.sap.proveedor;

import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.sap.SapLog;

import java.util.List;

/**
 * Created by USER on 27/09/2017.
 */
public interface ProveedorWebService {

    ProveedorResponse grabarProveedorSAP(List<Proveedor> listaProveedorPotencial);

    SapLog bloquearProveedorSAP(Proveedor proveedor);
}
