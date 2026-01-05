package com.incloud.hcp.bean;

import com.incloud.hcp.domain.Prefactura;
import com.incloud.hcp.dto.PrefacturaDto;

/**
 * Created by Administrador on 23/08/2017.
 */
public class MensajePrefactura {
    private String type;
    private String mensaje;
    private String mensajeEcm;
    private String mensajeSaveEcm;
    private PrefacturaDto prefactura;

    public MensajePrefactura(){

    }

    public MensajePrefactura(String type, String mensaje, String mensajeEcm, String mensajeSaveEcm, PrefacturaDto prefactura) {
        this.type = type;
        this.mensaje = mensaje;
        this.mensajeEcm = mensajeEcm;
        this.mensajeSaveEcm = mensajeSaveEcm;
        this.prefactura = prefactura;
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

    public String getMensajeEcm() {
        return mensajeEcm;
    }


    public void setMensajeEcm(String mensajeEcm) {
        this.mensajeEcm = mensajeEcm;
    }

    public String getMensajeSaveEcm() {
        return mensajeSaveEcm;
    }

    public void setMensajeSaveEcm(String mensajeSaveEcm) {
        this.mensajeSaveEcm = mensajeSaveEcm;
    }

    public PrefacturaDto getPrefactura() {
        return prefactura;
    }

    public void setPrefactura(PrefacturaDto prefactura) {
        this.prefactura = prefactura;
    }
}
