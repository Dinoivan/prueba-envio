package com.incloud.hcp.dto;


import com.incloud.hcp.domain.balanza.GuiaRemision;
import com.incloud.hcp.domain.balanza.GuiaRemisionDetalle;
import com.incloud.hcp.domain.balanza.TicketPesaje;

import java.util.List;

/**
 * Created by Administrador on 30/08/2017.
 */
public class TicketPesajeDto {
    private TicketPesaje ticketPesaje;
    private GuiaRemision guiaRemision;
    private List<GuiaRemisionDetalle> guiaRemisionDetalle;

    public TicketPesaje getTicketPesaje() {
        return ticketPesaje;
    }

    public void setTicketPesaje(TicketPesaje ticketPesaje) {
        this.ticketPesaje = ticketPesaje;
    }

    public GuiaRemision getGuiaRemision() {
        return guiaRemision;
    }

    public void setGuiaRemision(GuiaRemision guiaRemision) {
        this.guiaRemision = guiaRemision;
    }

    public List<GuiaRemisionDetalle> getGuiaRemisionDetalle() {
        return guiaRemisionDetalle;
    }

    public void setGuiaRemisionDetalle(List<GuiaRemisionDetalle> guiaRemisionDetalle) {
        this.guiaRemisionDetalle = guiaRemisionDetalle;
    }
}
