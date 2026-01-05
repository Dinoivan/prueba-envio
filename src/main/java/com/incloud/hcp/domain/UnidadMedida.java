package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;


/**
 * The persistent class for the unidad_medida database table.
 * 
 */
@Entity
@Table(name="unidad_medida")
public class UnidadMedida extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_unidad_medida", unique=true, nullable=false)
	@GeneratedValue(generator = "unidad_medida_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "unidad_medida_id_seq", sequenceName = "unidad_medida_id_seq", allocationSize = 1)
	private Integer idUnidadMedida;

	@Column(length=100)
	private String descripcion;

	@Column(name="codigo_sap", nullable=false, length=4)
	private String codigoSap;

	@Column(name="texto_um", nullable=false, length=20)
	private String textoUm;

	public UnidadMedida() {
	}

	public Integer getIdUnidadMedida() {
		return this.idUnidadMedida;
	}

	public void setIdUnidadMedida(Integer idUnidadMedida) {
		this.idUnidadMedida = idUnidadMedida;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTextoUm() {
		return this.textoUm;
	}

	public void setTextoUm(String textoUm) {
		this.textoUm = textoUm;
	}

	public String getCodigoSap() {
		return codigoSap;
	}

	public void setCodigoSap(String codigoSap) {
		this.codigoSap = codigoSap;
	}

	@Override
	public String toString() {
		return "UnidadMedida{" +
				"idUnidadMedida=" + idUnidadMedida +
				", descripcion='" + descripcion + '\'' +
				", codigoSap='" + codigoSap + '\'' +
				", textoUm='" + textoUm + '\'' +
				'}';
	}
}