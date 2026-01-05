package com.incloud.hcp.enums;

/**
 * Created by Administrador on 14/09/2017.
 */
public enum EstadoBlackListEnum {

    GENERADA("GE", "GENERADA"),
    PENDIENTE_APROBACION("PE", "PENDIENTE DE APROBACION"),
    APROBADA("AP", "APROBADA"),
    RECHAZADA("RE", "RECHAZADA"),
    RECHAZADA_ADMIN("RA", "RECHAZADA POR ADMINISTRADOR");

    String codigo;
    String texto;
    EstadoBlackListEnum(String codigo, String texto){
        this.codigo = codigo;
        this.texto = texto;
    }

    public String getCodigo(){
        return this.codigo;
    }

    public String getTexto() {
        return texto;
    }
}
