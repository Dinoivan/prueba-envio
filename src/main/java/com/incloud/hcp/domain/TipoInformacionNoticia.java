package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;


/**
 * The persistent class for the tipo_informacion_noticia database table.
 * 
 */
@Entity
@Table(name="tipo_informacion_noticia")
public class TipoInformacionNoticia extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_informacion_noticia", unique=true, nullable=false)
	@GeneratedValue(generator = "tipo_informacion_noticia_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "tipo_informacion_noticia_id_seq", sequenceName = "tipo_informacion_noticia_id_seq", allocationSize = 1)
	private Integer idTipoInformacionNoticia;

	@Column(nullable=false, length=100)
	private String descripcion;

	@Column(name = "icon_text", length = 50)
	private String iconText;

	@Column(name="carpeta_id", length=100)
	private String carpetaId;

	public String getIconText() {
		return iconText;
	}

	public void setIconText(String iconText) {
		this.iconText = iconText;
	}

	public TipoInformacionNoticia() {
	}

	public Integer getIdTipoInformacionNoticia() {
		return this.idTipoInformacionNoticia;
	}

	public void setIdTipoInformacionNoticia(Integer idTipoInformacionNoticia) {
		this.idTipoInformacionNoticia = idTipoInformacionNoticia;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCarpetaId() {
		return carpetaId;
	}

	public void setCarpetaId(String carpetaId) {
		this.carpetaId = carpetaId;
	}

	@Override
	public String toString() {
		return "TipoInformacionNoticia{" +
				"idTipoInformacionNoticia=" + idTipoInformacionNoticia +
				", descripcion='" + descripcion + '\'' +
				", iconText='" + iconText + '\'' +
				", carpetaId='" + carpetaId + '\'' +
				'}';
	}
}