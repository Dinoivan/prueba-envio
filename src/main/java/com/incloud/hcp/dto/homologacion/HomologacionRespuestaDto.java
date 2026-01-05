package com.incloud.hcp.dto.homologacion;

/**
 * Created by Administrador on 05/10/2017.
 */
public class HomologacionRespuestaDto {
    private Integer idHomologacionRespuesta;
    private String respuesta;

    public HomologacionRespuestaDto() {
    }

    public Integer getIdHomologacionRespuesta() {
        return idHomologacionRespuesta;
    }

    public void setIdHomologacionRespuesta(Integer idHomologacionRespuesta) {
        this.idHomologacionRespuesta = idHomologacionRespuesta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
