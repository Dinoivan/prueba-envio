package com.incloud.hcp.service;

import com.incloud.hcp.domain.balanza.TipoProducto;

import java.util.List;

public interface TipoProductoService {
    List<TipoProducto> getAllTipoProducto();
    TipoProducto cambiarEstado(Integer tipoProductoId) throws Exception;
    TipoProducto eliminarTipo(Integer tipoProductoId) throws Exception;


}
