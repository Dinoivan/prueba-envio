package com.incloud.hcp.jco.balanza.PedidosTraslado.service;

import com.incloud.hcp.jco.balanza.PedidosTraslado.dto.PedidoTrasladoInput;
import com.incloud.hcp.jco.balanza.PedidosTraslado.dto.PedidosTrasladoResponse;

import java.util.List;

public interface JCOPedidosTrasladoService {
    List<PedidosTrasladoResponse> consultaPedTraslado(PedidoTrasladoInput pedidoTrasladoInput) throws Exception;
}
