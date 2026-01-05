package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Access(AccessType.FIELD)
@Table(name="PREFACTURA")
public class Prefactura extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PREFACTURA_ID_SEQ", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "PREFACTURA_ID_SEQ", sequenceName = "PREFACTURA_ID_SEQ", allocationSize = 1)
	@Column(name="ID_PREFACTURA", unique=true, nullable=false)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = EstadoPrefactura.class)
	@JoinColumn(name="ID_ESTADO_PREFACTURA", referencedColumnName = "ID_ESTADO_PREFACTURA", insertable = false, updatable = false)
	private EstadoPrefactura estadoPrefactura;

	@Column(name="ID_ESTADO_PREFACTURA")
	private Integer idEstadoPrefactura;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Sociedad.class)
    @JoinColumn(name="SOCIEDAD", referencedColumnName = "CODIGO_SOCIEDAD", insertable = false, updatable = false)
    private Sociedad sociedad;

    @Column(name="SOCIEDAD", length = 4)
    private String codigoSociedad;

    @Column(name="PROVEEDOR_RUC", length = 16)
    private String proveedorRuc;

    @Column(name="PROVEEDOR_RAZON_SOCIAL", length = 100)
    private String proveedorRazonSocial;

    @Column(name="FECHA_EMISION")
    private Date fechaEmision;

    @Column(name="FECHA_CONTABLIZACION")
    private Date fechaContabilizacion;

    @Column(name="FECHA_BASE")
    private Date fechaBase;

    @Column(name="INDICADOR_IMPUESTO", length = 2)
    private String indicadorImpuesto;

    @Column(name="REFERENCIA", length = 20)
    private String referencia;

    @Column(name="OBSERVACIONES", length = 40)
    private String observaciones;

    @Column(name="CADENA_NUMEROS_ORDEN_COMPRA", length = 5000)
    private String cadenaNumerosOrdenCompra;

    @Column(name="CADENA_NUMEROS_GUIA", length = 5000)
    private String cadenaNumerosGuia;

    @Column(name="CODIGO_SAP", length = 10)
    private String codigoSap;

    @Column(name="EJERCICIO", length = 4)
    private String ejercicio;

    @Column(name="NUMERO_DOCUMENTO_CONTABLE", length = 10)
    private String numeroDocumentoContable;

    @Column(name="CODIGO_MONEDA", length = 5)
    private String codigoMondeda;

    @Column(name="SUB_TOTAL", precision = 12, scale = 2)
    private BigDecimal subTotal;

    @Column(name="IGV", precision = 12, scale = 2)
    private BigDecimal igv;

    @Column(name="TOTAL", precision = 12, scale = 2)
    private BigDecimal total;

    @Column(name="FECHA_RECEPCION")
    private Timestamp fechaRecepcion;

    @Column(name="FECHA_DESCARTE")
    private Timestamp fechaDescarte;

    @Column(name="FECHA_REGISTRO_SAP")
    private Timestamp fechaRegistroSap;

    @Column(name="USUARIO_REGISTRO_SAP", length = 12)
    private String usuarioRegistroSap;

    @Column(name="XML_ECM_PATH", length = 500)
    private String xmlEcmPath;

    @Column(name="PDF_ECM_PATH", length = 500)
    private String pdfEcmPath;

    @Column(name="CENTRO", length = 50)
    private String centro;

    @Column(name="USUARIO_COMPRADOR", length = 200)
    private String usuarioComprador;

    @Column(name="MOTIVO_RECHAZO", length = 5000)
    private String motivoRechazo;

    @Column(name="FECHA_PAGO")
    private Date fechaPago;

    @Column(name="FECHA_VENCIMIENTO")
    private Date fechaVencimiento;

    @Column(name="XML_ECM_PATH_CF", length = 500)
    private String xmlEcmPathCf;

    @Column(name="PDF_ECM_PATH_CF", length = 500)
    private String pdfEcmPathCf;

    @Column(name="ARCHIVO_ID_PDF_CF", length = 500)
    private String archivoIdPdfCf;

    @Column(name="ARCHIVO_ID_XML_CF", length = 500)
    private String archivoIdXMLCf;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EstadoPrefactura getEstadoPrefactura() {
        return estadoPrefactura;
    }

    public void setEstadoPrefactura(EstadoPrefactura estadoPrefactura) {
        this.estadoPrefactura = estadoPrefactura;
    }

    public Integer getIdEstadoPrefactura() {
        return idEstadoPrefactura;
    }

    public void setIdEstadoPrefactura(Integer idEstadoPrefactura) {
        this.idEstadoPrefactura = idEstadoPrefactura;
    }

    public Sociedad getSociedad() {
        return sociedad;
    }

    public void setSociedad(Sociedad sociedad) {
        this.sociedad = sociedad;
    }

    public String getCodigoSociedad() {
        return codigoSociedad;
    }

    public void setCodigoSociedad(String codigoSociedad) {
        this.codigoSociedad = codigoSociedad;
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

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaContabilizacion() {
        return fechaContabilizacion;
    }

    public void setFechaContabilizacion(Date fechaContabilizacion) {
        this.fechaContabilizacion = fechaContabilizacion;
    }

    public Date getFechaBase() {
        return fechaBase;
    }

    public void setFechaBase(Date fechaBase) {
        this.fechaBase = fechaBase;
    }

    public String getIndicadorImpuesto() {
        return indicadorImpuesto;
    }

    public void setIndicadorImpuesto(String indicadorImpuesto) {
        this.indicadorImpuesto = indicadorImpuesto;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getCadenaNumerosOrdenCompra() {
        return cadenaNumerosOrdenCompra;
    }

    public void setCadenaNumerosOrdenCompra(String cadenaNumerosOrdenCompra) {
        this.cadenaNumerosOrdenCompra = cadenaNumerosOrdenCompra;
    }

    public String getCadenaNumerosGuia() {
        return cadenaNumerosGuia;
    }

    public void setCadenaNumerosGuia(String cadenaNumerosGuia) {
        this.cadenaNumerosGuia = cadenaNumerosGuia;
    }

    public String getCodigoSap() {
        return codigoSap;
    }

    public void setCodigoSap(String codigoSap) {
        this.codigoSap = codigoSap;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getNumeroDocumentoContable() {
        return numeroDocumentoContable;
    }

    public void setNumeroDocumentoContable(String numeroDocumentoContable) {
        this.numeroDocumentoContable = numeroDocumentoContable;
    }

    public String getCodigoMondeda() {
        return codigoMondeda;
    }

    public void setCodigoMondeda(String codigoMondeda) {
        this.codigoMondeda = codigoMondeda;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getIgv() {
        return igv;
    }

    public void setIgv(BigDecimal igv) {
        this.igv = igv;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Timestamp getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Timestamp fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Timestamp getFechaDescarte() {
        return fechaDescarte;
    }

    public void setFechaDescarte(Timestamp fechaDescarte) {
        this.fechaDescarte = fechaDescarte;
    }

    public Timestamp getFechaRegistroSap() {
        return fechaRegistroSap;
    }

    public void setFechaRegistroSap(Timestamp fechaRegistroSap) {
        this.fechaRegistroSap = fechaRegistroSap;
    }

    public String getUsuarioRegistroSap() {
        return usuarioRegistroSap;
    }

    public void setUsuarioRegistroSap(String usuarioRegistroSap) {
        this.usuarioRegistroSap = usuarioRegistroSap;
    }

    public String getXmlEcmPath() {
        return xmlEcmPath;
    }

    public void setXmlEcmPath(String xmlEcmPath) {
        this.xmlEcmPath = xmlEcmPath;
    }

    public String getPdfEcmPath() {
        return pdfEcmPath;
    }

    public void setPdfEcmPath(String pdfEcmPath) {
        this.pdfEcmPath = pdfEcmPath;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getUsuarioComprador() {
        return usuarioComprador;
    }

    public void setUsuarioComprador(String usuarioComprador) {
        this.usuarioComprador = usuarioComprador;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getXmlEcmPathCf() {
        return xmlEcmPathCf;
    }

    public void setXmlEcmPathCf(String xmlEcmPathCf) {
        this.xmlEcmPathCf = xmlEcmPathCf;
    }

    public String getPdfEcmPathCf() {
        return pdfEcmPathCf;
    }

    public void setPdfEcmPathCf(String pdfEcmPathCf) {
        this.pdfEcmPathCf = pdfEcmPathCf;
    }

    public String getArchivoIdPdfCf() {
        return archivoIdPdfCf;
    }

    public void setArchivoIdPdfCf(String archivoIdPdfCf) {
        this.archivoIdPdfCf = archivoIdPdfCf;
    }

    public String getArchivoIdXMLCf() {
        return archivoIdXMLCf;
    }

    public void setArchivoIdXMLCf(String archivoIdXMLCf) {
        this.archivoIdXMLCf = archivoIdXMLCf;
    }

    @Override
    public String toString() {
        return "Prefactura{" +
                "id=" + id +
                ", estadoPrefactura=" + estadoPrefactura +
                ", idEstadoPrefactura=" + idEstadoPrefactura +
                ", sociedad=" + sociedad +
                ", codigoSociedad='" + codigoSociedad + '\'' +
                ", proveedorRuc='" + proveedorRuc + '\'' +
                ", proveedorRazonSocial='" + proveedorRazonSocial + '\'' +
                ", fechaEmision=" + fechaEmision +
                ", fechaContabilizacion=" + fechaContabilizacion +
                ", fechaBase=" + fechaBase +
                ", indicadorImpuesto='" + indicadorImpuesto + '\'' +
                ", referencia='" + referencia + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", cadenaNumerosOrdenCompra='" + cadenaNumerosOrdenCompra + '\'' +
                ", cadenaNumerosGuia='" + cadenaNumerosGuia + '\'' +
                ", codigoSap='" + codigoSap + '\'' +
                ", ejercicio='" + ejercicio + '\'' +
                ", numeroDocumentoContable='" + numeroDocumentoContable + '\'' +
                ", codigoMondeda='" + codigoMondeda + '\'' +
                ", subTotal=" + subTotal +
                ", igv=" + igv +
                ", total=" + total +
                ", fechaRecepcion=" + fechaRecepcion +
                ", fechaDescarte=" + fechaDescarte +
                ", fechaRegistroSap=" + fechaRegistroSap +
                ", usuarioRegistroSap='" + usuarioRegistroSap + '\'' +
                ", xmlEcmPath='" + xmlEcmPath + '\'' +
                ", pdfEcmPath='" + pdfEcmPath + '\'' +
                ", centro='" + centro + '\'' +
                ", usuarioComprador='" + usuarioComprador + '\'' +
                ", motivoRechazo='" + motivoRechazo + '\'' +
                ", fechaPago=" + fechaPago +
                ", fechaVencimiento=" + fechaVencimiento +
                '}';
    }
}