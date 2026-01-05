package com.incloud.hcp.jco.ordenCompra.dto;

import com.incloud.hcp.domain._framework.BaseDomain;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class OrdenCompraPosicionPdfDto extends BaseDomain implements Serializable {
	private String numeroOrdenCompra;
    private String posicion;
    private String centro;
	private String material;
    private String codigoProv;
	private String descripcion;
    private BigDecimal cantidad;
	private String unidad;
    private Date fechaEntrega;
    private BigDecimal precioUnitario;
    private BigDecimal cantidadBase;
    private BigDecimal importe;

    private OrdenCompraPosicionDataAdicionalPdfDto ordenCompraPosicionDataAdicionalPdfDto;


    public String getNumeroOrdenCompra() {
        return numeroOrdenCompra;
    }

    public void setNumeroOrdenCompra(String numeroOrdenCompra) {
        this.numeroOrdenCompra = numeroOrdenCompra;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCodigoProv() {
        return codigoProv;
    }

    public void setCodigoProv(String codigoProv) {
        this.codigoProv = codigoProv;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getCantidadBase() {
        return cantidadBase;
    }

    public void setCantidadBase(BigDecimal cantidadBase) {
        this.cantidadBase = cantidadBase;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public OrdenCompraPosicionDataAdicionalPdfDto getOrdenCompraPosicionDataAdicionalPdfDto() {
        return ordenCompraPosicionDataAdicionalPdfDto;
    }

    public void setOrdenCompraPosicionDataAdicionalPdfDto(OrdenCompraPosicionDataAdicionalPdfDto ordenCompraPosicionDataAdicionalPdfDto) {
        this.ordenCompraPosicionDataAdicionalPdfDto = ordenCompraPosicionDataAdicionalPdfDto;
    }


    @Override
    public String toString() {
        return "OrdenCompraPosicionPdfDto{" +
                "numeroOrdenCompra='" + numeroOrdenCompra + '\'' +
                ", posicion='" + posicion + '\'' +
                ", centro='" + centro + '\'' +
                ", material='" + material + '\'' +
                ", codigoProv='" + codigoProv + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cantidad=" + cantidad +
                ", unidad='" + unidad + '\'' +
                ", fechaEntrega=" + fechaEntrega +
                ", precioUnitario=" + precioUnitario +
                ", cantidadBase=" + cantidadBase +
                ", importe=" + importe +
                ", ordenCompraPosicionDataAdicionalPdfDto=" + ordenCompraPosicionDataAdicionalPdfDto +
                '}';
    }
}