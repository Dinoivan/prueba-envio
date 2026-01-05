package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the clase_documento database table.
 * 
 */
@Entity
@Table(name="clase_documento")
public class ClaseDocumento extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_clase_documento", unique=true, nullable=false)
	@GeneratedValue(generator = "clase_documento_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "clase_documento_id_seq", sequenceName = "clase_documento_id_seq", allocationSize = 1)
	private Integer idClaseDocumento;

	@Column(name="codigo_clase_documento", nullable=false, length=4)
	private String codigoClaseDocumento;

	@Column(nullable=false, length=50)
	private String descripcion;

	@Column(name="id_padre")
	private Integer idPadre;

	@Column(nullable=false)
	private Integer nivel;

	@Column(name="tipo_clase_documento",  length=10)
	private String tipoClaseDocumento;

	public ClaseDocumento() {
	}

	public Integer getIdClaseDocumento() {
		return this.idClaseDocumento;
	}

	public void setIdClaseDocumento(Integer idClaseDocumento) {
		this.idClaseDocumento = idClaseDocumento;
	}

	public String getCodigoClaseDocumento() {
		return this.codigoClaseDocumento;
	}

	public void setCodigoClaseDocumento(String codigoClaseDocumento) {
		this.codigoClaseDocumento = codigoClaseDocumento;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getIdPadre() {
		return this.idPadre;
	}

	public void setIdPadre(Integer idPadre) {
		this.idPadre = idPadre;
	}

	public Integer getNivel() {
		return this.nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public String getTipoClaseDocumento() {
		return tipoClaseDocumento;
	}

	public void setTipoClaseDocumento(String tipoClaseDocumento) {
		this.tipoClaseDocumento = tipoClaseDocumento;
	}

	@Override
	public String toString() {
		return "ClaseDocumento{" +
				"idClaseDocumento=" + idClaseDocumento +
				", codigoClaseDocumento='" + codigoClaseDocumento + '\'' +
				", descripcion='" + descripcion + '\'' +
				", idPadre=" + idPadre +
				", nivel=" + nivel +
				", tipoClaseDocumento='" + tipoClaseDocumento + '\'' +
				'}';
	}
}