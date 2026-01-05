package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the aprobador_solicitud database table.
 * 
 */
@Entity
@Table(name="aprobador_solicitud")
public class AprobadorSolicitud extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	//uni-directional many-to-one association to SolicitudBlacklist
	@ManyToOne
	@JoinColumn(name="id_solicitud", nullable=false, insertable=false, updatable=false)
	private SolicitudBlacklist solicitudBlacklist;

	//uni-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario", nullable=false, insertable=false, updatable=false)
	private Usuario usuario;

	@EmbeddedId
	private AprobadorSolicitudPK id;

	@Column(name="orden_aprobacion", nullable=true, length=1)
	private String ordenAprobacion;

	@Column(name="fecha_respuesta_aprobador", nullable=true)
	private Timestamp fechaRespuestaAprobador;

	@Column(nullable=false, length=2)
	private String estado;

	@Column(name="ind_activo_aprobacion", nullable=true, length=1)
	private String indActivoAprobacion;


	public AprobadorSolicitud() {
	}

	public AprobadorSolicitud(SolicitudBlacklist solicitudBlacklist, Usuario usuario, AprobadorSolicitudPK id, String ordenAprobacion, String estado, String indActivoAprobacion) {
		this.solicitudBlacklist = solicitudBlacklist;
		this.usuario = usuario;
		this.id = id;
		this.ordenAprobacion = ordenAprobacion;
		this.estado = estado;
		this.indActivoAprobacion = indActivoAprobacion;
	}

	public SolicitudBlacklist getSolicitudBlacklist() {
		return solicitudBlacklist;
	}

	public void setSolicitudBlacklist(SolicitudBlacklist solicitudBlacklist) {
		this.solicitudBlacklist = solicitudBlacklist;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public AprobadorSolicitudPK getId() {
		return id;
	}

	public void setId(AprobadorSolicitudPK id) {
		this.id = id;
	}

	public String getOrdenAprobacion() {
		return ordenAprobacion;
	}

	public void setOrdenAprobacion(String ordenAprobacion) {
		this.ordenAprobacion = ordenAprobacion;
	}

	public Timestamp getFechaRespuestaAprobador() {
		return fechaRespuestaAprobador;
	}

	public void setFechaRespuestaAprobador(Timestamp fechaRespuestaAprobador) {
		this.fechaRespuestaAprobador = fechaRespuestaAprobador;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getIndActivoAprobacion() {
		return indActivoAprobacion;
	}

	public void setIndActivoAprobacion(String indActivoAprobacion) {
		this.indActivoAprobacion = indActivoAprobacion;
	}

	@Override
	public String toString() {
		return "AprobadorSolicitud{" +
				"solicitudBlacklist=" + solicitudBlacklist +
				", usuario=" + usuario +
				", id=" + id +
				", ordenAprobacion='" + ordenAprobacion + '\'' +
				", fechaRespuestaAprobador=" + fechaRespuestaAprobador +
				", estado='" + estado + '\'' +
				", indActivoAprobacion='" + indActivoAprobacion + '\'' +
				'}';
	}
}