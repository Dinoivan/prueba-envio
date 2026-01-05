package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;


/**
 * The persistent class for the ubigeo database table.
 * 
 */
@Entity
@Table(name="ubigeo")
public class Ubigeo extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_ubigeo", precision = 10)
	//@GeneratedValue(strategy = AUTO)
//	@GeneratedValue(generator = "ubigeo_id_seq", strategy = GenerationType.SEQUENCE)
//	@SequenceGenerator(name = "ubigeo_id_seq", sequenceName = "ubigeo_id_seq", allocationSize = 1)
	private Integer idUbigeo;

	@Column(name="codigo_ubigeo_sap", nullable=false, length=12)
	private String codigoUbigeoSap;

	@Column(nullable=false, length=40)
	private String descripcion;

	@Column(name="id_padre")
	private Integer idPadre;

	@Column(nullable=false)
	private Integer nivel;

	@Column(name="codigo_ubigeo_sap_erp", length=12)
	private String codigoUbigeoSapErp;

	public Ubigeo() {
	}

	public Integer getIdUbigeo() {
		return this.idUbigeo;
	}

	public void setIdUbigeo(Integer idUbigeo) {
		this.idUbigeo = idUbigeo;
	}

	public String getCodigoUbigeoSap() {
		return this.codigoUbigeoSap;
	}

	public void setCodigoUbigeoSap(String codigoUbigeoSap) {
		this.codigoUbigeoSap = codigoUbigeoSap;
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

	public String getCodigoUbigeoSapErp() {
		return codigoUbigeoSapErp;
	}

	public void setCodigoUbigeoSapErp(String codigoUbigeoSapErp) {
		this.codigoUbigeoSapErp = codigoUbigeoSapErp;
	}

	@Override
	public String toString() {
		return "Ubigeo{" +
				"idUbigeo=" + idUbigeo +
				", codigoUbigeoSap='" + codigoUbigeoSap + '\'' +
				", descripcion='" + descripcion + '\'' +
				", idPadre=" + idPadre +
				", nivel=" + nivel +
				", codigoUbigeoSapErp='" + codigoUbigeoSapErp + '\'' +
				'}';
	}
}