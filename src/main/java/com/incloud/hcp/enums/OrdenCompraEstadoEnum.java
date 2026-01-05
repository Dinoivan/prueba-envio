package com.incloud.hcp.enums;

public enum  OrdenCompraEstadoEnum {
    ACTIVA(1), VISUALIZADA(2), APROBADA(3), RECHAZADA(4), ANULADA(5);

    private final Integer id;

    OrdenCompraEstadoEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
