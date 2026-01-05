package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the licitacion_detalle database table.
 * 
 */
@Entity
@Table(name="licitacion_detalle")
public class LicitacionDetalle extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_licitacion_detalle", unique=true, nullable=false)
	@GeneratedValue(generator = "licitacion_detalle_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "licitacion_detalle_id_seq", sequenceName = "licitacion_detalle_id_seq", allocationSize = 1)
	private Integer idLicitacionDetalle;

	@Column(name="cantidad_solicitada", nullable=false, precision=15, scale=2)
	private BigDecimal cantidadSolicitada;

	@Column(name="cantidad_solped", precision=15, scale=2)
	private BigDecimal cantidadSolped;

	@Column(nullable=false, length=80)
	private String descripcion;

	@Column(length=500)
	private String especificacion;

	@Column(name="numero_parte", length=40)
	private String numeroParte;

	@Column(name="posicion_solicitud_pedido", length=5)
	private String posicionSolicitudPedido;

	@Column(name="solicitud_pedido", length=10)
    private String solicitudPedido;

    @Column(name="grupo_articulo", length=60)
    private String grupoArticulo;

	@Column(name="id_bien_servicio")
	private Integer idNumberBienServicio;

    @Column(name="id_licitacion", nullable=false)
    private Integer idNumberLicitacion;

    @Column(name="id_centro")
    private Integer idNumberCentro;

	//uni-directional many-to-one association to BienServicio
	@ManyToOne
	@JoinColumn(name="id_bien_servicio", insertable = false, updatable = false)
	private BienServicio bienServicio;

	//uni-directional many-to-one association to Licitacion
	@ManyToOne
	@JoinColumn(name="id_licitacion", insertable = false, updatable = false)
	private Licitacion licitacion;

	//uni-directional many-to-one association to UnidadMedida
	@ManyToOne
	@JoinColumn(name="id_unidad_medida", nullable=false)
	private UnidadMedida unidadMedida;

	@ManyToOne
	@JoinColumn(name="id_almacen")
	private CentroAlmacen idAlmacen;

	@ManyToOne
	@JoinColumn(name="id_centro", insertable = false, updatable = false)
	private CentroAlmacen idCentro;

	@Transient
	private String nombreProveedorSugerido;

	@Transient
	private String nombreTipoDocumentoSap;

	@Transient
	private Integer idTipoDocumentoSap;




	public LicitacionDetalle() {
	}

	public LicitacionDetalle(BigDecimal cantidadSolicitada, BigDecimal cantidadSolped, String descripcion, String especificacion, String numeroParte, String posicionSolicitudPedido, String solicitudPedido, String grupoArticulo, BienServicio bienServicio, Licitacion licitacion, UnidadMedida unidadMedida) {
		this.cantidadSolicitada = cantidadSolicitada;
		this.cantidadSolped = cantidadSolped;
		this.descripcion = descripcion;
		this.especificacion = especificacion;
		this.numeroParte = numeroParte;
		this.posicionSolicitudPedido = posicionSolicitudPedido;
		this.solicitudPedido = solicitudPedido;
		this.grupoArticulo = grupoArticulo;
		this.bienServicio = bienServicio;
		this.licitacion = licitacion;
		this.unidadMedida = unidadMedida;
	}

    public LicitacionDetalle(
			BigDecimal cantidadSolicitada,
			BigDecimal cantidadSolped,
			String descripcion,
			String especificacion,
			String numeroParte,
			String posicionSolicitudPedido,
			String solicitudPedido,
			String grupoArticulo,
			BienServicio bienServicio,
			Licitacion licitacion,
			UnidadMedida unidadMedida,
			CentroAlmacen centro,
			CentroAlmacen almacen) {
		this.cantidadSolicitada = cantidadSolicitada;
		this.cantidadSolped = cantidadSolped;
		this.descripcion = descripcion;
		this.especificacion = especificacion;
		this.numeroParte = numeroParte;
		this.posicionSolicitudPedido = posicionSolicitudPedido;
		this.solicitudPedido = solicitudPedido;
		this.grupoArticulo = grupoArticulo;
		this.bienServicio = bienServicio;
		this.licitacion = licitacion;
		this.unidadMedida = unidadMedida;
		this.idCentro = centro;
		this.idAlmacen = almacen;
	}

	public Integer getIdLicitacionDetalle() {
		return this.idLicitacionDetalle;
	}

	public void setIdLicitacionDetalle(Integer idLicitacionDetalle) {
		this.idLicitacionDetalle = idLicitacionDetalle;
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

	public String getNumeroParte() {
		return this.numeroParte;
	}

	public void setNumeroParte(String numeroParte) {
		this.numeroParte = numeroParte;
	}

	public BigDecimal getCantidadSolicitada() {
		return cantidadSolicitada;
	}

	public void setCantidadSolicitada(BigDecimal cantidadSolicitada) {
		this.cantidadSolicitada = cantidadSolicitada;
	}

	public BigDecimal getCantidadSolped() {
		return cantidadSolped;
	}

	public void setCantidadSolped(BigDecimal cantidadSolped) {
		this.cantidadSolped = cantidadSolped;
	}

	public String getPosicionSolicitudPedido() {
		return this.posicionSolicitudPedido;
	}

	public void setPosicionSolicitudPedido(String posicionSolicitudPedido) {
		this.posicionSolicitudPedido = posicionSolicitudPedido;
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

	public Licitacion getLicitacion() {
		return this.licitacion;
	}

	public void setLicitacion(Licitacion licitacion) {
		this.licitacion = licitacion;
	}

	public UnidadMedida getUnidadMedida() {
		return this.unidadMedida;
	}

	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public String getGrupoArticulo() {
		return grupoArticulo;
	}

	public void setGrupoArticulo(String grupoArticulo) {
		this.grupoArticulo = grupoArticulo;
	}

    public Integer getIdNumberBienServicio() {
        return idNumberBienServicio;
    }

    public void setIdNumberBienServicio(Integer idNumberBienServicio) {
        this.idNumberBienServicio = idNumberBienServicio;
    }

    public Integer getIdNumberLicitacion() {
        return idNumberLicitacion;
    }

    public void setIdNumberLicitacion(Integer idNumberLicitacion) {
        this.idNumberLicitacion = idNumberLicitacion;
    }

    public Integer getIdNumberCentro() {
        return idNumberCentro;
    }

    public void setIdNumberCentro(Integer idNumberCentro) {
        this.idNumberCentro = idNumberCentro;
    }

    public CentroAlmacen getIdAlmacen() {
		return idAlmacen;
	}

	public void setIdAlmacen(CentroAlmacen idAlmacen) {
		this.idAlmacen = idAlmacen;
	}

	public CentroAlmacen getIdCentro() {
		return idCentro;
	}

	public void setIdCentro(CentroAlmacen idCentro) {
		this.idCentro = idCentro;
	}

	public String getNombreProveedorSugerido() {
		return nombreProveedorSugerido;
	}

	public void setNombreProveedorSugerido(String nombreProveedorSugerido) {
		this.nombreProveedorSugerido = nombreProveedorSugerido;
	}

	public String getNombreTipoDocumentoSap() {
		return nombreTipoDocumentoSap;
	}

	public void setNombreTipoDocumentoSap(String nombreTipoDocumentoSap) {
		this.nombreTipoDocumentoSap = nombreTipoDocumentoSap;
	}

	public Integer getIdTipoDocumentoSap() {
		return idTipoDocumentoSap;
	}

	public void setIdTipoDocumentoSap(Integer idTipoDocumentoSap) {
		this.idTipoDocumentoSap = idTipoDocumentoSap;
	}

	@Override
	public String toString() {
		return "LicitacionDetalle{" +
				"idLicitacionDetalle=" + idLicitacionDetalle +
				", cantidadSolicitada=" + cantidadSolicitada +
				", cantidadSolped=" + cantidadSolped +
				", descripcion='" + descripcion + '\'' +
				", especificacion='" + especificacion + '\'' +
				", numeroParte='" + numeroParte + '\'' +
				", posicionSolicitudPedido='" + posicionSolicitudPedido + '\'' +
				", solicitudPedido='" + solicitudPedido + '\'' +
				", grupoArticulo='" + grupoArticulo + '\'' +
				", bienServicio=" + bienServicio +
				", licitacion=" + licitacion +
				", unidadMedida=" + unidadMedida +
				", idAlmacen=" + idAlmacen +
				", idCentro=" + idCentro +
				", nombreProveedorSugerido='" + nombreProveedorSugerido + '\'' +
				", nombreTipoDocumentoSap='" + nombreTipoDocumentoSap + '\'' +
				", idTipoDocumentoSap=" + idTipoDocumentoSap +
				'}';
	}
}