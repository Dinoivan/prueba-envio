package com.incloud.hcp.enums;

public enum OrdenCompraTipoEnum {
    MATERIAL(1,"Material"),
    SERVICIO(2,"Servicio"),
    CONTRATO_MARCO(3,"Contrato MARCO");

    private final int id;
    private final String descripcion;

    OrdenCompraTipoEnum(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
