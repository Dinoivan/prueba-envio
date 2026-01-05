package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import com.incloud.hcp.validation.FixedLength;
import jakarta.persistence.*;

import java.io.Serializable;


/**
 * The persistent class for the linea_comercial database table.
 * 
 */
@Entity
@Table(name="linea_comercial")
public class LineaComercial extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_linea_comercial", unique=true, nullable=false)
	@GeneratedValue(generator = "linea_comercial_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "linea_comercial_id_seq", sequenceName = "linea_comercial_id_seq", allocationSize = 1)
	private Integer idLineaComercial;

	@Column(nullable=false, length=100)
	private String descripcion;

	@Column(name="id_padre")
	private Integer idPadre;

	@Column(nullable=false)
	private Integer nivel;

	@Column(name="ind_general", nullable=false, length=1)
	private String indGeneral;

	@Column(length=2000)
	private String comentario;

	@FixedLength(length = 1)
	@Column(name = "ind_bien", length = 1)
	private String indBien;

	@FixedLength(length = 1)
	@Column(name = "ind_servicio", length = 1)
	private String indServicio;

	@Column(name = "codigo_grupo_comercial", length = 3)
	private String codigoGrupoComercial;


	@Transient
	private String descripcionPadre;

	@Transient
	private String codigoGrupoComercialPadre;

	@Transient
	private Integer idLinea;

	@Transient
	private Integer idFamilia;

	public LineaComercial() {
	}

	public Integer getIdLineaComercial() {
		return this.idLineaComercial;
	}

	public void setIdLineaComercial(Integer idLineaComercial) {
		this.idLineaComercial = idLineaComercial;
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

	public String getIndGeneral() {
		return indGeneral;
	}

	public void setIndGeneral(String indGeneral) {
		this.indGeneral = indGeneral;
	}

	// -- [indBien] ------------------------


	public String getIndBien() {
		return indBien;
	}

	public void setIndBien(String indBien) {
		this.indBien = indBien;
	}

	public LineaComercial indBien(String indBien) {
		setIndBien(indBien);
		return this;
	}
	// -- [indServicio] ------------------------

	public String getIndServicio() {
		return indServicio;
	}

	public void setIndServicio(String indServicio) {
		this.indServicio = indServicio;
	}

	public LineaComercial indServicio(String indServicio) {
		setIndServicio(indServicio);
		return this;
	}

	// -- [codigoGrupoComercial] ------------------------

	public String getCodigoGrupoComercial() {
		return codigoGrupoComercial;
	}

	public void setCodigoGrupoComercial(String codigoGrupoComercial) {
		this.codigoGrupoComercial = codigoGrupoComercial;
	}

	public LineaComercial codigoGrupoComercial(String codigoGrupoComercial) {
		setCodigoGrupoComercial(codigoGrupoComercial);
		return this;
	}

	public String getDescripcionPadre() {
		return descripcionPadre;
	}

	public void setDescripcionPadre(String descripcionPadre) {
		this.descripcionPadre = descripcionPadre;
	}

	public String getCodigoGrupoComercialPadre() {
		return codigoGrupoComercialPadre;
	}

	public void setCodigoGrupoComercialPadre(String codigoGrupoComercialPadre) {
		this.codigoGrupoComercialPadre = codigoGrupoComercialPadre;
	}

	public Integer getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(Integer idLinea) {
		this.idLinea = idLinea;
	}

	public Integer getIdFamilia() {
		return idFamilia;
	}

	public void setIdFamilia(Integer idFamilia) {
		this.idFamilia = idFamilia;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Override
	public String toString() {
		return "LineaComercial{" +
				"idLineaComercial=" + idLineaComercial +
				", descripcion='" + descripcion + '\'' +
				", idPadre=" + idPadre +
				", nivel=" + nivel +
				", indGeneral='" + indGeneral + '\'' +
				", comentario='" + comentario + '\'' +
				", indBien='" + indBien + '\'' +
				", indServicio='" + indServicio + '\'' +
				", codigoGrupoComercial='" + codigoGrupoComercial + '\'' +
				", descripcionPadre='" + descripcionPadre + '\'' +
				", codigoGrupoComercialPadre='" + codigoGrupoComercialPadre + '\'' +
				", idLinea=" + idLinea +
				", idFamilia=" + idFamilia +
				'}';
	}
}