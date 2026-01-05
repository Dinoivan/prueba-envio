package com.incloud.hcp.enums;

public enum EstadoProveedorEnum {

    REGISTRADO("REG"),
    APROBADO_DATA_MAESTRA("ADM"),
    RECHAZADO_DATA_MAESTRA("RDM"),
    HOMOLOGADO("HOM"),
    MIGRADO_DE_SAP("MIG");

    String codigo;
    EstadoProveedorEnum(String codigo){
        this.codigo = codigo;
    }

    public String getCodigo(){
        return this.codigo;
    }
}
