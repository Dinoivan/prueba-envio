package com.incloud.hcp.dto;

public class FiltroProveedorDTO {
    private String ruc;
    private String razonSocial;

    private String acreedorCodigoSap;


    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getAcreedorCodigoSap() {
        return acreedorCodigoSap;
    }

    public void setAcreedorCodigoSap(String acreedorCodigoSap) {
        this.acreedorCodigoSap = acreedorCodigoSap;
    }

    @Override
    public String toString() {
        return "FiltroProveedorDTO{" +
                "ruc='" + ruc + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", acreedorCodigoSap='" + acreedorCodigoSap + '\'' +
                '}';
    }
}
