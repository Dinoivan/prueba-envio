package com.incloud.hcp.enums;

public enum OrdenCompraAprobacionEnum {
    APROBAR(3), RECHAZAR(4);

    private final Integer id;

    OrdenCompraAprobacionEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
