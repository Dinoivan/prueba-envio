package com.incloud.hcp.pdf.bean;

import java.util.List;

public class ParameterComprobanteRetencionPdfDTO {


    private String serieCorrelativo;
    private String rucEmisor;
    private String nombreEmisor;
    private String direccionEmisor;

    private String identificacionCliente;
    private String nombreCliente;
    private String fechaEmision;
    private String tasaRetencion;

    private String totalPagado;
    private String totalRetenido;

    private List<FieldComprobanteRetencionPdfDTO> fieldComprobanteRetencionPdfDTOList;


    public ParameterComprobanteRetencionPdfDTO() {
    }

    public ParameterComprobanteRetencionPdfDTO(String serieCorrelativo, String rucEmisor, String nombreEmisor, String direccionEmisor, String identificacionCliente, String nombreCliente, String fechaEmision, String tasaRetencion, String totalPagado, String totalRetenido) {
        this.serieCorrelativo = serieCorrelativo;
        this.rucEmisor = rucEmisor;
        this.nombreEmisor = nombreEmisor;
        this.direccionEmisor = direccionEmisor;
        this.identificacionCliente = identificacionCliente;
        this.nombreCliente = nombreCliente;
        this.fechaEmision = fechaEmision;
        this.tasaRetencion = tasaRetencion;
        this.totalPagado = totalPagado;
        this.totalRetenido = totalRetenido;
    }

    public String getSerieCorrelativo() {
        return serieCorrelativo;
    }

    public void setSerieCorrelativo(String serieCorrelativo) {
        this.serieCorrelativo = serieCorrelativo;
    }

    public String getRucEmisor() {
        return rucEmisor;
    }

    public void setRucEmisor(String rucEmisor) {
        this.rucEmisor = rucEmisor;
    }

    public String getNombreEmisor() {
        return nombreEmisor;
    }

    public void setNombreEmisor(String nombreEmisor) {
        this.nombreEmisor = nombreEmisor;
    }

    public String getDireccionEmisor() {
        return direccionEmisor;
    }

    public void setDireccionEmisor(String direccionEmisor) {
        this.direccionEmisor = direccionEmisor;
    }

    public String getIdentificacionCliente() {
        return identificacionCliente;
    }

    public void setIdentificacionCliente(String identificacionCliente) {
        this.identificacionCliente = identificacionCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getTasaRetencion() {
        return tasaRetencion;
    }

    public void setTasaRetencion(String tasaRetencion) {
        this.tasaRetencion = tasaRetencion;
    }

    public String getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(String totalPagado) {
        this.totalPagado = totalPagado;
    }

    public String getTotalRetenido() {
        return totalRetenido;
    }

    public void setTotalRetenido(String totalRetenido) {
        this.totalRetenido = totalRetenido;
    }

    public List<FieldComprobanteRetencionPdfDTO> getFieldComprobanteRetencionPdfDTOList() {
        return fieldComprobanteRetencionPdfDTOList;
    }

    public void setFieldComprobanteRetencionPdfDTOList(List<FieldComprobanteRetencionPdfDTO> fieldComprobanteRetencionPdfDTOList) {
        this.fieldComprobanteRetencionPdfDTOList = fieldComprobanteRetencionPdfDTOList;
    }
}
