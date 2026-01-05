package com.incloud.hcp.jco.comprobanteRetencion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ComprobanteRetencionDto implements Serializable {

    private String numeroDocumentoErp;
    private String sociedad;
    private Integer ejercicio;
    private String tipoComprobante;
    private String serie;
    private String correlativo;
    private String proveedorRuc;
    private String proveedorRazonSocial;
    private Date fechaEmision;
    private String estado;
    private String moneda;
    private BigDecimal tasaRetencion;
    private BigDecimal importeRetencionTotalSoles;
    private BigDecimal importeNetoPagadoTotalSoles;
    private String sociedadRazonSocial;
    private String sociedadDireccion1;
    private String sociedadDireccion2;
    private String sociedadTelefono;
    private String sociedadRuc;

    private List<ComprobanteRetencionItemDto> comprobanteRetencionItemDtoList;

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

    @JsonProperty("Serie")
    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    @JsonProperty("Correlativo")
    public String getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
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

    @JsonProperty("Estado")
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @JsonProperty("Moneda")
    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    @JsonProperty("TasaRetencion")
    public BigDecimal getTasaRetencion() {
        return tasaRetencion;
    }

    public void setTasaRetencion(BigDecimal tasaRetencion) {
        this.tasaRetencion = tasaRetencion;
    }

    @JsonProperty("ImporteRetencionTotalSoles")
    public BigDecimal getImporteRetencionTotalSoles() {
        return importeRetencionTotalSoles;
    }

    public void setImporteRetencionTotalSoles(BigDecimal importeRetencionTotalSoles) {
        this.importeRetencionTotalSoles = importeRetencionTotalSoles;
    }

    @JsonProperty("ImporteNetoPagadoTotalSoles")
    public BigDecimal getImporteNetoPagadoTotalSoles() {
        return importeNetoPagadoTotalSoles;
    }

    public void setImporteNetoPagadoTotalSoles(BigDecimal importeNetoPagadoTotalSoles) {
        this.importeNetoPagadoTotalSoles = importeNetoPagadoTotalSoles;
    }

    @JsonProperty("SociedadRazonSocial")
    public String getSociedadRazonSocial() {
        return sociedadRazonSocial;
    }

    public void setSociedadRazonSocial(String sociedadRazonSocial) {
        this.sociedadRazonSocial = sociedadRazonSocial;
    }

    @JsonProperty("SociedadDireccion1")
    public String getSociedadDireccion1() {
        return sociedadDireccion1;
    }

    public void setSociedadDireccion1(String sociedadDireccion1) {
        this.sociedadDireccion1 = sociedadDireccion1;
    }

    @JsonProperty("SociedadDireccion2")
    public String getSociedadDireccion2() {
        return sociedadDireccion2;
    }

    public void setSociedadDireccion2(String sociedadDireccion2) {
        this.sociedadDireccion2 = sociedadDireccion2;
    }

    @JsonProperty("SociedadTelefono")
    public String getSociedadTelefono() {
        return sociedadTelefono;
    }

    public void setSociedadTelefono(String sociedadTelefono) {
        this.sociedadTelefono = sociedadTelefono;
    }

    @JsonProperty("SociedadRuc")
    public String getSociedadRuc() {
        return sociedadRuc;
    }

    public void setSociedadRuc(String sociedadRuc) {
        this.sociedadRuc = sociedadRuc;
    }


    public List<ComprobanteRetencionItemDto> getComprobanteRetencionItemDtoList() {
        return comprobanteRetencionItemDtoList;
    }

    public void setComprobanteRetencionItemDtoList(List<ComprobanteRetencionItemDto> comprobanteRetencionItemDtoList) {
        this.comprobanteRetencionItemDtoList = comprobanteRetencionItemDtoList;
    }
}
