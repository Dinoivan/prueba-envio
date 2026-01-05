package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the log_app database table.
 * 
 */
@Entity
@Table(name="log_app")
public class LogApp extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_log", unique=true, nullable=false)
	@GeneratedValue(generator = "log_app_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "log_app_id_seq", sequenceName = "log_app_id_seq", allocationSize = 1)
	private Integer idLog;

	@Column(name="codigo_mensaje", length=10)
	private String codigoMensaje;

	@Column(name="fecha_log", nullable=false)
	private Timestamp fechaLog;

	@Column(name="mensaje_descripcion", length=100)
	private String mensajeDescripcion;

	@Column(name="tipo_proceso", nullable=false, length=50)
	private String tipoProceso;

	public LogApp() {
	}

	public Integer getIdLog() {
		return this.idLog;
	}

	public void setIdLog(Integer idLog) {
		this.idLog = idLog;
	}

	public String getCodigoMensaje() {
		return this.codigoMensaje;
	}

	public void setCodigoMensaje(String codigoMensaje) {
		this.codigoMensaje = codigoMensaje;
	}

	public Timestamp getFechaLog() {
		return this.fechaLog;
	}

	public void setFechaLog(Timestamp fechaLog) {
		this.fechaLog = fechaLog;
	}

	public String getMensajeDescripcion() {
		return this.mensajeDescripcion;
	}

	public void setMensajeDescripcion(String mensajeDescripcion) {
		this.mensajeDescripcion = mensajeDescripcion;
	}

	public String getTipoProceso() {
		return this.tipoProceso;
	}

	public void setTipoProceso(String tipoProceso) {
		this.tipoProceso = tipoProceso;
	}

	@Override
	public String toString() {
		return "LogApp{" +
				"idLog=" + idLog +
				", codigoMensaje='" + codigoMensaje + '\'' +
				", fechaLog=" + fechaLog +
				", mensajeDescripcion='" + mensajeDescripcion + '\'' +
				", tipoProceso='" + tipoProceso + '\'' +
				'}';
	}
}