package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;


/**
 * The persistent class for the solicitud_adjunto_blacklist database table.
 * 
 */
@Entity
@Table(name="solicitud_adjunto_blacklist")
public class SolicitudAdjuntoBlacklist extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_solicitud_adjunto", unique=true, nullable=false)
	@GeneratedValue(generator = "solicitud_adjunto_blacklist_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "solicitud_adjunto_blacklist_id_seq", sequenceName = "solicitud_adjunto_blacklist_id_seq", allocationSize = 1)
	private Integer idSolicitudAdjunto;

	@Column(name="adjunto_url", nullable=false, length=1000)
	private String adjuntoUrl;

	@Column(name="adjunto_id", nullable=false, length=60)
	private String adjuntoId;

	@Column(name="adjunto_nombre", nullable=false, length=100)
	private String adjuntoNombre;

	@Column(name="adjunto_tipo", nullable=false, length=100)
	private String adjuntoTipo;

	//uni-directional many-to-one association to SolicitudBlacklist
	@ManyToOne
	@JoinColumn(name="id_solicitud", nullable=false)
	private SolicitudBlacklist solicitudBlacklist;

	public SolicitudAdjuntoBlacklist() {
	}

	public Integer getIdSolicitudAdjunto() {
		return idSolicitudAdjunto;
	}

	public void setIdSolicitudAdjunto(Integer idSolicitudAdjunto) {
		this.idSolicitudAdjunto = idSolicitudAdjunto;
	}

	public String getAdjuntoUrl() {
		return adjuntoUrl;
	}

	public void setAdjuntoUrl(String adjuntoUrl) {
		this.adjuntoUrl = adjuntoUrl;
	}

	public String getAdjuntoId() {
		return adjuntoId;
	}

	public void setAdjuntoId(String adjuntoId) {
		this.adjuntoId = adjuntoId;
	}

	public String getAdjuntoNombre() {
		return adjuntoNombre;
	}

	public void setAdjuntoNombre(String adjuntoNombre) {
		this.adjuntoNombre = adjuntoNombre;
	}

	public String getAdjuntoTipo() {
		return adjuntoTipo;
	}

	public void setAdjuntoTipo(String adjuntoTipo) {
		this.adjuntoTipo = adjuntoTipo;
	}

	public SolicitudBlacklist getSolicitudBlacklist() {
		return solicitudBlacklist;
	}

	public void setSolicitudBlacklist(SolicitudBlacklist solicitudBlacklist) {
		this.solicitudBlacklist = solicitudBlacklist;
	}

	@Override
	public String toString() {
		return "SolicitudAdjuntoBlacklist{" +
				"idSolicitudAdjunto=" + idSolicitudAdjunto +
				", adjuntoUrl='" + adjuntoUrl + '\'' +
				", adjuntoId='" + adjuntoId + '\'' +
				", adjuntoNombre='" + adjuntoNombre + '\'' +
				", adjuntoTipo='" + adjuntoTipo + '\'' +
				", solicitudBlacklist=" + solicitudBlacklist +
				'}';
	}
}