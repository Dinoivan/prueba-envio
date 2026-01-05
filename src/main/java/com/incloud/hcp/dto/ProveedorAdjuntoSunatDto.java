package com.incloud.hcp.dto;

public class ProveedorAdjuntoSunatDto {

    private Integer id;
    private String archivoId;
    private String archivoNombre;
    private String archivoTipo;
    private String rutaAdjunto;
    private String archivoNombreFinal;
    private String archivoExtension;
    private Long archivoSize;
    private String archivoCarpetaId;
    private String archivoNombreFolder;
    private String archivoParentPath;

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

    public String getArchivoNombreFinal() {
        return archivoNombreFinal;
    }

    public void setArchivoNombreFinal(String archivoNombreFinal) {
        this.archivoNombreFinal = archivoNombreFinal;
    }

    public String getArchivoExtension() {
        return archivoExtension;
    }

    public void setArchivoExtension(String archivoExtension) {
        this.archivoExtension = archivoExtension;
    }

    public Long getArchivoSize() {
        return archivoSize;
    }

    public void setArchivoSize(Long archivoSize) {
        this.archivoSize = archivoSize;
    }

    public String getArchivoCarpetaId() {
        return archivoCarpetaId;
    }

    public void setArchivoCarpetaId(String archivoCarpetaId) {
        this.archivoCarpetaId = archivoCarpetaId;
    }

    public String getArchivoNombreFolder() {
        return archivoNombreFolder;
    }

    public void setArchivoNombreFolder(String archivoNombreFolder) {
        this.archivoNombreFolder = archivoNombreFolder;
    }

    public String getArchivoParentPath() {
        return archivoParentPath;
    }

    public void setArchivoParentPath(String archivoParentPath) {
        this.archivoParentPath = archivoParentPath;
    }

    @Override
    public String toString() {
        return "ProveedorAdjuntoSunatDto{" +
                "idProveedorAdjuntoSunat=" + id +
                ", id='" + archivoId + '\'' +
                ", url='" + rutaAdjunto + '\'' +
                ", nombre='" + archivoNombre + '\'' +
                ", tipo='" + archivoTipo + '\'' +
                ", archivoNombreFinal='" + archivoNombreFinal + '\'' +
                ", archivoExtension='" + archivoExtension + '\'' +
                ", archivoSize='" + archivoSize + '\'' +
                ", archivoCarpetaId='" + archivoCarpetaId + '\'' +
                ", archivoNombreFolder='" + archivoNombreFolder + '\'' +
                ", archivoParentPath='" + archivoParentPath + '\'' +
                '}';
    }
}
