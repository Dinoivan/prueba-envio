package com.incloud.hcp.dto;

import com.incloud.hcp.domain.DocumentoAceptacionDetalle;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class DocumentoAceptacionDto {
    private Integer id;
    private String numeroDocumentoAceptacion;
    private TipoDocumentoAceptacionDto tipoDocumentoAceptacion;
    private Integer idTipoDocumentoAceptacion;
    private EstadoDocumentoAceptacionDto estadoDocumentoAceptacion;
    private Integer idEstadoDocumentoAceptacion;
    private Integer idOrdenCompra;
    private String numeroOrdenCompra;
    private OrdenCompraDto ordenCompra;
    private String posicionOrdenCompra;
    private String numeroGuiaProveedor;
    private String proveedorRuc;
    private String proveedorRazonSocial;
    private String usuarioSapRecepcion;
    private String usuarioSapAutoriza;
    private String codigoMoneda;
    private Date fechaEmision;
    private Date fechaAceptacion;
    private Timestamp fechaPublicacion;
    private String statusSap;
    private List<DocumentoAceptacionDetalle> documentoAceptacionDetalleList;
    private BigDecimal sumatoria;
    private Date fechaContabilizacion;
    private Date fechaEmisionPrefactura;
    private String referencia;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroDocumentoAceptacion() {
        return numeroDocumentoAceptacion;
    }

    public void setNumeroDocumentoAceptacion(String numeroDocumentoAceptacion) {
        this.numeroDocumentoAceptacion = numeroDocumentoAceptacion;
    }

    public TipoDocumentoAceptacionDto getTipoDocumentoAceptacion() {
        return tipoDocumentoAceptacion;
    }

    public void setTipoDocumentoAceptacion(TipoDocumentoAceptacionDto tipoDocumentoAceptacion) {
        this.tipoDocumentoAceptacion = tipoDocumentoAceptacion;
    }

    public Integer getIdTipoDocumentoAceptacion() {
        return idTipoDocumentoAceptacion;
    }

    public void setIdTipoDocumentoAceptacion(Integer idTipoDocumentoAceptacion) {
        this.idTipoDocumentoAceptacion = idTipoDocumentoAceptacion;
    }

    public EstadoDocumentoAceptacionDto getEstadoDocumentoAceptacion() {
        return estadoDocumentoAceptacion;
    }

    public void setEstadoDocumentoAceptacion(EstadoDocumentoAceptacionDto estadoDocumentoAceptacion) {
        this.estadoDocumentoAceptacion = estadoDocumentoAceptacion;
    }

    public Integer getIdEstadoDocumentoAceptacion() {
        return idEstadoDocumentoAceptacion;
    }

    public void setIdEstadoDocumentoAceptacion(Integer idEstadoDocumentoAceptacion) {
        this.idEstadoDocumentoAceptacion = idEstadoDocumentoAceptacion;
    }

    public Integer getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(Integer idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public String getNumeroOrdenCompra() {
        return numeroOrdenCompra;
    }

    public void setNumeroOrdenCompra(String numeroOrdenCompra) {
        this.numeroOrdenCompra = numeroOrdenCompra;
    }

    public OrdenCompraDto getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompraDto ordenCompra) {
        this.ordenCompra = ordenCompra;
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

    public String getUsuarioSapRecepcion() {
        return usuarioSapRecepcion;
    }

    public void setUsuarioSapRecepcion(String usuarioSapRecepcion) {
        this.usuarioSapRecepcion = usuarioSapRecepcion;
    }

    public String getUsuarioSapAutoriza() {
        return usuarioSapAutoriza;
    }

    public void setUsuarioSapAutoriza(String usuarioSapAutoriza) {
        this.usuarioSapAutoriza = usuarioSapAutoriza;
    }

    public String getCodigoMoneda() {
        return codigoMoneda;
    }

    public void setCodigoMoneda(String codigoMoneda) {
        this.codigoMoneda = codigoMoneda;
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

    public Timestamp getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Timestamp fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getStatusSap() {
        return statusSap;
    }

    public void setStatusSap(String statusSap) {
        this.statusSap = statusSap;
    }

    public List<DocumentoAceptacionDetalle> getDocumentoAceptacionDetalleList() {
        return documentoAceptacionDetalleList;
    }

    public void setDocumentoAceptacionDetalleList(List<DocumentoAceptacionDetalle> documentoAceptacionDetalleList) {
        this.documentoAceptacionDetalleList = documentoAceptacionDetalleList;
    }

    public BigDecimal getSumatoria() {
        return sumatoria;
    }

    public void setSumatoria(BigDecimal sumatoria) {
        this.sumatoria = sumatoria;
    }

    public Date getFechaContabilizacion() {return fechaContabilizacion;}

    public void setFechaContabilizacion(Date fechaContabilizacion) {this.fechaContabilizacion = fechaContabilizacion;}

    public Date getFechaEmisionPrefactura() {return fechaEmisionPrefactura;
    }

    public void setFechaEmisionPrefactura(Date fechaEmisionPrefactura) {this.fechaEmisionPrefactura = fechaEmisionPrefactura;}

    public String getReferencia() { return referencia; }

    public void setReferencia(String referencia) { this.referencia = referencia; }

    @Override
    public String toString() {
        return "DocumentoAceptacionDto{" +
                "id=" + id +
                ", numeroDocumentoAceptacion='" + numeroDocumentoAceptacion + '\'' +
                ", tipoDocumentoAceptacion=" + tipoDocumentoAceptacion +
                ", idTipoDocumentoAceptacion=" + idTipoDocumentoAceptacion +
                ", estadoDocumentoAceptacion=" + estadoDocumentoAceptacion +
                ", idEstadoDocumentoAceptacion=" + idEstadoDocumentoAceptacion +
                ", idOrdenCompra=" + idOrdenCompra +
                ", numeroOrdenCompra='" + numeroOrdenCompra + '\'' +
                ", ordenCompra=" + ordenCompra +
                ", posicionOrdenCompra='" + posicionOrdenCompra + '\'' +
                ", numeroGuiaProveedor='" + numeroGuiaProveedor + '\'' +
                ", proveedorRuc='" + proveedorRuc + '\'' +
                ", proveedorRazonSocial='" + proveedorRazonSocial + '\'' +
                ", usuarioSapRecepcion='" + usuarioSapRecepcion + '\'' +
                ", usuarioSapAutoriza='" + usuarioSapAutoriza + '\'' +
                ", codigoMoneda='" + codigoMoneda + '\'' +
                ", fechaEmision=" + fechaEmision +
                ", fechaAceptacion=" + fechaAceptacion +
                ", fechaPublicacion=" + fechaPublicacion +
                ", statusSap='" + statusSap + '\'' +
                ", documentoAceptacionDetalleList=" + documentoAceptacionDetalleList +
                ", sumatoria=" + sumatoria +
                ", fechaContabilizacion=" + fechaContabilizacion +
                ", fechaEmisionPrefactura=" + fechaEmisionPrefactura +
                ", referencia=" + referencia +
                '}';
    }
}
