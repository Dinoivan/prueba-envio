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

    @Override
    public String toString() {
        return "ProveedorCatalogoDto{" +
                "idProveedorCatalogo=" + id +
                ", id='" + archivoId + '\'' +
                ", url='" + rutaCatalogo + '\'' +
                ", nombre='" + archivoNombre + '\'' +
                ", tipo='" + archivoTipo + '\'' +
                '}';
    }
}
