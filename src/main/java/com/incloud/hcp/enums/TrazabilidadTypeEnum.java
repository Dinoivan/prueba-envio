package com.incloud.hcp.enums;

public enum TrazabilidadTypeEnum {

    TYPE_01("Type01"),
    TYPE_02("Type02"),
    TYPE_03("Type03"),
    TYPE_04("Type04");

    private final String estado;
    TrazabilidadTypeEnum(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "TrazabilidadTypeEnum{" +
                "estado='" + estado + '\'' +
                '}';
    }
    public String getEstado() {
        return this.estado;
    }

}
