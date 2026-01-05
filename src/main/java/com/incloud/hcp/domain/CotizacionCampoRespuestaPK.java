package com.incloud.hcp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the cotizacion_campo_respuesta database table.
 * 
 */
@Embeddable
public class CotizacionCampoRespuestaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_cotizacion")
	private Integer idCotizacion;

	@Column(name="id_licitacion_grupo_condicion")
	private Integer idLicitacionGrupoCondicion;

	public CotizacionCampoRespuestaPK() {
	}
	public Integer getIdCotizacion() {
		return this.idCotizacion;
	}
	public void setIdCotizacion(Integer idCotizacion) {
		this.idCotizacion = idCotizacion;
	}
	public Integer getIdLicitacionGrupoCondicion() {
		return this.idLicitacionGrupoCondicion;
	}
	public void setIdLicitacionGrupoCondicion(Integer idLicitacionGrupoCondicion) {
		this.idLicitacionGrupoCondicion = idLicitacionGrupoCondicion;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CotizacionCampoRespuestaPK)) {
			return false;
		}
		CotizacionCampoRespuestaPK castOther = (CotizacionCampoRespuestaPK)other;
		return 
			this.idCotizacion.equals(castOther.idCotizacion)
			&& this.idLicitacionGrupoCondicion.equals(castOther.idLicitacionGrupoCondicion);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idCotizacion.hashCode();
		hash = hash * prime + this.idLicitacionGrupoCondicion.hashCode();
		
		return hash;
	}
}