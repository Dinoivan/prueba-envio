package com.incloud.hcp.jco.ordenCompra.dto;

import java.io.Serializable;

public class SociedadDto implements Serializable {
	private String razonSocial;
    private String ruc;
    private String calle;
    private String numero;
    private String poblacion;
    private String distrito;
    private String telefono;


    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    @Override
    public String toString() {
        return "SociedadDto{" +
                "razonSocial='" + razonSocial + '\'' +
                ", ruc='" + ruc + '\'' +
                ", calle='" + calle + '\'' +
                ", numero='" + numero + '\'' +
                ", poblacion='" + poblacion + '\'' +
                ", distrito='" + distrito + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}