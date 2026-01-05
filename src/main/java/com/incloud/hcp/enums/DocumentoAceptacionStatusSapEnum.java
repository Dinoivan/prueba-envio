package com.incloud.hcp.enums;

public enum DocumentoAceptacionStatusSapEnum {

    ACEPTADO("Aceptado"),
    BORRADO("Borrado");

    private final String descripcion;

    DocumentoAceptacionStatusSapEnum(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
