package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the solicitud_blacklist database table.
 * 
 */
@Entity
@Table(name="solicitud_blacklist")
public class SolicitudBlacklist extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_solicitud", unique=true, nullable=false)
	@GeneratedValue(generator = "solicitud_blacklist_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "solicitud_blacklist_id_seq", sequenceName = "solicitud_blacklist_id_seq", allocationSize = 1)
	private Integer idSolicitud;

	@Column(name="codigo_solicitud", nullable=false, length=13)
	private String codigoSolicitud;

	@Column(nullable=false, length=250)
	private String sede;

	@Column(nullable=false, length=500)
	private String motivo;

	//uni-directional many-to-one association to CriteriosBlacklist
	@ManyToOne
	@JoinColumn(name="id_criterio", nullable=false)
	private CriteriosBlacklist criteriosBlacklist;

	//uni-directional many-to-one association to Proveedor
	@ManyToOne
	@JoinColumn(name="id_proveedor", nullable=false)
	private Proveedor proveedor;

	@Column(name="estado_solicitud", nullable=false, length=2)
	private String estadoSolicitud;

	@Column(name="admin_id_revision", nullable=true, length=30)
	private String adminIdRevision;

	@Column(name="admin_name_revision", nullable=true, length=50)
	private String adminNameRevision;

	@Column(name="admin_fecha_revision", nullable=true)
	private Timestamp adminFechaRevision;

	@Column(name="regla_aprobacion", nullable=true, length=2)
	private String reglaAprobacion;

	@Column(name="ind_rechazo_admin", nullable=true, length=1)
	private String indRechazoAdmin;

	@Column(name="motivo_rechazo_admin", nullable=true, length=500)
	private String motivoRechazoAdmin;

	@Column(name="fecha_creacion", nullable=false)
	private Timestamp fechaCreacion;

	@Column(name="usuario_creacion", nullable=false)
	private String usuarioCreacion;

	@Column(name="usuario_creacion_name", nullable=true, length=50)
	private String usuarioCreacionName;

	@Column(name="usuario_creacion_email", nullable=true, length=50)
	private String usuarioCreacionEmail;

	@Column(name="fecha_modificacion")
	private Timestamp fechaModificacion;

	@Column(name="usuario_modificacion")
	private String usuarioModificacion;

	@Transient
	private List<AprobadorSolicitud> listAprobador;

	@Transient
	private List<SolicitudAdjuntoBlacklist> listAdjunto;


	public SolicitudBlacklist() {
	}

	public Integer getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getCodigoSolicitud() {
		return codigoSolicitud;
	}

	public void setCodigoSolicitud(String codigoSolicitud) {
		this.codigoSolicitud = codigoSolicitud;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public CriteriosBlacklist getCriteriosBlacklist() {
		return criteriosBlacklist;
	}

	public void setCriteriosBlacklist(CriteriosBlacklist criteriosBlacklist) {
		this.criteriosBlacklist = criteriosBlacklist;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public String getAdminIdRevision() {
		return adminIdRevision;
	}

	public void setAdminIdRevision(String adminIdRevision) {
		this.adminIdRevision = adminIdRevision;
	}

	public String getAdminNameRevision() {
		return adminNameRevision;
	}

	public void setAdminNameRevision(String adminNameRevision) {
		this.adminNameRevision = adminNameRevision;
	}

	public Timestamp getAdminFechaRevision() {
		return adminFechaRevision;
	}

	public void setAdminFechaRevision(Timestamp adminFechaRevision) {
		this.adminFechaRevision = adminFechaRevision;
	}

	public String getReglaAprobacion() {
		return reglaAprobacion;
	}

	public void setReglaAprobacion(String reglaAprobacion) {
		this.reglaAprobacion = reglaAprobacion;
	}

	public String getIndRechazoAdmin() {
		return indRechazoAdmin;
	}

	public void setIndRechazoAdmin(String indRechazoAdmin) {
		this.indRechazoAdmin = indRechazoAdmin;
	}

	public String getMotivoRechazoAdmin() {
		return motivoRechazoAdmin;
	}

	public void setMotivoRechazoAdmin(String motivoRechazoAdmin) {
		this.motivoRechazoAdmin = motivoRechazoAdmin;
	}

	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public String getUsuarioCreacionName() {
		return usuarioCreacionName;
	}

	public void setUsuarioCreacionName(String usuarioCreacionName) {
		this.usuarioCreacionName = usuarioCreacionName;
	}

	public String getUsuarioCreacionEmail() {
		return usuarioCreacionEmail;
	}

	public void setUsuarioCreacionEmail(String usuarioCreacionEmail) {
		this.usuarioCreacionEmail = usuarioCreacionEmail;
	}

	public Timestamp getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public List<AprobadorSolicitud> getListAprobador() {
		return listAprobador;
	}

	public void setListAprobador(List<AprobadorSolicitud> listAprobador) {
		this.listAprobador = listAprobador;
	}

	public List<SolicitudAdjuntoBlacklist> getListAdjunto() {
		return listAdjunto;
	}

	public void setListAdjunto(List<SolicitudAdjuntoBlacklist> listAdjunto) {
		this.listAdjunto = listAdjunto;
	}

	@Override
	public String toString() {
		return "SolicitudBlacklist{" +
				"idSolicitud=" + idSolicitud +
				", codigoSolicitud='" + codigoSolicitud + '\'' +
				", sede='" + sede + '\'' +
				", motivo='" + motivo + '\'' +
				", criteriosBlacklist=" + criteriosBlacklist +
				", proveedor=" + proveedor +
				", estadoSolicitud='" + estadoSolicitud + '\'' +
				", adminIdRevision='" + adminIdRevision + '\'' +
				", adminNameRevision='" + adminNameRevision + '\'' +
				", adminFechaRevision=" + adminFechaRevision +
				", reglaAprobacion='" + reglaAprobacion + '\'' +
				", indRechazoAdmin='" + indRechazoAdmin + '\'' +
				", motivoRechazoAdmin='" + motivoRechazoAdmin + '\'' +
				", fechaCreacion=" + fechaCreacion +
				", usuarioCreacion='" + usuarioCreacion + '\'' +
				", usuarioCreacionName='" + usuarioCreacionName + '\'' +
				", usuarioCreacionEmail='" + usuarioCreacionEmail + '\'' +
				", fechaModificacion=" + fechaModificacion +
				", usuarioModificacion='" + usuarioModificacion + '\'' +
				", listAprobador=" + listAprobador +
				", listAdjunto=" + listAdjunto +
				'}';
	}
}