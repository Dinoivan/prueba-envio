package com.incloud.hcp.enums;

/**
 * Created by USER on 17/11/2017.
 */
public enum EstadoLicitacionEnum {

    GENERADA("GE"),
    PENDIENTE_RESPUESTA("PE"),
    EVALUACION("EV"),
    ADJUDICADA("AD"),
    ENVIADO_SAP("ES"),
    ANULADA("AN");

    String codigo;
    EstadoLicitacionEnum(String codigo){
        this.codigo = codigo;
    }

    public String getCodigo(){
        return this.codigo;
    }

}
