package com.incloud.hcp.enums;

/**
 * Created by Administrador on 27/10/2017.
 */
public enum EstadoPreRegistroEnum {
    PENDIENTE("PE"),
    APROBADA("AP"),
    RECHAZADA("RE");

    String codigo;
    EstadoPreRegistroEnum(String codigo){
        this.codigo = codigo;
    }

    public String getCodigo(){
        return this.codigo;
    }

   public static EstadoPreRegistroEnum getByCodigo(String codigo){
        for(EstadoPreRegistroEnum item : values()){
            if(item.codigo.equals(codigo))
                return item;
        }
        throw new IllegalArgumentException("El codigo del estado del pre-registro no existe : " + codigo);
    }
}
