package com.incloud.hcp.dto;

public class DatosBLZProveedorDTO {
    private String ruc;
    private String razonSocial;

    private String direccionFiscal;

    private String email;

    private String acreedorCodigoSap;

    public DatosBLZProveedorDTO() {
    }

    public DatosBLZProveedorDTO(String ruc, String razonSocial, String direccionFiscal, String email, String acreedorCodigoSap) {
        this.ruc = ruc;
        this.razonSocial = razonSocial;
        this.direccionFiscal = direccionFiscal;
        this.email = email;
        this.acreedorCodigoSap = acreedorCodigoSap;
    }

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

    public String getDireccionFiscal() {
        return direccionFiscal;
    }

    public void setDireccionFiscal(String direccionFiscal) {
        this.direccionFiscal = direccionFiscal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAcreedorCodigoSap() {
        return acreedorCodigoSap;
    }

    public void setAcreedorCodigoSap(String acreedorCodigoSap) {
        this.acreedorCodigoSap = acreedorCodigoSap;
    }

    @Override
    public String toString() {
        return "DatosBLZProveedorDTO{" +
                "ruc='" + ruc + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", direccionFiscal='" + direccionFiscal + '\'' +
                ", email='" + email + '\'' +
                ", acreedorCodigoSap='" + acreedorCodigoSap + '\'' +
                '}';
    }
}
