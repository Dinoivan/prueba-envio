package com.incloud.hcp.jco.balanza.DocumentoMaterial.dto;

import com.incloud.hcp.domain.balanza.TicketPesaje;

import java.util.List;

public class TicketPesajeDto {

    private TicketPesaje ticketPesaje;
    private List<DocumentoMaterialResponseDto> responseList;

    public TicketPesajeDto() {
    }

    public TicketPesaje getTicketPesaje() {
        return ticketPesaje;
    }

    public void setTicketPesaje(TicketPesaje ticketPesaje) {
        this.ticketPesaje = ticketPesaje;
    }

    public List<DocumentoMaterialResponseDto> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<DocumentoMaterialResponseDto> responseList) {
        this.responseList = responseList;
    }

    @Override
    public String toString() {
        return "TicketPesajeDto{" +
                "ticketPesaje=" + ticketPesaje +
                ", responseList=" + responseList +
                '}';
    }
}
