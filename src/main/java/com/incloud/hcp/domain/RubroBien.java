package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the rubro_bien database table.
 * 
 */
@Entity
@Table(name="rubro_bien")
public class RubroBien extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_rubro", unique=true, nullable=false)
	@GeneratedValue(generator = "rubro_bien_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "rubro_bien_id_seq", sequenceName = "rubro_bien_id_seq", allocationSize = 1)
	private Integer idRubro;

	@Column(name="codigo_sap", length=10)
	private String codigoSap;

	@Column(length=60)
	private String descripcion;

	@Column
	private Integer nivel;

	@ManyToOne
	@JoinColumn(name="id_tipo_licitacion", nullable=true)
	private TipoLicitacion tipoLicitacion;


	@ManyToOne
	@JoinColumn(name="id_tipo_cuestionario", nullable=true)
	private TipoLicitacion tipoCuestionario;


	public RubroBien() {
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getIdRubro() {
		return idRubro;
	}

	public void setIdRubro(Integer idRubro) {
		this.idRubro = idRubro;
	}

	public String getCodigoSap() {
		return codigoSap;
	}

	public void setCodigoSap(String codigoSap) {
		this.codigoSap = codigoSap;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public TipoLicitacion getTipoLicitacion() {
		return tipoLicitacion;
	}

	public void setTipoLicitacion(TipoLicitacion tipoLicitacion) {
		this.tipoLicitacion = tipoLicitacion;
	}

	public TipoLicitacion getTipoCuestionario() {
		return tipoCuestionario;
	}

	public void setTipoCuestionario(TipoLicitacion tipoCuestionario) {
		this.tipoCuestionario = tipoCuestionario;
	}

	@Override
	public String toString() {
		return "RubroBien{" +
				"idRubro=" + idRubro +
				", codigoSap='" + codigoSap + '\'' +
				", descripcion='" + descripcion + '\'' +
				", nivel=" + nivel +
				", tipoLicitacion=" + tipoLicitacion +
				", tipoCuestionario=" + tipoCuestionario +
				'}';
	}
}