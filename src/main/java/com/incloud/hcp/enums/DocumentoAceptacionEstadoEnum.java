package com.incloud.hcp.enums;

public enum DocumentoAceptacionEstadoEnum {
    ACTIVO(1),
    TRANSITO(2),
    PREFACTURADO(3),
    ANULACION(4),
    ANULADO(5),
    REGISTRADO(6),
    BLOQUEADO(7);

    private final int id;

    DocumentoAceptacionEstadoEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}