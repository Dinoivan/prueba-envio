package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the criterios_blacklist database table.
 * 
 */
@Entity
@Table(name="criterios_blacklist")
public class CriteriosBlacklist extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_criterio", unique=true, nullable=false)
	@GeneratedValue(generator = "criterios_blacklist_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "criterios_blacklist_id_seq", sequenceName = "criterios_blacklist_id_seq", allocationSize = 1)
	private Integer idCriterio;

	@Column(nullable=false, length=250)
	private String descripcion;

	@Column(name="fecha_creacion", nullable=false)
	private Timestamp fechaCreacion;

	@Column(name="fecha_modificacion")
	private Timestamp fechaModificacion;

	@Column(name="usuario_creacion", nullable=false)
	private String  usuarioCreacion;

	@Column(name="usuario_modificacion")
	private String usuarioModificacion;

	//uni-directional many-to-one association to TipoSolicitudBlacklist
	@ManyToOne
	@JoinColumn(name="id_tipo_solicitud", nullable=false)
	private TipoSolicitudBlacklist tipoSolicitudBlacklist;

	public CriteriosBlacklist() {
	}

	public Integer getIdCriterio() {
		return this.idCriterio;
	}

	public void setIdCriterio(Integer idCriterio) {
		this.idCriterio = idCriterio;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Timestamp getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Timestamp getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getUsuarioCreacion() {
		return this.usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public String getUsuarioModificacion() {
		return this.usuarioModificacion;
	}

	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public TipoSolicitudBlacklist getTipoSolicitudBlacklist() {
		return this.tipoSolicitudBlacklist;
	}

	public void setTipoSolicitudBlacklist(TipoSolicitudBlacklist tipoSolicitudBlacklist) {
		this.tipoSolicitudBlacklist = tipoSolicitudBlacklist;
	}

	@Override
	public String toString() {
		return "CriteriosBlacklist{" +
				"idCriterio=" + idCriterio +
				", descripcion='" + descripcion + '\'' +
				", fechaCreacion=" + fechaCreacion +
				", fechaModificacion=" + fechaModificacion +
				", usuarioCreacion='" + usuarioCreacion + '\'' +
				", usuarioModificacion='" + usuarioModificacion + '\'' +
				", tipoSolicitudBlacklist=" + tipoSolicitudBlacklist +
				'}';
	}
}