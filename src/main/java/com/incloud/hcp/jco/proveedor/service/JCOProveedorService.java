package com.incloud.hcp.jco.proveedor.service;

import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.jco.proveedor.dto.ProveedorRFCResponseDto;
import com.incloud.hcp.jco.proveedor.dto.ProveedorResponseRFC;

import java.util.List;

public interface JCOProveedorService {

    ProveedorRFCResponseDto grabarProveedor(Integer idProveedor,String usuarioSap) throws Exception;

    ProveedorResponseRFC grabarListaProveedorSAP(List<Proveedor> listaProveedorPotencial,String usuarioSap) throws Exception;

    ProveedorRFCResponseDto grabarUnicoProveedorSAP(Proveedor proveedorPotencial,String usuarioSap) throws Exception;
}


