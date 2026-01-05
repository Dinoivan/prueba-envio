package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.io.Serializable;


/**
 * The persistent class for the proveedor_homologacion database table.
 * 
 */
@Entity
@Table(name="proveedor_homologacion")
public class ProveedorHomologacion extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_proveedor_homologacion", unique=true, nullable=false)
	@GeneratedValue(generator = "proveedor_homologacion_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "proveedor_homologacion_id_seq", sequenceName = "proveedor_homologacion_id_seq", allocationSize = 1)
	private Integer idProveedorHomologacion;

	//uni-directional many-to-one association to Proveedor
	@ManyToOne
	@JoinColumn(name="id_proveedor", nullable=false)
	private Proveedor proveedor;

	//uni-directional many-to-one association to Homologacion
	@ManyToOne
	@JoinColumn(name="id_homologacion", nullable=false)
	private Homologacion homologacion;

	//uni-directional many-to-one association to HomologacionRespuesta
	@ManyToOne
	@JoinColumn(name="id_homologacion_respuesta")
	private HomologacionRespuesta homologacionRespuesta;

	@Column(name="ruta_adjunto", length=300)
	private String rutaAdjunto;

	@Column(name="archivo_id", length=60)
	private String archivoId;

	@Column(name="archivo_nombre", length=100)
	private String archivoNombre;

	@Column(name="archivo_tipo", length=100)
	private String archivoTipo;

	private String valorRespuestaLibre;


	public ProveedorHomologacion() {
	}

	public Integer getIdProveedorHomologacion() {
		return this.idProveedorHomologacion;
	}

	public void setIdProveedorHomologacion(Integer idProveedorHomologacion) {
		this.idProveedorHomologacion = idProveedorHomologacion;
	}

	public String getRutaAdjunto() {
		return rutaAdjunto;
	}

	public void setRutaAdjunto(String rutaAdjunto) {
		this.rutaAdjunto = rutaAdjunto;
	}

	public Homologacion getHomologacion() {
		return this.homologacion;
	}

	public void setHomologacion(Homologacion homologacion) {
		this.homologacion = homologacion;
	}

	public HomologacionRespuesta getHomologacionRespuesta() {
		return this.homologacionRespuesta;
	}

	public void setHomologacionRespuesta(HomologacionRespuesta homologacionRespuesta) {
		this.homologacionRespuesta = homologacionRespuesta;
	}

	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public String getArchivoId() {
		return archivoId;
	}

	public void setArchivoId(String archivoId) {
		this.archivoId = archivoId;
	}

	public String getArchivoNombre() {
		return archivoNombre;
	}

	public void setArchivoNombre(String archivoNombre) {
		this.archivoNombre = archivoNombre;
	}

	public String getArchivoTipo() {
		return archivoTipo;
	}

	// -- [valorRespuestaLibre] ------------------------

	@Size(max = 300)
	@Column(name = "valor_respuesta_libre", length = 300)
	public String getValorRespuestaLibre() {
		return valorRespuestaLibre;
	}

	public void setValorRespuestaLibre(String valorRespuestaLibre) {
		this.valorRespuestaLibre = valorRespuestaLibre;
	}

	public ProveedorHomologacion valorRespuestaLibre(String valorRespuestaLibre) {
		setValorRespuestaLibre(valorRespuestaLibre);
		return this;
	}


	@Override
	public String toString() {
		return "ProveedorHomologacion{" +
				"idProveedorHomologacion=" + idProveedorHomologacion +
				", proveedor=" + proveedor +
				", homologacion=" + homologacion +
				", homologacionRespuesta=" + homologacionRespuesta +
				", rutaAdjunto='" + rutaAdjunto + '\'' +
				", archivoId='" + archivoId + '\'' +
				", archivoNombre='" + archivoNombre + '\'' +
				", archivoTipo='" + archivoTipo + '\'' +
				", valorRespuestaLibre='" + valorRespuestaLibre + '\'' +
				'}';
	}
}