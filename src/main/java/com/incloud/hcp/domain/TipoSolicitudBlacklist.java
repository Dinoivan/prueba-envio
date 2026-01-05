package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;


/**
 * The persistent class for the tipo_solicitud_blacklist database table.
 * 
 */
@Entity
@Table(name="tipo_solicitud_blacklist")
public class TipoSolicitudBlacklist extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_solicitud", unique=true, nullable=false)
	@GeneratedValue(generator = "tipo_solicitud_blacklist_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "tipo_solicitud_blacklist_id_seq", sequenceName = "tipo_solicitud_blacklist_id_seq", allocationSize = 1)
	private Integer idTipoSolicitud;

	@Column(nullable=false, length=100)
	private String descripcion;

	@Column(name="codigo_tipo_solicitud", nullable=false, length=2)
	private String codigoTipoSolicitud;

	public TipoSolicitudBlacklist() {
	}

	public Integer getIdTipoSolicitud() {
		return this.idTipoSolicitud;
	}

	public void setIdTipoSolicitud(Integer idTipoSolicitud) {
		this.idTipoSolicitud = idTipoSolicitud;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigoTipoSolicitud() {
		return codigoTipoSolicitud;
	}

	public void setCodigoTipoSolicitud(String codigoTipoSolicitud) {
		this.codigoTipoSolicitud = codigoTipoSolicitud;
	}

	@Override
	public String toString() {
		return "TipoSolicitudBlacklist{" +
				"idTipoSolicitud=" + idTipoSolicitud +
				", descripcion='" + descripcion + '\'' +
				", codigoTipoSolicitud='" + codigoTipoSolicitud + '\'' +
				'}';
	}
}