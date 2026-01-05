package com.incloud.hcp.enums;

public enum DocumentoAceptacionTipoEnum {
    ENTRADA_MERCADERIA(1),
    HOJA_ENTRADA_SERVICIO(2);

    private final int id;

    DocumentoAceptacionTipoEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
