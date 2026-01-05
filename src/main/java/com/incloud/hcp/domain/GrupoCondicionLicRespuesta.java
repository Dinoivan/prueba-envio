package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the grupo_condicion_lic_respuesta database table.
 * 
 */
@Entity
@Table(name="grupo_condicion_lic_respuesta")
public class GrupoCondicionLicRespuesta extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_condicion_respuesta", unique=true, nullable=false)
	@GeneratedValue(generator = "grupo_condicion_lic_respuesta_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "grupo_condicion_lic_respuesta_id_seq", sequenceName = "grupo_condicion_lic_respuesta_id_seq", allocationSize = 1)
	private Integer idCondicionRespuesta;

	@Column(name="ind_predefinido", length=1)
	private String indPredefinido;

	@Column(nullable=false, length=30)
	private String opcion;

	@Column(name="puntaje", nullable=false, precision=15, scale=2)
	private BigDecimal puntaje;

	//uni-directional many-to-one association to LicitacionGrupoCondicionLic
	@ManyToOne
	@JoinColumn(name="id_licitacion_grupo_condicion", nullable=false)
	private LicitacionGrupoCondicionLic licitacionGrupoCondicionLic;

	public GrupoCondicionLicRespuesta() {
	}

	public GrupoCondicionLicRespuesta(String indPredefinido, String opcion, BigDecimal puntaje, LicitacionGrupoCondicionLic licitacionGrupoCondicionLic) {
		this.indPredefinido = indPredefinido;
		this.opcion = opcion;
		this.puntaje = puntaje;
		this.licitacionGrupoCondicionLic = licitacionGrupoCondicionLic;
	}

	public Integer getIdCondicionRespuesta() {
		return this.idCondicionRespuesta;
	}

	public void setIdCondicionRespuesta(Integer idCondicionRespuesta) {
		this.idCondicionRespuesta = idCondicionRespuesta;
	}

	public String getIndPredefinido() {
		return this.indPredefinido;
	}

	public void setIndPredefinido(String indPredefinido) {
		this.indPredefinido = indPredefinido;
	}

	public String getOpcion() {
		return this.opcion;
	}

	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

	public BigDecimal getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(BigDecimal puntaje) {
		this.puntaje = puntaje;
	}

	public LicitacionGrupoCondicionLic getLicitacionGrupoCondicionLic() {
		return this.licitacionGrupoCondicionLic;
	}

	public void setLicitacionGrupoCondicionLic(LicitacionGrupoCondicionLic licitacionGrupoCondicionLic) {
		this.licitacionGrupoCondicionLic = licitacionGrupoCondicionLic;
	}

	@Override
	public String toString() {
		return "GrupoCondicionLicRespuesta{" +
				"idCondicionRespuesta=" + idCondicionRespuesta +
				", indPredefinido='" + indPredefinido + '\'' +
				", opcion='" + opcion + '\'' +
				", puntaje=" + puntaje +
				", licitacionGrupoCondicionLic=" + licitacionGrupoCondicionLic +
				'}';
	}
}