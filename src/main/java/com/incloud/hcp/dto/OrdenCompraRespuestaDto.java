package com.incloud.hcp.dto;

import com.incloud.hcp.domain.OrdenCompra;

import java.util.List;

public class OrdenCompraRespuestaDto {
    private OrdenCompra ordenCompra;
    private List<String> mensajes;

    public OrdenCompraRespuestaDto() {
    }

    public OrdenCompraRespuestaDto(OrdenCompra ordenCompra, List<String> mensajes) {
        this.ordenCompra = ordenCompra;
        this.mensajes = mensajes;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public List<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String> mensajes) {
        this.mensajes = mensajes;
    }
}
