package com.incloud.hcp.dto;

import java.math.BigDecimal;

public class DocumentoAceptacionDetalleDto {
    private Integer id;
    private DocumentoAceptacionDto documentoAceptacion;
    private Integer idDocumentoAceptacion;
    private EstadoDocumentoAceptacionDto estadoDocumentoAceptacion;
    private Integer idEstadoDocumentoAceptacionDetalle;
    private String numeroDocumentoAceptacion;
    private Integer numeroItem;
    private String numeroOrdenCompra;
    private String posicionOrdenCompra;
    private String movimiento;
    private String codigoSapBienServicio;
    private String descripcionBienServicio;
    private String unidadMedida;
    private BigDecimal cantidadAceptadaCliente;
    private BigDecimal cantidadPendiente;
    private BigDecimal precioUnitario;
    private BigDecimal valorRecibido;
    private BigDecimal valorRecibidoMonedalocal;
    private String indicadorImpuesto;
    private String numDocApectacionRelacionado;
    private Integer numItemRelacionado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DocumentoAceptacionDto getDocumentoAceptacion() {
        return documentoAceptacion;
    }

    public void setDocumentoAceptacion(DocumentoAceptacionDto documentoAceptacion) {
        this.documentoAceptacion = documentoAceptacion;
    }

    public Integer getIdDocumentoAceptacion() {
        return idDocumentoAceptacion;
    }

    public void setIdDocumentoAceptacion(Integer idDocumentoAceptacion) {
        this.idDocumentoAceptacion = idDocumentoAceptacion;
    }

    public EstadoDocumentoAceptacionDto getEstadoDocumentoAceptacion() {
        return estadoDocumentoAceptacion;
    }

    public void setEstadoDocumentoAceptacion(EstadoDocumentoAceptacionDto estadoDocumentoAceptacion) {
        this.estadoDocumentoAceptacion = estadoDocumentoAceptacion;
    }

    public Integer getIdEstadoDocumentoAceptacionDetalle() {
        return idEstadoDocumentoAceptacionDetalle;
    }

    public void setIdEstadoDocumentoAceptacionDetalle(Integer idEstadoDocumentoAceptacionDetalle) {
        this.idEstadoDocumentoAceptacionDetalle = idEstadoDocumentoAceptacionDetalle;
    }

    public String getNumeroDocumentoAceptacion() {
        return numeroDocumentoAceptacion;
    }

    public void setNumeroDocumentoAceptacion(String numeroDocumentoAceptacion) {
        this.numeroDocumentoAceptacion = numeroDocumentoAceptacion;
    }

    public Integer getNumeroItem() {
        return numeroItem;
    }

    public void setNumeroItem(Integer numeroItem) {
        this.numeroItem = numeroItem;
    }

    public String getNumeroOrdenCompra() {
        return numeroOrdenCompra;
    }

    public void setNumeroOrdenCompra(String numeroOrdenCompra) {
        this.numeroOrdenCompra = numeroOrdenCompra;
    }

    public String getPosicionOrdenCompra() {
        return posicionOrdenCompra;
    }

    public void setPosicionOrdenCompra(String posicionOrdenCompra) {
        this.posicionOrdenCompra = posicionOrdenCompra;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public String getCodigoSapBienServicio() {
        return codigoSapBienServicio;
    }

    public void setCodigoSapBienServicio(String codigoSapBienServicio) {
        this.codigoSapBienServicio = codigoSapBienServicio;
    }

    public String getDescripcionBienServicio() {
        return descripcionBienServicio;
    }

    public void setDescripcionBienServicio(String descripcionBienServicio) {
        this.descripcionBienServicio = descripcionBienServicio;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public BigDecimal getCantidadAceptadaCliente() {
        return cantidadAceptadaCliente;
    }

    public void setCantidadAceptadaCliente(BigDecimal cantidadAceptadaCliente) {
        this.cantidadAceptadaCliente = cantidadAceptadaCliente;
    }

    public BigDecimal getCantidadPendiente() {
        return cantidadPendiente;
    }

    public void setCantidadPendiente(BigDecimal cantidadPendiente) {
        this.cantidadPendiente = cantidadPendiente;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getValorRecibido() {
        return valorRecibido;
    }

    public void setValorRecibido(BigDecimal valorRecibido) {
        this.valorRecibido = valorRecibido;
    }

    public BigDecimal getValorRecibidoMonedalocal() {
        return valorRecibidoMonedalocal;
    }

    public void setValorRecibidoMonedalocal(BigDecimal valorRecibidoMonedalocal) {
        this.valorRecibidoMonedalocal = valorRecibidoMonedalocal;
    }

    public String getIndicadorImpuesto() {
        return indicadorImpuesto;
    }

    public void setIndicadorImpuesto(String indicadorImpuesto) {
        this.indicadorImpuesto = indicadorImpuesto;
    }

    public String getNumDocApectacionRelacionado() {
        return numDocApectacionRelacionado;
    }

    public void setNumDocApectacionRelacionado(String numDocApectacionRelacionado) {
        this.numDocApectacionRelacionado = numDocApectacionRelacionado;
    }

    public Integer getNumItemRelacionado() {
        return numItemRelacionado;
    }

    public void setNumItemRelacionado(Integer numItemRelacionado) {
        this.numItemRelacionado = numItemRelacionado;
    }

    @Override
    public String toString() {
        return "DocumentoAceptacionDetalleDto{" +
                "id=" + id +
                ", documentoAceptacion=" + documentoAceptacion +
                ", idDocumentoAceptacion=" + idDocumentoAceptacion +
                ", estadoDocumentoAceptacion=" + estadoDocumentoAceptacion +
                ", idEstadoDocumentoAceptacionDetalle=" + idEstadoDocumentoAceptacionDetalle +
                ", numeroDocumentoAceptacion='" + numeroDocumentoAceptacion + '\'' +
                ", numeroItem=" + numeroItem +
                ", numeroOrdenCompra='" + numeroOrdenCompra + '\'' +
                ", posicionOrdenCompra='" + posicionOrdenCompra + '\'' +
                ", movimiento='" + movimiento + '\'' +
                ", codigoSapBienServicio='" + codigoSapBienServicio + '\'' +
                ", descripcionBienServicio='" + descripcionBienServicio + '\'' +
                ", unidadMedida='" + unidadMedida + '\'' +
                ", cantidadAceptadaCliente=" + cantidadAceptadaCliente +
                ", cantidadPendiente=" + cantidadPendiente +
                ", precioUnitario=" + precioUnitario +
                ", valorRecibido=" + valorRecibido +
                ", valorRecibidoMonedalocal=" + valorRecibidoMonedalocal +
                ", indicadorImpuesto='" + indicadorImpuesto + '\'' +
                ", numDocApectacionRelacionado='" + numDocApectacionRelacionado + '\'' +
                ", numItemRelacionado=" + numItemRelacionado +
                '}';
    }
}
