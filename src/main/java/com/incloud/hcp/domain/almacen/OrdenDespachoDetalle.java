package com.incloud.hcp.domain.almacen;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Entity
@Access(AccessType.FIELD)
@Table(name="GA_ORDEN_DESPACHO_DETALLE")
public class OrdenDespachoDetalle extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ORDEN_DESPACHO_DETALLE_ID_SEQ", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ORDEN_DESPACHO_DETALLE_ID_SEQ", sequenceName = "ORDEN_DESPACHO_DETALLE_ID_SEQ", allocationSize = 1)
	@Column(name="ID_ORDEN_DESPACHO_DETALLE", unique=true, nullable=false)
	private Integer id;

	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = OrdenDespacho.class)
    @JoinColumn(name="ID_ORDEN_DESPACHO", referencedColumnName = "ID_ORDEN_DESPACHO", insertable = false, updatable = false)
	private OrdenDespacho ordenDespacho;

	@Column(name="ID_ORDEN_DESPACHO", nullable=false)
	private Integer idOrdenDespacho;

	@Column(name="NUMERO_ORDEN_COMPRA", nullable=false, length=10)
	private String numeroOrdenCompra;

    @Column(name="POSICION", length=5)
    private String posicion;

    @Column(name="TIPO_POSICION", length=1)
    private String tipoPosicion;

	@Column(name="CODIGO_SAP_BIEN_SERVICIO", length=18)
	private String codigoSapBienServicio;

	@Column(name="DESCRIPCION_BIEN_SERVICIO", length = 50)
	private String descripcionBienServicio;

	@Column(name="UNIDAD_MEDIDA_BIEN_SERVICIO", length = 3)
	private String unidadMedidaBienServicio;

	@Column(name="CODIGO_SAP_CENTRO", length = 4)
	private String codigoSapCentro;

	@Column(name="DENOMINACION_CENTRO", length = 30)
	private String denominacionCentro;

	@Column(name="DIRECCION_CENTRO", length = 50)
	private String direccionCentro;

	@Column(name="CODIGO_SAP_ALMACEN", length = 4)
	private String codigoSapAlmacen;

    @Column(name="DENOMINACION_ALMACEN", length = 20)
    private String denominacionAlmacen;

    @Column(name="CANTIDAD", precision = 14, scale = 4)
    private BigDecimal cantidad;

    @Column(name="PRECIO_UNITARIO", precision = 14, scale = 4)
    private BigDecimal precioUnitario;

    @Column(name="PRECIO_TOTAL", precision = 14, scale = 4)
    private BigDecimal precioTotal;

    @Column(name="INDICADOR_IMPUESTO", length = 2)
    private String indicadorImpuesto;

	@Column(name="FECHA_ENTREGA")
	private Date fechaEntrega;

    @OneToMany(mappedBy = "ordenDespachoDetalle", targetEntity = OrdenDespachoDetalleTexto.class, fetch = FetchType.LAZY)
    private List<OrdenDespachoDetalleTexto> ordenDespachoDetalleTextoList;

    @OneToMany(mappedBy = "ordenDespachoDetalle", targetEntity = OrdenDespachoDetalleTextoRegistroInfo.class, fetch = FetchType.LAZY)
    private List<OrdenDespachoDetalleTextoRegistroInfo> ordenDespachoDetalleTextoRegistroInfoList;

    @OneToMany(mappedBy = "ordenDespachoDetalle", targetEntity = OrdenDespachoDetalleTextoMaterialAmpliado.class, fetch = FetchType.LAZY)
    private List<OrdenDespachoDetalleTextoMaterialAmpliado> ordenDespachoDetalleTextoMaterialAmpliadoList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrdenDespacho getOrdenDespacho() {
        return ordenDespacho;
    }

    public void setOrdenDespacho(OrdenDespacho ordenDespacho) {
        this.ordenDespacho = ordenDespacho;
    }

    public Integer getIdOrdenDespacho() {
        return idOrdenDespacho;
    }

    public void setIdOrdenDespacho(Integer idOrdenDespacho) {
        this.idOrdenDespacho = idOrdenDespacho;
    }

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

    public String getTipoPosicion() {
        return tipoPosicion;
    }

    public void setTipoPosicion(String tipoPosicion) {
        this.tipoPosicion = tipoPosicion;
    }

    public String getCodigoSapBienServicio() {
        return codigoSapBienServicio;
    }

    public void setCodigoSapBienServicio(String codigoSapBienServicio) {
        this.codigoSapBienServicio = codigoSapBienServicio;
    }

    public String getDescripcionBienServicio() {
        return descripcionBienServicio;
    }

    public void setDescripcionBienServicio(String descripcionBienServicio) {
        this.descripcionBienServicio = descripcionBienServicio;
    }

    public String getUnidadMedidaBienServicio() {
        return unidadMedidaBienServicio;
    }

    public void setUnidadMedidaBienServicio(String unidadMedidaBienServicio) {
        this.unidadMedidaBienServicio = unidadMedidaBienServicio;
    }

    public String getCodigoSapCentro() {
        return codigoSapCentro;
    }

    public void setCodigoSapCentro(String codigoSapCentro) {
        this.codigoSapCentro = codigoSapCentro;
    }

    public String getDenominacionCentro() {
        return denominacionCentro;
    }

    public void setDenominacionCentro(String denominacionCentro) {
        this.denominacionCentro = denominacionCentro;
    }

    public String getDireccionCentro() {
        return direccionCentro;
    }

    public void setDireccionCentro(String direccionCentro) {
        this.direccionCentro = direccionCentro;
    }

    public String getCodigoSapAlmacen() {
        return codigoSapAlmacen;
    }

    public void setCodigoSapAlmacen(String codigoSapAlmacen) {
        this.codigoSapAlmacen = codigoSapAlmacen;
    }

    public String getDenominacionAlmacen() {
        return denominacionAlmacen;
    }

    public void setDenominacionAlmacen(String denominacionAlmacen) {
        this.denominacionAlmacen = denominacionAlmacen;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getIndicadorImpuesto() {
        return indicadorImpuesto;
    }

    public void setIndicadorImpuesto(String indicadorImpuesto) {
        this.indicadorImpuesto = indicadorImpuesto;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public List<OrdenDespachoDetalleTexto> getOrdenDespachoDetalleTextoList() {
        return ordenDespachoDetalleTextoList;
    }

    public void setOrdenDespachoDetalleTextoList(List<OrdenDespachoDetalleTexto> ordenDespachoDetalleTextoList) {
        this.ordenDespachoDetalleTextoList = ordenDespachoDetalleTextoList;
    }

    public List<OrdenDespachoDetalleTextoRegistroInfo> getOrdenDespachoDetalleTextoRegistroInfoList() {
        return ordenDespachoDetalleTextoRegistroInfoList;
    }

    public void setOrdenDespachoDetalleTextoRegistroInfoList(List<OrdenDespachoDetalleTextoRegistroInfo> ordenDespachoDetalleTextoRegistroInfoList) {
        this.ordenDespachoDetalleTextoRegistroInfoList = ordenDespachoDetalleTextoRegistroInfoList;
    }

    public List<OrdenDespachoDetalleTextoMaterialAmpliado> getOrdenDespachoDetalleTextoMaterialAmpliadoList() {
        return ordenDespachoDetalleTextoMaterialAmpliadoList;
    }

    public void setOrdenDespachoDetalleTextoMaterialAmpliadoList(List<OrdenDespachoDetalleTextoMaterialAmpliado> ordenDespachoDetalleTextoMaterialAmpliadoList) {
        this.ordenDespachoDetalleTextoMaterialAmpliadoList = ordenDespachoDetalleTextoMaterialAmpliadoList;
    }

    @Override
    public String toString() {
        return "OrdenDespachoDetalle{" +
                "id=" + id +
                ", ordenDespacho=" + ordenDespacho +
                ", idOrdenDespacho=" + idOrdenDespacho +
                ", numeroOrdenCompra='" + numeroOrdenCompra + '\'' +
                ", posicion='" + posicion + '\'' +
                ", tipoPosicion='" + tipoPosicion + '\'' +
                ", codigoSapBienServicio='" + codigoSapBienServicio + '\'' +
                ", descripcionBienServicio='" + descripcionBienServicio + '\'' +
                ", unidadMedidaBienServicio='" + unidadMedidaBienServicio + '\'' +
                ", codigoSapCentro='" + codigoSapCentro + '\'' +
                ", denominacionCentro='" + denominacionCentro + '\'' +
                ", direccionCentro='" + direccionCentro + '\'' +
                ", codigoSapAlmacen='" + codigoSapAlmacen + '\'' +
                ", denominacionAlmacen='" + denominacionAlmacen + '\'' +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", precioTotal=" + precioTotal +
                ", indicadorImpuesto='" + indicadorImpuesto + '\'' +
                ", fechaEntrega=" + fechaEntrega +
                ", ordenDespachoDetalleTextoList=" + ordenDespachoDetalleTextoList +
                ", ordenDespachoDetalleTextoRegistroInfoList=" + ordenDespachoDetalleTextoRegistroInfoList +
                ", ordenDespachoDetalleTextoMaterialAmpliadoList=" + ordenDespachoDetalleTextoMaterialAmpliadoList +
                '}';
    }
}