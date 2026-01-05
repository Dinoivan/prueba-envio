package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the tipo_licitacion database table.
 * 
 */
@Entity
@Table(name="tipo_licitacion")
public class TipoLicitacion extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_licitacion", unique=true, nullable=false)
	//@GeneratedValue(generator = "tipo_licitacion_id_seq", strategy = GenerationType.SEQUENCE)
	//@SequenceGenerator(name = "tipo_licitacion_id_seq", sequenceName = "tipo_licitacion_id_seq", allocationSize = 1)
	private Integer idTipoLicitacion;

	@Column(nullable=false, length=60)
	private String descripcion;

	@Column(name="id_padre")
	private Integer idPadre;

	@Column(nullable=false)
	private Integer nivel;

	@Column(name="peso_moneda", nullable=true, precision=5, scale=2)
	private BigDecimal pesoMoneda;

	@Column(name="peso_condicion", nullable=true, precision=5, scale=2)
	private BigDecimal pesoCondicion;

	@Column(name="peso_evaluacion_tecnica", nullable=true, precision=5, scale=2)
	private BigDecimal pesoEvaluacionTecnica;

	@Column(name="ind_evaluacion_auto",nullable=true, length=1)
	private String indEvaluacionAuto;


	public TipoLicitacion() {
	}

	public Integer getIdTipoLicitacion() {
		return this.idTipoLicitacion;
	}

	public void setIdTipoLicitacion(Integer idTipoLicitacion) {
		this.idTipoLicitacion = idTipoLicitacion;
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

	public BigDecimal getPesoMoneda() {
		return pesoMoneda;
	}

	public void setPesoMoneda(BigDecimal pesoMoneda) {
		this.pesoMoneda = pesoMoneda;
	}

	public BigDecimal getPesoCondicion() {
		return pesoCondicion;
	}

	public void setPesoCondicion(BigDecimal pesoCondicion) {
		this.pesoCondicion = pesoCondicion;
	}

	public BigDecimal getPesoEvaluacionTecnica() {
		return pesoEvaluacionTecnica;
	}

	public void setPesoEvaluacionTecnica(BigDecimal pesoEvaluacionTecnica) {
		this.pesoEvaluacionTecnica = pesoEvaluacionTecnica;
	}

	public String getIndEvaluacionAuto() {
		return indEvaluacionAuto;
	}

	public void setIndEvaluacionAuto(String indEvaluacionAuto) {
		this.indEvaluacionAuto = indEvaluacionAuto;
	}

	@Override
	public String toString() {
		return "TipoLicitacion{" +
				"idTipoLicitacion=" + idTipoLicitacion +
				", descripcion='" + descripcion + '\'' +
				", idPadre=" + idPadre +
				", nivel=" + nivel +
				", pesoMoneda=" + pesoMoneda +
				", pesoCondicion=" + pesoCondicion +
				", pesoEvaluacionTecnica=" + pesoEvaluacionTecnica +
				", indEvaluacionAuto='" + indEvaluacionAuto + '\'' +
				'}';
	}
}