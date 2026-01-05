package com.incloud.hcp.service;

import com.incloud.hcp.domain.almacen.GuiaDespachoDetalle;

import java.util.List;

public interface GuiaDespachoDetalleService {
    List<GuiaDespachoDetalle> getAllGuiaDepachoDetalleById(Integer idDespacho);
}
