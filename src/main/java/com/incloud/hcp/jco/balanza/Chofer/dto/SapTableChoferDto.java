package com.incloud.hcp.jco.balanza.Chofer.dto;


public class SapTableChoferDto {

    private String dni;
    private String licencia;
    private String tipoDocumento;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombre;
    private String status;
    private String correlativo;


    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
    }

    @Override
    public String toString() {
        return "SapTableChoferDto{" +
                "dni='" + dni + '\'' +
                ", licencia='" + licencia + '\'' +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", nombre='" + nombre + '\'' +
                ", status='" + status + '\'' +
                ", correlativo='" + correlativo + '\'' +
                '}';
    }
}
