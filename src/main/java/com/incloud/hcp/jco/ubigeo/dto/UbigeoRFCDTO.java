package com.incloud.hcp.jco.ubigeo.dto;

public class UbigeoRFCDTO {

    private String denominacion;
    private String clavePais;
    private String claveRegion;
    private String clavePoblacion;
    private String claveDistrito;
    private String claveEquivalenciaSunat;

    public String getClavePais() {
        return clavePais;
    }

    public void setClavePais(String clavePais) {
        this.clavePais = clavePais;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getClaveRegion() {
        return claveRegion;
    }

    public void setClaveRegion(String claveRegion) {
        this.claveRegion = claveRegion;
    }

    public String getClavePoblacion() {
        return clavePoblacion;
    }

    public void setClavePoblacion(String clavePoblacion) {
        this.clavePoblacion = clavePoblacion;
    }

    public String getClaveDistrito() {
        return claveDistrito;
    }

    public void setClaveDistrito(String claveDistrito) {
        this.claveDistrito = claveDistrito;
    }

    public String getClaveEquivalenciaSunat() {
        return claveEquivalenciaSunat;
    }

    public void setClaveEquivalenciaSunat(String claveEquivalenciaSunat) {
        this.claveEquivalenciaSunat = claveEquivalenciaSunat;
    }


    @Override
    public String toString() {
        return "UbigeoRFCDTO{" +
                "denominacion='" + denominacion + '\'' +
                ", clavePais='" + clavePais + '\'' +
                ", claveRegion='" + claveRegion + '\'' +
                ", clavePoblacion='" + clavePoblacion + '\'' +
                ", claveDistrito='" + claveDistrito + '\'' +
                ", claveEquivalenciaSunat='" + claveEquivalenciaSunat + '\'' +
                '}';
    }


}
