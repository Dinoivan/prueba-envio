package com.incloud.hcp.jco.balanza.PedidosVentas.service;

import com.incloud.hcp.jco.balanza.PedidosVentas.dto.PedidosVentasResponse;

import java.util.List;

public interface JCOPedidoVentasService {
    List<PedidosVentasResponse> consultaPedVentas(String vbapMatnr, String piWerks) throws Exception;
}
