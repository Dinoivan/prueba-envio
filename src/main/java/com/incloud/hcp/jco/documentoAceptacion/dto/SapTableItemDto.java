package com.incloud.hcp.jco.documentoAceptacion.dto;

import java.math.BigDecimal;
import java.util.Date;

// para la tabla "T_HPEDIDO" (lista de items de EM o HES)
public class SapTableItemDto{

    private String numeroDocumentoAceptacion;
    private Integer numeroItem;
    private String movimiento;
    private String numeroOrdenCompra;
    private String posicionOrdenCompra;
    private String numeroGuiaProveedor;
    private String codigoMaterial;
    private String descripcionMaterial;
    private String descripcionServicio;
    private String unidadMedidaMaterial;
    private String unidadMedidaServicio;
    private String codigoMoneda;
    private String usuarioSapRecepcion;
    private BigDecimal cantidadAceptadaClienteMaterial;
    private BigDecimal cantidadAceptadaClienteServicio;
    private BigDecimal cantidadPendiente;
    private BigDecimal precioUnitario;
    private BigDecimal valorRecibido;
    private BigDecimal valorRecibidoMonedalocal;
    private BigDecimal valorRecibidoServicio;
    private Date fechaEmision;
    private Date fechaAceptacion;
    private Date fechaContabilizacion;
    private String indicadorImpuesto;
    private String numDocApectacionRelacionado;
    private Integer numItemRelacionado;
    private String status;


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

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
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

    public String getNumeroGuiaProveedor() {
        return numeroGuiaProveedor;
    }

    public void setNumeroGuiaProveedor(String numeroGuiaProveedor) {
        this.numeroGuiaProveedor = numeroGuiaProveedor;
    }

    public String getCodigoMaterial() {
        return codigoMaterial;
    }

    public void setCodigoMaterial(String codigoMaterial) {
        this.codigoMaterial = codigoMaterial;
    }

    public String getDescripcionMaterial() {
        return descripcionMaterial;
    }

    public void setDescripcionMaterial(String descripcionMaterial) {
        this.descripcionMaterial = descripcionMaterial;
    }

    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    public String getUnidadMedidaMaterial() {
        return unidadMedidaMaterial;
    }

    public void setUnidadMedidaMaterial(String unidadMedidaMaterial) {
        this.unidadMedidaMaterial = unidadMedidaMaterial;
    }

    public String getUnidadMedidaServicio() {
        return unidadMedidaServicio;
    }

    public void setUnidadMedidaServicio(String unidadMedidaServicio) {
        this.unidadMedidaServicio = unidadMedidaServicio;
    }

    public String getCodigoMoneda() {
        return codigoMoneda;
    }

    public void setCodigoMoneda(String codigoMoneda) {
        this.codigoMoneda = codigoMoneda;
    }

    public String getUsuarioSapRecepcion() {
        return usuarioSapRecepcion;
    }

    public void setUsuarioSapRecepcion(String usuarioSapRecepcion) {
        this.usuarioSapRecepcion = usuarioSapRecepcion;
    }

    public BigDecimal getCantidadAceptadaClienteMaterial() {
        return cantidadAceptadaClienteMaterial;
    }

    public void setCantidadAceptadaClienteMaterial(BigDecimal cantidadAceptadaClienteMaterial) {
        this.cantidadAceptadaClienteMaterial = cantidadAceptadaClienteMaterial;
    }

    public BigDecimal getCantidadAceptadaClienteServicio() {
        return cantidadAceptadaClienteServicio;
    }

    public void setCantidadAceptadaClienteServicio(BigDecimal cantidadAceptadaClienteServicio) {
        this.cantidadAceptadaClienteServicio = cantidadAceptadaClienteServicio;
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

    public BigDecimal getValorRecibidoServicio() {
        return valorRecibidoServicio;
    }

    public void setValorRecibidoServicio(BigDecimal valorRecibidoServicio) {
        this.valorRecibidoServicio = valorRecibidoServicio;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaAceptacion() {
        return fechaAceptacion;
    }

    public void setFechaAceptacion(Date fechaAceptacion) {
        this.fechaAceptacion = fechaAceptacion;
    }

    public Date getFechaContabilizacion() { return fechaContabilizacion; }

    public void setFechaContabilizacion(Date fechaContabilizacion) { this.fechaContabilizacion = fechaContabilizacion; }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "SapTableItemDto{" +
                "numeroDocumentoAceptacion='" + numeroDocumentoAceptacion + '\'' +
                ", numeroItem=" + numeroItem +
                ", movimiento='" + movimiento + '\'' +
                ", numeroOrdenCompra='" + numeroOrdenCompra + '\'' +
                ", posicionOrdenCompra='" + posicionOrdenCompra + '\'' +
                ", numeroGuiaProveedor='" + numeroGuiaProveedor + '\'' +
                ", codigoMaterial='" + codigoMaterial + '\'' +
                ", descripcionMaterial='" + descripcionMaterial + '\'' +
                ", descripcionServicio='" + descripcionServicio + '\'' +
                ", unidadMedidaMaterial='" + unidadMedidaMaterial + '\'' +
                ", unidadMedidaServicio='" + unidadMedidaServicio + '\'' +
                ", codigoMoneda='" + codigoMoneda + '\'' +
                ", usuarioSapRecepcion='" + usuarioSapRecepcion + '\'' +
                ", cantidadAceptadaClienteMaterial=" + cantidadAceptadaClienteMaterial +
                ", cantidadAceptadaClienteServicio=" + cantidadAceptadaClienteServicio +
                ", cantidadPendiente=" + cantidadPendiente +
                ", precioUnitario=" + precioUnitario +
                ", valorRecibido=" + valorRecibido +
                ", valorRecibidoMonedalocal=" + valorRecibidoMonedalocal +
                ", valorRecibidoServicio=" + valorRecibidoServicio +
                ", fechaEmision=" + fechaEmision +
                ", fechaAceptacion=" + fechaAceptacion +
                ", fechaContabilizacion=" + fechaContabilizacion +
                ", indicadorImpuesto='" + indicadorImpuesto + '\'' +
                ", numDocApectacionRelacionado='" + numDocApectacionRelacionado + '\'' +
                ", numItemRelacionado=" + numItemRelacionado +
                ", status='" + status + '\'' +
                '}';
    }
}
