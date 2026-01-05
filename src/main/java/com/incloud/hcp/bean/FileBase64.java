package com.incloud.hcp.bean;


public class FileBase64 {

    private String nombreDocumento;
    private String base64;
    private String tipo;
    private String extension;
    private String descripcion;

    //---------------------------------------------------------------------------------------------------
    private String enviar; //Utilizado como flag para identificar los adjuntos que se enviaran con el Mail
    private String esEstandar;
    private String tipoEstandar;
    private String usuario;

    public FileBase64() {
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEnviar() {
        return enviar;
    }

    public void setEnviar(String enviar) {
        this.enviar = enviar;
    }

    public String getEsEstandar() {
        return esEstandar;
    }

    public void setEsEstandar(String esEstandar) {
        this.esEstandar = esEstandar;
    }

    public String getTipoEstandar() {
        return tipoEstandar;
    }

    public void setTipoEstandar(String tipoEstandar) {
        this.tipoEstandar = tipoEstandar;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
