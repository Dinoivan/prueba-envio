package com.incloud.hcp.util.constant;

/**
 * Created by Administrador on 14/09/2017.
 */
public enum TipoSolicitudConstant {
    ENVIO_BLACK_LIST(1, "Envío al BlackList"),
    RETIRO_BLACK_LIST(2, "Retiro del BlackList"),
    REGISTRO_NO_CONFORME(3, "Registro de No Conforme");

    int id;
    String descripcion;
    TipoSolicitudConstant(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId(){
        return this.id;
    }

    public static TipoSolicitudConstant getTipoSolicitud(int id){
        for(TipoSolicitudConstant t : values()){
            if(t.getId() == id){
                return t;
            }
        }
        return null;
    }

    public static boolean existe(int id){
        for(TipoSolicitudConstant t : values()){
            if(t.getId() == id){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.descripcion;
    }
}
