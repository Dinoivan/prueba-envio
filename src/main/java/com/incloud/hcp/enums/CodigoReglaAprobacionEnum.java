package com.incloud.hcp.enums;

/**
 * Created by Administrador on 14/09/2017.
 */
public enum CodigoReglaAprobacionEnum {
    JERARQUICA("JE"),
    NINGUNA("NA");

    String codigo;
    CodigoReglaAprobacionEnum(String codigo){
        this.codigo = codigo;
    }

    public String getCodigo(){
        return this.codigo;
    }
}
