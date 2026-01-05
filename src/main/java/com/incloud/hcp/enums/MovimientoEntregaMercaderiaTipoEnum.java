package com.incloud.hcp.enums;

public enum MovimientoEntregaMercaderiaTipoEnum {
    LIBERACION_EM("101"),
    ANULACION_EM("102"),
    LIBERACION_1_PASO("103"),
    ANULACION_1_PASO("104"),
    LIBERACION_2_PASO("105"),
    ANULACION_2_PASO("106");

    private final String codigo;

    MovimientoEntregaMercaderiaTipoEnum(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}