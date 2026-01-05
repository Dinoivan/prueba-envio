package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the homologacion database table.
 * 
 */
@Entity
@Table(name="homologacion")
public class Homologacion extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_homologacion", unique=true, nullable=false)
	@GeneratedValue(generator = "homologacion_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "homologacion_id_seq", sequenceName = "homologacion_id_seq", allocationSize = 1)
	private Integer idHomologacion;

	@Column(name="fecha_creacion", nullable=false)
	private Date fechaCreacion;

	@Column(name="fecha_modificacion")
	private Date fechaModificacion;

	@Column(name="ind_adjunto", nullable=false, length=1)
	private String indAdjunto;

	@Column(nullable=false, precision=8, scale=2)
	private BigDecimal peso;

	@Column(nullable=false, length=1000)
	private String pregunta;

	@Column(name="usuario_creacion", nullable=false)
	private Integer usuarioCreacion;

	@Column(name="usuario_modificacion")
	private Integer usuarioModificacion;

	@Column(name = "estado", nullable = false, length = 1)
	private String estado;

	@Column(name="ind_adjunto_general", length=1)
	private String indAdjuntoGeneral;



	//uni-directional many-to-one association to LineaComercial
	@ManyToOne
	@JoinColumn(name="id_linea_comercial", nullable=false)
	private LineaComercial lineaComercial;

	public Homologacion() {
	}

	public Integer getIdHomologacion() {
		return idHomologacion;
	}

	public void setIdHomologacion(Integer idHomologacion) {
		this.idHomologacion = idHomologacion;
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

	public String getIndAdjunto() {
		return indAdjunto;
	}

	public void setIndAdjunto(String indAdjunto) {
		this.indAdjunto = indAdjunto;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public Integer getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(Integer usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Integer getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(Integer usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public LineaComercial getLineaComercial() {
		return lineaComercial;
	}

	public void setLineaComercial(LineaComercial lineaComercial) {
		this.lineaComercial = lineaComercial;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getIndAdjuntoGeneral() {
		return indAdjuntoGeneral;
	}

	public void setIndAdjuntoGeneral(String indAdjuntoGeneral) {
		this.indAdjuntoGeneral = indAdjuntoGeneral;
	}

	@Override
	public String toString() {
		return "Homologacion{" +
				"idHomologacion=" + idHomologacion +
				", fechaCreacion=" + fechaCreacion +
				", fechaModificacion=" + fechaModificacion +
				", indAdjunto='" + indAdjunto + '\'' +
				", peso=" + peso +
				", pregunta='" + pregunta + '\'' +
				", usuarioCreacion=" + usuarioCreacion +
				", usuarioModificacion=" + usuarioModificacion +
				", estado='" + estado + '\'' +
				", indAdjuntoGeneral='" + indAdjuntoGeneral + '\'' +
				", lineaComercial=" + lineaComercial +
				'}';
	}
}