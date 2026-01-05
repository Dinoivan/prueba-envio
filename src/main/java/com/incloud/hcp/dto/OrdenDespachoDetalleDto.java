package com.incloud.hcp.dto;

import java.math.BigDecimal;
import java.util.Date;

public class OrdenDespachoDetalleDto {
    private Integer id;
    private BigDecimal cantidad;
    private String posicion;
    private String numeroOrdenCompra;
    private String codigoSapAlmacen;
    private String codigoSapBienServicio;
    private String descripcionBienServicio;
    private String unidadMedidaBienServicio;
    private String codigoSapCentro;
    private String denominacionCentro;
    private String direccionCentro;
    private String denominacionAlmacen;
    private BigDecimal precioUnitario;
    private BigDecimal precioTotal;
    private String indicadorImpuesto;
    private Date fechaEntrega;
    private BigDecimal totalDespachado;
    private BigDecimal cantidadRestante;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getNumeroOrdenCompra() {
        return numeroOrdenCompra;
    }

    public void setNumeroOrdenCompra(String numeroOrdenCompra) {
        this.numeroOrdenCompra = numeroOrdenCompra;
    }

    public String getCodigoSapAlmacen() {
        return codigoSapAlmacen;
    }

    public void setCodigoSapAlmacen(String codigoSapAlmacen) {
        this.codigoSapAlmacen = codigoSapAlmacen;
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

    public String getUnidadMedidaBienServicio() {
        return unidadMedidaBienServicio;
    }

    public void setUnidadMedidaBienServicio(String unidadMedidaBienServicio) {
        this.unidadMedidaBienServicio = unidadMedidaBienServicio;
    }

    public String getCodigoSapCentro() {
        return codigoSapCentro;
    }

    public void setCodigoSapCentro(String codigoSapCentro) {
        this.codigoSapCentro = codigoSapCentro;
    }

    public String getDenominacionCentro() {
        return denominacionCentro;
    }

    public void setDenominacionCentro(String denominacionCentro) {
        this.denominacionCentro = denominacionCentro;
    }

    public String getDireccionCentro() {
        return direccionCentro;
    }

    public void setDireccionCentro(String direccionCentro) {
        this.direccionCentro = direccionCentro;
    }

    public String getDenominacionAlmacen() {
        return denominacionAlmacen;
    }

    public void setDenominacionAlmacen(String denominacionAlmacen) {
        this.denominacionAlmacen = denominacionAlmacen;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getIndicadorImpuesto() {
        return indicadorImpuesto;
    }

    public void setIndicadorImpuesto(String indicadorImpuesto) {
        this.indicadorImpuesto = indicadorImpuesto;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public BigDecimal getTotalDespachado() {
        return totalDespachado;
    }

    public void setTotalDespachado(BigDecimal totalDespachado) {
        this.totalDespachado = totalDespachado;
    }

    public BigDecimal getCantidadRestante() {
        return cantidadRestante;
    }

    public void setCantidadRestante(BigDecimal cantidadRestante) {
        this.cantidadRestante = cantidadRestante;
    }

    @Override
    public String toString() {
        return "OrdenDespachoDetalleDto{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                ", posicion='" + posicion + '\'' +
                ", numeroOrdenCompra='" + numeroOrdenCompra + '\'' +
                ", codigoSapAlmacen='" + codigoSapAlmacen + '\'' +
                ", codigoSapBienServicio='" + codigoSapBienServicio + '\'' +
                ", descripcionBienServicio='" + descripcionBienServicio + '\'' +
                ", unidadMedidaBienServicio='" + unidadMedidaBienServicio + '\'' +
                ", codigoSapCentro='" + codigoSapCentro + '\'' +
                ", denominacionCentro='" + denominacionCentro + '\'' +
                ", direccionCentro='" + direccionCentro + '\'' +
                ", denominacionAlmacen='" + denominacionAlmacen + '\'' +
                ", precioUnitario=" + precioUnitario +
                ", precioTotal=" + precioTotal +
                ", indicadorImpuesto='" + indicadorImpuesto + '\'' +
                ", fechaEntrega=" + fechaEntrega +
                ", totalDespachado=" + totalDespachado +
                ", cantidadRestante=" + cantidadRestante +
                '}';
    }
}
