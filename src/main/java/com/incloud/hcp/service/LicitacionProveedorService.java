package com.incloud.hcp.service;

import com.incloud.hcp.domain.LicitacionProveedorRenegociacion;

public interface LicitacionProveedorService {

    LicitacionProveedorRenegociacion validarNuevaRenegociacion(Integer idLicitacion, Integer idProveedor);
}
