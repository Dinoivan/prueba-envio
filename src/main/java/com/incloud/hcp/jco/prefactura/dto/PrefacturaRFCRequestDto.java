package com.incloud.hcp.jco.prefactura.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PrefacturaRFCRequestDto implements Serializable {

    private String sociedad;
    private String referencia;
    private String fechaEmision; // Date
    private String fechaContabilizacion; // Date
    private String fechaBase; // Date
    private String indicadorImpuesto;
    private BigDecimal baseImponibleTotal;
    private BigDecimal igvTotal;
    private BigDecimal retencionTotal;
    private String textoCabecera;
    private String moneda;
    private String usuarioRegistroSap;

    private List<PrefacturaRFCPosicionDto> prefacturaRFCPosicionDtoList;


    public String getSociedad() {
        return sociedad;
    }

    public void setSociedad(String sociedad) {
        this.sociedad = sociedad;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getFechaContabilizacion() {
        return fechaContabilizacion;
    }

    public void setFechaContabilizacion(String fechaContabilizacion) {
        this.fechaContabilizacion = fechaContabilizacion;
    }

    public String getFechaBase() {
        return fechaBase;
    }

    public void setFechaBase(String fechaBase) {
        this.fechaBase = fechaBase;
    }

    public String getIndicadorImpuesto() {
        return indicadorImpuesto;
    }

    public void setIndicadorImpuesto(String indicadorImpuesto) {
        this.indicadorImpuesto = indicadorImpuesto;
    }

    public BigDecimal getBaseImponibleTotal() {
        return baseImponibleTotal;
    }

    public void setBaseImponibleTotal(BigDecimal baseImponibleTotal) {
        this.baseImponibleTotal = baseImponibleTotal;
    }

    public BigDecimal getIgvTotal() {
        return igvTotal;
    }

    public void setIgvTotal(BigDecimal igvTotal) {
        this.igvTotal = igvTotal;
    }

    public BigDecimal getRetencionTotal() {
        return retencionTotal;
    }

    public void setRetencionTotal(BigDecimal retencionTotal) {
        this.retencionTotal = retencionTotal;
    }

    public String getTextoCabecera() {
        return textoCabecera;
    }

    public void setTextoCabecera(String textoCabecera) {
        this.textoCabecera = textoCabecera;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getUsuarioRegistroSap() {
        return usuarioRegistroSap;
    }

    public void setUsuarioRegistroSap(String usuarioRegistroSap) {
        this.usuarioRegistroSap = usuarioRegistroSap;
    }

    public List<PrefacturaRFCPosicionDto> getPrefacturaRFCPosicionDtoList() {
        return prefacturaRFCPosicionDtoList;
    }

    public void setPrefacturaRFCPosicionDtoList(List<PrefacturaRFCPosicionDto> prefacturaRFCPosicionDtoList) {
        this.prefacturaRFCPosicionDtoList = prefacturaRFCPosicionDtoList;
    }


    @Override
    public String toString() {
        return "PrefacturaRFCRequestDto{" +
                "sociedad='" + sociedad + '\'' +
                ", referencia='" + referencia + '\'' +
                ", fechaEmision='" + fechaEmision + '\'' +
                ", fechaContabilizacion='" + fechaContabilizacion + '\'' +
                ", fechaBase='" + fechaBase + '\'' +
                ", indicadorImpuesto='" + indicadorImpuesto + '\'' +
                ", baseImponibleTotal=" + baseImponibleTotal +
                ", igvTotal=" + igvTotal +
                ", retencionTotal=" + retencionTotal +
                ", textoCabecera='" + textoCabecera + '\'' +
                ", moneda='" + moneda + '\'' +
                ", usuarioRegistroSap='" + usuarioRegistroSap + '\'' +
                ", prefacturaRFCPosicionDtoList=" + prefacturaRFCPosicionDtoList +
                '}';
    }
}
