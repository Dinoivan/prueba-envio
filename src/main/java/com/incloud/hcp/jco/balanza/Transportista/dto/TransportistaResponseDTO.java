package com.incloud.hcp.jco.balanza.Transportista.dto;

public class TransportistaResponseDTO {
    private String ruc;
    private String razonSocial;
    private String direccion;
    private String acreedor;
    private String grupoCuentas;

    public String getAcreedor() {
        return acreedor;
    }

    public void setAcreedor(String acreedor) {
        this.acreedor = acreedor;
    }

    public String getGrupoCuentas() {
        return grupoCuentas;
    }

    public void setGrupoCuentas(String grupoCuentas) {
        this.grupoCuentas = grupoCuentas;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "TransportistaResponseDTO{" +
                "ruc='" + ruc + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", direccion='" + direccion + '\'' +
                ", acreedor='" + acreedor + '\'' +
                ", grupoCuentas='" + grupoCuentas + '\'' +
                '}';
    }
}
