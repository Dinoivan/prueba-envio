package com.incloud.hcp.enums;

public enum OrdenCompraEstadoSapEnum {
    LIBERADA("L"),
    BLOQUEADA("B"),
    ANULADA("A");

    private final String codigo;

    OrdenCompraEstadoSapEnum(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
