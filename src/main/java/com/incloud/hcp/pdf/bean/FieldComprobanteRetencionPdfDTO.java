package com.incloud.hcp.pdf.bean;

public class FieldComprobanteRetencionPdfDTO {
    private String tipoDocumento;
    private String numeroDocumento;
    private String fechaEmision;
    private String moneda;
    private String importeOperacion;
    private String fechaPago;
    private String numeroPago;
    private String importePago;
    private String totalRetenido;
    private String totalPagado;

    public FieldComprobanteRetencionPdfDTO() {
    }

    public FieldComprobanteRetencionPdfDTO(String tipoDocumento, String numeroDocumento, String fechaEmision, String moneda, String importeOperacion, String fechaPago, String numeroPago, String importePago, String totalRetenido, String totalPagado) {
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.fechaEmision = fechaEmision;
        this.moneda = moneda;
        this.importeOperacion = importeOperacion;
        this.fechaPago = fechaPago;
        this.numeroPago = numeroPago;
        this.importePago = importePago;
        this.totalRetenido = totalRetenido;
        this.totalPagado = totalPagado;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getImporteOperacion() {
        return importeOperacion;
    }

    public void setImporteOperacion(String importeOperacion) {
        this.importeOperacion = importeOperacion;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getNumeroPago() {
        return numeroPago;
    }

    public void setNumeroPago(String numeroPago) {
        this.numeroPago = numeroPago;
    }

    public String getImportePago() {
        return importePago;
    }

    public void setImportePago(String importePago) {
        this.importePago = importePago;
    }

    public String getTotalRetenido() {
        return totalRetenido;
    }

    public void setTotalRetenido(String totalRetenido) {
        this.totalRetenido = totalRetenido;
    }

    public String getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(String totalPagado) {
        this.totalPagado = totalPagado;
    }
}
