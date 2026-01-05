package com.incloud.hcp.dto;

/**
 * Created by Administrador on 25/09/2017.
 */
public class AdjuntoOrdenCompraDto {
    private String idAdjunto;
    private String rutaCatalogo;
    private String archivoId;
    private String archivoNombre;
    private String archivoTipo;
    private String usuario;
    private String nombreUsurio;
    private String ordencompra;
    private String archivo;

    public String getIdAdjunto() {
        return idAdjunto;
    }

    public void setIdAdjunto(String idAdjunto) {
        this.idAdjunto = idAdjunto;
    }

    public String getRutaCatalogo() {
        return rutaCatalogo;
    }

    public void setRutaCatalogo(String rutaCatalogo) {
        this.rutaCatalogo = rutaCatalogo;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombreUsurio() {
        return nombreUsurio;
    }

    public void setNombreUsurio(String nombreUsurio) {
        this.nombreUsurio = nombreUsurio;
    }

    public String getOrdencompra() {
        return ordencompra;
    }

    public void setOrdencompra(String ordencompra) {
        this.ordencompra = ordencompra;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    @Override
    public String toString() {
        return "AdjuntoOrdenCompraDto{" +
                "idAdjunto=" + idAdjunto +
                ", rutaCatalogo='" + rutaCatalogo + '\'' +
                ", archivoId='" + archivoId + '\'' +
                ", archivoNombre='" + archivoNombre + '\'' +
                ", archivoTipo='" + archivoTipo + '\'' +
                ", archivoTipo='" + usuario + '\'' +
                ", archivoTipo='" + nombreUsurio + '\'' +
                ", ordencompra='" + ordencompra + '\'' +
                ", archivo='" + archivo + '\'' +
                '}';
    }
}
