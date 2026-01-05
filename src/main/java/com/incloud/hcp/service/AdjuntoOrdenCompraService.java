package com.incloud.hcp.service;

import com.incloud.hcp.domain.AdjuntoOrdenCompra;

/**
 * Created by Administrador on 25/09/2017.
 */
public interface AdjuntoOrdenCompraService {

    AdjuntoOrdenCompra save(AdjuntoOrdenCompra adjuntoOrdenCompra);

   // List<AdjuntoOrdenCompra> getListCatalogoByIdProveedor(Integer idProveedor);

    /*List<AdjuntoOrdenCompra> getListCatalogoDtoByIdProveedor(Integer idProveedor);*/

    void deleteCatalogoByIdProveedorCatalogoById(Integer idProveedor, String archivoId);
}
