package com.incloud.hcp.enums;

/**
 * Created by Administrador on 14/09/2017.
 */
public enum CodigoTipoSolicitudBlackListEnum {
    REGISTRO_PO("01"),
    RETIRO_PO("02"),
    REGISTRO_NC("03");

    String codigo;
    CodigoTipoSolicitudBlackListEnum(String codigo){
        this.codigo = codigo;
    }

    public String getCodigo(){
        return this.codigo;
    }
    }