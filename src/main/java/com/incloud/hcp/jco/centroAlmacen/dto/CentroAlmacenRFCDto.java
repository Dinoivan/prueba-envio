package com.incloud.hcp.jco.centroAlmacen.dto;

public class CentroAlmacenRFCDto {

    private String centro;
    private String poblacion;
    private String distrito;
    private String direccion;
    private String codigoalmacen;
    private String descripcionAlmacen;

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoalmacen() {
        return codigoalmacen;
    }

    public void setCodigoalmacen(String codigoalmacen) {
        this.codigoalmacen = codigoalmacen;
    }

    public String getDescripcionAlmacen() {
        return descripcionAlmacen;
    }

    public void setDescripcionAlmacen(String descripcionAlmacen) {
        this.descripcionAlmacen = descripcionAlmacen;
    }

    @Override
    public String toString() {
        return "CentroAlmacenRFCDto{" +
                "centro='" + centro + '\'' +
                ", poblacion='" + poblacion + '\'' +
                ", distrito='" + distrito + '\'' +
                ", direccion='" + direccion + '\'' +
                ", codigoalmacen='" + codigoalmacen + '\'' +
                ", descripcionAlmacen='" + descripcionAlmacen + '\'' +
                '}';
    }
}
