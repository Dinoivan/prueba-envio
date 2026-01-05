package com.incloud.hcp.jco.balanza.DireccionAlternaProveedor.services;

import com.incloud.hcp.jco.balanza.DireccionAlternaProveedor.dto.DireccionAlternaProveedorResponse;

import java.util.List;

public interface JCODireccionAlternaProveedorService {
    List<DireccionAlternaProveedorResponse> consultaDireccionAlterna(String lifnr) throws Exception;
    DireccionAlternaProveedorResponse crearDireccionAlternaProveedor(String lifnr, DireccionAlternaProveedorResponse direccionAlternaProveedorResponse)throws Exception;
}
