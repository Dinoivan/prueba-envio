package com.incloud.hcp.enums;

public enum ConditionEnum {

    IGUAL("="),
    MAYOR(">"),
    MAYOR_IGUAL(">="),
    MENOR("<"),
    MENOR_IGUAL("<="),
    CONTIENE("CONTIENE")
    ;

    private final String estado;
    ConditionEnum(String estado)  {
        this.estado = estado;
    }

    public String getEstado() {
        return this.estado;
    }

}
