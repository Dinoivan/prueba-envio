package com.incloud.hcp.service;

import com.incloud.hcp.domain.balanza.GuiaRemisionDetalle;

import java.util.List;

public interface GuiaRemisionDetalleService {
    List<GuiaRemisionDetalle> getAllGuiaRemisionDetalle();

    GuiaRemisionDetalle save(GuiaRemisionDetalle guiaRemisionDetalle);

    List<GuiaRemisionDetalle> getGuiaRemisionDetalleByTicket(String ticketPesajeId);
}
