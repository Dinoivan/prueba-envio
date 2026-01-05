package com.incloud.hcp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the parametros database table.
 * 
 */
@Entity
@Table(name="parametros")
public class Parametro extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_parametro", unique=true, nullable=false)
	@GeneratedValue(generator = "parametros_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "parametros_id_seq", sequenceName = "parametros_id_seq", allocationSize = 1)
	private Integer idParametro;

	@Column(nullable=false, length=10)
	private String codigo;

	@Column(length=250)
	private String descripcion;

	@Column(name="fecha_creacion", nullable=false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private Timestamp fechaCreacion;

	@Column(name="fecha_modificacion")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private Timestamp fechaModificacion;

	@Column(nullable=false, length=50)
	private String modulo;

	@Column(nullable=false, length=30)
	private String tipo;

	@Column(name="usuario_creacion", nullable=false)
	private Integer usuarioCreacion;

	@Column(name="usuario_modificacion")
	private Integer usuarioModificacion;

	@Column(nullable=false, length=200)
	private String valor;

	private String valorAsociado;

	public Parametro() {
	}

	public Integer getIdParametro() {
		return this.idParametro;
	}

	public void setIdParametro(Integer idParametro) {
		this.idParametro = idParametro;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	public String getModulo() {
		return this.modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}



	// -- [valorAsociado] ------------------------

	@Size(max = 200)
	@Column(name = "valor_asociado", length = 200)
	public String getValorAsociado() {
		return valorAsociado;
	}

	public void setValorAsociado(String valorAsociado) {
		this.valorAsociado = valorAsociado;
	}

	public Parametro valorAsociado(String valorAsociado) {
		setValorAsociado(valorAsociado);
		return this;
	}


	@Override
	public String toString() {
		return "Parametro{" +
				"idParametro=" + idParametro +
				", codigo='" + codigo + '\'' +
				", descripcion='" + descripcion + '\'' +
				", fechaCreacion=" + fechaCreacion +
				", fechaModificacion=" + fechaModificacion +
				", modulo='" + modulo + '\'' +
				", tipo='" + tipo + '\'' +
				", usuarioCreacion=" + usuarioCreacion +
				", usuarioModificacion=" + usuarioModificacion +
				", valor='" + valor + '\'' +
				", valorAsociado='" + valorAsociado + '\'' +
				'}';
	}
}