package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the ccomparativo database table.
 * 
 */
@Entity
@Table(name="ccomparativo")
public class Ccomparativo extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_ccomparativo", unique=true, nullable=false)
	@GeneratedValue(generator = "ccomparativo_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ccomparativo_id_seq", sequenceName = "ccomparativo_id_seq", allocationSize = 1)
	private Integer idCcomparativo;

	@Column(name="fecha_creacion", nullable=false)
	private Timestamp fechaCreacion;

	@Column(name="fecha_modificacion")
	private Timestamp fechaModificacion;

	@Column(name="usuario_creacion", nullable=false)
	private Integer usuarioCreacion;

	@Column(name="usuario_modificacion")
	private Integer usuarioModificacion;

	//uni-directional many-to-one association to Licitacion
	@ManyToOne
	@JoinColumn(name="id_licitacion", nullable=false)
	private Licitacion licitacion;

	public Ccomparativo() {
	}

	public Integer getIdCcomparativo() {
		return this.idCcomparativo;
	}

	public void setIdCcomparativo(Integer idCcomparativo) {
		this.idCcomparativo = idCcomparativo;
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

	public Licitacion getLicitacion() {
		return this.licitacion;
	}

	public void setLicitacion(Licitacion licitacion) {
		this.licitacion = licitacion;
	}

	@Override
	public String toString() {
		return "Ccomparativo{" +
				"idCcomparativo=" + idCcomparativo +
				", fechaCreacion=" + fechaCreacion +
				", fechaModificacion=" + fechaModificacion +
				", usuarioCreacion=" + usuarioCreacion +
				", usuarioModificacion=" + usuarioModificacion +
				", licitacion=" + licitacion +
				'}';
	}
}