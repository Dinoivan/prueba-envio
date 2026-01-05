package com.incloud.hcp.jco.balanza.Proveedor.dto;

public class ProveedorResponseDTO {
    private String ruc;
    private String razonSocial;
    private String direccion;
    private String grupoCuentas;
    private String acreedor;
    private String region;
    private String bezei;
    private String city1;
    private String city2;

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGrupoCuentas() {
        return grupoCuentas;
    }

    public void setGrupoCuentas(String grupoCuentas) {
        this.grupoCuentas = grupoCuentas;
    }

    public String getAcreedor() {
        return acreedor;
    }

    public void setAcreedor(String acreedor) {
        this.acreedor = acreedor;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBezei() {
        return bezei;
    }

    public void setBezei(String bezei) {
        this.bezei = bezei;
    }

    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
    }

    public String getCity2() {
        return city2;
    }

    public void setCity2(String city2) {
        this.city2 = city2;
    }

    @Override
    public String toString() {
        return "ProveedorResponseDTO{" +
                "ruc='" + ruc + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", direccion='" + direccion + '\'' +
                ", grupoCuentas='" + grupoCuentas + '\'' +
                ", acreedor='" + acreedor + '\'' +
                ", region='" + region + '\'' +
                ", bezei='" + bezei + '\'' +
                ", city1='" + city1 + '\'' +
                ", city2='" + city2 + '\'' +
                '}';
    }
}
