package com.incloud.hcp.jco.balanza.DireccionAlternaCliente.services;

import com.incloud.hcp.jco.balanza.DireccionAlternaCliente.dto.DireccionAlternaClienteArdc;
import com.incloud.hcp.jco.balanza.DireccionAlternaCliente.dto.DireccionAlternaClienteResponse;

import java.util.List;

public interface JCODireccionAlternaClienteService {
    List<DireccionAlternaClienteResponse> consultaDirAltCliente(String kna1Kunnr) throws Exception;

    DireccionAlternaClienteResponse crearDireccionAlternarCliente(String kna1Kunnr, DireccionAlternaClienteArdc response) throws Exception;
}
