package com.incloud.hcp.jco.comprobantePago.dto;

import com.incloud.hcp.domain.Sociedad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ComprobantePagoDto implements Serializable {
    private String codigoDocumentoSap;
    private String ejercicio;
    private String numeroDocumentoContable;
    private String numeroComprobantePago;
    private Sociedad sociedad;
    private String codigoSociedad;
    private String proveedorRuc;
    private String tipoComprobante;
    private String codigoMoneda;
    private BigDecimal subTotal;
    private BigDecimal igv;
    private BigDecimal total;
    private String estado;
    private Integer formaPago;
    private Date fechaEmision;
    private Date fechaRegistroDocContable;
    private Date fechaContabilizacion;
    private Date fechaBase;
    private Date fechaVencimiento;
    private Date fechaPosiblePago;
    private Date fechaRealPago;
    private Date fechaPagoNuevo;
    private String observaciones;
    private String tipoPago;
    private String numeroDocumentoCompensacion;
    private String banco;

    private List<ComprobantePagoItemDto> comprobantePagoItemDtoList;

    public Date getFechaPagoNuevo() {
        return fechaPagoNuevo;
    }

    public void setFechaPagoNuevo(Date fechaPagoNuevo) {
        this.fechaPagoNuevo = fechaPagoNuevo;
    }

    public String getCodigoDocumentoSap() {
        return codigoDocumentoSap;
    }

    public void setCodigoDocumentoSap(String codigoDocumentoSap) {
        this.codigoDocumentoSap = codigoDocumentoSap;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getNumeroDocumentoContable() {
        return numeroDocumentoContable;
    }

    public void setNumeroDocumentoContable(String numeroDocumentoContable) {
        this.numeroDocumentoContable = numeroDocumentoContable;
    }

    public String getNumeroComprobantePago() {
        return numeroComprobantePago;
    }

    public void setNumeroComprobantePago(String numeroComprobantePago) {
        this.numeroComprobantePago = numeroComprobantePago;
    }

    public Sociedad getSociedad() {
        return sociedad;
    }

    public void setSociedad(Sociedad sociedad) {
        this.sociedad = sociedad;
    }

    public String getCodigoSociedad() {
        return codigoSociedad;
    }

    public void setCodigoSociedad(String codigoSociedad) {
        this.codigoSociedad = codigoSociedad;
    }

    public String getProveedorRuc() {
        return proveedorRuc;
    }

    public void setProveedorRuc(String proveedorRuc) {
        this.proveedorRuc = proveedorRuc;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getCodigoMoneda() {
        return codigoMoneda;
    }

    public void setCodigoMoneda(String codigoMoneda) {
        this.codigoMoneda = codigoMoneda;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getIgv() {
        return igv;
    }

    public void setIgv(BigDecimal igv) {
        this.igv = igv;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(Integer formaPago) {
        this.formaPago = formaPago;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaRegistroDocContable() {
        return fechaRegistroDocContable;
    }

    public void setFechaRegistroDocContable(Date fechaRegistroDocContable) {
        this.fechaRegistroDocContable = fechaRegistroDocContable;
    }

    public Date getFechaContabilizacion() {
        return fechaContabilizacion;
    }

    public void setFechaContabilizacion(Date fechaContabilizacion) {
        this.fechaContabilizacion = fechaContabilizacion;
    }

    public Date getFechaBase() {
        return fechaBase;
    }

    public void setFechaBase(Date fechaBase) {
        this.fechaBase = fechaBase;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaPosiblePago() {
        return fechaPosiblePago;
    }

    public void setFechaPosiblePago(Date fechaPosiblePago) {
        this.fechaPosiblePago = fechaPosiblePago;
    }

    public Date getFechaRealPago() {
        return fechaRealPago;
    }

    public void setFechaRealPago(Date fechaRealPago) {
        this.fechaRealPago = fechaRealPago;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getNumeroDocumentoCompensacion() {
        return numeroDocumentoCompensacion;
    }

    public void setNumeroDocumentoCompensacion(String numeroDocumentoCompensacion) {
        this.numeroDocumentoCompensacion = numeroDocumentoCompensacion;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public List<ComprobantePagoItemDto> getComprobantePagoItemDtoList() {
        return comprobantePagoItemDtoList;
    }

    public void setComprobantePagoItemDtoList(List<ComprobantePagoItemDto> comprobantePagoItemDtoList) {
        this.comprobantePagoItemDtoList = comprobantePagoItemDtoList;
    }


    @Override
    public String toString() {
        return "ComprobantePagoDto{" +
                "codigoDocumentoSap='" + codigoDocumentoSap + '\'' +
                ", ejercicio='" + ejercicio + '\'' +
                ", numeroDocumentoContable='" + numeroDocumentoContable + '\'' +
                ", numeroComprobantePago='" + numeroComprobantePago + '\'' +
                ", sociedad=" + sociedad +
                ", codigoSociedad='" + codigoSociedad + '\'' +
                ", proveedorRuc='" + proveedorRuc + '\'' +
                ", tipoComprobante='" + tipoComprobante + '\'' +
                ", codigoMoneda='" + codigoMoneda + '\'' +
                ", subTotal=" + subTotal +
                ", igv=" + igv +
                ", total=" + total +
                ", estado='" + estado + '\'' +
                ", formaPago=" + formaPago +
                ", fechaEmision=" + fechaEmision +
                ", fechaRegistroDocContable=" + fechaRegistroDocContable +
                ", fechaContabilizacion=" + fechaContabilizacion +
                ", fechaBase=" + fechaBase +
                ", fechaVencimiento=" + fechaVencimiento +
                ", fechaPosiblePago=" + fechaPosiblePago +
                ", fechaRealPago=" + fechaRealPago +
                ", fechaPagoNuevo=" + fechaPagoNuevo +
                ", observaciones='" + observaciones + '\'' +
                ", tipoPago='" + tipoPago + '\'' +
                ", numeroDocumentoCompensacion='" + numeroDocumentoCompensacion + '\'' +
                ", banco='" + banco + '\'' +
                ", comprobantePagoItemDtoList=" + comprobantePagoItemDtoList +
                '}';
    }
}
