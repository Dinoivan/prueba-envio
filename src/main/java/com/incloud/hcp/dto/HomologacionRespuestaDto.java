package com.incloud.hcp.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class HomologacionRespuestaDto {
    private Integer idHomologacionRespuesta;
    private String respuesta;
    private BigDecimal puntaje;

    public HomologacionRespuestaDto() {
        this.puntaje = new BigDecimal(BigInteger.ZERO);
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

    public BigDecimal getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(BigDecimal puntaje) {
        this.puntaje = puntaje;
    }
}
