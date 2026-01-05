package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
@Table(name="DOCUMENTO_ACEPTACION")
public class DocumentoAceptacion extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "DOCUMENTO_ACEPTACION_ID_SEQ", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "DOCUMENTO_ACEPTACION_ID_SEQ", sequenceName = "DOCUMENTO_ACEPTACION_ID_SEQ", allocationSize = 1)
	@Column(name="ID_DOCUMENTO_ACEPTACION", unique=true, nullable=false)
	private Integer id;

    @Column(name="NUMERO_DOCUMENTO_ACEPTACION", nullable=false, length=10)
    private String numeroDocumentoAceptacion;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = TipoDocumentoAceptacion.class)
    @JoinColumn(name="ID_TIPO_DOCUMENTO_ACEPTACION", referencedColumnName = "ID_TIPO_DOCUMENTO_ACEPTACION", insertable = false, updatable = false)
    private TipoDocumentoAceptacion tipoDocumentoAceptacion;

    @Column(name="ID_TIPO_DOCUMENTO_ACEPTACION", nullable=false)
    private Integer idTipoDocumentoAceptacion;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = EstadoDocumentoAceptacion.class)
    @JoinColumn(name="ID_ESTADO_DOCUMENTO_ACEPTACION", referencedColumnName = "ID_ESTADO_DOCUMENTO_ACEPTACION", insertable = false, updatable = false)
    private EstadoDocumentoAceptacion estadoDocumentoAceptacion;

    @Column(name="ID_ESTADO_DOCUMENTO_ACEPTACION", nullable=false)
    private Integer idEstadoDocumentoAceptacion;

    @Column(name="ID_ORDEN_COMPRA")
    private Integer idOrdenCompra;

	@Column(name="NUMERO_ORDEN_COMPRA", length=10)
	private String numeroOrdenCompra;

    @Column(name="POSICION_ORDEN_COMPRA", length=5)
    private String posicionOrdenCompra;

    @Column(name="NUMERO_GUIA_PROVEEDOR", length=30)
    private String numeroGuiaProveedor;

    @Column(name="PROVEEDOR_RUC", length = 16)
    private String proveedorRuc;

    @Column(name="PROVEEDOR_RAZON_SOCIAL", length = 50)
    private String proveedorRazonSocial;

    @Column(name="USUARIO_SAP_RECEPCION", length = 12)
    private String usuarioSapRecepcion;

    @Column(name="USUARIO_SAP_AUTORIZA", length = 12)
    private String usuarioSapAutoriza;

    @Column(name="CODIGO_MONEDA", length=5)
    private String codigoMoneda;

    @Column(name="FECHA_EMISION")
    private Date fechaEmision;

    @Column(name="FECHA_ACEPTACION")
    private Date fechaAceptacion;

    @Column(name="FECHA_PUBLICACION")
    private Timestamp fechaPublicacion;

    @Column(name="FECHA_CONTABILIZACION")
    private Date fechaContabilizacion;

    @Column(name="STATUS_SAP", length=20)
    private String statusSap;

    @OneToMany(mappedBy = "documentoAceptacion", targetEntity = DocumentoAceptacionDetalle.class, fetch = FetchType.LAZY)
    private List<DocumentoAceptacionDetalle> documentoAceptacionDetalleList;


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

    public TipoDocumentoAceptacion getTipoDocumentoAceptacion() {
        return tipoDocumentoAceptacion;
    }

    public void setTipoDocumentoAceptacion(TipoDocumentoAceptacion tipoDocumentoAceptacion) {
        this.tipoDocumentoAceptacion = tipoDocumentoAceptacion;
    }

    public Integer getIdTipoDocumentoAceptacion() {
        return idTipoDocumentoAceptacion;
    }

    public void setIdTipoDocumentoAceptacion(Integer idTipoDocumentoAceptacion) {
        this.idTipoDocumentoAceptacion = idTipoDocumentoAceptacion;
    }

    public EstadoDocumentoAceptacion getEstadoDocumentoAceptacion() {
        return estadoDocumentoAceptacion;
    }

    public void setEstadoDocumentoAceptacion(EstadoDocumentoAceptacion estadoDocumentoAceptacion) {
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

    public Date getFechaContabilizacion() { return fechaContabilizacion; }

    public void setFechaContabilizacion(Date fechaContabilizacion) { this.fechaContabilizacion = fechaContabilizacion; }

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


    @Override
    public String toString() {
        return "DocumentoAceptacion{" +
                "id=" + id +
                ", numeroDocumentoAceptacion='" + numeroDocumentoAceptacion + '\'' +
                ", tipoDocumentoAceptacion=" + tipoDocumentoAceptacion +
                ", idTipoDocumentoAceptacion=" + idTipoDocumentoAceptacion +
                ", estadoDocumentoAceptacion=" + estadoDocumentoAceptacion +
                ", idEstadoDocumentoAceptacion=" + idEstadoDocumentoAceptacion +
                ", idOrdenCompra=" + idOrdenCompra +
                ", numeroOrdenCompra='" + numeroOrdenCompra + '\'' +
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
                ", fechaContabilizacion=" + fechaContabilizacion +
                ", statusSap='" + statusSap + '\'' +
                ", documentoAceptacionDetalleList=" + documentoAceptacionDetalleList +
                '}';
    }
}