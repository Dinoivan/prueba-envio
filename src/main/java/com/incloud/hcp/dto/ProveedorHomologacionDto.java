package com.incloud.hcp.dto;

import java.math.BigDecimal;

/**
 * Created by Administrador on 19/09/2017.
 */
public class ProveedorHomologacionDto {

    private int idLineaComercial;
    private String lineaComercial;
    private int idHomologacion;
    private String pregunta;
    private boolean intAdjunto;
    private BigDecimal peso;
    private String rutaAdjunto;
    private String archivoId;
    private String archivoNombre;
    private String archivoTipo;
    private String respuesta;
    private BigDecimal puntaje;

    private String estado;
    private boolean indEstado;

    private String valorRespuestaLibre;

    public ProveedorHomologacionDto() {
    }

    public int getIdLineaComercial() {
        return idLineaComercial;
    }

    public void setIdLineaComercial(int idLineaComercial) {
        this.idLineaComercial = idLineaComercial;
    }

    public String getLineaComercial() {
        return lineaComercial;
    }

    public void setLineaComercial(String lineaComercial) {
        this.lineaComercial = lineaComercial;
    }

    public int getIdHomologacion() {
        return idHomologacion;
    }

    public void setIdHomologacion(int idHomologacion) {
        this.idHomologacion = idHomologacion;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public boolean isIntAdjunto() {
        return intAdjunto;
    }

    public void setIntAdjunto(boolean intAdjunto) {
        this.intAdjunto = intAdjunto;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public String getRutaAdjunto() {
        return rutaAdjunto;
    }

    public void setRutaAdjunto(String rutaAdjunto) {
        this.rutaAdjunto = rutaAdjunto;
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

    public String getArchivoId() {
        return archivoId;
    }

    public void setArchivoId(String archivoId) {
        this.archivoId = archivoId;
    }

    public String getArchivoNombre() {
        return archivoNombre;
    }

    public void setArchivoNombre(String archivoNombre) {
        this.archivoNombre = archivoNombre;
    }

    public String getArchivoTipo() {
        return archivoTipo;
    }

    public void setArchivoTipo(String archivoTipo) {
        this.archivoTipo = archivoTipo;
    }

    public boolean isIndEstado() {
        return indEstado;
    }

    public void setIndEstado(boolean indEstado) {
        this.indEstado = indEstado;
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
        return "ProveedorHomologacionDto{" +
                "idLineaComercial=" + idLineaComercial +
                ", lineaComercial='" + lineaComercial + '\'' +
                ", idHomologacion=" + idHomologacion +
                ", pregunta='" + pregunta + '\'' +
                ", intAdjunto=" + intAdjunto +
                ", peso=" + peso +
                ", rutaAdjunto='" + rutaAdjunto + '\'' +
                ", archivoId='" + archivoId + '\'' +
                ", archivoNombre='" + archivoNombre + '\'' +
                ", archivoTipo='" + archivoTipo + '\'' +
                ", respuesta='" + respuesta + '\'' +
                ", puntaje=" + puntaje +
                ", estado='" + estado + '\'' +
                ", indEstado=" + indEstado +
                ", valorRespuestaLibre='" + valorRespuestaLibre + '\'' +
                '}';
    }
}
