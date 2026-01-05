package com.incloud.hcp.dto;

import com.incloud.hcp.domain.OrdenCompra;
import com.incloud.hcp.domain.almacen.OrdenDespacho;

import java.util.List;

public class OrdenDespachoRespuestaDto {
    private OrdenDespacho ordenDespacho;
    private List<String> mensajes;

    public OrdenDespachoRespuestaDto() {
    }

    public OrdenDespachoRespuestaDto(OrdenDespacho ordenDespacho, List<String> mensajes) {
        this.ordenDespacho = ordenDespacho;
        this.mensajes = mensajes;
    }

    public OrdenDespacho getOrdenDespacho() {
        return ordenDespacho;
    }

    public void setOrdenDespacho(OrdenDespacho ordenDespacho) {
        this.ordenDespacho = ordenDespacho;
    }

    public List<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String> mensajes) {
        this.mensajes = mensajes;
    }
}
