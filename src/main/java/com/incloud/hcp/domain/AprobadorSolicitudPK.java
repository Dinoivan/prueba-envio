package com.incloud.hcp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

/**
 * The primary key class for the aprobador_solicitud database table.
 * 
 */
@Embeddable
public class AprobadorSolicitudPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_solicitud")
	private Integer idSolicitud;

	@Column(name="id_usuario")
	private Integer idUsuario;

	public AprobadorSolicitudPK() {
	}

	public AprobadorSolicitudPK(Integer idSolicitud, Integer idUsuario) {
		this.idSolicitud = idSolicitud;
		this.idUsuario = idUsuario;
	}

	public Integer getIdSolicitud() {
		return this.idSolicitud;
	}
	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public Integer getIdUsuario() {
		return this.idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AprobadorSolicitudPK)) {
			return false;
		}
		AprobadorSolicitudPK castOther = (AprobadorSolicitudPK)other;
		return 
			this.idSolicitud.equals(castOther.idSolicitud)
			&& this.idUsuario.equals(castOther.idUsuario);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idSolicitud.hashCode();
		hash = hash * prime + this.idUsuario.hashCode();
		
		return hash;
	}

	@Override
	public String toString() {
		return "AprobadorSolicitudPK{" +
				"idSolicitud=" + idSolicitud +
				", idUsuario=" + idUsuario +
				'}';
	}
}