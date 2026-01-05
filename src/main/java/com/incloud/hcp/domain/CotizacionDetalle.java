package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the cotizacion_detalle database table.
 * 
 */
@Entity
@Table(name="cotizacion_detalle")
public class CotizacionDetalle extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_cotizacion_detalle", unique=true, nullable=false)
	@GeneratedValue(generator = "cotizacion_detalle_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "cotizacion_detalle_id_seq", sequenceName = "cotizacion_detalle_id_seq", allocationSize = 1)
	private Integer idCotizacionDetalle;

	@Column(name="cantidad_cotizada", nullable=false, precision=15, scale=2)
	private BigDecimal cantidadCotizada;

	@Column(name="cantidad_solicitada", nullable=false, precision=15, scale=2)
	private BigDecimal cantidadSolicitada;

	@Column(nullable=false, length=80)
	private String descripcion;

	@Column(length=200)
	private String especificacion;

	@Column(name="ind_ganador", nullable=false, length=1)
	private String indGanador;

	@Column(name="numero_parte", length=40)
	private String numeroParte;

	@Column(name="precio_unitario", nullable=false, precision=15, scale=3)
	private BigDecimal precioUnitario;

	@Column(name="solicitud_pedido", length=10)
	private String solicitudPedido;

	//uni-directional many-to-one association to BienServicio
	@ManyToOne
	@JoinColumn(name="id_bien_servicio")
	private BienServicio bienServicio;

	//uni-directional many-to-one association to Cotizacion
	@ManyToOne
	@JoinColumn(name="id_cotizacion", nullable=false)
	private Cotizacion cotizacion;

	//uni-directional many-to-one association to UnidadMedida
	@ManyToOne
	@JoinColumn(name="id_unidad_medida", nullable=false)
	private UnidadMedida unidadMedida;

	/**
	 * JRAMOS - UPDATE
	 */
	@Column(name = "descripcion_alternativa")
	private String descripcionAlternativa;
	@Column(name="descuento", precision = 15, scale = 2)
	private BigDecimal descuento;
	@Column(name="precio", precision = 15, scale = 2)
	private BigDecimal precio;
	@Column(name="tiempo_entrega")
	private String tiempoEntrega;

	@ManyToOne
	@JoinColumn(name="id_licitacion_detalle", nullable=false)
	private LicitacionDetalle licitacionDetalle;


	public CotizacionDetalle() {
	}

	public Integer getIdCotizacionDetalle() {
		return this.idCotizacionDetalle;
	}

	public void setIdCotizacionDetalle(Integer idCotizacionDetalle) {
		this.idCotizacionDetalle = idCotizacionDetalle;
	}

	public BigDecimal getCantidadCotizada() {
		return cantidadCotizada;
	}

	public void setCantidadCotizada(BigDecimal cantidadCotizada) {
		this.cantidadCotizada = cantidadCotizada;
	}

	public BigDecimal getCantidadSolicitada() {
		return cantidadSolicitada;
	}

	public void setCantidadSolicitada(BigDecimal cantidadSolicitada) {
		this.cantidadSolicitada = cantidadSolicitada;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEspecificacion() {
		return this.especificacion;
	}

	public void setEspecificacion(String especificacion) {
		this.especificacion = especificacion;
	}

	public String getIndGanador() {
		return this.indGanador;
	}

	public void setIndGanador(String indGanador) {
		this.indGanador = indGanador;
	}

	public String getNumeroParte() {
		return this.numeroParte;
	}

	public void setNumeroParte(String numeroParte) {
		this.numeroParte = numeroParte;
	}

	public BigDecimal getPrecioUnitario() {
		return this.precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public String getSolicitudPedido() {
		return this.solicitudPedido;
	}

	public void setSolicitudPedido(String solicitudPedido) {
		this.solicitudPedido = solicitudPedido;
	}

	public BienServicio getBienServicio() {
		return this.bienServicio;
	}

	public void setBienServicio(BienServicio bienServicio) {
		this.bienServicio = bienServicio;
	}

	public Cotizacion getCotizacion() {
		return this.cotizacion;
	}

	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}

	public UnidadMedida getUnidadMedida() {
		return this.unidadMedida;
	}

	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public LicitacionDetalle getLicitacionDetalle() {
		return licitacionDetalle;
	}

	public void setLicitacionDetalle(LicitacionDetalle licitacionDetalle) {
		this.licitacionDetalle = licitacionDetalle;
	}

	public String getDescripcionAlternativa() {
		return descripcionAlternativa;
	}

	public void setDescripcionAlternativa(String descripcionAlternativa) {
		this.descripcionAlternativa = descripcionAlternativa;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public String getTiempoEntrega() {
		return tiempoEntrega;
	}

	public void setTiempoEntrega(String tiempoEntrega) {
		this.tiempoEntrega = tiempoEntrega;
	}

	@Override
	public String toString() {
		return "CotizacionDetalle{" +
				"idCotizacionDetalle=" + idCotizacionDetalle +
				", cantidadCotizada=" + cantidadCotizada +
				", cantidadSolicitada=" + cantidadSolicitada +
				", descripcion='" + descripcion + '\'' +
				", especificacion='" + especificacion + '\'' +
				", indGanador='" + indGanador + '\'' +
				", numeroParte='" + numeroParte + '\'' +
				", precioUnitario=" + precioUnitario +
				", solicitudPedido='" + solicitudPedido + '\'' +
				", bienServicio=" + bienServicio +
				", cotizacion=" + cotizacion +
				", unidadMedida=" + unidadMedida +

				", descripcionAlternativa=" + descripcionAlternativa +
				", descuento=" + descuento +
				", precio=" + precio +
				", tiempoEntrega=" + tiempoEntrega +

				", licitacionDetalle=" + licitacionDetalle +
				'}';
	}
}