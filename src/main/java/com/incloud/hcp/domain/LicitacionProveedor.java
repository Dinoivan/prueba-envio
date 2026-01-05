package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import static jakarta.persistence.TemporalType.TIMESTAMP;


/**
 * The persistent class for the licitacion_proveedor database table.
 * 
 */
@Entity
@Table(name="licitacion_proveedor")
public class LicitacionProveedor extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LicitacionProveedorPK id;

	@Column(name="fecha_registro", nullable=false)
	private Timestamp fechaRegistro;

	@Column(name="evaluacion_desempeno", nullable=true, precision=5, scale=2)
	private BigDecimal evaluacionDesempeno;

	@Column(name="evaluacion_homologacion", nullable=true, precision=5, scale=2)
	private BigDecimal evaluacionHomologacion;

	@Column(name="no_conformes", nullable=true)
	private Integer noConformes;

	@Column(name = "fecha_cierre_recepcion", length = 29)
	@Temporal(TIMESTAMP)
	private Date fechaCierreRecepcion;


	@Size(max = 1)
	@Column(name = "ind_si_participa", length = 1)
	private String indSiParticipa;

	@Column(name = "fecha_confirmacion_participacion", length = 29)
	@Temporal(TIMESTAMP)
	private Date fechaConfirmacionParticipacion;

	//uni-directional many-to-one association to Licitacion
	@ManyToOne
	@JoinColumn(name="id_licitacion", nullable=false, insertable=false, updatable=false)
	private Licitacion licitacion;

	//uni-directional many-to-one association to Proveedor
	@ManyToOne
	@JoinColumn(name="id_proveedor", nullable=false, insertable=false, updatable=false)
	private Proveedor proveedor;

	@Transient
	private String fechaCierreRecepcionProveedorString;

	public LicitacionProveedor() {
	}

	public LicitacionProveedor(LicitacionProveedorPK id, Timestamp fechaRegistro, Licitacion licitacion, Proveedor proveedor) {
		this.id = id;
		this.fechaRegistro = fechaRegistro;
		this.licitacion = licitacion;
		this.proveedor = proveedor;
	}

	public LicitacionProveedor(LicitacionProveedorPK id,
							   Timestamp fechaRegistro,
							   Licitacion licitacion,
							   Proveedor proveedor,
							   Timestamp fechaCierreRecepcion) {
		this.id = id;
		this.fechaRegistro = fechaRegistro;
		this.licitacion = licitacion;
		this.proveedor = proveedor;
		this.fechaCierreRecepcion = fechaCierreRecepcion;
	}

	public LicitacionProveedor(LicitacionProveedorPK id,
							   Timestamp fechaRegistro,
							   Licitacion licitacion,
							   Proveedor proveedor,
							   Timestamp fechaCierreRecepcion,
							   String indicadorSiParticipa) {
		this.id = id;
		this.fechaRegistro = fechaRegistro;
		this.licitacion = licitacion;
		this.proveedor = proveedor;
		this.fechaCierreRecepcion = fechaCierreRecepcion;
		this.indSiParticipa = indicadorSiParticipa;
	}

	public LicitacionProveedorPK getId() {
		return this.id;
	}

	public void setId(LicitacionProveedorPK id) {
		this.id = id;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Licitacion getLicitacion() {
		return this.licitacion;
	}

	public void setLicitacion(Licitacion licitacion) {
		this.licitacion = licitacion;
	}

	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public BigDecimal getEvaluacionDesempeno() {
		return evaluacionDesempeno;
	}

	public void setEvaluacionDesempeno(BigDecimal evaluacionDesempeno) {
		this.evaluacionDesempeno = evaluacionDesempeno;
	}

	public BigDecimal getEvaluacionHomologacion() {
		return evaluacionHomologacion;
	}

	public void setEvaluacionHomologacion(BigDecimal evaluacionHomologacion) {
		this.evaluacionHomologacion = evaluacionHomologacion;
	}

	public Integer getNoConformes() {
		return noConformes;
	}

	public void setNoConformes(Integer noConformes) {
		this.noConformes = noConformes;
	}

	public Date getFechaCierreRecepcion() {
		return fechaCierreRecepcion;
	}

	public void setFechaCierreRecepcion(Date fechaCierreRecepcion) {
		this.fechaCierreRecepcion = fechaCierreRecepcion;
	}


	public String getFechaCierreRecepcionProveedorString() {
		return fechaCierreRecepcionProveedorString;
	}

	public void setFechaCierreRecepcionProveedorString(String fechaCierreRecepcionProveedorString) {
		this.fechaCierreRecepcionProveedorString = fechaCierreRecepcionProveedorString;
	}


	public Date getFechaConfirmacionParticipacion() {
		return fechaConfirmacionParticipacion;
	}

	public void setFechaConfirmacionParticipacion(Date fechaConfirmacionParticipacion) {
		this.fechaConfirmacionParticipacion = fechaConfirmacionParticipacion;
	}

	public String getIndSiParticipa() {
		return indSiParticipa;
	}

	public void setIndSiParticipa(String indSiParticipa) {
		this.indSiParticipa = indSiParticipa;
	}

	@Override
	public String toString() {
		return "LicitacionProveedor{" +
				"id=" + id +
				", fechaRegistro=" + fechaRegistro +
				", evaluacionDesempeno=" + evaluacionDesempeno +
				", evaluacionHomologacion=" + evaluacionHomologacion +
				", noConformes=" + noConformes +
				", fechaCierreRecepcion=" + fechaCierreRecepcion +
				", indSiParticipa='" + indSiParticipa + '\'' +
				", fechaConfirmacionParticipacion=" + fechaConfirmacionParticipacion +
				", licitacion=" + licitacion +
				", proveedor=" + proveedor +
				", fechaCierreRecepcionProveedorString='" + fechaCierreRecepcionProveedorString + '\'' +
				'}';
	}
}