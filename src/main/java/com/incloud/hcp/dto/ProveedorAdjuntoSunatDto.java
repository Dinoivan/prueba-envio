package com.incloud.hcp.dto;

public class ProveedorAdjuntoSunatDto {

    private Integer id;
    private String archivoId;
    private String archivoNombre;
    private String archivoTipo;
    private String rutaAdjunto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getRutaAdjunto() {
        return rutaAdjunto;
    }

    public void setRutaAdjunto(String rutaAdjunto) {
        this.rutaAdjunto = rutaAdjunto;
    }

    @Override
    public String toString() {
        return "ProveedorAdjuntoSunatDto{" +
                "idProveedorAdjuntoSunat=" + id +
                ", id='" + archivoId + '\'' +
                ", url='" + rutaAdjunto + '\'' +
                ", nombre='" + archivoNombre + '\'' +
                ", tipo='" + archivoTipo + '\'' +
                '}';
    }
}
