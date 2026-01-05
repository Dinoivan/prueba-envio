package com.incloud.hcp.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


public class PrefacturaDto {
    private String sociedad;
    private String proveedorRuc;
    private String proveedorRazonSocial;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date fechaEmision;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date fechaContabilizacion;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date fechaBase;
    private String indicadorImpuesto;
    private String referencia;
    private String observaciones;
    private String cadenaNumerosOrdenCompra;
    private String cadenaNumerosGuia;
    private String codigoMoneda;
    private String subTotal; // bigdecimal (2 decimales)
    private String igv; // bigdecimal (2 decimales)
    private String total; // bigdecimal (2 decimales)
    private String centro;
    private String usuarioComprador;


    private List<Integer> idDocumentoAceptacionList;


    @JsonProperty("Sociedad")
    public String getSociedad() {
        return sociedad;
    }

    public void setSociedad(String sociedad) {
        this.sociedad = sociedad;
    }

    @JsonProperty("ProveedorRuc")
    public String getProveedorRuc() {
        return proveedorRuc;
    }

    public void setProveedorRuc(String proveedorRuc) {
        this.proveedorRuc = proveedorRuc;
    }

    @JsonProperty("ProveedorRazonSocial")
    public String getProveedorRazonSocial() {
        return proveedorRazonSocial;
    }

    public void setProveedorRazonSocial(String proveedorRazonSocial) {
        this.proveedorRazonSocial = proveedorRazonSocial;
    }

    @JsonProperty("FechaEmision")
    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    @JsonProperty("FechaContabilizacion")
    public Date getFechaContabilizacion() {
        return fechaContabilizacion;
    }

    public void setFechaContabilizacion(Date fechaContabilizacion) {
        this.fechaContabilizacion = fechaContabilizacion;
    }

    @JsonProperty("FechaBase")
    public Date getFechaBase() {
        return fechaBase;
    }

    public void setFechaBase(Date fechaBase) {
        this.fechaBase = fechaBase;
    }

    @JsonProperty("IndicadorImpuesto")
    public String getIndicadorImpuesto() {
        return indicadorImpuesto;
    }

    public void setIndicadorImpuesto(String indicadorImpuesto) {
        this.indicadorImpuesto = indicadorImpuesto;
    }

    @JsonProperty("Referencia")
    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    @JsonProperty("Observaciones")
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @JsonProperty("CadenaNumerosOrdenCompra")
    public String getCadenaNumerosOrdenCompra() {
        return cadenaNumerosOrdenCompra;
    }

    public void setCadenaNumerosOrdenCompra(String cadenaNumerosOrdenCompra) {
        this.cadenaNumerosOrdenCompra = cadenaNumerosOrdenCompra;
    }

    @JsonProperty("CadenaNumerosGuia")
    public String getCadenaNumerosGuia() {
        return cadenaNumerosGuia;
    }

    public void setCadenaNumerosGuia(String cadenaNumerosGuia) {
        this.cadenaNumerosGuia = cadenaNumerosGuia;
    }

    @JsonProperty("CodigoMoneda")
    public String getCodigoMoneda() {
        return codigoMoneda;
    }

    public void setCodigoMoneda(String codigoMoneda) {
        this.codigoMoneda = codigoMoneda;
    }

    @JsonProperty("SubTotal")
    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    @JsonProperty("Igv")
    public String getIgv() {
        return igv;
    }

    public void setIgv(String igv) {
        this.igv = igv;
    }

    @JsonProperty("Total")
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @JsonProperty("Centro")
    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    @JsonProperty("UsuarioComprador")
    public String getUsuarioComprador() {
        return usuarioComprador;
    }

    public void setUsuarioComprador(String usuarioComprador) {
        this.usuarioComprador = usuarioComprador;
    }


    public List<Integer> getIdDocumentoAceptacionList() {
        return idDocumentoAceptacionList;
    }

    public void setIdDocumentoAceptacionList(List<Integer> idDocumentoAceptacionList) {
        this.idDocumentoAceptacionList = idDocumentoAceptacionList;
    }


    @Override
    public String toString() {
        return "PrefacturaDto{" +
                "sociedad='" + sociedad + '\'' +
                ", proveedorRuc='" + proveedorRuc + '\'' +
                ", proveedorRazonSocial='" + proveedorRazonSocial + '\'' +
                ", fechaEmision=" + fechaEmision +
                ", fechaContabilizacion=" + fechaContabilizacion +
                ", fechaBase=" + fechaBase +
                ", indicadorImpuesto='" + indicadorImpuesto + '\'' +
                ", referencia='" + referencia + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", cadenaNumerosOrdenCompra='" + cadenaNumerosOrdenCompra + '\'' +
                ", cadenaNumerosGuia='" + cadenaNumerosGuia + '\'' +
                ", codigoMoneda='" + codigoMoneda + '\'' +
                ", subTotal='" + subTotal + '\'' +
                ", igv='" + igv + '\'' +
                ", total='" + total + '\'' +
                ", centro='" + centro + '\'' +
                ", usuarioComprador='" + usuarioComprador + '\'' +
                ", idDocumentoAceptacionList=" + idDocumentoAceptacionList +
                '}';
    }
}
