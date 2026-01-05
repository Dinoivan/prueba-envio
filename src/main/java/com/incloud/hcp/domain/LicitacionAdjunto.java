package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;


/**
 * The persistent class for the licitacion_adjunto database table.
 * 
 */
@Entity
@Table(name="licitacion_adjunto")
public class LicitacionAdjunto extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_licitacion_adjunto", unique=true, nullable=false)
	@GeneratedValue(generator = "licitacion_adjunto_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "licitacion_adjunto_id_seq", sequenceName = "licitacion_adjunto_id_seq", allocationSize = 1)
	private Integer idLicitacionAdjunto;

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

	//Nuevo
	@Transient
	private String carpetaId; //Campo transitorio para uso temporal (no se persiste)


	//bi-directional many-to-one association to Licitacion
	@ManyToOne
	@JoinColumn(name="id_licitacion", nullable=false)
	private Licitacion licitacion;

	public LicitacionAdjunto() {
	}

	public LicitacionAdjunto(Licitacion licitacion, String rutaAdjunto, String archivoId, String archivoNombre, String archivoTipo) {
		this.licitacion = licitacion;
		this.rutaAdjunto = rutaAdjunto;
		this.archivoId = archivoId;
		this.archivoNombre = archivoNombre;
		this.archivoTipo = archivoTipo;
	}

	public Integer getIdLicitacionAdjunto() {
		return this.idLicitacionAdjunto;
	}

	public void setIdLicitacionAdjunto(Integer idLicitacionAdjunto) {
		this.idLicitacionAdjunto = idLicitacionAdjunto;
	}

	public String getRutaAdjunto() {
		return this.rutaAdjunto;
	}

	public void setRutaAdjunto(String rutaAdjunto) {
		this.rutaAdjunto = rutaAdjunto;
	}

	public Licitacion getLicitacion() {
		return this.licitacion;
	}

	public void setLicitacion(Licitacion licitacion) {
		this.licitacion = licitacion;
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

	public String getCarpetaId(){
		return carpetaId;
	}

	public void setCarpetaId(String carpetaId){
		this.carpetaId = carpetaId;
	}

	@Override
	public String toString() {
		return "LicitacionAdjunto{" +
				"idLicitacionAdjunto=" + idLicitacionAdjunto +
				", rutaAdjunto='" + rutaAdjunto + '\'' +
				", descripcion='" + descripcion + '\'' +
				", archivoId='" + archivoId + '\'' +
				", archivoNombre='" + archivoNombre + '\'' +
				", archivoTipo='" + archivoTipo + '\'' +
				", carpetaId='" + carpetaId + '\'' +
				", licitacion=" + licitacion +
				'}';
	}
}