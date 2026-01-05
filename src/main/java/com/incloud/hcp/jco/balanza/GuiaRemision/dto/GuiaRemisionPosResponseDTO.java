package com.incloud.hcp.jco.balanza.GuiaRemision.dto;

import java.util.Date;

public class GuiaRemisionPosResponseDTO {
    private Integer numPos;
    private String docMaterial;
    private Integer ejercicio;
    private Integer posicionDocumento;
    private String posicion;
    private String material;
    private String descripcion;
    private String lote;
    private Double cantDisponible;
    private String um;

    ////

    private String subticket;
    private String almacen;
    private String unidadMedidaNeto;
    private Double pesoNeto;
    private String unidadMedidaSap;
    private Double pesoSap;
    private String pedidoVenta;
    private Date fechaProduccion;
    private String dam;
    private String documentoTraslado;


    public String getCodigoTipoProducto() {
        return codigoTipoProducto;
    }

    public void setCodigoTipoProducto(String codigoTipoProducto) {
        this.codigoTipoProducto = codigoTipoProducto;
    }

    private String codigoTipoProducto;

    public Integer getNumPos() {
        return numPos;
    }

    public void setNumPos(Integer numPos) {
        this.numPos = numPos;
    }

    public String getDocMaterial() {
        return docMaterial;
    }

    public void setDocMaterial(String docMaterial) {
        this.docMaterial = docMaterial;
    }

    public Integer getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Integer getPosicionDocumento() {
        return posicionDocumento;
    }

    public void setPosicionDocumento(Integer posicionDocumento) {
        this.posicionDocumento = posicionDocumento;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Double getCantDisponible() {
        return cantDisponible;
    }

    public void setCantDisponible(Double cantDisponible) {
        this.cantDisponible = cantDisponible;
    }

    public String getUm() {
        return um;
    }

    public void setUm(String um) {
        this.um = um;
    }

    public String getSubticket() {
        return subticket;
    }

    public void setSubticket(String subticket) {
        this.subticket = subticket;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getUnidadMedidaNeto() {
        return unidadMedidaNeto;
    }

    public void setUnidadMedidaNeto(String unidadMedidaNeto) {
        this.unidadMedidaNeto = unidadMedidaNeto;
    }

    public Double getPesoNeto() {
        return pesoNeto;
    }

    public void setPesoNeto(Double pesoNeto) {
        this.pesoNeto = pesoNeto;
    }

    public String getUnidadMedidaSap() {
        return unidadMedidaSap;
    }

    public void setUnidadMedidaSap(String unidadMedidaSap) {
        this.unidadMedidaSap = unidadMedidaSap;
    }

    public Double getPesoSap() {
        return pesoSap;
    }

    public void setPesoSap(Double pesoSap) {
        this.pesoSap = pesoSap;
    }

    public String getPedidoVenta() {
        return pedidoVenta;
    }

    public void setPedidoVenta(String pedidoVenta) {
        this.pedidoVenta = pedidoVenta;
    }

    public Date getFechaProduccion() {
        return fechaProduccion;
    }

    public void setFechaProduccion(Date fechaProduccion) {
        this.fechaProduccion = fechaProduccion;
    }

    public String getDam() {
        return dam;
    }

    public void setDam(String dam) {
        this.dam = dam;
    }

    public String getDocumentoTraslado() {
        return documentoTraslado;
    }

    public void setDocumentoTraslado(String documentoTraslado) {
        this.documentoTraslado = documentoTraslado;
    }

    @Override
    public String toString() {
        return "GuiaRemisionPosResponseDTO{" +
                "numPos=" + numPos +
                ", docMaterial='" + docMaterial + '\'' +
                ", ejercicio=" + ejercicio +
                ", posicion=" + posicion +
                ", material='" + material + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", lote='" + lote + '\'' +
                ", cantDisponible=" + cantDisponible +
                ", um='" + um + '\'' +
                ", subticket='" + subticket + '\'' +
                ", almacen='" + almacen + '\'' +
                ", unidadMedidaNeto='" + unidadMedidaNeto + '\'' +
                ", pesoNeto=" + pesoNeto +
                ", unidadMedidaSap='" + unidadMedidaSap + '\'' +
                ", pesoSap=" + pesoSap +
                ", pedidoVenta='" + pedidoVenta + '\'' +
                ", fechaProduccion=" + fechaProduccion +
                ", codigoTipoProducto='" + codigoTipoProducto + '\'' +
                '}';
    }
}
