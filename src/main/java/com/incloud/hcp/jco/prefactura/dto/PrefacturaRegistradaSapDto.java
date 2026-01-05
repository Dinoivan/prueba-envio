package com.incloud.hcp.jco.prefactura.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

public class PrefacturaRegistradaSapDto implements Serializable {

    private String referencia;
    private String sociedad;
    private String rucProveedor;
    private String numeroDocumentoFacturaLogistica;
    private String numeroDocumentoContable;
    private String ejercicio;
    private String usuarioSapRegistrador;
    private Date fechaRegistro;
    private Time horaRegistro;
    private Date fechaBase;
    private Date fechaContabilizacion;
    private BigDecimal importe;
    private String indicadorImpuesto;


    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getSociedad() {
        return sociedad;
    }

    public void setSociedad(String sociedad) {
        this.sociedad = sociedad;
    }

    public String getRucProveedor() {
        return rucProveedor;
    }

    public void setRucProveedor(String rucProveedor) {
        this.rucProveedor = rucProveedor;
    }

    public String getNumeroDocumentoFacturaLogistica() {
        return numeroDocumentoFacturaLogistica;
    }

    public void setNumeroDocumentoFacturaLogistica(String numeroDocumentoFacturaLogistica) {
        this.numeroDocumentoFacturaLogistica = numeroDocumentoFacturaLogistica;
    }

    public String getNumeroDocumentoContable() {
        return numeroDocumentoContable;
    }

    public void setNumeroDocumentoContable(String numeroDocumentoContable) {
        this.numeroDocumentoContable = numeroDocumentoContable;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getUsuarioSapRegistrador() {
        return usuarioSapRegistrador;
    }

    public void setUsuarioSapRegistrador(String usuarioSapRegistrador) {
        this.usuarioSapRegistrador = usuarioSapRegistrador;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Time getHoraRegistro() {
        return horaRegistro;
    }

    public void setHoraRegistro(Time horaRegistro) {
        this.horaRegistro = horaRegistro;
    }

    public Date getFechaBase() {
        return fechaBase;
    }

    public void setFechaBase(Date fechaBase) {
        this.fechaBase = fechaBase;
    }

    public Date getFechaContabilizacion() {
        return fechaContabilizacion;
    }

    public void setFechaContabilizacion(Date fechaContabilizacion) {
        this.fechaContabilizacion = fechaContabilizacion;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getIndicadorImpuesto() {
        return indicadorImpuesto;
    }

    public void setIndicadorImpuesto(String indicadorImpuesto) {
        this.indicadorImpuesto = indicadorImpuesto;
    }


    @Override
    public String toString() {
        return "PrefacturaRegistradaSapDto{" +
                "referencia='" + referencia + '\'' +
                ", sociedad='" + sociedad + '\'' +
                ", rucProveedor='" + rucProveedor + '\'' +
                ", numeroDocumentoFacturaLogistica='" + numeroDocumentoFacturaLogistica + '\'' +
                ", numeroDocumentoContable='" + numeroDocumentoContable + '\'' +
                ", ejercicio='" + ejercicio + '\'' +
                ", usuarioSapRegistrador='" + usuarioSapRegistrador + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", horaRegistro=" + horaRegistro +
                ", fechaBase=" + fechaBase +
                ", fechaContabilizacion=" + fechaContabilizacion +
                ", importe=" + importe +
                ", indicadorImpuesto='" + indicadorImpuesto + '\'' +
                '}';
    }
}
