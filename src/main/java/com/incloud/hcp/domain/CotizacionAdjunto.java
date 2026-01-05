package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the cotizacion_adjunto database table.
 * 
 */
@Entity
@Table(name="cotizacion_adjunto")
public class CotizacionAdjunto extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_cotizacion_adjunto", unique=true, nullable=false)
	@GeneratedValue(generator = "cotizacion_adjunto_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "cotizacion_adjunto_id_seq", sequenceName = "cotizacion_adjunto_id_seq", allocationSize = 1)
	private Integer idCotizacionAdjunto;

	@Column(name="ind_cotizacion", length=1)
	private String indCotizacion;

	@Column(name="ruta_adjunto", nullable=false, length=1000)
	private String rutaAdjunto;

	@Column(name="descripcion", nullable=true, length=100)
	private String descripcion;

	@Column(name="archivo_id", nullable=true, length=100)
	private String archivoId;

	@Column(name="archivo_nombre", nullable=true, length=100)
	private String archivoNombre;

	@Column(name="archivo_tipo", nullable=true, length=100)
	private String archivoTipo;


	//uni-directional many-to-one association to Cotizacion
	@ManyToOne
	@JoinColumn(name="id_cotizacion", nullable=false)
	private Cotizacion cotizacion;



	public CotizacionAdjunto() {
	}

	public CotizacionAdjunto(Cotizacion cotizacion,String rutaAdjunto, String archivoId, String archivoNombre, String archivoTipo) {

		this.rutaAdjunto = rutaAdjunto;
		this.archivoId = archivoId;
		this.archivoNombre = archivoNombre;
		this.archivoTipo = archivoTipo;
		this.cotizacion = cotizacion;
	}

	public Integer getIdCotizacionAdjunto() {
		return this.idCotizacionAdjunto;
	}

	public void setIdCotizacionAdjunto(Integer idCotizacionAdjunto) {
		this.idCotizacionAdjunto = idCotizacionAdjunto;
	}

	public String getIndCotizacion() {
		return this.indCotizacion;
	}

	public void setIndCotizacion(String indCotizacion) {
		this.indCotizacion = indCotizacion;
	}

	public String getRutaAdjunto() {
		return this.rutaAdjunto;
	}

	public void setRutaAdjunto(String rutaAdjunto) {
		this.rutaAdjunto = rutaAdjunto;
	}

	public Cotizacion getCotizacion() {
		return this.cotizacion;
	}

	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public void setArchivoTipo(String archivoTipo) {
		this.archivoTipo = archivoTipo;
	}

	@Override
	public String toString() {
		return "CotizacionAdjunto{" +
				"idCotizacionAdjunto=" + idCotizacionAdjunto +
				", indCotizacion='" + indCotizacion + '\'' +
				", rutaAdjunto='" + rutaAdjunto + '\'' +
				", descripcion='" + descripcion + '\'' +
				", archivoId='" + archivoId + '\'' +
				", archivoNombre='" + archivoNombre + '\'' +
				", archivoTipo='" + archivoTipo + '\'' +
				", cotizacion=" + cotizacion +
				'}';
	}
}