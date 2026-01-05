package com.incloud.hcp.dto;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;


public class PrefacturaExcelRequestDto {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date fechaEmisionInicio;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date fechaEmisionFin;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date fechaEntradaInicio;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date fechaEntradaFin;
    private String ruc;
    private String referencia;
    private String comprador;
    private String centro;
    private Integer idEstado;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date filtroFechaEmision;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date filtroFechaEntrada;
    private String filtroRuc;
    private String filtroReferencia;
    private String filtroComprador;
    private String filtroCentro;
    private String filtroEstado;
    private String filtroSociedad;
    private String filtroRazonSocial;
    private String filtroDocumentoErp;
    private String filtroImporte;
    private String filtroIndicador;
    private String sortCampo;
    private String sortReversed;


    public Date getFechaEmisionInicio() {
        return fechaEmisionInicio;
    }

    public void setFechaEmisionInicio(Date fechaEmisionInicio) {
        this.fechaEmisionInicio = fechaEmisionInicio;
    }

    public Date getFechaEmisionFin() {
        return fechaEmisionFin;
    }

    public void setFechaEmisionFin(Date fechaEmisionFin) {
        this.fechaEmisionFin = fechaEmisionFin;
    }

    public Date getFechaEntradaInicio() {
        return fechaEntradaInicio;
    }

    public void setFechaEntradaInicio(Date fechaEntradaInicio) {
        this.fechaEntradaInicio = fechaEntradaInicio;
    }

    public Date getFechaEntradaFin() {
        return fechaEntradaFin;
    }

    public void setFechaEntradaFin(Date fechaEntradaFin) {
        this.fechaEntradaFin = fechaEntradaFin;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getComprador() {
        return comprador;
    }

    public void setComprador(String comprador) {
        this.comprador = comprador;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public Date getFiltroFechaEmision() {
        return filtroFechaEmision;
    }

    public void setFiltroFechaEmision(Date filtroFechaEmision) {
        this.filtroFechaEmision = filtroFechaEmision;
    }

    public Date getFiltroFechaEntrada() {
        return filtroFechaEntrada;
    }

    public void setFiltroFechaEntrada(Date filtroFechaEntrada) {
        this.filtroFechaEntrada = filtroFechaEntrada;
    }

    public String getFiltroRuc() {
        return filtroRuc;
    }

    public void setFiltroRuc(String filtroRuc) {
        this.filtroRuc = filtroRuc;
    }

    public String getFiltroReferencia() {
        return filtroReferencia;
    }

    public void setFiltroReferencia(String filtroReferencia) {
        this.filtroReferencia = filtroReferencia;
    }

    public String getFiltroComprador() {
        return filtroComprador;
    }

    public void setFiltroComprador(String filtroComprador) {
        this.filtroComprador = filtroComprador;
    }

    public String getFiltroCentro() {
        return filtroCentro;
    }

    public void setFiltroCentro(String filtroCentro) {
        this.filtroCentro = filtroCentro;
    }

    public String getFiltroEstado() {
        return filtroEstado;
    }

    public void setFiltroEstado(String filtroEstado) {
        this.filtroEstado = filtroEstado;
    }

    public String getFiltroSociedad() {
        return filtroSociedad;
    }

    public void setFiltroSociedad(String filtroSociedad) {
        this.filtroSociedad = filtroSociedad;
    }

    public String getFiltroRazonSocial() {
        return filtroRazonSocial;
    }

    public void setFiltroRazonSocial(String filtroRazonSocial) {
        this.filtroRazonSocial = filtroRazonSocial;
    }

    public String getFiltroDocumentoErp() {
        return filtroDocumentoErp;
    }

    public void setFiltroDocumentoErp(String filtroDocumentoErp) {
        this.filtroDocumentoErp = filtroDocumentoErp;
    }

    public String getFiltroImporte() {
        return filtroImporte;
    }

    public void setFiltroImporte(String filtroImporte) {
        this.filtroImporte = filtroImporte;
    }

    public String getFiltroIndicador() {
        return filtroIndicador;
    }

    public void setFiltroIndicador(String filtroIndicador) {
        this.filtroIndicador = filtroIndicador;
    }

    public String getSortCampo() {
        return sortCampo;
    }

    public void setSortCampo(String sortCampo) {
        this.sortCampo = sortCampo;
    }

    public String getSortReversed() {
        return sortReversed;
    }

    public void setSortReversed(String sortReversed) {
        this.sortReversed = sortReversed;
    }

    @Override
    public String toString() {
        return "PrefacturaExcelRequestDto{" +
                "fechaEmisionInicio=" + fechaEmisionInicio +
                ", fechaEmisionFin=" + fechaEmisionFin +
                ", fechaEntradaInicio=" + fechaEntradaInicio +
                ", fechaEntradaFin=" + fechaEntradaFin +
                ", ruc='" + ruc + '\'' +
                ", referencia='" + referencia + '\'' +
                ", comprador='" + comprador + '\'' +
                ", centro='" + centro + '\'' +
                ", idEstado=" + idEstado +
                ", filtroFechaEmision=" + filtroFechaEmision +
                ", filtroFechaEntrada=" + filtroFechaEntrada +
                ", filtroRuc='" + filtroRuc + '\'' +
                ", filtroReferencia='" + filtroReferencia + '\'' +
                ", filtroComprador='" + filtroComprador + '\'' +
                ", filtroCentro='" + filtroCentro + '\'' +
                ", filtroEstado='" + filtroEstado + '\'' +
                ", filtroSociedad='" + filtroSociedad + '\'' +
                ", filtroRazonSocial='" + filtroRazonSocial + '\'' +
                ", filtroDocumentoErp='" + filtroDocumentoErp + '\'' +
                ", filtroImporte='" + filtroImporte + '\'' +
                ", filtroIndicador='" + filtroIndicador + '\'' +
                ", sortCampo='" + sortCampo + '\'' +
                ", sortReversed='" + sortReversed + '\'' +
                '}';
    }
}
