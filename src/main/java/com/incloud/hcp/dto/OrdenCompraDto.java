package com.incloud.hcp.dto;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class OrdenCompraDto {
    private Integer id;
    private String numeroOrdenCompra;
    private Integer version;
    private String isActive;
    private TipoOrdenCompraDto tipoOrdenCompra;
    private Integer idTipoOrdenCompra;
    private EstadoOrdenCompraDto estadoOrdenCompra;
    private Integer idEstadoOrdenCompra;
    private String estadoSap;
    private String codigoClaseOrdenCompra;
    private String claseOrdenCompra;
    private SociedadBeanDto infoSociedad;
    private String sociedad;
    private String compradorUsuarioSap;
    private String compradorNombre;
    private String ultimoLiberadorUsuarioSap;
    private String proveedorCodigoSap;
    private String proveedorRuc;
    private String proveedorRazonSocial;
    private String codigoMondeda;
    private BigDecimal total;
    private String condicionPago;
    private String condicionPagoDescripcion;
    private Date fechaEntrega;
    private Date fechaRegistro;
    private Date fechaModificacion;
    private Time horaModificacion;
    private Timestamp fechaPublicacion;
    private Timestamp fechaVisualizacion;
    private Timestamp fechaAprobacion;
    private String motivoRechazo;
    private String lugarEntrega;
    private String indicadorContratoMarco;
    private String autorizadorFechaLiberacion;
    private List<OrdenCompraTextoCabeceraDto> ordenCompraTextoCabeceraList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroOrdenCompra() {
        return numeroOrdenCompra;
    }

    public void setNumeroOrdenCompra(String numeroOrdenCompra) {
        this.numeroOrdenCompra = numeroOrdenCompra;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public TipoOrdenCompraDto getTipoOrdenCompra() {
        return tipoOrdenCompra;
    }

    public void setTipoOrdenCompra(TipoOrdenCompraDto tipoOrdenCompra) {
        this.tipoOrdenCompra = tipoOrdenCompra;
    }

    public Integer getIdTipoOrdenCompra() {
        return idTipoOrdenCompra;
    }

    public void setIdTipoOrdenCompra(Integer idTipoOrdenCompra) {
        this.idTipoOrdenCompra = idTipoOrdenCompra;
    }

    public EstadoOrdenCompraDto getEstadoOrdenCompra() {
        return estadoOrdenCompra;
    }

    public void setEstadoOrdenCompra(EstadoOrdenCompraDto estadoOrdenCompra) {
        this.estadoOrdenCompra = estadoOrdenCompra;
    }

    public Integer getIdEstadoOrdenCompra() {
        return idEstadoOrdenCompra;
    }

    public void setIdEstadoOrdenCompra(Integer idEstadoOrdenCompra) {
        this.idEstadoOrdenCompra = idEstadoOrdenCompra;
    }

    public String getEstadoSap() {
        return estadoSap;
    }

    public void setEstadoSap(String estadoSap) {
        this.estadoSap = estadoSap;
    }

    public String getCodigoClaseOrdenCompra() {
        return codigoClaseOrdenCompra;
    }

    public void setCodigoClaseOrdenCompra(String codigoClaseOrdenCompra) {
        this.codigoClaseOrdenCompra = codigoClaseOrdenCompra;
    }

    public String getClaseOrdenCompra() {
        return claseOrdenCompra;
    }

    public void setClaseOrdenCompra(String claseOrdenCompra) {
        this.claseOrdenCompra = claseOrdenCompra;
    }

    public SociedadBeanDto getInfoSociedad() {
        return infoSociedad;
    }

    public void setInfoSociedad(SociedadBeanDto infoSociedad) {
        this.infoSociedad = infoSociedad;
    }

    public String getSociedad() {
        return sociedad;
    }

    public void setSociedad(String sociedad) {
        this.sociedad = sociedad;
    }

    public String getCompradorUsuarioSap() {
        return compradorUsuarioSap;
    }

    public void setCompradorUsuarioSap(String compradorUsuarioSap) {
        this.compradorUsuarioSap = compradorUsuarioSap;
    }

    public String getCompradorNombre() {
        return compradorNombre;
    }

    public void setCompradorNombre(String compradorNombre) {
        this.compradorNombre = compradorNombre;
    }

    public String getUltimoLiberadorUsuarioSap() {
        return ultimoLiberadorUsuarioSap;
    }

    public void setUltimoLiberadorUsuarioSap(String ultimoLiberadorUsuarioSap) {
        this.ultimoLiberadorUsuarioSap = ultimoLiberadorUsuarioSap;
    }

    public String getProveedorCodigoSap() {
        return proveedorCodigoSap;
    }

    public void setProveedorCodigoSap(String proveedorCodigoSap) {
        this.proveedorCodigoSap = proveedorCodigoSap;
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

    public String getCodigoMondeda() {
        return codigoMondeda;
    }

    public void setCodigoMondeda(String codigoMondeda) {
        this.codigoMondeda = codigoMondeda;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getCondicionPago() {
        return condicionPago;
    }

    public void setCondicionPago(String condicionPago) {
        this.condicionPago = condicionPago;
    }

    public String getCondicionPagoDescripcion() {
        return condicionPagoDescripcion;
    }

    public void setCondicionPagoDescripcion(String condicionPagoDescripcion) {
        this.condicionPagoDescripcion = condicionPagoDescripcion;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Time getHoraModificacion() {
        return horaModificacion;
    }

    public void setHoraModificacion(Time horaModificacion) {
        this.horaModificacion = horaModificacion;
    }

    public Timestamp getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Timestamp fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Timestamp getFechaVisualizacion() {
        return fechaVisualizacion;
    }

    public void setFechaVisualizacion(Timestamp fechaVisualizacion) {
        this.fechaVisualizacion = fechaVisualizacion;
    }

    public Timestamp getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Timestamp fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public String getLugarEntrega() {
        return lugarEntrega;
    }

    public void setLugarEntrega(String lugarEntrega) {
        this.lugarEntrega = lugarEntrega;
    }

    public String getIndicadorContratoMarco() {
        return indicadorContratoMarco;
    }

    public void setIndicadorContratoMarco(String indicadorContratoMarco) {
        this.indicadorContratoMarco = indicadorContratoMarco;
    }

    public String getAutorizadorFechaLiberacion() {
        return autorizadorFechaLiberacion;
    }

    public void setAutorizadorFechaLiberacion(String autorizadorFechaLiberacion) {
        this.autorizadorFechaLiberacion = autorizadorFechaLiberacion;
    }

    public List<OrdenCompraTextoCabeceraDto> getOrdenCompraTextoCabeceraList() {
        return ordenCompraTextoCabeceraList;
    }

    public void setOrdenCompraTextoCabeceraList(List<OrdenCompraTextoCabeceraDto> ordenCompraTextoCabeceraList) {
        this.ordenCompraTextoCabeceraList = ordenCompraTextoCabeceraList;
    }

    @Override
    public String toString() {
        return "OrdenCompraDto{" +
                "id=" + id +
                ", numeroOrdenCompra='" + numeroOrdenCompra + '\'' +
                ", version=" + version +
                ", isActive='" + isActive + '\'' +
                ", tipoOrdenCompra=" + tipoOrdenCompra +
                ", idTipoOrdenCompra=" + idTipoOrdenCompra +
                ", estadoOrdenCompra=" + estadoOrdenCompra +
                ", idEstadoOrdenCompra=" + idEstadoOrdenCompra +
                ", estadoSap='" + estadoSap + '\'' +
                ", codigoClaseOrdenCompra='" + codigoClaseOrdenCompra + '\'' +
                ", claseOrdenCompra='" + claseOrdenCompra + '\'' +
                ", infoSociedad=" + infoSociedad +
                ", sociedad='" + sociedad + '\'' +
                ", compradorUsuarioSap='" + compradorUsuarioSap + '\'' +
                ", compradorNombre='" + compradorNombre + '\'' +
                ", ultimoLiberadorUsuarioSap='" + ultimoLiberadorUsuarioSap + '\'' +
                ", proveedorCodigoSap='" + proveedorCodigoSap + '\'' +
                ", proveedorRuc='" + proveedorRuc + '\'' +
                ", proveedorRazonSocial='" + proveedorRazonSocial + '\'' +
                ", codigoMondeda='" + codigoMondeda + '\'' +
                ", total=" + total +
                ", condicionPago='" + condicionPago + '\'' +
                ", condicionPagoDescripcion='" + condicionPagoDescripcion + '\'' +
                ", fechaEntrega=" + fechaEntrega +
                ", fechaRegistro=" + fechaRegistro +
                ", fechaModificacion=" + fechaModificacion +
                ", horaModificacion=" + horaModificacion +
                ", fechaPublicacion=" + fechaPublicacion +
                ", fechaVisualizacion=" + fechaVisualizacion +
                ", fechaAprobacion=" + fechaAprobacion +
                ", motivoRechazo='" + motivoRechazo + '\'' +
                ", lugarEntrega='" + lugarEntrega + '\'' +
                ", indicadorContratoMarco='" + indicadorContratoMarco + '\'' +
                ", autorizadorFechaLiberacion='" + autorizadorFechaLiberacion + '\'' +
                ", ordenCompraTextoCabeceraList=" + ordenCompraTextoCabeceraList +
                '}';
    }
}
