package com.incloud.hcp.dto.homologacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 16/10/2017.
 */
public class HomologacionDto {
    private Integer idHomologacion;
    private String pregunta;
    private Boolean indicadorAdjunto;
    private BigDecimal peso;
    private ProveedorRespuestaDto respuestaProveedor;
    private List<HomologacionRespuestaDto> opciones;
    private String estado;

    private String valorRespuestaLibre;

    public HomologacionDto() {
        opciones = new ArrayList<>();
        respuestaProveedor = new ProveedorRespuestaDto();
    }

    public Integer getIdHomologacion() {
        return idHomologacion;
    }

    public void setIdHomologacion(Integer idHomologacion) {
        this.idHomologacion = idHomologacion;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Boolean getIndicadorAdjunto() {
        return indicadorAdjunto;
    }

    public void setIndicadorAdjunto(Boolean indicadorAdjunto) {
        this.indicadorAdjunto = indicadorAdjunto;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public ProveedorRespuestaDto getRespuestaProveedor() {
        return respuestaProveedor;
    }

    public void setRespuestaProveedor(ProveedorRespuestaDto respuestaProveedor) {
        this.respuestaProveedor = respuestaProveedor;
    }

    public List<HomologacionRespuestaDto> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<HomologacionRespuestaDto> opciones) {
        this.opciones = opciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getValorRespuestaLibre() {
        return valorRespuestaLibre;
    }

    public void setValorRespuestaLibre(String valorRespuestaLibre) {
        this.valorRespuestaLibre = valorRespuestaLibre;
    }

    @Override
    public String toString() {
        return "HomologacionDto{" +
                "idHomologacion=" + idHomologacion +
                ", pregunta='" + pregunta + '\'' +
                ", indicadorAdjunto=" + indicadorAdjunto +
                ", respuestaProveedor=" + respuestaProveedor +
                ", opciones=" + opciones +
                ", estado='" + estado + '\'' +
                ", valorRespuestaLibre='" + valorRespuestaLibre + '\'' +
                '}';
    }
}
