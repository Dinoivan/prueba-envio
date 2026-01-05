package com.incloud.hcp.pdf.bean;

public class FieldConformidadServicioPdfDTO {
    private String nroItem;
    private String nroOrdenServicio;
    private String nroItemOrdenServicio;
    private String descripcionServicio;
    private String cantidad;
    private String unidad;
    private String valorRecibido;

    public FieldConformidadServicioPdfDTO() {
    }

    public FieldConformidadServicioPdfDTO(String nroItem, String nroOrdenServicio, String nroItemOrdenServicio, String descripcionServicio, String cantidad, String unidad, String valorRecibido) {
        this.nroItem = nroItem;
        this.nroOrdenServicio = nroOrdenServicio;
        this.nroItemOrdenServicio = nroItemOrdenServicio;
        this.descripcionServicio = descripcionServicio;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.valorRecibido = valorRecibido;
    }

    public String getNroItem() {
        return nroItem;
    }

    public void setNroItem(String nroItem) {
        this.nroItem = nroItem;
    }

    public String getNroOrdenServicio() {
        return nroOrdenServicio;
    }

    public void setNroOrdenServicio(String nroOrdenServicio) {
        this.nroOrdenServicio = nroOrdenServicio;
    }

    public String getNroItemOrdenServicio() {
        return nroItemOrdenServicio;
    }

    public void setNroItemOrdenServicio(String nroItemOrdenServicio) {
        this.nroItemOrdenServicio = nroItemOrdenServicio;
    }

    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getValorRecibido() {
        return valorRecibido;
    }

    public void setValorRecibido(String valorRecibido) {
        this.valorRecibido = valorRecibido;
    }
}
