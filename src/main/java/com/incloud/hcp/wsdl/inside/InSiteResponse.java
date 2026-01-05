package com.incloud.hcp.wsdl.inside;

/**
 * Created by Administrador on 09/11/2017.
 */
public class InSiteResponse {
    private String ruc;
    private String razonSocial;
    private boolean estado;
    private boolean condicion;
    private String region;
    private String provincia;
    private String distrito;
    private String ubigeo;
    private String direccion;

    private String actividadEconomica;
    private String fechaInicioActiSunat;
    private String codigoSistemaEmisionElect;
    private String codigoComprobantePago;
    private String codigoPadron;

    public InSiteResponse() {
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

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean getCondicion() {
        return condicion;
    }

    public void setCondicion(boolean condicion) {
        this.condicion = condicion;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isEstado() {
        return estado;
    }

    public boolean isCondicion() {
        return condicion;
    }

    public String getActividadEconomica() {
        return actividadEconomica;
    }

    public void setActividadEconomica(String actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    public String getFechaInicioActiSunat() {
        return fechaInicioActiSunat;
    }

    public void setFechaInicioActiSunat(String fechaInicioActiSunat) {
        this.fechaInicioActiSunat = fechaInicioActiSunat;
    }

    public String getCodigoSistemaEmisionElect() {
        return codigoSistemaEmisionElect;
    }

    public void setCodigoSistemaEmisionElect(String codigoSistemaEmisionElect) {
        this.codigoSistemaEmisionElect = codigoSistemaEmisionElect;
    }

    public String getCodigoComprobantePago() {
        return codigoComprobantePago;
    }

    public void setCodigoComprobantePago(String codigoComprobantePago) {
        this.codigoComprobantePago = codigoComprobantePago;
    }

    public String getCodigoPadron() {
        return codigoPadron;
    }

    public void setCodigoPadron(String codigoPadron) {
        this.codigoPadron = codigoPadron;
    }

    @Override
    public String toString() {
        return "InSiteResponse{" +
                "ruc='" + ruc + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", estado=" + estado +
                ", condicion=" + condicion +
                ", region='" + region + '\'' +
                ", provincia='" + provincia + '\'' +
                ", distrito='" + distrito + '\'' +
                ", ubigeo='" + ubigeo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", actividadEconomica='" + actividadEconomica + '\'' +
                ", fechaInicioActiSunat='" + fechaInicioActiSunat + '\'' +
                ", codigoSistemaEmisionElect='" + codigoSistemaEmisionElect + '\'' +
                ", codigoComprobantePago='" + codigoComprobantePago + '\'' +
                ", codigoPadron='" + codigoPadron + '\'' +
                '}';
    }
}
