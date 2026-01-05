package com.incloud.hcp.bean;

/**
 * Created by Administrador on 23/08/2017.
 */
public class MensajeBean {
    private String type;
    private String mensaje;

    public MensajeBean(){

    }

    public MensajeBean(String type, String mensaje) {
        //super();
        this.type = type;
        this.mensaje = mensaje;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
