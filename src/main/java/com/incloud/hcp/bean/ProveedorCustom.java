package com.incloud.hcp.bean;

import java.io.Serializable;

/**
 * Created by USER on 05/09/2017.
 */
public class ProveedorCustom implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer idProveedor;
    private String ruc;
    private String razonSocial;
    private String direccionFiscal;
    private String region;
    private String email;
    private String tipoProveedor;
    private String tipoPersona;
    private String estadoHomologacion;
    private String evaluacionHomologacion;
    private String indBlackList;
    private String indMigradoSap;
    private String indBlackListString;
    private String idHCP;
    private String codigoAcreedorSap;

    public ProveedorCustom() {
    }

    public ProveedorCustom(Integer idProveedor, String idHCP) {
        this.idProveedor = idProveedor;
        this.idHCP = idHCP;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
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

    public String getTipoProveedor() {
        return tipoProveedor;
    }

    public void setTipoProveedor(String tipoProveedor) {
        this.tipoProveedor = tipoProveedor;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getEstadoHomologacion() {
        return estadoHomologacion;
    }

    public void setEstadoHomologacion(String estadoHomologacion) {
        this.estadoHomologacion = estadoHomologacion;
    }

    public String getEvaluacionHomologacion() {
        return evaluacionHomologacion;
    }

    public void setEvaluacionHomologacion(String evaluacionHomologacion) {
        this.evaluacionHomologacion = evaluacionHomologacion;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getIndBlackList() {
        return indBlackList;
    }

    public void setIndBlackList(String indBlackList) {
        this.indBlackList = indBlackList;
    }

    public String getIndBlackListString() {
        return indBlackListString;
    }

    public void setIndBlackListString(String indBlackListString) {
        this.indBlackListString = indBlackListString;
    }

    public String getIdHCP() {
        return idHCP;
    }

    public void setIdHCP(String idHCP) {
        this.idHCP = idHCP;
    }

    public String getIndMigradoSap() {
        return indMigradoSap;
    }

    public void setIndMigradoSap(String indMigradoSap) {
        this.indMigradoSap = indMigradoSap;
    }

    public String getCodigoAcreedorSap() {
        return codigoAcreedorSap;
    }

    public void setCodigoAcreedorSap(String codigoAcreedorSap) {
        this.codigoAcreedorSap = codigoAcreedorSap;
    }

    @Override
    public String toString() {
        return "ProveedorCustom{" +
                "idProveedor=" + idProveedor +
                ", ruc='" + ruc + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", direccionFiscal='" + direccionFiscal + '\'' +
                ", region='" + region + '\'' +
                ", email='" + email + '\'' +
                ", tipoProveedor='" + tipoProveedor + '\'' +
                ", tipoPersona='" + tipoPersona + '\'' +
                ", estadoHomologacion='" + estadoHomologacion + '\'' +
                ", evaluacionHomologacion='" + evaluacionHomologacion + '\'' +
                ", indBlackList='" + indBlackList + '\'' +
                ", indMigradoSap='" + indMigradoSap + '\'' +
                ", indBlackListString='" + indBlackListString + '\'' +
                ", idHCP='" + idHCP + '\'' +
                ", codigoAcreedorSap='" + codigoAcreedorSap + '\'' +
                '}';
    }
}
