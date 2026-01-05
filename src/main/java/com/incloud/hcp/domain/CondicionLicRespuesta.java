package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the condicion_lic_respuesta database table.
 * 
 */
@Entity
@Table(name="condicion_lic_respuesta")
public class CondicionLicRespuesta extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_condicion_respuesta", unique=true, nullable=false)
	@GeneratedValue(generator = "condicion_lic_respuesta_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "condicion_lic_respuesta_id_seq", sequenceName = "condicion_lic_respuesta_id_seq", allocationSize = 1)
	private Integer idCondicionRespuesta;

	@Column(name="fecha_creacion", nullable=false)
	private Timestamp fechaCreacion;

	@Column(name="fecha_modificacion")
	private Timestamp fechaModificacion;

	@Column(name="ind_predefinido", length=1)
	private String indPredefinido;

	@Column(nullable=false, length=30)
	private String opcion;

	@Column(name="puntaje", nullable=false, precision=15, scale=2)
	private BigDecimal puntaje;

	@Column(name="usuario_creacion", nullable=false)
	private String usuarioCreacion;

	@Column(name="usuario_modificacion")
	private String usuarioModificacion;

	//uni-directional many-to-one association to CondicionLic
	@ManyToOne
	@JoinColumn(name="id_condicion", nullable=false, insertable = false, updatable = false)
	private CondicionLic condicionLic;

	@Column(name="id_condicion")
	private Integer idCondicionLic;

	public CondicionLicRespuesta() {
	}

	public Integer getIdCondicionRespuesta() {
		return this.idCondicionRespuesta;
	}

	public void setIdCondicionRespuesta(Integer idCondicionRespuesta) {
		this.idCondicionRespuesta = idCondicionRespuesta;
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

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public CondicionLic getCondicionLic() {
		return this.condicionLic;
	}

	public void setCondicionLic(CondicionLic condicionLic) {
		this.condicionLic = condicionLic;
	}

	public Integer getIdCondicionLic() {
		return idCondicionLic;
	}

	public void setIdCondicionLic(Integer idCondicionLic) {
		this.idCondicionLic = idCondicionLic;
	}

	@Override
	public String toString() {
		return "CondicionLicRespuesta{" +
				"idCondicionRespuesta=" + idCondicionRespuesta +
				", fechaCreacion=" + fechaCreacion +
				", fechaModificacion=" + fechaModificacion +
				", indPredefinido='" + indPredefinido + '\'' +
				", opcion='" + opcion + '\'' +
				", puntaje=" + puntaje +
				", usuarioCreacion='" + usuarioCreacion + '\'' +
				", usuarioModificacion='" + usuarioModificacion + '\'' +
				", condicionLic=" + condicionLic +
				", idCondicionLic=" + idCondicionLic +
				'}';
	}
}