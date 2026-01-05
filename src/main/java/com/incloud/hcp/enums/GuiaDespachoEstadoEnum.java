package com.incloud.hcp.enums;

public enum GuiaDespachoEstadoEnum {
    REGISTRADA(1),
    ANULADA(2),
    DESCARTADA(3),
    RECHAZADA(4),
    INGRESADA(5);

    private final Integer id;

    GuiaDespachoEstadoEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
