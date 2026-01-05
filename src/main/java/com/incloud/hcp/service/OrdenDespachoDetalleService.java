package com.incloud.hcp.service;

import com.incloud.hcp.domain.almacen.OrdenDespachoDetalle;
import com.incloud.hcp.dto.OrdenDespachoDetalleDto;

import java.util.List;

public interface OrdenDespachoDetalleService {

   List<OrdenDespachoDetalle> getOrdenDespachoDetalleListByIdOcLiberada(Integer idOrdenDespacho, String ruc);

   List<OrdenDespachoDetalle> getOCDetalleListByIdOcLiberada(Integer idOrdenDespacho);

   List<OrdenDespachoDetalleDto> getOrdenDespachoDetalleListByOcLiberada(String numeroOrdenCompra, String ruc);

   List<OrdenDespachoDetalle> getOrdenDespachoDetalleListByIdOc(Integer idOrdenDespacho);

}
