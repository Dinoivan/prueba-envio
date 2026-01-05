package com.incloud.hcp.enums;

public enum OpcionGenericaEnum {

    SI(1,"1",true),
    NO(0,"0",false);

    private final Integer id;
    private final String codigo;
    private final boolean valor;

    OpcionGenericaEnum(Integer id, String codigo, boolean valor) {
        this.id = id;
        this.codigo = codigo;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean getValor() {
        return valor;
    }
}
