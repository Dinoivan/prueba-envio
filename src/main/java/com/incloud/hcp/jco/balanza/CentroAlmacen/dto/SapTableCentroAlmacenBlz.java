package com.incloud.hcp.jco.balanza.CentroAlmacen.dto;

public class SapTableCentroAlmacenBlz {
    private String centro;
    private String poblacion;
    private String distrito;
    private String codigoAlmacen;
    private String descripcionAlmacen;
    private String cenStras;
    private String region;
    private String denominacion;
    private String direccion1;
    private String direccion2;
    private String direccion3;
    private String nombre;

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

    public String getCodigoAlmacen() {
        return codigoAlmacen;
    }

    public void setCodigoAlmacen(String codigoAlmacen) {
        this.codigoAlmacen = codigoAlmacen;
    }

    public String getDescripcionAlmacen() {
        return descripcionAlmacen;
    }

    public void setDescripcionAlmacen(String descripcionAlmacen) {
        this.descripcionAlmacen = descripcionAlmacen;
    }

    public String getDireccion1() {
        return direccion1;
    }

    public void setDireccion1(String direccion1) {
        this.direccion1 = direccion1;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public void setDireccion2(String direccion2) {
        this.direccion2 = direccion2;
    }

    public String getDireccion3() {
        return direccion3;
    }

    public void setDireccion3(String direccion3) {
        this.direccion3 = direccion3;
    }

    public String getCenStras() {
        return cenStras;
    }

    public void setCenStras(String cenStras) {
        this.cenStras = cenStras;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "SapTableCentroAlmacenBlz{" +
                "centro='" + centro + '\'' +
                ", poblacion='" + poblacion + '\'' +
                ", distrito='" + distrito + '\'' +
                ", codigoAlmacen='" + codigoAlmacen + '\'' +
                ", descripcionAlmacen='" + descripcionAlmacen + '\'' +
                ", cenStras='" + cenStras + '\'' +
                ", region='" + region + '\'' +
                ", denominacion='" + denominacion + '\'' +
                ", direccion1='" + direccion1 + '\'' +
                ", direccion2='" + direccion2 + '\'' +
                ", direccion3='" + direccion3 + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

