package com.incloud.hcp.jco.ordenCompra.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class OrdenCompraPdfDto implements Serializable {
    private String ordenCompraNumero;
	private String ordenCompraTipo;
    private String ordenCompraVersion;
    private Date ordenCompraFechaCreacion;
    private String ordenCompraPersonaContacto;
    private String ordenCompraFormaPago;
    private String ordenCompraMoneda;
    private String ordenCompraAutorizador;

    private String clienteRuc;
    private String clienteTelefono;
    private String clienteDireccion;
    private String clienteRazonSocial;

    private String proveedorRazonSocial;
    private String proveedorRuc;
    private String proveedorDireccion;
    private String proveedorContactoNombre;
    private String proveedorContactoTelefono;
    private String proveedorNumero;

    private String lugarEntrega;

    private String lugarEntregaDireccion;

    private String lugarEntregaDescription;
    private String almacenMaterialEmail;
    private String almacenMaterialTelefono1;
    private String almacenMaterialTelefono2;

    private BigDecimal montoSubtotal;
    private BigDecimal montoDescuento;
    private BigDecimal montoIgv;
    private BigDecimal montoImporteTotal;

    private String textoCabecera;
    private String textoCabeceraObservaciones;
    private String textoFecha;
    private String textoNotasImportantes;
    private String textoNotasProveedor;
    private String textoNotasClausula;

    private List<OrdenCompraPosicionPdfDto> ordenCompraPosicionPdfDtoList;


    public String getOrdenCompraNumero() {
        return ordenCompraNumero;
    }

    public void setOrdenCompraNumero(String ordenCompraNumero) {
        this.ordenCompraNumero = ordenCompraNumero;
    }

    public String getOrdenCompraTipo() {
        return ordenCompraTipo;
    }

    public void setOrdenCompraTipo(String ordenCompraTipo) {
        this.ordenCompraTipo = ordenCompraTipo;
    }

    public String getOrdenCompraVersion() {
        return ordenCompraVersion;
    }

    public void setOrdenCompraVersion(String ordenCompraVersion) {
        this.ordenCompraVersion = ordenCompraVersion;
    }

    public Date getOrdenCompraFechaCreacion() {
        return ordenCompraFechaCreacion;
    }

    public void setOrdenCompraFechaCreacion(Date ordenCompraFechaCreacion) {
        this.ordenCompraFechaCreacion = ordenCompraFechaCreacion;
    }

    public String getOrdenCompraPersonaContacto() {
        return ordenCompraPersonaContacto;
    }

    public void setOrdenCompraPersonaContacto(String ordenCompraPersonaContacto) {
        this.ordenCompraPersonaContacto = ordenCompraPersonaContacto;
    }

    public String getOrdenCompraFormaPago() {
        return ordenCompraFormaPago;
    }

    public void setOrdenCompraFormaPago(String ordenCompraFormaPago) {
        this.ordenCompraFormaPago = ordenCompraFormaPago;
    }

    public String getOrdenCompraMoneda() {
        return ordenCompraMoneda;
    }

    public void setOrdenCompraMoneda(String ordenCompraMoneda) {
        this.ordenCompraMoneda = ordenCompraMoneda;
    }

    public String getOrdenCompraAutorizador() {
        return ordenCompraAutorizador;
    }

    public void setOrdenCompraAutorizador(String ordenCompraAutorizador) {
        this.ordenCompraAutorizador = ordenCompraAutorizador;
    }

    public String getClienteRuc() {
        return clienteRuc;
    }

    public void setClienteRuc(String clienteRuc) {
        this.clienteRuc = clienteRuc;
    }

    public String getClienteTelefono() {
        return clienteTelefono;
    }

    public void setClienteTelefono(String clienteTelefono) {
        this.clienteTelefono = clienteTelefono;
    }

    public String getClienteDireccion() {
        return clienteDireccion;
    }

    public void setClienteDireccion(String clienteDireccion) {
        this.clienteDireccion = clienteDireccion;
    }

    public String getClienteRazonSocial() {
        return clienteRazonSocial;
    }

    public void setClienteRazonSocial(String clienteRazonSocial) {
        this.clienteRazonSocial = clienteRazonSocial;
    }

    public String getProveedorRazonSocial() {
        return proveedorRazonSocial;
    }

    public void setProveedorRazonSocial(String proveedorRazonSocial) {
        this.proveedorRazonSocial = proveedorRazonSocial;
    }

    public String getProveedorRuc() {
        return proveedorRuc;
    }

    public void setProveedorRuc(String proveedorRuc) {
        this.proveedorRuc = proveedorRuc;
    }

    public String getProveedorDireccion() {
        return proveedorDireccion;
    }

    public void setProveedorDireccion(String proveedorDireccion) {
        this.proveedorDireccion = proveedorDireccion;
    }

    public String getProveedorContactoNombre() {
        return proveedorContactoNombre;
    }

    public void setProveedorContactoNombre(String proveedorContactoNombre) {
        this.proveedorContactoNombre = proveedorContactoNombre;
    }

    public String getProveedorContactoTelefono() {
        return proveedorContactoTelefono;
    }

    public void setProveedorContactoTelefono(String proveedorContactoTelefono) {
        this.proveedorContactoTelefono = proveedorContactoTelefono;
    }

    public String getProveedorNumero() {
        return proveedorNumero;
    }

    public void setProveedorNumero(String proveedorNumero) {
        this.proveedorNumero = proveedorNumero;
    }

    public String getLugarEntrega() {
        return lugarEntrega;
    }

    public void setLugarEntrega(String lugarEntrega) {
        this.lugarEntrega = lugarEntrega;
    }

    public String getLugarEntregaDireccion(){return lugarEntregaDireccion;}

    public void setLugarEntregaDireccion(String lugarEntregaDireccion){this.lugarEntregaDireccion = lugarEntregaDireccion;}

    public String getLugarEntregaDescription() {return lugarEntregaDescription;}

    public void setLugarEntregaDescription(String lugarEntregaDescription){this.lugarEntregaDescription = lugarEntregaDescription;}


    public String getAlmacenMaterialEmail() {
        return almacenMaterialEmail;
    }

    public void setAlmacenMaterialEmail(String almacenMaterialEmail) {
        this.almacenMaterialEmail = almacenMaterialEmail;
    }

    public String getAlmacenMaterialTelefono1() {
        return almacenMaterialTelefono1;
    }

    public void setAlmacenMaterialTelefono1(String almacenMaterialTelefono1) {
        this.almacenMaterialTelefono1 = almacenMaterialTelefono1;
    }

    public String getAlmacenMaterialTelefono2() {
        return almacenMaterialTelefono2;
    }

    public void setAlmacenMaterialTelefono2(String almacenMaterialTelefono2) {
        this.almacenMaterialTelefono2 = almacenMaterialTelefono2;
    }

    public BigDecimal getMontoSubtotal() {
        return montoSubtotal;
    }

    public void setMontoSubtotal(BigDecimal montoSubtotal) {
        this.montoSubtotal = montoSubtotal;
    }

    public BigDecimal getMontoDescuento() {
        return montoDescuento;
    }

    public void setMontoDescuento(BigDecimal montoDescuento) {
        this.montoDescuento = montoDescuento;
    }

    public BigDecimal getMontoIgv() {
        return montoIgv;
    }

    public void setMontoIgv(BigDecimal montoIgv) {
        this.montoIgv = montoIgv;
    }

    public BigDecimal getMontoImporteTotal() {
        return montoImporteTotal;
    }

    public void setMontoImporteTotal(BigDecimal montoImporteTotal) {
        this.montoImporteTotal = montoImporteTotal;
    }

    public String getTextoCabecera() {
        return textoCabecera;
    }

    public void setTextoCabecera(String textoCabecera) {
        this.textoCabecera = textoCabecera;
    }

    public String getTextoCabeceraObservaciones() {
        return textoCabeceraObservaciones;
    }

    public void setTextoCabeceraObservaciones(String textoCabeceraObservaciones) {
        this.textoCabeceraObservaciones = textoCabeceraObservaciones;
    }

    public String getTextoFecha() {
        return textoFecha;
    }

    public void setTextoFecha(String textoFecha) {
        this.textoFecha = textoFecha;
    }

    public String getTextoNotasImportantes() {
        return textoNotasImportantes;
    }

    public void setTextoNotasImportantes(String textoNotasImportantes) {
        this.textoNotasImportantes = textoNotasImportantes;
    }

    public String getTextoNotasProveedor() {
        return textoNotasProveedor;
    }

    public void setTextoNotasProveedor(String textoNotasProveedor) {
        this.textoNotasProveedor = textoNotasProveedor;
    }

    public String getTextoNotasClausula() {
        return textoNotasClausula;
    }

    public void setTextoNotasClausula(String textoNotasClausula) {
        this.textoNotasClausula = textoNotasClausula;
    }

    public List<OrdenCompraPosicionPdfDto> getOrdenCompraPosicionPdfDtoList() {
        return ordenCompraPosicionPdfDtoList;
    }

    public void setOrdenCompraPosicionPdfDtoList(List<OrdenCompraPosicionPdfDto> ordenCompraPosicionPdfDtoList) {
        this.ordenCompraPosicionPdfDtoList = ordenCompraPosicionPdfDtoList;
    }


    @Override
    public String toString() {
        return "OrdenCompraPdfDto{" +
                "ordenCompraNumero='" + ordenCompraNumero + '\'' +
                ", ordenCompraTipo='" + ordenCompraTipo + '\'' +
                ", ordenCompraVersion='" + ordenCompraVersion + '\'' +
                ", ordenCompraFechaCreacion=" + ordenCompraFechaCreacion +
                ", ordenCompraPersonaContacto='" + ordenCompraPersonaContacto + '\'' +
                ", ordenCompraFormaPago='" + ordenCompraFormaPago + '\'' +
                ", ordenCompraMoneda='" + ordenCompraMoneda + '\'' +
                ", ordenCompraAutorizador='" + ordenCompraAutorizador + '\'' +
                ", clienteRuc='" + clienteRuc + '\'' +
                ", clienteTelefono='" + clienteTelefono + '\'' +
                ", clienteDireccion='" + clienteDireccion + '\'' +
                ", clienteRazonSocial='" + clienteRazonSocial + '\'' +
                ", proveedorRazonSocial='" + proveedorRazonSocial + '\'' +
                ", proveedorRuc='" + proveedorRuc + '\'' +
                ", proveedorDireccion='" + proveedorDireccion + '\'' +
                ", proveedorContactoNombre='" + proveedorContactoNombre + '\'' +
                ", proveedorContactoTelefono='" + proveedorContactoTelefono + '\'' +
                ", proveedorNumero='" + proveedorNumero + '\'' +
                ", lugarEntrega='" + lugarEntrega + '\'' +
                ", lugarEntregaDireccion='" + lugarEntregaDireccion + '\'' +
                ", lugarEntregaDescription='" + lugarEntregaDescription + '\'' +
                ", almacenMaterialEmail='" + almacenMaterialEmail + '\'' +
                ", almacenMaterialTelefono1='" + almacenMaterialTelefono1 + '\'' +
                ", almacenMaterialTelefono2='" + almacenMaterialTelefono2 + '\'' +
                ", montoSubtotal=" + montoSubtotal +
                ", montoDescuento=" + montoDescuento +
                ", montoIgv=" + montoIgv +
                ", montoImporteTotal=" + montoImporteTotal +
                ", textoCabecera='" + textoCabecera + '\'' +
                ", textoCabeceraObservaciones='" + textoCabeceraObservaciones + '\'' +
                ", textoFecha='" + textoFecha + '\'' +
                ", textoNotasImportantes='" + textoNotasImportantes + '\'' +
                ", textoNotasProveedor='" + textoNotasProveedor + '\'' +
                ", textoNotasClausula='" + textoNotasClausula + '\'' +
                ", ordenCompraPosicionPdfDtoList=" + ordenCompraPosicionPdfDtoList +
                '}';
    }
}