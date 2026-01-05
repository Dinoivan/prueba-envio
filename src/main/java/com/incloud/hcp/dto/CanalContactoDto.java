package com.incloud.hcp.dto;

/**
 * Created by Administrador on 24/10/2017.
 */
public class CanalContactoDto {
    private Integer idCanalContacto;
    private Integer codigoPais;
    private String pais;
    private Integer codigoRegion;
    private String region;
    private Integer codigoProvincia;
    private String provincia;
    private String direccion;
    private String codigoArea;
    private String area;
    private String contacto;
    private String email;
    private String telefono;

    public CanalContactoDto() {
    }

    public Integer getIdCanalContacto() {
        return idCanalContacto;
    }

    public void setIdCanalContacto(Integer idCanalContacto) {
        this.idCanalContacto = idCanalContacto;
    }

    public Integer getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(Integer codigoPais) {
        this.codigoPais = codigoPais;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Integer getCodigoRegion() {
        return codigoRegion;
    }

    public void setCodigoRegion(Integer codigoRegion) {
        this.codigoRegion = codigoRegion;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getCodigoProvincia() {
        return codigoProvincia;
    }

    public void setCodigoProvincia(Integer codigoProvincia) {
        this.codigoProvincia = codigoProvincia;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoArea() {
        return codigoArea;
    }

    public void setCodigoArea(String codigoArea) {
        this.codigoArea = codigoArea;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "CanalContactoDto{" +
                "idCanalContacto=" + idCanalContacto +
                ", codigoPais=" + codigoPais +
                ", pais='" + pais + '\'' +
                ", codigoRegion=" + codigoRegion +
                ", region='" + region + '\'' +
                ", codigoProvincia=" + codigoProvincia +
                ", provincia='" + provincia + '\'' +
                ", direccion='" + direccion + '\'' +
                ", codigoArea='" + codigoArea + '\'' +
                ", area='" + area + '\'' +
                ", contacto='" + contacto + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
