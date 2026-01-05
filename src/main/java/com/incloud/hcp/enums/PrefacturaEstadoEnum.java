package com.incloud.hcp.enums;

public enum PrefacturaEstadoEnum {
    ENVIADA(1),
    REGISTRADA(2),
    RECHAZADA(3),
    DESCARTADA(4),
    ANULADA(5),
    REGISTRO_MIRO(6);

    private final Integer id;

    PrefacturaEstadoEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
