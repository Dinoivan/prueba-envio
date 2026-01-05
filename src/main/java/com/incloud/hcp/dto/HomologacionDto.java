package com.incloud.hcp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HomologacionDto {
    @NotNull(message = "Es obligatorio")
    private Integer idLineaComercial;
    private String lineaComercial;
    @NotNull(message = "Es obligatorio")
    private Integer idHomologacion;
    @NotNull(message = "Es obligatorio")
    private List<TipoHomologacionDto> tipo;
    @NotNull(message = "Es obligatorio")
    private BigDecimal peso;
    @NotNull(message = "Es obligatorio")
    @Size(max = 1000, message = "Campo debe ser como máximo 1000 caracteres")
    private String pregunta;
    @Size(min = 2, message = "Debe tener minimo 2 opciones")
    private List<HomologacionRespuestaDto> respuestas;

    private String estado;

    public HomologacionDto() {
        this.respuestas = new ArrayList<>();
    }

    public Integer getIdHomologacion() {
        return idHomologacion;
    }

    public void setIdHomologacion(Integer idHomologacion) {
        this.idHomologacion = idHomologacion;
    }

    public List<TipoHomologacionDto> getTipo() {
        return tipo;
    }

    public void setTipo(List<TipoHomologacionDto> tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Integer getIdLineaComercial() {
        return idLineaComercial;
    }

    public void setIdLineaComercial(Integer idLineaComercial) {
        this.idLineaComercial = idLineaComercial;
    }

    public String getLineaComercial() {
        return lineaComercial;
    }

    public void setLineaComercial(String lineaComercial) {
        this.lineaComercial = lineaComercial;
    }

    public List<HomologacionRespuestaDto> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<HomologacionRespuestaDto> respuestas) {
        this.respuestas = respuestas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
