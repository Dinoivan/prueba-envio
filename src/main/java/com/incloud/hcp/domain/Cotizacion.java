package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import com.incloud.hcp.util.constant.CotizacionConstant;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the cotizacion database table.
 * 
 */
@Entity
@Table(name="cotizacion")
public class Cotizacion extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_cotizacion", unique=true, nullable=false)
	@GeneratedValue(generator = "cotizacion_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "cotizacion_id_seq", sequenceName = "cotizacion_id_seq", allocationSize = 1)
	private Integer idCotizacion;

	@Column(name="estado_cotizacion", nullable=false, length=2)
	private String estadoCotizacion;

	@Column(name="fecha_creacion", nullable=false)
	private Timestamp fechaCreacion;

	@Column(name="fecha_modificacion")
	private Timestamp fechaModificacion;

	@Column(name="importe_total", precision=15, scale=3)
	private BigDecimal importeTotal;

	@Column(name="ind_ganador", nullable=false, length=1)
	private String indGanador;

	@Column(name="usuario_creacion", nullable=false)
	private Integer usuarioCreacion;

	@Column(name="usuario_modificacion")
	private Integer usuarioModificacion;

	//uni-directional many-to-one association to Proveedor
	@ManyToOne
	@JoinColumn(name="id_proveedor", nullable=false)
	private Proveedor proveedor;

	//uni-directional many-to-one association to Licitacion
	@ManyToOne
	@JoinColumn(name="id_licitacion", nullable=false)
	private Licitacion licitacion;

	//uni-directional many-to-one association to Moneda
	@ManyToOne
	@JoinColumn(name="id_moneda", nullable=false)
	private Moneda moneda;


	@Transient
	private String descripcionEstadoCotizacion;

	@Transient
	private LicitacionProveedor licitacionProveedor;


	public Cotizacion() {
	}

	public Integer getIdCotizacion() {
		return this.idCotizacion;
	}

	public void setIdCotizacion(Integer idCotizacion) {
		this.idCotizacion = idCotizacion;
	}

	public String getEstadoCotizacion() {
		return this.estadoCotizacion;
	}

	public void setEstadoCotizacion(String estadoCotizacion) {
		this.estadoCotizacion = estadoCotizacion;
	}

	public Timestamp getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Timestamp getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public BigDecimal getImporteTotal() {
		return this.importeTotal;
	}

	public void setImporteTotal(BigDecimal importeTotal) {
		this.importeTotal = importeTotal;
	}

	public String getIndGanador() {
		return this.indGanador;
	}

	public void setIndGanador(String indGanador) {
		this.indGanador = indGanador;
	}

	public Integer getUsuarioCreacion() {
		return this.usuarioCreacion;
	}

	public void setUsuarioCreacion(Integer usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Integer getUsuarioModificacion() {
		return this.usuarioModificacion;
	}

	public void setUsuarioModificacion(Integer usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Licitacion getLicitacion() {
		return licitacion;
	}

	public void setLicitacion(Licitacion licitacion) {
		this.licitacion = licitacion;
	}

	public Moneda getMoneda() {
		return this.moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public String getDescripcionEstadoCotizacion() {

		if (this.estadoCotizacion.equals(CotizacionConstant.ESTADO_GENERADA))
			return CotizacionConstant.DESCRIPCION_ESTADO_GENERADA;
		if (this.estadoCotizacion.equals(CotizacionConstant.ESTADO_ENVIADA))
			return CotizacionConstant.DESCRIPCION_ESTADO_ENVIADA;
		if (this.estadoCotizacion.equals(CotizacionConstant.ESTADO_NO_PARTICIPAR))
			return CotizacionConstant.DESCRIPCION_ESTADO_NO_PARTICIPAR;

		return CotizacionConstant.DESCRIPCION_ESTADO_POR_GENERADA;
	}

	public void setDescripcionEstadoCotizacion(String descripcionEstadoCotizacion) {
		this.descripcionEstadoCotizacion = descripcionEstadoCotizacion;
	}

	public LicitacionProveedor getLicitacionProveedor() {
		return licitacionProveedor;
	}

	public void setLicitacionProveedor(LicitacionProveedor licitacionProveedor) {
		this.licitacionProveedor = licitacionProveedor;
	}

	@Override
	public String toString() {
		return "Cotizacion{" +
				"idCotizacion=" + idCotizacion +
				", estadoCotizacion='" + estadoCotizacion + '\'' +
				", fechaCreacion=" + fechaCreacion +
				", fechaModificacion=" + fechaModificacion +
				", importeTotal=" + importeTotal +
				", indGanador='" + indGanador + '\'' +
				", usuarioCreacion=" + usuarioCreacion +
				", usuarioModificacion=" + usuarioModificacion +
				", proveedor=" + proveedor +
				", licitacion=" + licitacion +
				", moneda=" + moneda +
				", descripcionEstadoCotizacion='" + descripcionEstadoCotizacion + '\'' +
				", licitacionProveedor=" + licitacionProveedor +
				'}';
	}
}