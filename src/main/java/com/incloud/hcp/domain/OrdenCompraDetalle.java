package com.incloud.hcp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Entity
@Access(AccessType.FIELD)
@Table(name="ORDEN_COMPRA_DETALLE")
public class OrdenCompraDetalle extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ORDEN_COMPRA_DETALLE_ID_SEQ", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ORDEN_COMPRA_DETALLE_ID_SEQ", sequenceName = "ORDEN_COMPRA_DETALLE_ID_SEQ", allocationSize = 1)
	@Column(name="ID_ORDEN_COMPRA_DETALLE", unique=true, nullable=false)
	private Integer id;

	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = OrdenCompra.class)
    @JoinColumn(name="ID_ORDEN_COMPRA", referencedColumnName = "ID_ORDEN_COMPRA", insertable = false, updatable = false)
	private OrdenCompra ordenCompra;

	@Column(name="ID_ORDEN_COMPRA", nullable=false)
	private Integer idOrdenCompra;

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

    @OneToMany(mappedBy = "ordenCompraDetalle", targetEntity = OrdenCompraDetalleTexto.class, fetch = FetchType.LAZY)
    private List<OrdenCompraDetalleTexto> ordenCompraDetalleTextoList;

    @OneToMany(mappedBy = "ordenCompraDetalle", targetEntity = OrdenCompraDetalleTextoRegistroInfo.class, fetch = FetchType.LAZY)
    private List<OrdenCompraDetalleTextoRegistroInfo> ordenCompraDetalleTextoRegistroInfoList;

    @OneToMany(mappedBy = "ordenCompraDetalle", targetEntity = OrdenCompraDetalleTextoMaterialAmpliado.class, fetch = FetchType.LAZY)
    private List<OrdenCompraDetalleTextoMaterialAmpliado> ordenCompraDetalleTextoMaterialAmpliadoList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
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

    public List<OrdenCompraDetalleTexto> getOrdenCompraDetalleTextoList() {
        return ordenCompraDetalleTextoList;
    }

    public void setOrdenCompraDetalleTextoList(List<OrdenCompraDetalleTexto> ordenCompraDetalleTextoList) {
        this.ordenCompraDetalleTextoList = ordenCompraDetalleTextoList;
    }

    public List<OrdenCompraDetalleTextoRegistroInfo> getOrdenCompraDetalleTextoRegistroInfoList() {
        return ordenCompraDetalleTextoRegistroInfoList;
    }

    public void setOrdenCompraDetalleTextoRegistroInfoList(List<OrdenCompraDetalleTextoRegistroInfo> ordenCompraDetalleTextoRegistroInfoList) {
        this.ordenCompraDetalleTextoRegistroInfoList = ordenCompraDetalleTextoRegistroInfoList;
    }

    public List<OrdenCompraDetalleTextoMaterialAmpliado> getOrdenCompraDetalleTextoMaterialAmpliadoList() {
        return ordenCompraDetalleTextoMaterialAmpliadoList;
    }

    public void setOrdenCompraDetalleTextoMaterialAmpliadoList(List<OrdenCompraDetalleTextoMaterialAmpliado> ordenCompraDetalleTextoMaterialAmpliadoList) {
        this.ordenCompraDetalleTextoMaterialAmpliadoList = ordenCompraDetalleTextoMaterialAmpliadoList;
    }


    @Override
    public String toString() {
        return "OrdenCompraDetalle{" +
                "id=" + id +
                ", ordenCompra=" + ordenCompra +
                ", idOrdenCompra=" + idOrdenCompra +
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
                ", ordenCompraDetalleTextoList=" + ordenCompraDetalleTextoList +
                ", ordenCompraDetalleTextoRegistroInfoList=" + ordenCompraDetalleTextoRegistroInfoList +
                ", ordenCompraDetalleTextoMaterialAmpliadoList=" + ordenCompraDetalleTextoMaterialAmpliadoList +
                '}';
    }
}