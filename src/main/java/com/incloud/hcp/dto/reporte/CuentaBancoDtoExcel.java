package com.incloud.hcp.dto.reporte;

/**
 * Created by Administrador on 30/11/2017.
 */
public class CuentaBancoDtoExcel {
    private String codigoSap;
    private String ruc;
    private String razonSocial;
    private String banco;
    private String tipoCuenta;
    private String numeroCuenta;
    private String codigoCci;
    private String moneda;
    private String contacto;

    public CuentaBancoDtoExcel() {
    }

    public String getCodigoSap() {
        return codigoSap;
    }

    public void setCodigoSap(String codigoSap) {
        this.codigoSap = codigoSap;
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

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getCodigoCci() {
        return codigoCci;
    }

    public void setCodigoCci(String codigoCci) {
        this.codigoCci = codigoCci;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
}
