package com.incloud.hcp.enums;

public enum ComprobanteTipoEnum {

    FACTURA("01","Factura"),
    RETENCION("20","Retencion");

    private final String codigo;
    private final String descripcion;

    ComprobanteTipoEnum(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
