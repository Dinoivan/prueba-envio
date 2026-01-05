package com.incloud.hcp.enums;

public enum ComprobanteRetencionEstadoEnum {

    ACEPTADO("","Aceptado"),
    ANULADO("A","Anulado");

    private final String codigo;
    private final String descripcion;

    ComprobanteRetencionEstadoEnum(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
