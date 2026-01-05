package com.incloud.hcp.pdf.bean;

import java.math.BigDecimal;
import java.util.List;


public class PrefacturaPdfDto {
    private String clienteRuc;
    private String clienteRazonSocial;
    private String proveedorRuc;
    private String proveedorRazonSocial;
    private String prefacturaReferencia;
    private String prefacturaEstado;
    private String prefacturaOrdenes;
    private String prefacturaGuias;
    private String prefacturaFechaEmision;
    private String prefacturaFechaRegistro;
    private String prefacturaIndicadorImpuesto;
    private String prefacturaMoneda;
    private String prefacturaObservaciones;
    private BigDecimal montoSubtotal; // bigdecimal (2 decimales)
    private BigDecimal montoIgv; // bigdecimal (2 decimales)
    private BigDecimal montoTotal; // bigdecimal (2 decimales)

    private List<PrefacturaItemPdfDto> prefacturaItemPdfDtoList;


    public String getClienteRuc() {
        return clienteRuc;
    }

    public void setClienteRuc(String clienteRuc) {
        this.clienteRuc = clienteRuc;
    }

    public String getClienteRazonSocial() {
        return clienteRazonSocial;
    }

    public void setClienteRazonSocial(String clienteRazonSocial) {
        this.clienteRazonSocial = clienteRazonSocial;
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

    public String getPrefacturaReferencia() {
        return prefacturaReferencia;
    }

    public void setPrefacturaReferencia(String prefacturaReferencia) {
        this.prefacturaReferencia = prefacturaReferencia;
    }

    public String getPrefacturaEstado() {
        return prefacturaEstado;
    }

    public void setPrefacturaEstado(String prefacturaEstado) {
        this.prefacturaEstado = prefacturaEstado;
    }

    public String getPrefacturaOrdenes() {
        return prefacturaOrdenes;
    }

    public void setPrefacturaOrdenes(String prefacturaOrdenes) {
        this.prefacturaOrdenes = prefacturaOrdenes;
    }

    public String getPrefacturaGuias() {
        return prefacturaGuias;
    }

    public void setPrefacturaGuias(String prefacturaGuias) {
        this.prefacturaGuias = prefacturaGuias;
    }

    public String getPrefacturaFechaEmision() {
        return prefacturaFechaEmision;
    }

    public void setPrefacturaFechaEmision(String prefacturaFechaEmision) {
        this.prefacturaFechaEmision = prefacturaFechaEmision;
    }

    public String getPrefacturaFechaRegistro() {
        return prefacturaFechaRegistro;
    }

    public void setPrefacturaFechaRegistro(String prefacturaFechaRegistro) {
        this.prefacturaFechaRegistro = prefacturaFechaRegistro;
    }

    public String getPrefacturaIndicadorImpuesto() {
        return prefacturaIndicadorImpuesto;
    }

    public void setPrefacturaIndicadorImpuesto(String prefacturaIndicadorImpuesto) {
        this.prefacturaIndicadorImpuesto = prefacturaIndicadorImpuesto;
    }

    public String getPrefacturaMoneda() {
        return prefacturaMoneda;
    }

    public void setPrefacturaMoneda(String prefacturaMoneda) {
        this.prefacturaMoneda = prefacturaMoneda;
    }

    public String getPrefacturaObservaciones() {
        return prefacturaObservaciones;
    }

    public void setPrefacturaObservaciones(String prefacturaObservaciones) {
        this.prefacturaObservaciones = prefacturaObservaciones;
    }

    public BigDecimal getMontoSubtotal() {
        return montoSubtotal;
    }

    public void setMontoSubtotal(BigDecimal montoSubtotal) {
        this.montoSubtotal = montoSubtotal;
    }

    public BigDecimal getMontoIgv() {
        return montoIgv;
    }

    public void setMontoIgv(BigDecimal montoIgv) {
        this.montoIgv = montoIgv;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public List<PrefacturaItemPdfDto> getPrefacturaItemPdfDtoList() {
        return prefacturaItemPdfDtoList;
    }

    public void setPrefacturaItemPdfDtoList(List<PrefacturaItemPdfDto> prefacturaItemPdfDtoList) {
        this.prefacturaItemPdfDtoList = prefacturaItemPdfDtoList;
    }


    @Override
    public String toString() {
        return "PrefacturaPdfDto{" +
                "clienteRuc='" + clienteRuc + '\'' +
                ", clienteRazonSocial='" + clienteRazonSocial + '\'' +
                ", proveedorRuc='" + proveedorRuc + '\'' +
                ", proveedorRazonSocial='" + proveedorRazonSocial + '\'' +
                ", prefacturaReferencia='" + prefacturaReferencia + '\'' +
                ", prefacturaEstado='" + prefacturaEstado + '\'' +
                ", prefacturaOrdenes='" + prefacturaOrdenes + '\'' +
                ", prefacturaGuias='" + prefacturaGuias + '\'' +
                ", prefacturaFechaEmision='" + prefacturaFechaEmision + '\'' +
                ", prefacturaFechaRegistro='" + prefacturaFechaRegistro + '\'' +
                ", prefacturaIndicadorImpuesto='" + prefacturaIndicadorImpuesto + '\'' +
                ", prefacturaMoneda='" + prefacturaMoneda + '\'' +
                ", prefacturaObservaciones='" + prefacturaObservaciones + '\'' +
                ", montoSubtotal=" + montoSubtotal +
                ", montoIgv=" + montoIgv +
                ", montoTotal=" + montoTotal +
                ", prefacturaItemPdfDtoList=" + prefacturaItemPdfDtoList +
                '}';
    }
}
