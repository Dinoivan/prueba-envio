package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario")
public class Usuario extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_usuario", unique=true, nullable=false)
	@GeneratedValue(generator = "usuario_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "usuario_id_seq", sequenceName = "usuario_id_seq", allocationSize = 1)
	private Integer idUsuario;

	@Column(name="codigo_sap", nullable=true, length=20)
	private String codigoSap;

	@Column(nullable=false, length=50)
	private String email;

	@Column(name="fecha_creacion", nullable=false)
	private Timestamp fechaCreacion;

	@Column(name="fecha_modificacion")
	private Timestamp fechaModificacion;

	@Column(nullable=false, length=50)
	private String nombre;

	@Column(nullable=false, length=100)
	private String apellido;

	@Column(name="usuario_creacion", nullable=false)
	private Integer usuarioCreacion;

	@Column(name="usuario_modificacion")
	private Integer usuarioModificacion;

	@Column(name="codigo_usuario_idp", length=20)
	private String codigoUsuarioIdp;

	@Column(name="codigo_empleado_sap",length=20)
	private String codigoEmpleadoSap;

	@ManyToOne
	@JoinColumn(name="id_cargo")
	private Cargo cargo;



	public Usuario() {
	}

	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getCodigoSap() {
		return this.codigoSap;
	}

	public void setCodigoSap(String codigoSap) {
		this.codigoSap = codigoSap;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
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

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	// -- [codigoUsuarioIdp] ------------------------
	public String getCodigoUsuarioIdp() {
		return codigoUsuarioIdp;
	}

	public void setCodigoUsuarioIdp(String codigoUsuarioIdp) {
		this.codigoUsuarioIdp = codigoUsuarioIdp;
	}

	public Usuario codigoUsuarioIdp(String codigoUsuarioIdp) {
		setCodigoUsuarioIdp(codigoUsuarioIdp);
		return this;
	}

	public String getCodigoEmpleadoSap() {
		return codigoEmpleadoSap;
	}

	public void setCodigoEmpleadoSap(String codigoEmpleadoSap) {
		this.codigoEmpleadoSap = codigoEmpleadoSap;
	}

	@Override
	public String toString() {
		return "Usuario{" +
				"idUsuario=" + idUsuario +
				", codigoSap='" + codigoSap + '\'' +
				", email='" + email + '\'' +
				", fechaCreacion=" + fechaCreacion +
				", fechaModificacion=" + fechaModificacion +
				", nombre='" + nombre + '\'' +
				", apellido='" + apellido + '\'' +
				", usuarioCreacion=" + usuarioCreacion +
				", usuarioModificacion=" + usuarioModificacion +
				", codigoUsuarioIdp='" + codigoUsuarioIdp + '\'' +
				", codigoEmpleadoSap='" + codigoEmpleadoSap + '\'' +
				", cargo=" + cargo +
				'}';
	}
}