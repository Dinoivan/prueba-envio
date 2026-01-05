package com.incloud.hcp.jco.comprobanteRetencion.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SapTableCRItemDto implements Serializable {
    private String numeroDocumentoErp;
    private String sociedad;
    private Integer ejercicio;
    private String tipoComprobante;
    private String serieFactura;
    private String correlativoFactura;
    private Date fechaEmision;
    private String moneda;
    private BigDecimal importeTotalComprobante;
    private BigDecimal importePago;
    private BigDecimal importeRetencionSoles;
    private BigDecimal importeNetoSoles;


    public String getNumeroDocumentoErp() {
        return numeroDocumentoErp;
    }

    public void setNumeroDocumentoErp(String numeroDocumentoErp) {
        this.numeroDocumentoErp = numeroDocumentoErp;
    }

    public String getSociedad() {
        return sociedad;
    }

    public void setSociedad(String sociedad) {
        this.sociedad = sociedad;
    }

    public Integer getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getSerieFactura() {
        return serieFactura;
    }

    public void setSerieFactura(String serieFactura) {
        this.serieFactura = serieFactura;
    }

    public String getCorrelativoFactura() {
        return correlativoFactura;
    }

    public void setCorrelativoFactura(String correlativoFactura) {
        this.correlativoFactura = correlativoFactura;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getImporteTotalComprobante() {
        return importeTotalComprobante;
    }

    public void setImporteTotalComprobante(BigDecimal importeTotalComprobante) {
        this.importeTotalComprobante = importeTotalComprobante;
    }

    public BigDecimal getImportePago() {
        return importePago;
    }

    public void setImportePago(BigDecimal importePago) {
        this.importePago = importePago;
    }

    public BigDecimal getImporteRetencionSoles() {
        return importeRetencionSoles;
    }

    public void setImporteRetencionSoles(BigDecimal importeRetencionSoles) {
        this.importeRetencionSoles = importeRetencionSoles;
    }

    public BigDecimal getImporteNetoSoles() {
        return importeNetoSoles;
    }

    public void setImporteNetoSoles(BigDecimal importeNetoSoles) {
        this.importeNetoSoles = importeNetoSoles;
    }
}
