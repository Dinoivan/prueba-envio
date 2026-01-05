package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the homologacion_respuesta database table.
 * 
 */
@Entity
@Table(name="homologacion_respuesta")
public class HomologacionRespuesta extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_homologacion_respuesta", unique=true, nullable=false)
	@GeneratedValue(generator = "homologacion_respuesta_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "homologacion_respuesta_id_seq", sequenceName = "homologacion_respuesta_id_seq", allocationSize = 1)
	private Integer idHomologacionRespuesta;

	@Column(name="fecha_creacion", nullable=false)
	private Date fechaCreacion;

	@Column(name="fecha_modificacion")
	private Date fechaModificacion;

	@Column(name="nro_orden", nullable=false, length=10)
	private String nroOrden;

	@Column(nullable=false, precision=8, scale=2)
	private BigDecimal puntaje;

	@Column(nullable=false, length=30)
	private String respuesta;

	@Column(name="usuario_creacion", nullable=false)
	private Integer usuarioCreacion;

	@Column(name="usuario_modificacion")
	private Integer usuarioModificacion;

	//uni-directional many-to-one association to Homologacion
	@ManyToOne
	@JoinColumn(name="id_homologacion", nullable=false)
	private Homologacion homologacion;

	public HomologacionRespuesta() {
	}

	public Integer getIdHomologacionRespuesta() {
		return this.idHomologacionRespuesta;
	}

	public void setIdHomologacionRespuesta(Integer idHomologacionRespuesta) {
		this.idHomologacionRespuesta = idHomologacionRespuesta;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getNroOrden() {
		return this.nroOrden;
	}

	public void setNroOrden(String nroOrden) {
		this.nroOrden = nroOrden;
	}

	public BigDecimal getPuntaje() {
		return this.puntaje;
	}

	public void setPuntaje(BigDecimal puntaje) {
		this.puntaje = puntaje;
	}

	public String getRespuesta() {
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Integer getUsuarioCreacion() {
		return this.usuarioCreacion;
	}

	public void setUsuarioCreacion(Integer usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Integer getUsuarioModificacion() {
		return this.usuarioModificacion;
	}

	public void setUsuarioModificacion(Integer usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Homologacion getHomologacion() {
		return this.homologacion;
	}

	public void setHomologacion(Homologacion homologacion) {
		this.homologacion = homologacion;
	}

	@Override
	public String toString() {
		return "HomologacionRespuesta{" +
				"idHomologacionRespuesta=" + idHomologacionRespuesta +
				", fechaCreacion=" + fechaCreacion +
				", fechaModificacion=" + fechaModificacion +
				", nroOrden='" + nroOrden + '\'' +
				", puntaje=" + puntaje +
				", respuesta='" + respuesta + '\'' +
				", usuarioCreacion=" + usuarioCreacion +
				", usuarioModificacion=" + usuarioModificacion +
				", homologacion=" + homologacion +
				'}';
	}
}