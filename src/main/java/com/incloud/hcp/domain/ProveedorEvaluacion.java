package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the proveedor_evaluacion database table.
 * 
 */
@Entity
@Table(name="proveedor_evaluacion")
public class ProveedorEvaluacion extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_proveedor_evaluacion", unique=true, nullable=false)
	@GeneratedValue(generator = "proveedor_evaluacion_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "proveedor_evaluacion_id_seq", sequenceName = "proveedor_evaluacion_id_seq", allocationSize = 1)
	private Integer idProveedorEvaluacion;

	@Column(nullable=false)
	private Integer evaluacion;

	@Column(name="anno", nullable=false)
	private String anio;

	@Column(name="fecha_evaluacion", nullable=false)
	private Date fechaEvaluacion;

	//uni-directional many-to-one association to Proveedor
	@ManyToOne
	@JoinColumn(name="id_proveedor", nullable=false)
	private Proveedor proveedor;

	public ProveedorEvaluacion() {
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getIdProveedorEvaluacion() {
		return idProveedorEvaluacion;
	}

	public void setIdProveedorEvaluacion(Integer idProveedorEvaluacion) {
		this.idProveedorEvaluacion = idProveedorEvaluacion;
	}

	public Integer getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(Integer evaluacion) {
		this.evaluacion = evaluacion;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public Date getFechaEvaluacion() {
		return fechaEvaluacion;
	}

	public void setFechaEvaluacion(Date fechaEvaluacion) {
		this.fechaEvaluacion = fechaEvaluacion;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	@Override
	public String toString() {
		return "ProveedorEvaluacion{" +
				"idProveedorEvaluacion=" + idProveedorEvaluacion +
				", evaluacion=" + evaluacion +
				", anio='" + anio + '\'' +
				", fechaEvaluacion=" + fechaEvaluacion +
				", proveedor=" + proveedor +
				'}';
	}
}