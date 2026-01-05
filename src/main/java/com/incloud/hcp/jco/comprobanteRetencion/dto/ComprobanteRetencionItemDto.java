package com.incloud.hcp.jco.comprobanteRetencion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ComprobanteRetencionItemDto implements Serializable {

    private String numeroDocumentoErp;
    private String sociedad;
    private Integer ejercicio;
    private String tipoComprobante;
    private String serieFactura;
    private String correlativoFactura;
    private Date fechaEmision;
    private String moneda;
    private BigDecimal importeTotalComprobante;
    private Date fechaPago;
    private String numeroPago;
    private BigDecimal importePago;
    private BigDecimal importeRetencionSoles;
    private BigDecimal importeNetoSoles;

    @JsonProperty("NumeroDocumentoErp")
    public String getNumeroDocumentoErp() {
        return numeroDocumentoErp;
    }

    public void setNumeroDocumentoErp(String numeroDocumentoErp) {
        this.numeroDocumentoErp = numeroDocumentoErp;
    }

    @JsonProperty("Sociedad")
    public String getSociedad() {
        return sociedad;
    }

    public void setSociedad(String sociedad) {
        this.sociedad = sociedad;
    }

    @JsonProperty("Ejercicio")
    public Integer getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    @JsonProperty("TipoComprobante")
    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    @JsonProperty("SerieFactura")
    public String getSerieFactura() {
        return serieFactura;
    }

    public void setSerieFactura(String serieFactura) {
        this.serieFactura = serieFactura;
    }

    @JsonProperty("CorrelativoFactura")
    public String getCorrelativoFactura() {
        return correlativoFactura;
    }

    public void setCorrelativoFactura(String correlativoFactura) {
        this.correlativoFactura = correlativoFactura;
    }

    @JsonProperty("FechaEmision")
    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    @JsonProperty("Moneda")
    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    @JsonProperty("ImporteTotalComprobante")
    public BigDecimal getImporteTotalComprobante() {
        return importeTotalComprobante;
    }

    public void setImporteTotalComprobante(BigDecimal importeTotalComprobante) {
        this.importeTotalComprobante = importeTotalComprobante;
    }

    @JsonProperty("FechaPago")
    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    @JsonProperty("NumeroPago")
    public String getNumeroPago() {
        return numeroPago;
    }

    public void setNumeroPago(String numeroPago) {
        this.numeroPago = numeroPago;
    }

    @JsonProperty("ImportePago")
    public BigDecimal getImportePago() {
        return importePago;
    }

    public void setImportePago(BigDecimal importePago) {
        this.importePago = importePago;
    }

    @JsonProperty("ImporteRetencionSoles")
    public BigDecimal getImporteRetencionSoles() {
        return importeRetencionSoles;
    }

    public void setImporteRetencionSoles(BigDecimal importeRetencionSoles) {
        this.importeRetencionSoles = importeRetencionSoles;
    }

    @JsonProperty("ImporteNetoSoles")
    public BigDecimal getImporteNetoSoles() {
        return importeNetoSoles;
    }

    public void setImporteNetoSoles(BigDecimal importeNetoSoles) {
        this.importeNetoSoles = importeNetoSoles;
    }
}
