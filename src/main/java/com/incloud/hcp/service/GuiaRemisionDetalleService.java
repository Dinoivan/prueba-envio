package com.incloud.hcp.service;

import com.incloud.hcp.domain.EstadoProveedor;
import com.incloud.hcp.domain.balanza.GuiaRemision;
import com.incloud.hcp.domain.balanza.GuiaRemisionDetalle;
import com.incloud.hcp.domain.balanza.TicketPesaje;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface GuiaRemisionDetalleService {
    List<GuiaRemisionDetalle> getAllGuiaRemisionDetalle();

    GuiaRemisionDetalle save(GuiaRemisionDetalle guiaRemisionDetalle);

    List<GuiaRemisionDetalle> getGuiaRemisionDetalleByTicket(String ticketPesajeId);
}
