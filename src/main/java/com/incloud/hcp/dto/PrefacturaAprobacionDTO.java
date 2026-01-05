package com.incloud.hcp.dto;


import com.incloud.hcp.domain.EstadoPrefactura;
import com.incloud.hcp.domain.Sociedad;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class PrefacturaAprobacionDTO {
    private Integer id;
    private EstadoPrefactura estadoPrefactura;
    private Integer idEstadoPrefactura;
    private Sociedad sociedad;
    private String codigoSociedad;
    private String proveedorRuc;
    private String proveedorRazonSocial;
    private Date fechaEmision;
    private Date fechaContabilizacion;
    private Date fechaBase;
    private String indicadorImpuesto;
    private String referencia;
    private String observaciones;
    private String cadenaNumerosOrdenCompra;
    private String cadenaNumerosGuia;
    private String codigoSap;
    private String ejercicio;
    private String numeroDocumentoContable;
    private String codigoMondeda;
    private BigDecimal subTotal;
    private BigDecimal igv;
    private BigDecimal total;
    private Timestamp fechaRecepcion;
    private Timestamp fechaDescarte;
    private Timestamp fechaRegistroSap;
    private String usuarioRegistroSap;
    private String xmlEcmPath;
    private String pdfEcmPath;
    private String centro;
    private String usuarioComprador;
    private String motivoRechazo;
    //Agregado para Aprobación
    private Date fechaPago;
    private Date fechaVencimiento;
    //Mejora 3
    private String usuarioUltimoEstado;
    private Date fechaUltimoEstado;
    private String condicionPago;

    private String xmlEcmPathCf;
    private String pdfEcmPathCf;
    private String archivoIdPdfCf;
    private String archivoIdXmlCf;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EstadoPrefactura getEstadoPrefactura() {
        return estadoPrefactura;
    }

    public void setEstadoPrefactura(EstadoPrefactura estadoPrefactura) {
        this.estadoPrefactura = estadoPrefactura;
    }

    public Integer getIdEstadoPrefactura() {
        return idEstadoPrefactura;
    }

    public void setIdEstadoPrefactura(Integer idEstadoPrefactura) {
        this.idEstadoPrefactura = idEstadoPrefactura;
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

    public String getProveedorRazonSocial() {
        return proveedorRazonSocial;
    }

    public void setProveedorRazonSocial(String proveedorRazonSocial) {
        this.proveedorRazonSocial = proveedorRazonSocial;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
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

    public String getIndicadorImpuesto() {
        return indicadorImpuesto;
    }

    public void setIndicadorImpuesto(String indicadorImpuesto) {
        this.indicadorImpuesto = indicadorImpuesto;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getCadenaNumerosOrdenCompra() {
        return cadenaNumerosOrdenCompra;
    }

    public void setCadenaNumerosOrdenCompra(String cadenaNumerosOrdenCompra) {
        this.cadenaNumerosOrdenCompra = cadenaNumerosOrdenCompra;
    }

    public String getCadenaNumerosGuia() {
        return cadenaNumerosGuia;
    }

    public void setCadenaNumerosGuia(String cadenaNumerosGuia) {
        this.cadenaNumerosGuia = cadenaNumerosGuia;
    }

    public String getCodigoSap() {
        return codigoSap;
    }

    public void setCodigoSap(String codigoSap) {
        this.codigoSap = codigoSap;
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

    public String getCodigoMondeda() {
        return codigoMondeda;
    }

    public void setCodigoMondeda(String codigoMondeda) {
        this.codigoMondeda = codigoMondeda;
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

    public Timestamp getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Timestamp fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Timestamp getFechaDescarte() {
        return fechaDescarte;
    }

    public void setFechaDescarte(Timestamp fechaDescarte) {
        this.fechaDescarte = fechaDescarte;
    }

    public Timestamp getFechaRegistroSap() {
        return fechaRegistroSap;
    }

    public void setFechaRegistroSap(Timestamp fechaRegistroSap) {
        this.fechaRegistroSap = fechaRegistroSap;
    }

    public String getUsuarioRegistroSap() {
        return usuarioRegistroSap;
    }

    public void setUsuarioRegistroSap(String usuarioRegistroSap) {
        this.usuarioRegistroSap = usuarioRegistroSap;
    }

    public String getXmlEcmPath() {
        return xmlEcmPath;
    }

    public void setXmlEcmPath(String xmlEcmPath) {
        this.xmlEcmPath = xmlEcmPath;
    }

    public String getPdfEcmPath() {
        return pdfEcmPath;
    }

    public void setPdfEcmPath(String pdfEcmPath) {
        this.pdfEcmPath = pdfEcmPath;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getUsuarioComprador() {
        return usuarioComprador;
    }

    public void setUsuarioComprador(String usuarioComprador) {
        this.usuarioComprador = usuarioComprador;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getUsuarioUltimoEstado() {
        return usuarioUltimoEstado;
    }

    public void setUsuarioUltimoEstado(String usuarioUltimoEstado) {
        this.usuarioUltimoEstado = usuarioUltimoEstado;
    }

    public Date getFechaUltimoEstado() {
        return fechaUltimoEstado;
    }

    public void setFechaUltimoEstado(Date fechaUltimoEstado) {
        this.fechaUltimoEstado = fechaUltimoEstado;
    }

    public String getCondicionPago() {
        return condicionPago;
    }

    public void setCondicionPago(String condicionPago) {
        this.condicionPago = condicionPago;
    }

    public String getXmlEcmPathCf() {
        return xmlEcmPathCf;
    }

    public void setXmlEcmPathCf(String xmlEcmPathCf) {
        this.xmlEcmPathCf = xmlEcmPathCf;
    }

    public String getPdfEcmPathCf() {
        return pdfEcmPathCf;
    }

    public void setPdfEcmPathCf(String pdfEcmPathCf) {
        this.pdfEcmPathCf = pdfEcmPathCf;
    }

    public String getArchivoIdPdfCf() {
        return archivoIdPdfCf;
    }

    public void setArchivoIdPdfCf(String archivoIdPdfCf) {
        this.archivoIdPdfCf = archivoIdPdfCf;
    }

    public String getArchivoIdXmlCf() {
        return archivoIdXmlCf;
    }

    public void setArchivoIdXmlCf(String archivoIdXmlCf) {
        this.archivoIdXmlCf = archivoIdXmlCf;
    }

    @Override
    public String toString() {
        return "PrefacturaAprobacionDTO{" +
                "id=" + id +
                ", estadoPrefactura=" + estadoPrefactura +
                ", idEstadoPrefactura=" + idEstadoPrefactura +
                ", sociedad=" + sociedad +
                ", codigoSociedad='" + codigoSociedad + '\'' +
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
                ", codigoSap='" + codigoSap + '\'' +
                ", ejercicio='" + ejercicio + '\'' +
                ", numeroDocumentoContable='" + numeroDocumentoContable + '\'' +
                ", codigoMondeda='" + codigoMondeda + '\'' +
                ", subTotal=" + subTotal +
                ", igv=" + igv +
                ", total=" + total +
                ", fechaRecepcion=" + fechaRecepcion +
                ", fechaDescarte=" + fechaDescarte +
                ", fechaRegistroSap=" + fechaRegistroSap +
                ", usuarioRegistroSap='" + usuarioRegistroSap + '\'' +
                ", xmlEcmPath='" + xmlEcmPath + '\'' +
                ", pdfEcmPath='" + pdfEcmPath + '\'' +
                ", centro='" + centro + '\'' +
                ", usuarioComprador='" + usuarioComprador + '\'' +
                ", motivoRechazo='" + motivoRechazo + '\'' +
                ", fechaPago=" + fechaPago +
                ", fechaVencimiento=" + fechaVencimiento +
                ", usuarioUltimoEstado='" + usuarioUltimoEstado + '\'' +
                ", fechaUltimoEstado=" + fechaUltimoEstado +
                ", condicionPago='" + condicionPago + '\'' +
                ", xmlEcmPathCf='" + xmlEcmPathCf + '\'' +
                ", pdfEcmPathCf='" + pdfEcmPathCf + '\'' +
                ", archivoIdXmlCf='" + archivoIdXmlCf + '\'' +
                ", archivoIdPdfCf='" + archivoIdPdfCf + '\'' +
                '}';
    }
}
