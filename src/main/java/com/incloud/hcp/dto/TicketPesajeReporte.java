package com.incloud.hcp.dto;

import com.incloud.hcp.domain.balanza.TicketPesaje;

public class TicketPesajeReporte {
    private TicketPesaje ticketPesaje;
    private GuiaRemisionReportDTO guiaRemision;

    public TicketPesaje getTicketPesaje() {
        return ticketPesaje;
    }

    public void setTicketPesaje(TicketPesaje ticketPesaje) {
        this.ticketPesaje = ticketPesaje;
    }

    public GuiaRemisionReportDTO getGuiaRemision() {
        return guiaRemision;
    }

    public void setGuiaRemision(GuiaRemisionReportDTO guiaRemision) {
        this.guiaRemision = guiaRemision;
    }
}
