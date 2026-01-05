package com.incloud.hcp.dto;

/**
 * Created by Administrador on 25/09/2017.
 */
public class ProveedorCatalogoDto {
    private Integer id;
    private String archivoId;
    private String rutaCatalogo;
    private String archivoNombre;
    private String archivoTipo;
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

    public String getRutaCatalogo() {
        return rutaCatalogo;
    }

    public void setRutaCatalogo(String rutaCatalogo) {
        this.rutaCatalogo = rutaCatalogo;
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
        return "ProveedorCatalogoDto{" +
                "idProveedorCatalogo=" + id +
                ", id='" + archivoId + '\'' +
                ", url='" + rutaCatalogo + '\'' +
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
