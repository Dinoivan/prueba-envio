package com.incloud.hcp.service;

import com.incloud.hcp.domain.ProveedorCatalogo;
import com.incloud.hcp.dto.ProveedorCatalogoDto;

import java.util.List;

/**
 * Created by Administrador on 25/09/2017.
 */
public interface ProveedorCatalogoService {

    ProveedorCatalogo save(ProveedorCatalogo proveedorCatalogo);

    List<ProveedorCatalogo> getListCatalogoByIdProveedor(Integer idProveedor);

    List<ProveedorCatalogoDto> getListCatalogoDtoByIdProveedor(Integer idProveedor);

    void deleteCatalogoByIdProveedorCatalogoById(Integer idProveedor, String archivoId);
}
