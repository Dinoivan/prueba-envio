package com.incloud.hcp.dto;

/**
 * Created by Administrador on 30/08/2017.
 */
public class CuentaBancariaDto {

    private Integer idBanco;
    private String banco;
    private Integer idMoneda;
    private String moneda;
    private Integer idTipoCuenta;
    private String codigoTipoCuenta;
    private String tipoCuenta;
    private String contacto;
    private String numeroCuenta;
    private String numeroCuentaCci;
    private String archivoId;
    private String archivoNombre;
    private String archivoTipo;
    private String rutaAdjunto;

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public Integer getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Integer idMoneda) {
        this.idMoneda = idMoneda;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Integer getIdTipoCuenta() {
        return idTipoCuenta;
    }

    public void setIdTipoCuenta(Integer idTipoCuenta) {
        this.idTipoCuenta = idTipoCuenta;
    }

    public String getCodigoTipoCuenta() {
        return codigoTipoCuenta;
    }

    public void setCodigoTipoCuenta(String codigoTipoCuenta) {
        this.codigoTipoCuenta = codigoTipoCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getNumeroCuentaCci() {
        return numeroCuentaCci;
    }

    public void setNumeroCuentaCci(String numeroCuentaCci) {
        this.numeroCuentaCci = numeroCuentaCci;
    }

    public String getArchivoId() {
        return archivoId;
    }

    public void setArchivoId(String archivoId) {
        this.archivoId = archivoId;
    }

    public String getArchivoNombre() {
        return archivoNombre;
    }

    public void setArchivoNombre(String archivoNombre) {
        this.archivoNombre = archivoNombre;
    }

    public String getArchivoTipo() {
        return archivoTipo;
    }

    public void setArchivoTipo(String archivoTipo) {
        this.archivoTipo = archivoTipo;
    }

    public String getRutaAdjunto() {
        return rutaAdjunto;
    }

    public void setRutaAdjunto(String rutaAdjunto) {
        this.rutaAdjunto = rutaAdjunto;
    }

    @Override
    public String toString() {
        return "CuentaBancariaDto{" +
                "idBanco=" + idBanco +
                ", banco='" + banco + '\'' +
                ", idMoneda=" + idMoneda +
                ", moneda='" + moneda + '\'' +
                ", idTipoCuenta=" + idTipoCuenta +
                ", codigoTipoCuenta='" + codigoTipoCuenta + '\'' +
                ", tipoCuenta='" + tipoCuenta + '\'' +
                ", contacto='" + contacto + '\'' +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", numeroCuentaCci='" + numeroCuentaCci + '\'' +
                ", archivoId='" + archivoId + '\'' +
                ", archivoNombre='" + archivoNombre + '\'' +
                ", archivoTipo='" + archivoTipo + '\'' +
                ", rutaAdjunto='" + rutaAdjunto + '\'' +
                '}';
    }
}
