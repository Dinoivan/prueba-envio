package com.incloud.hcp.jco.parametro.dto;

public class SapTableLugarEntrega {
    private String lugarEntrega;

    private String centro;

    private String numeroCuenta;

    private String calle;

    private String calleCuatro;

    private String poblacion;

    private String distrito;

    private String zRegion;

    public String getLugarEntrega() {
        return lugarEntrega;
    }

    public void setLugarEntrega(String lugarEntrega) {
        this.lugarEntrega = lugarEntrega;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCalleCuatro() {
        return calleCuatro;
    }

    public void setCalleCuatro(String calleCuatro) {
        this.calleCuatro = calleCuatro;
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

    public String getzRegion() {
        return zRegion;
    }

    public void setzRegion(String zRegion) {
        this.zRegion = zRegion;
    }

    @Override
    public String toString() {
        return "SapTableLugarEntrega{" +
                "lugarEntrega='" + lugarEntrega + '\'' +
                ", centro='" + centro + '\'' +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", calle='" + calle + '\'' +
                ", calleCuatro='" + calleCuatro + '\'' +
                ", poblacion='" + poblacion + '\'' +
                ", distrito='" + distrito + '\'' +
                ", zRegion='" + zRegion + '\'' +
                '}';
    }
}
